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
package megamek.server.victory.victoryConditions;

import megamek.common.IGame;
import megamek.common.IPlayer;

import java.util.stream.IntStream;

/**
 * Abstract base class for BV-checking victory implementations
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

    public int getEnemyBV(IGame game, IPlayer player) {
        return game.getPlayersVector().stream()
                .filter(p -> !p.isObserver() && p.isEnemyOf(player))
                .flatMapToInt(p -> IntStream.of(p.getBV()))
                .sum();
    }

    public int getEnemyInitialBV(IGame game, IPlayer player) {
        return game.getPlayersVector().stream()
                .filter(p -> !p.isObserver() && p.isEnemyOf(player))
                .flatMapToInt(p -> IntStream.of(p.getInitialBV()))
                .sum();
    }
}
