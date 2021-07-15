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
package megamek.common.generators.weatherGenerators;

import megamek.common.PlanetaryConditions;
import megamek.common.enums.Fog;
import megamek.common.enums.Weather;
import megamek.common.enums.WeatherGenerationMethod;
import megamek.common.enums.Wind;

public abstract class AbstractWeatherGenerator {
    //region Variable Declarations
    private final WeatherGenerationMethod method;
    //endregion Variable Declarations

    //region Constructors
    protected AbstractWeatherGenerator(final WeatherGenerationMethod method) {
        this.method = method;
    }
    //endregion Constructors

    //region Getters
    public WeatherGenerationMethod getMethod() {
        return method;
    }
    //endregion Getters

    /**
     * This generates random weather and assigns it to the provided {@link PlanetaryConditions}
     * @param conditions the {@link PlanetaryConditions} to assign the generated weather to
     */
    public abstract void generate(final PlanetaryConditions conditions);

    /**
     * Clears the skies for the given planetary conditions
     * @param conditions the {@link PlanetaryConditions} whose skies are to be cleared
     */
    public void clearSkies(final PlanetaryConditions conditions) {
        conditions.setWeather(Weather.CLEAR);
        conditions.setWindStrength(Wind.CALM);
        conditions.setFog(Fog.NONE);
    }
}
