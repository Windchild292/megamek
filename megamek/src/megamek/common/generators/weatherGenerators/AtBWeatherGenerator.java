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

import megamek.common.Compute;
import megamek.common.PlanetaryConditions;
import megamek.common.enums.Fog;
import megamek.common.enums.Weather;
import megamek.common.enums.WeatherGenerationMethod;
import megamek.common.enums.Wind;

public class AtBWeatherGenerator extends AbstractWeatherGenerator {
    //region Constructors
    public AtBWeatherGenerator() {
        super(WeatherGenerationMethod.ATB);
    }
    //endregion Constructors

    @Override
    public void generate(final PlanetaryConditions conditions) {
        // Clear the skies by default
        clearSkies(conditions);

        // Then determine the generation from a shifted table (starting at 0 instead of 1)
        switch (Compute.randomInt(10)) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                break;
            case 5:
                generateRain(conditions);
                break;
            case 6:
                generateSnowfall(conditions);
                break;
            case 7:
                generateGale(conditions);
                break;
            case 8:
                generateStorm(conditions);
                break;
            case 9:
            default:
                generateFog(conditions);
                break;
        }
    }

    private void generateRain(final PlanetaryConditions conditions) {
        switch (Compute.randomInt(6)) {
            case 0:
            case 1:
            case 2:
                conditions.setWeather(Weather.LIGHT_RAIN);
                break;
            case 3:
            case 4:
                conditions.setWeather(Weather.MODERATE_RAIN);
                break;
            case 5:
            default:
                conditions.setWeather(Weather.HEAVY_RAIN);
                break;
        }
    }

    private void generateSnowfall(final PlanetaryConditions conditions) {
        switch (Compute.randomInt(6)) {
            case 0:
            case 1:
            case 2:
                conditions.setWeather(Weather.LIGHT_SNOW);
                break;
            case 3:
            case 4:
                conditions.setWeather(Weather.MODERATE_SNOW);
                break;
            case 5:
            default:
                conditions.setWeather(Weather.HEAVY_SNOW);
                break;
        }
    }


    private void generateGale(final PlanetaryConditions conditions) {
        switch (Compute.randomInt(6)) {
            case 0:
            case 1:
            case 2:
                conditions.setWindStrength(Wind.LIGHT_GALE);
                break;
            case 3:
            case 4:
                conditions.setWindStrength(Wind.MODERATE_GALE);
                break;
            case 5:
            default:
                conditions.setWindStrength(Wind.STRONG_GALE);
                break;
        }
    }

    private void generateStorm(final PlanetaryConditions conditions) {
        switch (Compute.randomInt(6)) {
            case 0:
                conditions.setWindStrength(Wind.STORM);
                break;
            case 1:
                conditions.setWeather(Weather.DOWNPOUR);
                break;
            case 2:
                conditions.setWeather(Weather.SLEET);
                break;
            case 3:
                conditions.setWeather(Weather.ICE_STORM);
                break;
            case 4:
                conditions.setWindStrength(Wind.TORNADO_F13);
                break;
            case 5:
            default:
                conditions.setWindStrength(Wind.TORNADO_F4);
                break;
        }
    }

    private void generateFog(final PlanetaryConditions conditions) {
        switch (Compute.randomInt(6)) {
            case 0:
            case 1:
            case 2:
            case 3:
                conditions.setFog(Fog.LIGHT);
                break;
            case 4:
            case 5:
            default:
                conditions.setFog(Fog.HEAVY);
                break;
        }
    }
}
