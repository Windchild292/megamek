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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import megamek.common.Entity;
import megamek.common.IGame;
import megamek.common.Player;
import megamek.common.Report;
import megamek.server.victory.VictoryResult;

/**
 * Implements a kill count victory condition. Victory is achieved if a team (or
 * a player with no team) achieves more kills than the set amount. If multiple
 * teams/players achieve the kill condition in a turn, victory is awarded to the
 * player/team with the highest kill count.
 */
public class KillCountVictory extends AbstractVictoryCondition {
    //region Variable Declarations
    private static final long serialVersionUID = -6622529899835634696L;
    private int killTarget;
    //region Variable Declarations

    //region Constructors
    public KillCountVictory(int killTarget) {
        super("KillCountVictory.title");
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
        // Stores the number of kills for each team
        Map<Integer,Integer> killsTeam = new Hashtable<>();
        // Stores the number of kills for players not on a team
        Map<Integer,Integer> killsPlayer = new Hashtable<>();

        updateKillTables(game, killsTeam, killsPlayer, game.getWreckedEntities());
        updateKillTables(game, killsTeam, killsPlayer, game.getCarcassEntities());

        boolean teamHasHighestKills = true;
        int highestKillsId = -1;
        int kills = 0;
        for (Integer killer : killsTeam.keySet()) {
            if (killsTeam.get(killer) > kills) {
                highestKillsId = killer;
                kills = killsTeam.get(killer);
            }
        }

        for (Integer killer : killsPlayer.keySet()) {
            if (killsPlayer.get(killer) > kills) {
                highestKillsId = killer;
                kills = killsPlayer.get(killer);
                teamHasHighestKills = false;
            }
        }

        if (kills >= getKillTarget()) {
            VictoryResult victoryResult = new VictoryResult(true);
            Report report = new Report(7106, Report.PUBLIC);
            if (teamHasHighestKills) {
                report.add("Team " + highestKillsId);
                victoryResult.addTeamScore(highestKillsId, 1.0);
            } else {
                report.add(game.getPlayer(highestKillsId).getName());
                victoryResult.addPlayerScore(highestKillsId, 1.0);
            }
            report.add(kills);
            victoryResult.getReports().add(report);
            return victoryResult;
        } else {
            return VictoryResult.noResult();
        }
    }

    private void updateKillTables(IGame game, Map<Integer, Integer> teamKills,
                                  Map<Integer, Integer> playerKills, Enumeration<Entity> victims) {
        while (victims.hasMoreElements()) {
            Entity wreck = victims.nextElement();
            Entity killer = game.getEntityFromAllSources(wreck.getKillerId());
            
            if (killer == null) {
                continue;
            }            
            
            int team = killer.getOwner().getTeam();
            // Friendly fire doesn't count
            if (team == wreck.getOwner().getTeam()) {
                continue;
            }
            if (team != Player.TEAM_NONE) {
                Integer kills = teamKills.getOrDefault(team, 0);
                teamKills.put(team, ++kills);
            } else {
                Integer player = killer.getOwner().getId();
                // Friendly fire doesn't count
                if (wreck.getOwner().getId() == player) {
                    continue;
                }
                Integer kills = playerKills.getOrDefault(player, 0);
                playerKills.put(player, ++kills);
            }
        }
    }
}
