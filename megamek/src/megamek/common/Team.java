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

import megamek.common.annotations.Nullable;
import megamek.common.enums.TeamNumber;

import java.util.*;
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
    private final TeamNumber teamNumber;
    private final List<Player> players = new ArrayList<>();
    private Boolean observerTeam = null;
    //endregion Variable Declarations

    //region Constructors
    public Team(final TeamNumber teamNumber) {
        this.teamNumber = teamNumber;
    }
    //endregion Constructors

    //region Getters/Setters
    public TeamNumber getTeamNumber() {
        // If Team Initiative is not turned on, the id will be 0 for all teams,
        // However, the players accurately store their team id
        return getPlayers().isEmpty() ? getTeamNumberDirect() : getPlayers().get(0).getTeamNumber();
    }

    public TeamNumber getTeamNumberDirect() {
        return teamNumber;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(final Player player) {
        getPlayers().add(player);
    }

    private Boolean getObserverTeam() {
        return observerTeam;
    }

    private void setObserverTeam(final Boolean observerTeam) {
        this.observerTeam = observerTeam;
    }
    //endregion Getters/Setters

    public int getNonObserverSize() {
        return Math.toIntExact(getPlayers().stream().filter(player -> !player.isObserver()).count());
    }

    public List<Player> getNonObserverPlayers() {
        return getPlayers().stream().filter(player -> !player.isObserver()).collect(Collectors.toList());
    }

    public boolean isObserverTeam() {
        if (getObserverTeam() == null) {
            cacheObserverStatus();
        }
        return getObserverTeam();
    }

    public void cacheObserverStatus() {
        setObserverTeam(getPlayers().stream().allMatch(Player::isObserver));
    }

    /**
     * @param player the current player
     * @return the next valid player on this team, or the current player if there's no more valid
     * players
     */
    public Player getNextValidPlayer(final Game game, final Player player) {
        // start from the next player
        for (int i = getPlayers().indexOf(player) + 1; i < getPlayers().size(); i++) {
            if (game.getTurnForPlayer(getPlayers().get(i).getId()) != null) {
                return getPlayers().get(i);
            }
        }
        // if we haven't found one yet, start again from the beginning
        // worst case we reach exactly our current player again.
        for (int i = 0; i < (getPlayers().indexOf(player) + 1); i++) {
            if (game.getTurnForPlayer(getPlayers().get(i).getId()) != null) {
                return getPlayers().get(i);
            }
        }
        // this should not happen, but if we don't find anything return ourselves again.
        return player;
    }

    public TurnVectors determineTeamOrder(final Game game) {
        return generateTurnOrder(getPlayers(), game);
    }

    public boolean hasTAG() {
        return getPlayers().stream().anyMatch(Player::hasTAG);
    }

    /**
     * Cycle through entities on team and collect all the airborne VTOL/WiGE
     *
     * @return a vector of relevant entity ids
     */
    public List<Integer> getAirborneVTOL() {
        return getPlayers().stream()
                .flatMap(player -> player.getAirborneVTOL().stream())
                .collect(Collectors.toList());
    }

    /**
     * cycle through players team and select the best initiative
     * take negatives only if the current bonus is zero
     */
    public int getTotalInitiativeBonus(final boolean useInitiativeCompensationBonus) {
        int turnBonus = 0;
        int constantInitiativeBonus = Integer.MIN_VALUE;
        int commandBonus = Integer.MIN_VALUE;
        for (final Player player : getPlayers()) {
            turnBonus += player.getTurnInitBonus();
            if (player.getCommandBonus() > commandBonus) {
                commandBonus = player.getCommandBonus();
            }

            if (player.getConstantInitBonus() > constantInitiativeBonus) {
                constantInitiativeBonus = player.getConstantInitBonus();
            }
        }
        return constantInitiativeBonus + turnBonus + commandBonus
                + getInitiativeCompensationBonus(useInitiativeCompensationBonus);
    }

    //region TurnOrdered
    /**
     * Return the number of "normal" turns that this item requires. This is normally the sum of
     * multi-unit turns and the other turns. A team without any "normal" turns must return its
     * number of even turns to produce a fair distribution of moves.
     *
     * @return the <code>int</code> number of "normal" turns this item should take in a phase.
     */
    @Override
    public int getNormalTurns(final Game game) {
        final int normal = getMultiTurns(game) + getOtherTurns();
        return (normal == 0) ? getEvenTurns() : normal;
    }

    @Override
    public int getOtherTurns() {
        // Sum the other turns of all Players in this Team.
        return getPlayers().stream().mapToInt(TurnOrdered::getOtherTurns).sum();
    }

    @Override
    public int getEvenTurns() {
        // Sum the even turns of all Players in this Team.
        return getPlayers().stream().mapToInt(TurnOrdered::getEvenTurns).sum();
    }

    @Override
    public int getMultiTurns(final Game game) {
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
     * Clear the initiative of this object.
     */
    @Override
    public void clearInitiative(final boolean useInitiativeCompensationBonus) {
        getInitiative().clear();
        rollInitiative(getPlayers(), useInitiativeCompensationBonus);
    }

    @Override
    public int getInitCompensationBonus() {
        return getInitiativeCompensationBonus(true);
    }

    public int getInitiativeCompensationBonus(final boolean useInitiativeCompensationBonus) {
        return useInitiativeCompensationBonus
                ? getPlayers().stream().mapToInt(Player::getInitCompensationBonus).max().orElse(0) : 0;
    }

    @Override
    public void setInitCompensationBonus(final int bonus) {
        getPlayers().forEach(player -> player.setInitCompensationBonus(bonus));
    }
    //endregion TurnOrdered

    @Override
    public String toString() {
        return getTeamNumber().toString();
    }

    /**
     * Two teams are equal if their ids and players are equal.
     */
    @Override
    public boolean equals(final @Nullable Object object) {
        if (this == object) {
            return true;
        } else if ((object == null) || (getClass() != object.getClass())) {
            return false;
        } else {
            final Team other = (Team) object;
            return (getTeamNumber() == other.getTeamNumber()) && getPlayers().equals(other.getPlayers());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTeamNumber(), getPlayers());
    }
}
