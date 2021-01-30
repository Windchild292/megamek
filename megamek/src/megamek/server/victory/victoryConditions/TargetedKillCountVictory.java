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
package megamek.server.victory.victoryConditions;

import megamek.common.Entity;
import megamek.common.IGame;
import megamek.common.IPlayer;
import megamek.common.Report;
import megamek.common.Team;
import megamek.common.annotations.Nullable;
import megamek.server.victory.VictoryResult;

import java.util.Enumeration;

public class TargetedKillCountVictory extends AbstractTargetedVictoryCondition {
    //region Variable Declarations
    private static final long serialVersionUID = 3141435874368189755L;
    private int killTarget;
    //endregion Variable Declarations

    //region Constructors
    public TargetedKillCountVictory(@Nullable Team originTeam, @Nullable IPlayer originPlayer,
                                    @Nullable Team targetTeam, @Nullable IPlayer targetPlayer,
                                    int killTarget) {
        super("TargetedKillCountVictory.title", originTeam, originPlayer, targetTeam, targetPlayer);
        setKillTarget(killTarget);
    }
    //endregion Constructors

    //region Getters/Setters
    public int getKillTarget() {
        return killTarget;
    }

    public void setKillTarget(int killTarget) {
        this.killTarget = killTarget;
    }
    //endregion Getters/Setters

    @Override
    public VictoryResult victory(IGame game) {
        final boolean isOriginPlayer = getOriginPlayer() != null;
        final boolean isTargetPlayer = getTargetPlayer() != null;
        int kills = determineKills(game, game.getWreckedEntities(), isOriginPlayer, isTargetPlayer);
        kills += determineKills(game, game.getCarcassEntities(), isOriginPlayer, isTargetPlayer);

        if (kills >= getKillTarget()) {
            VictoryResult victoryResult = new VictoryResult(true, getOriginTeam().getId(), getOriginPlayer());
            Report report = new Report(7107, Report.PUBLIC);
            if (isOriginPlayer) {
                report.add(getOriginPlayer().getName());
                victoryResult.addPlayerScore(getOriginPlayer().getId(), 1.0);
            } else {
                report.add(getOriginTeam().toString());
                victoryResult.addTeamScore(getOriginTeam().getId(), 1.0);
            }
            report.add(isTargetPlayer ? getTargetPlayer().getName() : getTargetTeam().toString());
            report.add(Math.max(kills, 0));
            victoryResult.getReports().add(report);
            return victoryResult;
        } else {
            return VictoryResult.noResult();
        }
    }

    private int determineKills(IGame game, Enumeration<Entity> victims,
                               final boolean isOriginPlayer, final boolean isTargetPlayer) {
        int kills = 0;
        while (victims.hasMoreElements()) {
            Entity wreck = victims.nextElement();
            Entity killer = game.getEntityFromAllSources(wreck.getKillerId());

            if (killer == null) {
                continue;
            }

            // To be counted as a kill both of the following must be true
            // 1) The killer must either be owned by the origin player (for origin player objectives)
            // or by the origin team (for origin team objectives).
            // 2) The wreck must either be owned by the target player (for target player objectives)
            // or by the target team (for target team objectives)
            if (((isOriginPlayer && killer.getOwner().equals(getOriginPlayer()))
                    || (!isOriginPlayer && getOriginTeam().equals(killer.getOwner().getTeamObject())))
                    && ((isTargetPlayer && wreck.getOwner().equals(getTargetPlayer()))
                    || (!isTargetPlayer && getTargetTeam().equals(wreck.getOwner().getTeamObject())))) {
                kills++;
            }
        }
        return kills;
    }
}
