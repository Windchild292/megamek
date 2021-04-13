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

public class TargetedBVDestroyedVictory extends AbstractTargetedVictoryCondition {
    //region Variable Declarations
    private static final long serialVersionUID = -5588887849955271714L;
    private int targetPercentage;
    //endregion Variable Declarations

    //region Constructors
    protected TargetedBVDestroyedVictory(@Nullable Team originTeam, @Nullable IPlayer originPlayer,
                                         @Nullable Team targetTeam, @Nullable IPlayer targetPlayer,
                                         int targetPercentage) {
        super("TargetedBVDestroyedVictory.title", originTeam, originPlayer, targetTeam, targetPlayer);
        setTargetPercentage(targetPercentage);
    }
    //endregion Constructors

    //region Getters/Setters
    public int getTargetPercentage() {
        return targetPercentage;
    }

    public void setTargetPercentage(int targetPercentage) {
        this.targetPercentage = targetPercentage;
    }
    //endregion Getters/Setters

    @Override
    public VictoryResult victory(IGame game) {
        final boolean isTargetTeam = getTargetPlayer() == null;
        int enemyBV = isTargetTeam ? getTargetTeam().getBV() : getTargetPlayer().getBV();
        int initialEnemyBV = isTargetTeam ? getTargetTeam().getInitialBV() : getTargetPlayer().getInitialBV();

        if ((initialEnemyBV != 0) && (((enemyBV * 100) / initialEnemyBV) <= (100 - getTargetPercentage()))) {
            VictoryResult victoryResult = new VictoryResult(true);
            Report report = new Report(7105, Report.PUBLIC);
            if (getOriginPlayer() == null) {
                report.add(getOriginTeam().toString());
                victoryResult.addTeamScore(getOriginTeam().getId(), 1.0);
            } else {
                report.add(getOriginPlayer().getName());
                victoryResult.addPlayerScore(getOriginPlayer().getId(), 1.0);
            }
            report.add(100 - ((enemyBV * 100) / initialEnemyBV));
            victoryResult.getReports().add(report);
            return victoryResult;
        }

        return VictoryResult.noResult();
    }
}
