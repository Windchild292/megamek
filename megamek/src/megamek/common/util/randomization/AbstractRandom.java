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

import megamek.common.MMRoll;
import megamek.common.Roll;

public abstract class AbstractRandom {

    /**
     * Simulates six-sided die rolls.
     *
     * @param nDice - the <code>int</code> number of dice to roll. If this
     *            value is less than or equal to zero, an
     *            <code>IllegalArgumentException</code> will be thrown.
     * @return a <code>Roll</code> object containing the roll results.
     * @throws IllegalArgumentException will be thrown if the input is &lt;= 0.
     */
    public Roll d6(int nDice) {
        if (nDice <= 0) {
            throw new IllegalArgumentException("Must ask for a positive number of rolls, not " + nDice);
        }

        // Use the Roll object to record the rolls.
        MMRoll roll = new MMRoll(this, 6, 1);
        for (int i = 1; i < nDice; i++) {
            roll.addRoll(this);
        }
        return roll;
    }

    public Roll d6(int nDice, int keep) {
        if (nDice <= 0) {
            throw new IllegalArgumentException("Must ask for a positive number of rolls, not " + nDice);
        } else if (keep >= nDice) {
            throw new IllegalArgumentException("the number of dice to keep must be less than the number rolled");
        }
        // Use the Roll object to record the rolls.
        MMRoll roll = new MMRoll(this, 6, 1, keep);
        for (int i = 1; i < nDice; i++) {
            roll.addRoll(this);
        }
        return roll;
    }

    /**
     * Roll a single die
     * @return the value rolled by a single die
     */
    public Roll d6() {
        return d6(1);
    }

    /**
     * Returns a random <code>int</code> in the range from 0 to one less than the supplied max value.
     *
     * @param maxValue the smallest <code>int</code> value which will exceed any random number
     *                 returned by this method.
     * @return a random <code>int</code> from the value set [0, maxValue).
     */
    public abstract int randomInt(int maxValue);

    /**
     * Returns a random <code>float</code> in the range of 0 to 1
     * @return a random <code>float</code> from the value set [0, 1]
     */
    public abstract float randomFloat();
}
