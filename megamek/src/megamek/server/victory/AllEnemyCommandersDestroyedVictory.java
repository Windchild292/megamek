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

import java.util.HashSet;
import java.util.Set;

import megamek.common.IGame;
import megamek.common.IPlayer;
import megamek.common.Report;

/**
 * implements "enemy commander destroyed"
 */
public class AllEnemyCommandersDestroyedVictory extends AbstractVictoryCondition {
    //region Variable Declarations
    private static final long serialVersionUID = 2525190210964235691L;
    //endregion Variable Declarations

    //region Constructors
    public AllEnemyCommandersDestroyedVictory() {
        super("AllEnemyCommandersDestroyedVictory.title");
    }
    //endregion Constructors

    @Override
    public VictoryResult victory(IGame game) {
        VictoryResult victoryResult = new VictoryResult(false);
        // check all players/teams for killing enemy commanders
        // score is 1.0 when enemy commanders are dead
        Set<Integer> doneTeams = new HashSet<>();
        for (IPlayer player : game.getPlayersVector()) {
            boolean killedAll = true;
            int team = player.getTeam();
            if (team != IPlayer.TEAM_NONE) {
                // skip if already dealt with this team
                if (doneTeams.contains(team)) {
                    continue;
                }
                doneTeams.add(team);
            }
            for (IPlayer enemyPlayer : game.getPlayersVector()) {
                if (enemyPlayer.equals(player)
                        || ((team != IPlayer.TEAM_NONE) && (team == enemyPlayer.getTeam()))) {
                    continue;
                }
                if (game.getLiveCommandersOwnedBy(enemyPlayer) > 0) {
                    killedAll = false;
                }
            }
            // all enemy commanders are dead
            if (killedAll) {
                victoryResult.setVictory(true);
                createReport(victoryResult, player.getTeam(), player.getId(), player.getName());
            }
        }
        return victoryResult;
    }

    @Override
    protected VictoryResult createReport(Object... data) {
        Report report = new Report(7111, Report.PUBLIC);
        if ((Integer) data[1] == IPlayer.TEAM_NONE) {
            report.add((String) data[3]);
            ((VictoryResult) data[0]).addPlayerScore((Integer) data[2], 1);
        } else {
            report.add("Team " + data[1]);
            ((VictoryResult) data[0]).addTeamScore((Integer) data[1], 1);
        }
        ((VictoryResult) data[0]).getReports().add(report);
        return (VictoryResult) data[0];
    }
}
