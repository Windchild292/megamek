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

public class TargetedBVDestroyedVictory extends AbstractTargetedBVVictory {
    //region Variable Declarations
    private static final long serialVersionUID = -5588887849955271714L;
    private int targetPercentage;
    //endregion Variable Declarations

    //region Constructors
    protected TargetedBVDestroyedVictory(int originTeam, IPlayer originPlayer,
                                         int targetTeam, IPlayer targetPlayer, int targetPercentage) {
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
        int enemyBV = getEnemyBV(game);
        int initialEnemyBV = getEnemyInitialBV(game);

        if ((initialEnemyBV != 0) && (((enemyBV * 100) / initialEnemyBV) <= (100 - getTargetPercentage()))) {
            return createReport(getOriginTeam(),
                    (getOriginPlayer() == null) ? 0 : getOriginPlayer().getId(),
                    (getOriginPlayer() == null) ? "" : getOriginPlayer().getName(),
                    100 - ((enemyBV * 100) / initialEnemyBV));
        }

        return VictoryResult.noResult();
    }

    @Override
    protected VictoryResult createReport(Object... data) {
        VictoryResult victoryResult = new VictoryResult(true);
        Report report = new Report(7105, Report.PUBLIC);
        if ((Integer) data[0] == IPlayer.TEAM_NONE) {
            report.add((String) data[2]);
            victoryResult.addPlayerScore((Integer) data[1], 1.0);
        } else {
            report.add("Team " + data[0]);
            victoryResult.addTeamScore((Integer) data[0], 1.0);
        }
        report.add((Integer) data[3]);
        victoryResult.getReports().add(report);
        return victoryResult;
    }
}
