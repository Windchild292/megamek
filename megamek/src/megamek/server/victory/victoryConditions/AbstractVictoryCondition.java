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
import megamek.common.util.EncodeControl;
import megamek.server.victory.VictoryResult;

import java.io.Serializable;
import java.util.ResourceBundle;

public abstract class AbstractVictoryCondition implements Serializable {
    //region Variable Declarations
    private static final long serialVersionUID = 904773347180459053L;
    private String name;

    private final ResourceBundle resources = ResourceBundle.getBundle("megamek.server.messages", new EncodeControl());
    //endregion Variable Declarations

    //region Constructors
    public AbstractVictoryCondition(String name) {
        setName(resources.getString(name));
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
     * @param game The game to determine if the victory has occurred for
     */
    public abstract VictoryResult victory(IGame game);
    //endregion Abstract Methods

    @Override
    public String toString() {
        return name;
    }
}
