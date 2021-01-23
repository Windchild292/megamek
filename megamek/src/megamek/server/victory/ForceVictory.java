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

import java.util.List;

import megamek.common.IGame;
import megamek.common.IPlayer;

/**
 * implementation of player-agreed victory
 */
public class ForceVictory extends AbstractVictoryCondition {
    //region Variable Declarations
    private static final long serialVersionUID = 1782762191476942976L;
    //endregion Variable Declarations

    //region Constructors
    public ForceVictory() {
        super("ForceVictory.title");
    }
    //endregion Constructors

    @Override
    public VictoryResult victory(IGame game) {
        if (!game.isForceVictory()) {
            return VictoryResult.noResult();
        }
        int victoryPlayerId = game.getVictoryPlayerId();
        int victoryTeam = game.getVictoryTeam();
        List<IPlayer> players = game.getPlayersVector();
        boolean forceVictory = true;

        // Individual victory.
        if (victoryPlayerId != IPlayer.PLAYER_NONE) {
            for (IPlayer player : players) {
                if ((player.getId() != victoryPlayerId) && !player.isObserver()) {
                    if (!player.admitsDefeat()) {
                        forceVictory = false;
                        break;
                    }
                }
            }
        }

        // Team victory.
        if (victoryTeam != IPlayer.TEAM_NONE) {
            for (IPlayer player : players) {
                if ((player.getTeam() != victoryTeam) && !player.isObserver()) {
                    if (!player.admitsDefeat()) {
                        forceVictory = false;
                        break;
                    }
                }
            }
        }

        return forceVictory ? new VictoryResult(true, victoryPlayerId, victoryTeam) : VictoryResult.noResult();
    }

    @Override
    protected VictoryResult createReport(Object... data) {
        return null;
    }
}
