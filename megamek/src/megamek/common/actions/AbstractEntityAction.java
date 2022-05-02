/*
 * Copyright (c) 2000-2002 - Ben Mazur (bmazur@sev.org).
 * Copyright (c) 2022 - The MegaMek Team. All Rights Reserved.
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
package megamek.common.actions;

import megamek.client.Client;

import java.io.Serializable;

/**
 * Abstract superclass for any action that an entity takes.
 */
public abstract class AbstractEntityAction implements Serializable {
    //region Variable Declarations
    private static final long serialVersionUID = -758003433608975464L;
    private final int entityId;
    //endregion Variable Declarations

    //region Constructors
    public AbstractEntityAction(final int entityId) {
        this.entityId = entityId;
    }
    //endregion Constructors

    public int getEntityId() {
        return entityId;
    }

    public String toDisplayableString(final Client client) {
        return toString();
    }
}
