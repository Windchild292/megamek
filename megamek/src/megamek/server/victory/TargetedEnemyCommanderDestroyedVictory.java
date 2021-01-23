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

import megamek.common.Entity;
import megamek.common.IGame;
import megamek.common.IPlayer;
import megamek.common.Report;

public class TargetedEnemyCommanderDestroyedVictory extends AbstractTargetedVictoryCondition {
    //region Variable Declarations
    private static final long serialVersionUID = -6159035545154166127L;
    private Entity targetEntity;
    //endregion Variable Declarations

    //region Constructors
    public TargetedEnemyCommanderDestroyedVictory(int originTeam, IPlayer originPlayer,
                                                  int targetTeam, IPlayer targetPlayer, Entity targetEntity) {
        super("TargetedEnemyCommanderDestroyedVictory.title", originTeam, originPlayer, targetTeam, targetPlayer);
        setTargetEntity(targetEntity);
    }
    //endregion Constructors

    //region Getters/Setters
    public Entity getTargetEntity() {
        return targetEntity;
    }

    public void setTargetEntity(Entity targetEntity) {
        this.targetEntity = targetEntity;
    }
    //endregion Getters/Setters

    @Override
    public VictoryResult victory(IGame game) {
        return getTargetEntity().isDestroyed() ? createReport() : VictoryResult.noResult();
    }

    @Override
    protected VictoryResult createReport(Object... data) {
        VictoryResult victoryResult = new VictoryResult(true, getOriginTeam(), getOriginPlayer());
        Report report = new Report(7110, Report.PUBLIC);
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
