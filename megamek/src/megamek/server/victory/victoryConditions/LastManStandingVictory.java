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
import megamek.server.victory.VictoryResult;

/**
 * implementation of "last player/team standing"
 */
public class LastManStandingVictory extends AbstractVictoryCondition {
    //region Variable Declarations
    private static final long serialVersionUID = 3372431109525075853L;
    //endregion Variable Declarations

    //region Constructors
    public LastManStandingVictory() {
        super("LastManStandingVictory.title");
    }
    //endregion Constructors

    @Override
    public VictoryResult victory(IGame game) {
        // check all players/teams for aliveness
        int playersAlive = 0;
        IPlayer lastPlayer = null;
        boolean oneTeamAlive = false;
        int lastTeam = IPlayer.TEAM_NONE;
        boolean unteamedAlive = false;
        for (IPlayer player : game.getPlayersVector()) {
            int team = player.getTeam();
            if (game.getLiveDeployedEntitiesOwnedBy(player) <= 0) {
                continue;
            }
            // we found a live one!
            playersAlive++;
            lastPlayer = player;
            // check team
            if (team == IPlayer.TEAM_NONE) {
                unteamedAlive = true;
            } else if (lastTeam == IPlayer.TEAM_NONE) {
                // possibly only one team alive
                oneTeamAlive = true;
                lastTeam = team;
            } else if (team != lastTeam) {
                // more than one team alive
                oneTeamAlive = false;
                lastTeam = team;
            }
        }

        // check if there's one player alive
        if (playersAlive < 1) {
            return VictoryResult.drawResult();
        } else if ((playersAlive == 1) && (lastPlayer.getTeam() == IPlayer.TEAM_NONE)) {
            // individual victory
            return new VictoryResult(true, lastPlayer.getId(), IPlayer.TEAM_NONE);
        }

        return (oneTeamAlive && !unteamedAlive)
                ? new VictoryResult(true, IPlayer.PLAYER_NONE, lastTeam)
                : VictoryResult.noResult();
    }
}
