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
package megamek.server.victory;

import megamek.common.IGame;
import megamek.common.IPlayer;

public abstract class AbstractTargetedBVVictory extends AbstractTargetedVictoryCondition {
    //region Variable Declarations
    private static final long serialVersionUID = -1635903309893791028L;
    //endregion Variable Declarations

    //region Constructors
    protected AbstractTargetedBVVictory(String name,
                                        int originTeam, IPlayer originPlayer,
                                        int targetTeam, IPlayer targetPlayer) {
        super(name, originTeam, originPlayer, targetTeam, targetPlayer);
    }
    //endregion Constructors

    public int getFriendlyBV(IGame game) {
        if (getOriginPlayer() == null) {
            int friendlyBV = 0;
            for (IPlayer player : game.getPlayersVector()) {
                if (!player.isObserver() && (player.getTeam() == getOriginTeam())) {
                    friendlyBV += player.getBV();
                }
            }
            return friendlyBV;
        } else {
            return getOriginPlayer().getBV();
        }
    }

    public int getEnemyBV(IGame game) {
        if (getTargetPlayer() == null) {
            int enemyBV = 0;
            for (IPlayer player : game.getPlayersVector()) {
                if (!player.isObserver() && (player.getTeam() != getOriginTeam())) {
                    enemyBV += player.getBV();
                }
            }
            return enemyBV;
        } else {
            return getTargetPlayer().getBV();
        }
    }

    public int getEnemyInitialBV(IGame game) {
        if (getTargetPlayer() == null) {
            int enemyBV = 0;
            for (IPlayer player : game.getPlayersVector()) {
                if (!player.isObserver() && (player.getTeam() != getOriginTeam())) {
                    enemyBV += player.getInitialBV();
                }
            }
            return enemyBV;
        } else {
            return getTargetPlayer().getInitialBV();
        }
    }
}
