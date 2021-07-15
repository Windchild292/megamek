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
package megamek.common.generators.extendedPlanetaryConditionsGenerators;

import megamek.common.Compute;
import megamek.common.PlanetaryConditions;
import megamek.common.enums.Atmosphere;
import megamek.common.enums.AtmosphericPressure;
import megamek.common.enums.ExtendedPlanetaryConditionsGenerationMethod;

public class TacOpsExtendedPlanetaryConditionsGenerator extends AbstractExtendedPlanetaryConditionsGenerator {
    //region Constructors
    public TacOpsExtendedPlanetaryConditionsGenerator(final boolean constantPlanetaryValues) {
        super(ExtendedPlanetaryConditionsGenerationMethod.TACTICAL_OPERATIONS, constantPlanetaryValues);
    }
    //endregion Constructors

    @Override
    public void generate(final PlanetaryConditions conditions) {
        if (isConstantPlanetaryValues()) {
            generateHostile(conditions);
        } else {
            switch (Compute.randomInt(3)) {
                case 0:
                    generateExtremeTemperatures(conditions);
                    break;
                case 1:
                    generateHostile(conditions);
                    break;
                case 2:
                default:
                    generateHighLowGravity(conditions);
                    break;
            }
        }
    }

    private void generateExtremeTemperatures(final PlanetaryConditions conditions) {
        switch (Compute.d6()) {
            case 1:
                conditions.setTemperature((Compute.randomInt(105) / 10d) - 70d);
                break;
            case 2:
                conditions.setTemperature((Compute.randomInt(200) / 10d) - 59.5);
                break;
            case 3:
                conditions.setTemperature((Compute.randomInt(90) / 10d) - 39.5);
                break;
            case 4:
                conditions.setTemperature((Compute.randomInt(105) / 10d) + 50d);
                break;
            case 5:
                conditions.setTemperature((Compute.randomInt(100) / 10d) + 60.5);
                break;
            case 6:
            default:
                conditions.setTemperature((Compute.randomInt(95) / 10d) + 70.5);
                break;
        }
    }

    private void generateHostile(final PlanetaryConditions conditions) {
        switch (Compute.randomInt(5)) {
            case 0:
                conditions.setMeteorShower(true);
                break;
            case 1:
                conditions.setEarthquakeMagnitude(Compute.randomInt(5) + 1);
                break;
            case 2:
                generateAtmosphericPressure(conditions);
                break;
            case 3:
                generateTaintedToxicAtmosphere(conditions);
                break;
            case 4:
            default:
                conditions.setEMI(true);
                break;
        }
    }

    private void generateHighLowGravity(final PlanetaryConditions conditions) {
        switch (Compute.randomInt(6)) {
            case 0:
                conditions.setGravity((Compute.randomInt(250) / 1000f) + 0.1f);
                break;
            case 1:
                conditions.setGravity((Compute.randomInt(300) / 1000f) + 0.35f);
                break;
            case 2:
                conditions.setGravity((Compute.randomInt(250) / 1000f) + 0.65f);
                break;
            case 3:
                conditions.setGravity((Compute.randomInt(250) / 1000f) + 1.1f);
                break;
            case 4:
                conditions.setGravity((Compute.randomInt(200) / 1000f) + 1.35f);
                break;
            case 5:
            default:
                conditions.setGravity((Compute.randomInt(350) / 1000f) + 1.55f);
                break;
        }
    }

    private void generateAtmosphericPressure(final PlanetaryConditions conditions) {
        switch (Compute.randomInt(6)) {
            case 0:
                conditions.setAtmosphericPressure(AtmosphericPressure.VACUUM);
                break;
            case 1:
                conditions.setAtmosphericPressure(AtmosphericPressure.TRACE);
                break;
            case 2:
                conditions.setAtmosphericPressure(AtmosphericPressure.THIN);
                break;
            case 3:
                conditions.setAtmosphericPressure(AtmosphericPressure.STANDARD);
                break;
            case 4:
                conditions.setAtmosphericPressure(AtmosphericPressure.HIGH);
                break;
            case 5:
            default:
                conditions.setAtmosphericPressure(AtmosphericPressure.VERY_HIGH);
                break;
        }
    }

    private void generateTaintedToxicAtmosphere(final PlanetaryConditions conditions) {
        switch (Compute.randomInt(12)) {
            case 0:
            case 1:
                conditions.setAtmosphere(Atmosphere.TAINTED_CAUSTIC);
                break;
            case 2:
                conditions.setAtmosphere(Atmosphere.TAINTED_RADIOLOGICAL);
                break;
            case 3:
                conditions.setAtmosphere(Atmosphere.TAINTED_POISONOUS);
                break;
            case 4:
            case 5:
                conditions.setAtmosphere(Atmosphere.TAINTED_FLAMMABLE);
                break;
            case 6:
            case 7:
                conditions.setAtmosphere(Atmosphere.TOXIC_CAUSTIC);
                break;
            case 8:
                conditions.setAtmosphere(Atmosphere.TOXIC_RADIOLOGICAL);
                break;
            case 9:
                conditions.setAtmosphere(Atmosphere.TOXIC_POISONOUS);
                break;
            case 10:
            case 11:
            default:
                conditions.setAtmosphere(Atmosphere.TOXIC_FLAMMABLE);
                break;
        }
    }
}
