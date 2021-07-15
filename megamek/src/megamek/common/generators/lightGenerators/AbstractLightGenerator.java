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
package megamek.common.generators.lightGenerators;

import megamek.common.PlanetaryConditions;
import megamek.common.enums.LightGenerationMethod;

public abstract class AbstractLightGenerator {
    //region Variable Declarations
    private final LightGenerationMethod method;
    //endregion Variable Declarations

    //region Constructors
    protected AbstractLightGenerator(final LightGenerationMethod method) {
        this.method = method;
    }
    //endregion Constructors

    //region Getters
    public LightGenerationMethod getMethod() {
        return method;
    }
    //endregion Getters

    /**
     * This generates random light level and assigns it to the provided {@link PlanetaryConditions}
     * @param conditions the {@link PlanetaryConditions} to assign the generated light to
     */
    public abstract void generate(final PlanetaryConditions conditions);
}
