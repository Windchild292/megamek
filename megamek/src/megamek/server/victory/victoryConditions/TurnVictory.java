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
import megamek.common.Report;
import megamek.server.victory.VictoryResult;

public class TurnVictory extends AbstractVictoryCondition {
    //region Variable Declarations
    private static final long serialVersionUID = 2316521702386709171L;
    private int turnLimit;
    //endregion Variable Declarations

    //region Constructors
    public TurnVictory(int turnLimit) {
        super("TimeVictory.title");
        setTurnLimit(turnLimit);
    }
    //endregion Constructors

    //region Getters/Setters
    public int getTurnLimit() {
        return turnLimit;
    }

    public void setTurnLimit(int turnLimit) {
        this.turnLimit = turnLimit;
    }
    //endregion Getters/Setters

    @Override
    public VictoryResult victory(IGame game) {
        if (game.getRoundCount() >= getTurnLimit()) {
            VictoryResult victoryResult = VictoryResult.drawResult();
            Report report = new Report(7112, Report.PUBLIC);
            report.add(getTurnLimit());
            victoryResult.getReports().add(report);
            return victoryResult;
        } else {
            return VictoryResult.noResult();
        }
    }
}
