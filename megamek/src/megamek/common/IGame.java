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
package megamek.common;

import megamek.common.annotations.Nullable;
import megamek.common.enums.GamePhase;
import megamek.common.net.connections.AbstractConnection;
import megamek.common.options.GameOptions;

import java.util.Enumeration;
import java.util.List;

/**
 * Common interface for games with different rule sets, such as Total Warfare, BattleForce, or Alpha Strike.
 */
public interface IGame {

    GameOptions getOptions();

    GamePhase getPhase();

    /**
     * @param connection the connection to get the player for
     * @return the player for the specified connection
     */
    default @Nullable Player getPlayer(final AbstractConnection connection) {
        return getPlayer(connection.getId());
    }

    /**
     * @param id a player id
     * @return the individual player assigned the id parameter.
     */
    Player getPlayer(int id);

    /**
     * @return an enumeration of {@link Player players} in the game
     */
    Enumeration<Player> getPlayers();

    /**
     * @return The current players as a list.
     */
    List<Player> getPlayersVector();

    /**
     * Adds the player to the game with the id
     *
     * @param id
     * @param player
     */
    void addPlayer(int id, Player player);

    /**
     * Removes the player with the id from the game.
     *
     * @param id The player id
     */
    void removePlayer(int id);

    /**
     * @return the current number of active players in the game.
     */
    int getNoOfPlayers();

    /**
     * @return an enumeration of the teams in the game.
     */
    Enumeration<Team> getTeams();

    /**
     * @return An unused id
     */
    int getNextEntityId();

    /**
     * @return the number of entities owned by the player, regardless of their
     *         status, as long as they are in the game.
     */
    int getEntitiesOwnedBy(Player player);

    @Nullable
    GameTurn getTurn();

    void setupTeams();

    /**
     * @return Whether there is an active claim for victory.
     */
    boolean isForceVictory();
}
