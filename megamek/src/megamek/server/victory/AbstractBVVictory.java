/*
 * Copyright (C) 2007-2008 Ben Mazur (bmazur@sev.org)
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
package megamek.server.victory;

import megamek.common.IGame;
import megamek.common.IPlayer;

/**
 * abstract baseclass for BV-checking victory implementations
 */
public abstract class AbstractBVVictory extends AbstractVictoryCondition {
    //region Variable Declarations
    private static final long serialVersionUID = -689891568905531049L;
    //endregion Variable Declarations

    //region Constructors
    protected AbstractBVVictory(String name) {
        super(name);
    }
    //endregion Constructors

    public int getFriendlyBV(IGame game, IPlayer player) {
        int friendlyBV = 0;
        for (IPlayer other : game.getPlayersVector()) {
            if (!other.isObserver() && !other.isEnemyOf(player)) {
                friendlyBV += other.getBV();
            }
        }
        return friendlyBV;
    }

    public int getEnemyBV(IGame game, IPlayer player) {
        int enemyBV = 0;
        for (IPlayer other : game.getPlayersVector()) {
            if (!other.isObserver() && other.isEnemyOf(player)) {
                enemyBV += other.getBV();
            }
        }
        return enemyBV;
    }

    public int getEnemyInitialBV(IGame game, IPlayer player) {
        int enemyInitialBV = 0;
        for (IPlayer other : game.getPlayersVector()) {
            if (other.isObserver() && other.isEnemyOf(player)) {
                enemyInitialBV += other.getInitialBV();
            }
        }
        return enemyInitialBV;
    }
}
