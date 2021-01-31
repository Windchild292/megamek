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
package megamek.server.victory.defeatConditions;

import megamek.common.IGame;
import megamek.common.IPlayer;
import megamek.common.Team;
import megamek.common.util.EncodeControl;
import megamek.server.victory.DefeatResult;

import java.io.Serializable;
import java.util.ResourceBundle;

public abstract class AbstractDefeatCondition implements Serializable {
    //region Variable Declarations
    private static final long serialVersionUID = 5557501529262419221L;
    private String name;
    private Team originTeam;
    private IPlayer originPlayer;

    private final ResourceBundle resources = ResourceBundle.getBundle("megamek.server.messages", new EncodeControl());
    //endregion Variable Declarations

    //region Constructors
    protected AbstractDefeatCondition() {

    }
    //endregion Constructors

    //region Getters/Setters
    public void setName(String name) {
        this.name = name;
    }
    //endregion Getters/Setters

    //region Abstract Methods
    /**
     * This method determines if the conditions provided have led the player to victory
     *
     * @param game The game to determine if the defeat has occurred
     */
    public abstract DefeatResult defeat(IGame game);
    //endregion Abstract Methods

    @Override
    public String toString() {
        return name;
    }
}
