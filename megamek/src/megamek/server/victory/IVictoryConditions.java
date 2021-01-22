/*
 * MegaMek - Copyright (C) 2007-2008 Ben Mazur (bmazur@sev.org)
 *
 *  This program is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the Free
 *  Software Foundation; either version 2 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *  or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *  for more details.
 */
package megamek.server.victory;

import java.util.Map;

import megamek.common.IGame;

/**
 * Interface for classes judging whether a victory occurred or not. These
 * classes must NOT modify game state. Reporting must be done via the given
 * interface. Classes also should not have any nasty internal state outside
 * methods, thus guaranteeing them to act ONLY based on game state. The only
 * case where this should be done is in cases where you have to count occurrences
 * of some action or duration of something. In this case the information should
 * be stored in the context-object NOTE: if you delegate from one victory-class
 * instance to other, they must be created in similar fashion (ie. constructed
 * at the same time and destroyed at the same time) to guarantee proper functionality
 * Also, if you delegate, you should delegate each time to guarantee that
 * victory point-counting and duration-counting implementations get access to
 * game state every round.
 *
 * Note: calling victory 1 time or n times per round should not make a difference!
 * Victories counting rounds must not assume that they are called only once per round
 *
 * Note: Don't even think about making different victory classes communicate straight or
 * via context-object
 *
 * Note: Victories should take into account in their reporting (adding reports to the
 * result-object) so that that their results might be filtered. So the reports should be
 * mostly of the "what is the score"-type (player A occupies the victory
 * location) or fact-type (Player A has destroyed player B's commander)
 */
public interface IVictoryConditions {
    /**
     * @param game - the game (state) we are playing
     * @param context - a map of Strings to simple serializable objects (preferably
     *            Integers, Strings, Doubles, etc) which are used to store state
     *            between executions if such feature is absolutely required... as
     *            a key you should use something at least class-specific to
     *            limit namespace collisions
     * @return a result with true if victory occurred, false if not. Not Nullable, and cannot modify game state!
     */
    VictoryResult victory(IGame game, Map<String, Object> context);
}
