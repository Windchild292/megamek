/*
 * MegaMek - Copyright (C) 2003, 2004 Ben Mazur (bmazur@sev.org)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 */
/*
 * Copyright (c) 2003-2004 - Ben Mazur (bmazur@sev.org)
 * Copyright (c) 2021 - The MegaMek Team. All Rights Reserved.
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

import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * The Team class holds a list of information about a team. It holds the initiative for the team,
 * and contains a list of players on that team. It also implements functions that gather the number
 * of units each team has.
 *
 * TODO : Allow for teams to ally, not just have multiple players on the same team.
 * TODO : Allow for teams to be neutral to each other
 */
public final class Team extends TurnOrdered {
    //region Variable Declarations
    private static final long serialVersionUID = 2270215552964191597L;

    public static final int NONE = 0;
    public static final int UNASSIGNED = -1;
    public static final String[] NAMES = { "No Team", "Team 1", "Team 2", "Team 3", "Team 4", "Team 5" };

    private List<Player> players = new Vector<>();
    private int id;
    private Boolean ObserverTeam = null;
    //endregion Variable Declarations

    public Team(int newID) {
        id = newID;
    }

    public int getSize() {
        return players.size();
    }

    public int getNonObserverSize() {
        return Math.toIntExact(getPlayers().stream().filter(player -> !player.isObserver()).count());
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Player> getNonObserverPlayers() {
        return getPlayers().stream().filter(player -> !player.isObserver()).collect(Collectors.toList());
    }
    
    public Vector<Player> getPlayersVector() {
        return players;
    }

    public void resetTeam() {
        players.removeAllElements();
    }

    public void addPlayer(Player p) {
        players.addElement(p);
    }
    
    public boolean isObserverTeam() {
        if (ObserverTeam == null) {
            cacheObserverStatus();
        }
        return ObserverTeam;
    }
    
    public void cacheObserverStatus() {
        ObserverTeam = Boolean.TRUE;
        for (Player player : players) {
            if (!player.isObserver()) {
                ObserverTeam = false;
            }
        }
    }

    //get the next player on this team.
    public Player getNextValidPlayer(Player p, Game game) {
        //start from the next player
        for (int i = players.indexOf(p) + 1; i < players.size(); ++i) {
            if (game.getTurnForPlayer(players.get(i).getId()) != null) {
                return players.get(i);
            }
        }
        //if we haven't found one yet, start again from the beginning
        //worst case we reach exactly our current player again.
        for (int i = 0; i < (players.indexOf(p) + 1); ++i) {
            if (game.getTurnForPlayer(players.get(i).getId()) != null) {
                return players.get(i);
            }
        }
        //this should not happen, but if we don't find anything return ourselves again.
        return p;

    }

    /**
     * Clear the initiative of this object.
     */
    @Override
    public void clearInitiative(boolean useInitComp) {
        getInitiative().clear();
        TurnOrdered.rollInitiative(getPlayers(), useInitComp);
    }

    public TurnVectors determineTeamOrder(Game game) {
        return TurnOrdered.generateTurnOrder(players, game);
    }

    public int getId() {
        // If Team Initiative is not turned on, id will be 0 for all teams,
        //  however the players accurately store their team id
        if (players.size() > 0) {
            return players.get(0).getTeam();
        } else {
            return id;
        }
    }

    /**
     * Return the number of "normal" turns that this item requires. This is normally the sum of
     * multi-unit turns and the other turns. A team without any "normal" turns must return its
     * number of even turns to produce a fair distribution of moves.
     *
     * @return the <code>int</code> number of "normal" turns this item should take in a phase.
     */
    @Override
    public int getNormalTurns(Game game) {
        final int normal = getMultiTurns(game) + getOtherTurns();
        return (normal == 0) ? getEvenTurns() : normal;
    }

    @Override
    public int getEvenTurns() {
        // Sum the even turns of all Players in this Team.
        return getPlayers().stream().mapToInt(TurnOrdered::getEvenTurns).sum();
    }

    @Override
    public int getOtherTurns() {
        // Sum the other turns of all Players in this Team.
        return getPlayers().stream().mapToInt(TurnOrdered::getOtherTurns).sum();
    }

    @Override
    public int getMultiTurns(Game game) {
        // Sum the multi turns of all Players in this Team.
        return getPlayers().stream().mapToInt(player -> player.getMultiTurns(game)).sum();
    }

    @Override
    public int getSpaceStationTurns() {
        // Sum the other turns of all Players in this Team.
        return getPlayers().stream().mapToInt(TurnOrdered::getSpaceStationTurns).sum();
    }

    @Override
    public int getJumpshipTurns() {
        // Sum the other turns of all Players in this Team.
        return getPlayers().stream().mapToInt(TurnOrdered::getJumpshipTurns).sum();
    }

    @Override
    public int getWarshipTurns() {
        // Sum the other turns of all Players in this Team.
        return getPlayers().stream().mapToInt(TurnOrdered::getWarshipTurns).sum();
    }

    @Override
    public int getDropshipTurns() {
        // Sum the other turns of all Players in this Team.
        return getPlayers().stream().mapToInt(TurnOrdered::getDropshipTurns).sum();
    }

    @Override
    public int getSmallCraftTurns() {
        // Sum the other turns of all Players in this Team.
        return getPlayers().stream().mapToInt(TurnOrdered::getSmallCraftTurns).sum();
    }

    @Override
    public int getTeleMissileTurns() {
        // Sum the other turns of all Players in this Team.
        return getPlayers().stream().mapToInt(TurnOrdered::getTeleMissileTurns).sum();
    }

    @Override
    public int getAeroTurns() {
        // Sum the other turns of all Players in this Team.
        return getPlayers().stream().mapToInt(TurnOrdered::getAeroTurns).sum();
    }

    /**
     * Two teams are equal if their ids and players are equal. <p/> Override
     * <code>java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if ((null == object) || (getClass() != object.getClass())) {
            return false;
        }
        final Team other = (Team) object;
        return (id == other.id) && Objects.equals(players, other.players);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, players);
    }
    
    @Override
    public String toString() {
        return (getId() < 0) ? "Unassigned" : NAMES[getId()];
    }

    public boolean hasTAG(Game game) {
        for (Enumeration<Player> e = game.getPlayers(); e.hasMoreElements(); ) {
            Player m = e.nextElement();
            if (getId() == m.getTeam()) {
                if (m.hasTAG()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * cycle through players team and select the best initiative
     * take negatives only if the current bonus is zero
     */
    public int getTotalInitBonus(boolean bInitiativeCompensationBonus) {
        int turnb = 0;
        int constantb = Integer.MIN_VALUE;
        int commandb = Integer.MIN_VALUE;
        for (final Player player : getPlayers()) {
            turnb += player.getTurnInitBonus();
            if (player.getCommandBonus() > commandb) {
                commandb = player.getCommandBonus();
            }

            if (player.getConstantInitBonus() > constantb) {
                constantb = player.getConstantInitBonus();
            }
        }
        return constantb + turnb + commandb
                + getInitCompensationBonus(bInitiativeCompensationBonus);
    }
    
    @Override
    public int getInitCompensationBonus() {
        return getInitCompensationBonus(true);
    }

    public int getInitCompensationBonus(final boolean useInitCompensation) {
        return useInitCompensation
                ? getPlayers().stream().mapToInt(Player::getInitCompensationBonus).max().orElse(0) : 0;
    }

    @Override
    public void setInitCompensationBonus(final int bonus) {
        getPlayers().forEach(player -> player.setInitCompensationBonus(bonus));
    }

    /**
     * cycle through entities on team and collect all the airborne VTOL/WIGE
     *
     * @return a vector of relevant entity ids
     */
    public Vector<Integer> getAirborneVTOL() {
        // a vector of unit ids
        Vector<Integer> units = new Vector<>();
        for (Enumeration<Player> loop = players.elements(); loop.hasMoreElements(); ) {
            Player player = loop.nextElement();
            units.addAll(player.getAirborneVTOL());
        }
        return units;
    }
}
