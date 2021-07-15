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
package megamek.common.generators.planetaryConditionsGenerators;

import megamek.common.PlanetaryConditions;
import megamek.common.enums.PlanetaryConditionsGenerationMethod;
import megamek.common.generators.lightGenerators.AbstractLightGenerator;
import megamek.common.generators.weatherGenerators.AbstractWeatherGenerator;

public abstract class AbstractPlanetaryConditionsGenerator {
    //region Variable Declarations
    private final PlanetaryConditionsGenerationMethod method;
    private final AbstractLightGenerator lightGenerator;
    private final AbstractWeatherGenerator weatherGenerator;
    //endregion Variable Declarations

    //region Constructors
    protected AbstractPlanetaryConditionsGenerator(final PlanetaryConditionsGenerationMethod method,
                                                   final AbstractLightGenerator lightGenerator,
                                                   final AbstractWeatherGenerator weatherGenerator) {
        this.method = method;
        this.lightGenerator = lightGenerator;
        this.weatherGenerator = weatherGenerator;
    }
    //endregion Constructors

    //region Getters
    public PlanetaryConditionsGenerationMethod getMethod() {
        return method;
    }

    public AbstractLightGenerator getLightGenerator() {
        return lightGenerator;
    }

    public AbstractWeatherGenerator getWeatherGenerator() {
        return weatherGenerator;
    }
    //endregion Getters

    /**
     * @return a randomly generated set of planetary conditions
     */
    public PlanetaryConditions generate() {
        final PlanetaryConditions conditions = new PlanetaryConditions();
        generate(conditions);
        return conditions;
    }

    /**
     * This generates a random set of {@link PlanetaryConditions}
     * @param conditions the {@link PlanetaryConditions} to generate into
     */
    public void generate(final PlanetaryConditions conditions) {
        getLightGenerator().generate(conditions);
        getWeatherGenerator().generate(conditions);
    }
}
