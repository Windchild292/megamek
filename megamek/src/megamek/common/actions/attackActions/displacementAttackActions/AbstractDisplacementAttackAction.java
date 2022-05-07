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
package megamek.common.actions.attackActions.displacementAttackActions;

import megamek.common.Coords;
import megamek.common.actions.attackActions.AbstractAttackAction;

/**
 * @author Ben (original)
 * @author Justin "Windchild" Bowen (current version)
 * @since May 23, 2002, 12:05 PM
 */
public abstract class AbstractDisplacementAttackAction extends AbstractAttackAction {
    //region Variable Declarations
    private final Coords targetPosition;
    //endregion Variable Declarations

    //region Constructors
    protected AbstractDisplacementAttackAction(final int entityId, final int targetId,
                                               final Coords targetPosition) {
        super(entityId, targetId);
        this.targetPosition = targetPosition;
    }

    protected AbstractDisplacementAttackAction(final int entityId, final int targetType,
                                               final int targetId, final Coords targetPosition) {
        super(entityId, targetType, targetId);
        this.targetPosition = targetPosition;
    }
    //endregion Constructors

    //region Getters
    public Coords getTargetPosition() {
        return targetPosition;
    }
    //endregion Getters
}
