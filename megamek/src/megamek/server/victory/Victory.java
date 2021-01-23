/*
 * Copyright (C) 2007-2008 Ben Mazur (bmazur@sev.org)
 * Copyright (c) 2021 - The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MegaMek. If not, see <http://www.gnu.org/licenses/>.
 */
package megamek.server.victory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import megamek.common.IGame;
import megamek.common.options.GameOptions;
import megamek.common.options.OptionsConstants;

public class Victory implements Serializable {
    //region Variable Declarations
    private static final long serialVersionUID = -8633873540471130320L;
    
    private boolean checkForVictory;
    private int neededVictoryConditionsCount;
    private List<AbstractVictoryCondition> victoryConditions;
    private AbstractVictoryCondition timeVictory;
    //endregion Variable Declarations

    //region Constructors
    public Victory(GameOptions options) {
        setCheckForVictory(options.booleanOption(OptionsConstants.VICTORY_CHECK_VICTORY));
        if (checkForVictory() && options.booleanOption(OptionsConstants.VICTORY_USE_GAME_TURN_LIMIT)) {
            setTimeVictory(new TimeVictory(options.intOption(OptionsConstants.VICTORY_GAME_TURN_LIMIT)));
        }
        buildVictoryConditionsList(options);
    }
    //endregion Constructors

    //region Getters/Setters
    public boolean checkForVictory() {
        return checkForVictory;
    }

    public void setCheckForVictory(boolean checkForVictory) {
        this.checkForVictory = checkForVictory;
    }

    public int getNeededVictoryConditionsCount() {
        return neededVictoryConditionsCount;
    }

    public void setNeededVictoryConditionsCount(int neededVictoryConditionsCount) {
        this.neededVictoryConditionsCount = neededVictoryConditionsCount;
    }

    public List<AbstractVictoryCondition> getVictoryConditions() {
        return victoryConditions;
    }

    public void setVictoryConditions(List<AbstractVictoryCondition> victoryConditions) {
        this.victoryConditions = victoryConditions;
    }

    public AbstractVictoryCondition getTimeVictory() {
        return timeVictory;
    }

    public void setTimeVictory(AbstractVictoryCondition timeVictory) {
        this.timeVictory = timeVictory;
    }
    //endregion Getters/Setters

    private void buildVictoryConditionsList(GameOptions options) {
        List<AbstractVictoryCondition> victoryConditions = new ArrayList<>();

        // Default Victory Conditions that cannot be disabled
        victoryConditions.add(new ForceVictory());
        victoryConditions.add(new LastManStandingVictory());

        if (checkForVictory()) {
            setNeededVictoryConditionsCount(options.intOption(OptionsConstants.VICTORY_ACHIEVE_CONDITIONS));

            // BV related victory conditions
            if (options.booleanOption(OptionsConstants.VICTORY_USE_BV_DESTROYED)) {
                victoryConditions.add(new BVDestroyedVictory(options.intOption(OptionsConstants.VICTORY_BV_DESTROYED_PERCENT)));
            }

            if (options.booleanOption(OptionsConstants.VICTORY_USE_BV_RATIO)) {
                victoryConditions.add(new BVRatioVictory(options.intOption(OptionsConstants.VICTORY_BV_RATIO_PERCENT)));
            }

            // Kill count victory condition
            if (options.booleanOption(OptionsConstants.VICTORY_USE_KILL_COUNT)) {
                victoryConditions.add(new KillCountVictory(options.intOption(OptionsConstants.VICTORY_GAME_KILL_COUNT)));
            }

            // Commander killed victory condition
            if (options.booleanOption(OptionsConstants.VICTORY_COMMANDER_KILLED)) {
                victoryConditions.add(new AllEnemyCommandersDestroyedVictory());
            }
        }

        setVictoryConditions(victoryConditions);
    }

    public VictoryResult checkForVictory(IGame game) {
        VictoryResult victoryResult = VictoryResult.noResult();
        // Check optional Victory conditions
        // These can have reports
        if (checkForVictory()) {
            victoryResult = checkOptionalVictory(game);
            if (victoryResult.victory()) {
                return victoryResult;
            }
        }

        return victoryResult;
    }

    private VictoryResult checkOptionalVictory(IGame game) {
        boolean victory = false;
        VictoryResult victoryResult = new VictoryResult(true);

        // combine scores
        for (AbstractVictoryCondition victoryCondition : getVictoryConditions()) {
            VictoryResult result = victoryCondition.victory(game);
            victoryResult.getReports().addAll(result.getReports());
            if (result.victory()) {
                victory = true;
            }
            for (Map.Entry<Integer, Double> player : result.getPlayerScores().entrySet()) {
                victoryResult.addPlayerScore(player.getKey(), victoryResult.getPlayerScore(player.getKey()) + player.getValue());
            }
            for (Map.Entry<Integer, Double> team : result.getTeamScores().entrySet()) {
                victoryResult.addTeamScore(team.getKey(), victoryResult.getTeamScore(team.getKey()) + team.getValue());
            }
        }
        // find the high score for thresholding, also divide the score to an average
        double highScore = 0.0;
        for (Map.Entry<Integer, Double> player : victoryResult.getPlayerScores().entrySet()) {
            victoryResult.addPlayerScore(player.getKey(), player.getValue() / getVictoryConditions().size());
            if (player.getValue() > highScore) {
                highScore = player.getValue();
            }
        }
        for (Map.Entry<Integer, Double> team : victoryResult.getTeamScores().entrySet()) {
            victoryResult.addTeamScore(team.getKey(), team.getValue() / getVictoryConditions().size());
            if (team.getValue() > highScore) {
                highScore = team.getValue();
            }
        }
        if (highScore < getNeededVictoryConditionsCount()) {
            victory = false;
        }

        victoryResult.setVictory(victory);

        if (victoryResult.victory()) {
            return victoryResult;
        }

        VictoryResult timeVictory = (getTimeVictory() != null) ? getTimeVictory().victory(game) : null;
        return ((timeVictory != null) && timeVictory.victory()) ? timeVictory : victoryResult;
    }
}
