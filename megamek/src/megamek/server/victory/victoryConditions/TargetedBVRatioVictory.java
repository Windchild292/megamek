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

/**
 * Implements bv-ratio victory checking ratio is defined as
 * (friendly BV / enemy BC) > (BV Ratio Percent / 100) = win,
 * with a targeted opponent
 */
public class TargetedBVRatioVictory extends AbstractTargetedVictoryCondition {
    //region Variable Declarations
    private static final long serialVersionUID = 8670576088095041973L;
    private int targetRatio;
    //endregion Variable Declarations

    //region Constructors
    protected TargetedBVRatioVictory(@Nullable Team originTeam, @Nullable IPlayer originPlayer,
                                     @Nullable Team targetTeam, @Nullable IPlayer targetPlayer,
                                     int targetRatio) {
        super("TargetedBVRatioVictory.title", originTeam, originPlayer, targetTeam, targetPlayer);
        setTargetRatio(targetRatio);
    }
    //endregion Constructors

    //region Getters/Setters
    public int getTargetRatio() {
        return targetRatio;
    }

    public void setTargetRatio(int targetRatio) {
        this.targetRatio = targetRatio;
    }
    //endregion Getters/Setters

    @Override
    public VictoryResult victory(IGame game) {
        final boolean isOriginTeam = getOriginPlayer() == null;
        int friendlyBV = isOriginTeam ? getOriginTeam().getBV() : getOriginPlayer().getBV();
        int enemyBV = (getTargetPlayer() == null) ? getTargetTeam().getBV() : getTargetPlayer().getBV();

        if ((enemyBV == 0) || ((100 * friendlyBV) / enemyBV >= getTargetRatio())) {
            VictoryResult victoryResult = new VictoryResult(true);
            Report report = new Report(7100, Report.PUBLIC);
            if (isOriginTeam) {
                report.add(getOriginTeam().toString());
                victoryResult.addTeamScore(getOriginTeam().getId(), 1.0);
            } else {
                report.add(getOriginPlayer().getName());
                victoryResult.addPlayerScore(getOriginPlayer().getId(), 1.0);
            }
            report.add((enemyBV == 0) ? 9999 : (100 * friendlyBV) / enemyBV);
            victoryResult.getReports().add(report);
        }
        return VictoryResult.noResult();
    }
}
