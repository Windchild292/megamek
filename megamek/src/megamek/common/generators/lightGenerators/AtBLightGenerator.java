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

import megamek.common.Compute;
import megamek.common.PlanetaryConditions;
import megamek.common.enums.Light;
import megamek.common.enums.LightGenerationMethod;

public class AtBLightGenerator extends AbstractLightGenerator {
    //region Constructors
    public AtBLightGenerator() {
        super(LightGenerationMethod.ATB);
    }
    //endregion Constructors

    @Override
    public void generate(final PlanetaryConditions conditions) {
        switch (Compute.randomInt(10)) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                conditions.setLight(Light.DAY);
                break;
            case 5:
                conditions.setLight(Light.DUSK);
                break;
            case 6:
                conditions.setLight(Light.DAWN);
                break;
            case 7:
                conditions.setLight(Light.FULL_MOON);
                break;
            case 8:
                conditions.setLight(Light.MOONLESS_NIGHT);
                break;
            case 9:
            default:
                conditions.setLight(Light.PITCH_BLACK);
                break;
        }
    }
}
