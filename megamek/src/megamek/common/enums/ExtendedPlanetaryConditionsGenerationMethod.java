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
package megamek.common.enums;

import megamek.common.generators.extendedPlanetaryConditionsGenerators.AbstractExtendedPlanetaryConditionsGenerator;
import megamek.common.generators.extendedPlanetaryConditionsGenerators.DisabledExtendedPlanetaryConditionsGenerator;
import megamek.common.generators.extendedPlanetaryConditionsGenerators.TacOpsExtendedPlanetaryConditionsGenerator;
import megamek.common.preference.PreferenceManager;
import megamek.common.util.EncodeControl;

import java.util.ResourceBundle;

public enum ExtendedPlanetaryConditionsGenerationMethod {
    //region Enum Declarations
    NONE("ExtendedPlanetaryConditionsGenerationMethod.NONE.text", "ExtendedPlanetaryConditionsGenerationMethod.NONE.toolTipText"),
    TACTICAL_OPERATIONS("ExtendedPlanetaryConditionsGenerationMethod.TACTICAL_OPERATIONS.text", "ExtendedPlanetaryConditionsGenerationMethod.TACTICAL_OPERATIONS.toolTipText");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;

    private final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.messages",
            PreferenceManager.getClientPreferences().getLocale(), new EncodeControl());
    //endregion Variable Declarations

    //region Constructors
    ExtendedPlanetaryConditionsGenerationMethod(final String name, final String toolTipText) {
        this.name = resources.getString(name);
        this.toolTipText = resources.getString(toolTipText);
    }
    //endregion Constructors

    //region Getters
    public String getToolTipText() {
        return toolTipText;
    }
    //endregion Getters

    //region Boolean Comparison Methods
    public boolean isNone() {
        return this == NONE;
    }

    public boolean isTacticalOperations() {
        return this == TACTICAL_OPERATIONS;
    }
    //endregion Boolean Comparison Methods

    public AbstractExtendedPlanetaryConditionsGenerator getGenerator(final boolean constantPlanetaryValues) {
        switch (this) {
            case TACTICAL_OPERATIONS:
                return new TacOpsExtendedPlanetaryConditionsGenerator(constantPlanetaryValues);
            case NONE:
            default:
                return new DisabledExtendedPlanetaryConditionsGenerator();
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
