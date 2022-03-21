/*
 * Copyright (c) 2022 - The MegaMek Team. All Rights Reserved.
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
package megamek.common.util.randomization;

import megamek.common.util.enums.RandomizationMethod;

import java.security.SecureRandom;

public class MMSecureRandom extends AbstractRandom {
    //region Variable Declarations
    private final SecureRandom random;
    //endregion Variable Declarations

    //region Constructors
    /**
     * Constructor that uses a new thread to initialize the RNG
     */
    public MMSecureRandom() {
        super(RandomizationMethod.SECURE_RANDOM);
        random = new SecureRandom();
        new Thread(getRandom()::nextInt, "Random Number Init (SecureRandom)").start();
    }
    //endregion Constructors

    //region Getters
    public SecureRandom getRandom() {
        return random;
    }
    //endregion Getters

    @Override
    public int randomInt(int maxValue) {
        return random.nextInt(maxValue);
    }

    @Override
    public float randomFloat() {
        return random.nextFloat();
    }
}
