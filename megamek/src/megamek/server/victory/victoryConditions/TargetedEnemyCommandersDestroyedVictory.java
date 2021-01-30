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
package megamek.server.victory.victoryConditions;

import megamek.common.IGame;
import megamek.common.IPlayer;
import megamek.common.Report;
import megamek.common.Team;
import megamek.common.annotations.Nullable;
import megamek.server.victory.VictoryResult;

public class TargetedEnemyCommandersDestroyedVictory extends AbstractTargetedVictoryCondition {
    //region Variable Declarations
    private static final long serialVersionUID = -3052400931798257557L;
    //endregion Variable Declarations

    //region Constructors
    public TargetedEnemyCommandersDestroyedVictory(@Nullable Team originTeam, @Nullable IPlayer originPlayer,
                                                   @Nullable Team targetTeam, @Nullable IPlayer targetPlayer) {
        super("TargetedEnemyCommandersDestroyedVictory.title", originTeam, originPlayer, targetTeam, targetPlayer);
    }
    //endregion Constructors

    @Override
    public VictoryResult victory(IGame game) {
        if (getTargetPlayer() == null) {
            if (getTargetTeam().getPlayersVector().stream()
                    .allMatch(p -> game.getLiveCommandersOwnedBy(p) == 0)) {
                return createReport();
            }
        } else { // Target Player
            if (game.getLiveCommandersOwnedBy(getTargetPlayer()) == 0) {
                return createReport();
            }
        }

        return VictoryResult.noResult();
    }

    private VictoryResult createReport() {
        VictoryResult victoryResult = new VictoryResult(true, getOriginTeam().getId(), getOriginPlayer());
        Report report = new Report(7111, Report.PUBLIC);
        if (getOriginPlayer() == null) {
            report.add(getOriginTeam().toString());
            victoryResult.addTeamScore(getOriginTeam().getId(), 1.0);
        } else {
            report.add(getOriginPlayer().getName());
            victoryResult.addPlayerScore(getOriginPlayer().getId(), 1.0);
        }
        victoryResult.getReports().add(report);
        return victoryResult;
    }
}
