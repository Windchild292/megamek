/*
 * Copyright (c) 2021-2022 - The MegaMek Team. All Rights Reserved.
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

public class TacOpsWeatherGenerator extends AbstractWeatherGenerator {
    //region Constructors
    public TacOpsWeatherGenerator() {
        super(WeatherGenerationMethod.TACTICAL_OPERATIONS);
    }
    //endregion Constructors

    @Override
    public void generate(final PlanetaryConditions conditions) {
        switch (Compute.randomInt(4)) {
            case 0:
                generateWind(conditions);
                break;
            case 1:
                generateRain(conditions);
                break;
            case 2:
                generateSnow(conditions);
                break;
            case 3:
            default:
                generateCombinedWeather(conditions);
                break;
        }
    }

    private void generateWind(final PlanetaryConditions conditions) {
        switch (Compute.randomInt(6)) {
            case 0:
                conditions.setWindStrength(Wind.LIGHT_GALE);
                break;
            case 1:
                conditions.setWindStrength(Wind.MODERATE_GALE);
                break;
            case 2:
                conditions.setWindStrength(Wind.STRONG_GALE);
                break;
            case 3:
                conditions.setWindStrength(Wind.STORM);
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

    private void generateRain(final PlanetaryConditions conditions) {
        switch (Compute.randomInt(6)) {
            case 0:
                conditions.setFog(Fog.LIGHT);
                break;
            case 1:
                conditions.setFog(Fog.HEAVY);
                break;
            case 2:
                conditions.setWeather(Weather.LIGHT_RAIN);
                break;
            case 3:
                conditions.setWeather(Weather.MODERATE_RAIN);
                break;
            case 4:
                conditions.setWeather(Weather.HEAVY_RAIN);
                break;
            case 5:
            default:
                conditions.setWeather(Weather.DOWNPOUR);
                break;
        }
    }

    private void generateSnow(final PlanetaryConditions conditions) {
        switch (Compute.randomInt(6)) {
            case 0:
                conditions.setWeather(Weather.SLEET);
                break;
            case 1:
                conditions.setWeather(Weather.LIGHT_HAIL);
                break;
            case 2:
                conditions.setWeather(Weather.HEAVY_HAIL);
                break;
            case 3:
                conditions.setWeather(Weather.LIGHT_SNOW);
                break;
            case 4:
                conditions.setWeather(Weather.MODERATE_SNOW);
                break;
            case 5:
            default:
                conditions.setWeather(Weather.HEAVY_SNOW);
                break;
        }
    }

    private void generateCombinedWeather(final PlanetaryConditions conditions) {
        switch (Compute.randomInt(5)) {
            case 0:
                conditions.setWeather(Weather.GUSTING_RAIN);
                break;
            case 1:
                conditions.setWeather(Weather.SNOW_FLURRIES);
                break;
            case 2:
                conditions.setSandBlowing(true);
                break;
            case 3:
                conditions.setWeather(Weather.ICE_STORM);
                break;
            case 4:
            default:
                conditions.setWeather(Weather.LIGHTNING_STORM);
                break;
        }
    }
}
