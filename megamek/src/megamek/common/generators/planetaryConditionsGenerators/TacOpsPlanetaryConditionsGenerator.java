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

import megamek.common.Compute;
import megamek.common.PlanetaryConditions;
import megamek.common.enums.PlanetaryConditionsGenerationMethod;
import megamek.common.generators.extendedPlanetaryConditionsGenerators.TacOpsExtendedPlanetaryConditionsGenerator;
import megamek.common.generators.lightGenerators.TacOpsLightGenerator;
import megamek.common.generators.weatherGenerators.TacOpsWeatherGenerator;

public class TacOpsPlanetaryConditionsGenerator extends AbstractPlanetaryConditionsGenerator {
    //region Constructors
    public TacOpsPlanetaryConditionsGenerator(final boolean constantPlanetaryValues) {
        super(PlanetaryConditionsGenerationMethod.TACTICAL_OPERATIONS, new TacOpsLightGenerator(),
                new TacOpsWeatherGenerator(), new TacOpsExtendedPlanetaryConditionsGenerator(constantPlanetaryValues));
    }
    //endregion Constructors

    @Override
    public void generate(PlanetaryConditions conditions) {
        if (getExtendedPlanetaryConditionsGenerator().isConstantPlanetaryValues()) {
            switch (Compute.randomInt(6)) {
                case 0:
                    getLightGenerator().generate(conditions);
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                    getWeatherGenerator().generate(conditions);
                    break;
                case 5:
                default:
                    getExtendedPlanetaryConditionsGenerator().generate(conditions);
            }
        } else {
            switch (Compute.randomInt(8)) {
                case 0:
                    getLightGenerator().generate(conditions);
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                    getWeatherGenerator().generate(conditions);
                    break;
                case 5:
                case 6:
                case 7:
                default:
                    getExtendedPlanetaryConditionsGenerator().generate(conditions);
                    break;
            }
        }
    }
}
