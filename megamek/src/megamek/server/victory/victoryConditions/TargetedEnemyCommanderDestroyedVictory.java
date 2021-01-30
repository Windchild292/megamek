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

import megamek.common.Entity;
import megamek.common.IGame;
import megamek.common.IPlayer;
import megamek.common.Report;
import megamek.common.Team;
import megamek.common.annotations.Nullable;
import megamek.server.victory.VictoryResult;

import java.util.Objects;

public class TargetedEnemyCommanderDestroyedVictory extends AbstractTargetedVictoryCondition {
    //region Variable Declarations
    private static final long serialVersionUID = -6159035545154166127L;
    private Entity targetEntity;
    //endregion Variable Declarations

    //region Constructors
    public TargetedEnemyCommanderDestroyedVictory(@Nullable Team originTeam, @Nullable IPlayer originPlayer,
                                                  @Nullable Team targetTeam, @Nullable IPlayer targetPlayer,
                                                  Entity targetEntity) {
        super("TargetedEnemyCommanderDestroyedVictory.title", originTeam, originPlayer, targetTeam, targetPlayer);
        setTargetEntity(Objects.requireNonNull(targetEntity));
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
        if (getTargetEntity().isDestroyed()) {
            VictoryResult victoryResult = new VictoryResult(true, getOriginTeam().getId(), getOriginPlayer());
            Report report = new Report(7110, Report.PUBLIC);
            if (getOriginPlayer() == null) {
                report.add(getOriginTeam().toString());
                victoryResult.addTeamScore(getOriginTeam().getId(), 1.0);
            } else {
                report.add(getOriginPlayer().getName());
                victoryResult.addPlayerScore(getOriginPlayer().getId(), 1.0);
            }
            victoryResult.getReports().add(report);
            return victoryResult;
        } else {
            return VictoryResult.noResult();
        }
    }
}
