/*
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

import megamek.common.IGame;
import megamek.common.IPlayer;
import megamek.common.Report;

public class TargetedEnemyCommandersDestroyedVictory extends AbstractTargetedVictoryCondition {
    //region Variable Declarations
    private static final long serialVersionUID = -3052400931798257557L;
    //endregion Variable Declarations

    //region Constructors
    public TargetedEnemyCommandersDestroyedVictory(int originTeam, IPlayer originPlayer,
                                                   int targetTeam, IPlayer targetPlayer) {
        super("TargetedEnemyCommandersDestroyedVictory.title", originTeam, originPlayer, targetTeam, targetPlayer);
    }
    //endregion Constructors

    @Override
    public VictoryResult victory(IGame game) {
        if ((getTargetPlayer() != null) && (game.getLiveCommandersOwnedBy(getTargetPlayer()) == 0)) {
            return createReport();
        } else { // Target Team
            boolean allKilled = true;
            for (IPlayer enemy : game.getPlayersVector()) {
                if ((enemy.getTeam() == getTargetTeam()) && (game.getLiveCommandersOwnedBy(enemy) > 0)) {
                    allKilled = false;
                    break;
                }
            }

            if (allKilled) {
                return createReport();
            }
        }

        return VictoryResult.noResult();
    }

    @Override
    protected VictoryResult createReport(Object... data) {
        VictoryResult victoryResult = new VictoryResult(true, getOriginTeam(), getOriginPlayer());
        Report report = new Report(7111, Report.PUBLIC);
        if (getOriginPlayer() == null) {
            report.add("Team " + getOriginTeam());
            victoryResult.addTeamScore(getOriginTeam(), 1.0);
        } else {
            report.add(getOriginPlayer().getName());
            victoryResult.addPlayerScore(getOriginPlayer().getId(), 1.0);
        }
        victoryResult.getReports().add(report);
        return victoryResult;
    }
}
