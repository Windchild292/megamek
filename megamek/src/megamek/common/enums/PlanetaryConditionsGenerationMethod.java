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

import megamek.common.generators.planetaryConditionsGenerators.*;
import megamek.common.preference.PreferenceManager;
import megamek.common.util.EncodeControl;

import java.util.ResourceBundle;

public enum PlanetaryConditionsGenerationMethod {
    //region Enum Declarations
    NONE("PlanetaryConditionsGenerationMethod.NONE.text", "PlanetaryConditionsGenerationMethod.NONE.toolTipText"),
    SPECIFIED("PlanetaryConditionsGenerationMethod.SPECIFIED.text", "PlanetaryConditionsGenerationMethod.SPECIFIED.toolTipText"),
    ATB("PlanetaryConditionsGenerationMethod.ATB.text", "PlanetaryConditionsGenerationMethod.ATB.toolTipText"),
    TACTICAL_OPERATIONS("PlanetaryConditionsGenerationMethod.TACTICAL_OPERATIONS.text", "PlanetaryConditionsGenerationMethod.TACTICAL_OPERATIONS.toolTipText");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;

    private final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.messages",
            PreferenceManager.getClientPreferences().getLocale(), new EncodeControl());
    //endregion Variable Declarations

    //region Constructors
    PlanetaryConditionsGenerationMethod(final String name, final String toolTipText) {
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

    public boolean isSpecified() {
        return this == SPECIFIED;
    }

    public boolean isAtB() {
        return this == ATB;
    }

    public boolean isTacticalOperations() {
        return this == TACTICAL_OPERATIONS;
    }
    //endregion Boolean Comparison Methods

    public AbstractPlanetaryConditionsGenerator getGenerator(final boolean constantPlanetaryValues,
                                                             final LightGenerationMethod lightGenerationMethod,
                                                             final WeatherGenerationMethod weatherGenerationMethod,
                                                             final ExtendedPlanetaryConditionsGenerationMethod extendedPlanetaryConditionsGenerationMethod) {
        switch (this) {
            case SPECIFIED:
                return new SpecifiedPlanetaryConditionsGenerator(constantPlanetaryValues,
                        lightGenerationMethod, weatherGenerationMethod,
                        extendedPlanetaryConditionsGenerationMethod);
            case ATB:
                return new AtBPlanetaryConditionsGenerator();
            case TACTICAL_OPERATIONS:
                return new TacOpsPlanetaryConditionsGenerator(constantPlanetaryValues);
            case NONE:
            default:
                return new DisabledPlanetaryConditionsGenerator();
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
