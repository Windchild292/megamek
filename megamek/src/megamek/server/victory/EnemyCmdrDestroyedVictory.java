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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import megamek.common.IGame;
import megamek.common.IPlayer;
import megamek.common.Report;

/**
 * implements "enemy commander destroyed"
 */
public class EnemyCmdrDestroyedVictory implements IVictoryConditions, Serializable {
    //region Variable Declarations
    private static final long serialVersionUID = 2525190210964235691L;
    //endregion Variable Declarations

    //region Constructors
    public EnemyCmdrDestroyedVictory() {

    }
    //endregion Constructors

    @Override
    public VictoryResult victory(IGame game, Map<String, Object> ctx) {
        VictoryResult victoryResult = new VictoryResult(true);
        // check all players/teams for killing enemy commanders
        // score is 1.0 when enemy commanders are dead
        boolean victory = false;
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
                Report report = new Report(7110, Report.PUBLIC);
                if (team == IPlayer.TEAM_NONE) {
                    report.add(player.getName());
                    victoryResult.addPlayerScore(player.getId(), 1);
                } else {
                    report.add("Team " + team);
                    victoryResult.addTeamScore(team, 1);
                }
                victoryResult.getReports().add(report);
                victory = true;
            }
        }
        return victory ? victoryResult : VictoryResult.noResult();
    }
}
