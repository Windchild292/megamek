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
 * implements bv-ratio victory checking ratio is defined as
 * (friendly BV / enemy BC) > (BV Ratio Percent / 100) = win so this comparison is valid for
 * 3 team combat, but you must drop ALL enemies combined to below given ratio.
 * if multiple players reach this goal at the same time, the result is declared
 * a draw
 * Note: this could be improved to take into account ratios which exceed given ratio
 */
public class BVRatioVictory extends AbstractBVVictory {
    //region Variable Declarations
    private static final long serialVersionUID = -6622529899835634696L;
    private int ratio;
    //endregion Variable Declarations

    //region Constructors
    public BVRatioVictory(int ratio) {
        super("BVRatioVictory.title");
        setRatio(ratio);
    }
    //endregion Constructors

    //region Getters/Setters
    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
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
                    continue;
                }
                doneTeams.add(team);
            }
            int friendlyBV = player.getTeamObject().getBV();
            int enemyBV = getEnemyBV(game, player);

            if ((enemyBV == 0) || ((100 * friendlyBV) / enemyBV >= getRatio())) {
                victoryResult.setVictory(true);
                Report report = new Report(7100, Report.PUBLIC);
                if (player.getTeam() == IPlayer.TEAM_NONE) {
                    report.add(player.getName());
                    victoryResult.addPlayerScore(player.getId(), 1.0);
                } else {
                    report.add(player.getTeamObject().toString());
                    victoryResult.addTeamScore(player.getTeam(), 1.0);
                }
                report.add((enemyBV == 0) ? 9999 : (100 * friendlyBV) / enemyBV);
                victoryResult.getReports().add(report);
                return victoryResult;
            }
        }
        return victoryResult;
    }
}
