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

import megamek.common.MMShuffle;
import megamek.common.Roll;

/**
 * Behaves like SunRandom for everything but d6(2) calls. Then, it takes
 * numbers from an array of the 36 possible results of two dice, shuffled.
 */
public class Pool36Random extends AbstractRandom {
    public static final int NUM_SHUFFLES = 360;

    MMShuffle[] pool = new MMShuffle[36];
    int index = 0;

    public Pool36Random() {
        initPool();
        shufflePool();
    }

    /**
     * Watches for 2 as nDice and then does its special thing.
     */
    @Override
    public Roll d6(int nDice) {
        if (nDice != 2) {
            return super.d6(nDice);
        }
        // check pool
        if (index >= pool.length) {
            shufflePool();
        }
        // return next pool number
        return pool[index++];
    }

    /**
     * Initializes the dice pool with the possible results of two dice.
     */
    void initPool() {
        index = 0;
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                pool[index++] = new MMShuffle(i, j);
            }
        }
    }

    /**
     * Swaps two of the numbers in the pool NUM_SHUFFLES times. Resets the index. Uses the regular RNG to shuffle (OH NO!)
     */
    void shufflePool() {
        MMShuffle temp;
        int src;
        int dest;

        // Alakazam!
        for (int i = 0; i < NUM_SHUFFLES; i++) {
            src = this.randomInt(pool.length);
            dest = this.randomInt(pool.length);

            temp = pool[src];
            pool[src] = pool[dest];
            pool[dest] = temp;
        }

        // Label each of the "rolls" with their new deal order.
        for (int j = 0; j < pool.length; j++) {
            pool[j].setDeal(j + 1);
        }

        // Reset index
        index = 0;
    }
}
