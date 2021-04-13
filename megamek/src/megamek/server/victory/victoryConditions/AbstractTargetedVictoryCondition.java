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

import megamek.common.IPlayer;
import megamek.common.Team;
import megamek.common.annotations.Nullable;

public abstract class AbstractTargetedVictoryCondition extends AbstractVictoryCondition {
    //region Variable Declarations
    private static final long serialVersionUID = 3705712748148487741L;

    private Team originTeam;
    private IPlayer originPlayer;
    private Team targetTeam;
    private IPlayer targetPlayer;
    //endregion Variable Declarations

    //region Constructors
    protected AbstractTargetedVictoryCondition(String name,
                                               @Nullable Team originTeam, @Nullable IPlayer originPlayer,
                                               @Nullable Team targetTeam, @Nullable IPlayer targetPlayer) {
        super(name);
        setOriginTeam(originTeam);
        setOriginPlayer(originPlayer);
        setTargetTeam(targetTeam);
        setTargetPlayer(targetPlayer);
    }
    //endregion Constructors

    //region Getters/Setters
    public @Nullable Team getOriginTeam() {
        return originTeam;
    }

    public void setOriginTeam(@Nullable Team originTeam) {
        this.originTeam = originTeam;
    }

    public @Nullable IPlayer getOriginPlayer() {
        return originPlayer;
    }

    public void setOriginPlayer(@Nullable IPlayer originPlayer) {
        this.originPlayer = originPlayer;
    }

    public @Nullable Team getTargetTeam() {
        return targetTeam;
    }

    public void setTargetTeam(@Nullable Team targetTeam) {
        this.targetTeam = targetTeam;
    }

    public @Nullable IPlayer getTargetPlayer() {
        return targetPlayer;
    }

    public void setTargetPlayer(@Nullable IPlayer targetPlayer) {
        this.targetPlayer = targetPlayer;
    }
    //endregion Getters/Setters
}
