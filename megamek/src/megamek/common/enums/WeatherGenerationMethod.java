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

import megamek.common.generators.weatherGenerators.AbstractWeatherGenerator;
import megamek.common.generators.weatherGenerators.AtBWeatherGenerator;
import megamek.common.generators.weatherGenerators.DisabledWeatherGenerator;
import megamek.common.preference.PreferenceManager;
import megamek.common.util.EncodeControl;

import java.util.ResourceBundle;

public enum  WeatherGenerationMethod {
    //region Enum Declarations
    NONE("WeatherGenerationMethod.NONE.text", "WeatherGenerationMethod.NONE.toolTipText"),
    ATB("WeatherGenerationMethod.ATB.text", "WeatherGenerationMethod.ATB.toolTipText");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;

    private final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.messages",
            PreferenceManager.getClientPreferences().getLocale(), new EncodeControl());
    //endregion Variable Declarations

    //region Constructors
    WeatherGenerationMethod(final String name, final String toolTipText) {
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

    public boolean isAtB() {
        return this == ATB;
    }
    //endregion Boolean Comparison Methods

    public AbstractWeatherGenerator getGenerator() {
        switch (this) {
            case ATB:
                return new AtBWeatherGenerator();
            case NONE:
            default:
                return new DisabledWeatherGenerator();
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
