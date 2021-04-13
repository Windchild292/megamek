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
package megamek.server.victory.victoryConditions;

import java.util.HashSet;
import java.util.Set;

import megamek.common.IGame;
import megamek.common.IPlayer;
import megamek.common.Report;
import megamek.server.victory.VictoryResult;

/**
 * Implementation which will match when a certain percentage of all enemy BV is destroyed.
 *
 * Note: this could be improved by giving more points for killing more than required amount
 */
public class BVDestroyedVictory extends AbstractBVVictory {
    //region Variable Declarations
    private static final long serialVersionUID = -1807333576570154144L;
    private int destroyedPercent;
    //endregion Variable Declarations

    //region Constructors
    public BVDestroyedVictory(int destroyedPercent) {
        super("BVDestroyedVictory.title");
        setDestroyedPercent(destroyedPercent);
    }
    //endregion Constructors

    //region Getters/Setters
    public int getDestroyedPercent() {
        return destroyedPercent;
    }

    public void setDestroyedPercent(int destroyedPercent) {
        this.destroyedPercent = destroyedPercent;
    }
    //endregion Getters/Setters

    @Override
    public VictoryResult victory(IGame game) {
        VictoryResult victoryResult = new VictoryResult(false);
        // now check for detailed victory conditions...
        Set<Integer> doneTeams = new HashSet<>();
        for (IPlayer player : game.getPlayersVector()) {
            if (player.isObserver()) {
                continue;
            }
            int team = player.getTeam();
            if (team != IPlayer.TEAM_NONE) {
                if (doneTeams.contains(team)) {
                    continue; // skip if already
                }
                doneTeams.add(team);
            }
            int enemyBV = getEnemyBV(game, player);
            int initialEnemyBV = getEnemyInitialBV(game, player);

            if ((initialEnemyBV != 0)
                    && (((enemyBV * 100) / initialEnemyBV) <= (100 - getDestroyedPercent()))) {
                victoryResult.setVictory(true);
                Report report = new Report(7105, Report.PUBLIC);
                if (player.getTeam() == IPlayer.TEAM_NONE) {
                    report.add(player.getName());
                    victoryResult.addPlayerScore(player.getId(), 1.0);
                } else {
                    report.add(player.getTeamObject().toString());
                    victoryResult.addTeamScore(player.getTeam(), 1.0);
                }
                report.add(100 - ((enemyBV * 100) / initialEnemyBV));
                victoryResult.getReports().add(report);
                return victoryResult;
            }
        }
        return victoryResult;
    }
}
