/*
 * Copyright (C) 2021 - The MegaMek Team. All Rights Reserved
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
package megamek.common.crew;

import megamek.common.Compute;
import megamek.common.Entity;
import megamek.common.Report;
import megamek.common.crew.enums.CombatEdgeTrigger;
import megamek.common.crew.enums.EdgeStyle;
import megamek.common.enums.ReportType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class Edge implements Serializable {
    //region Variable Declarations
    private static final long serialVersionUID = -9105031105272651226L;

    private int count;
    private Set<CombatEdgeTrigger> combatEdgeTriggers;
    //endregion Variable Declarations

    //region Getters/Setters
    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public void incrementCount() {
        count++;
    }

    public void decrementCount() {
        count--;
    }

    public Set<CombatEdgeTrigger> getCombatEdgeTriggers() {
        return combatEdgeTriggers;
    }

    public void setCombatEdgeTriggers(final Set<CombatEdgeTrigger> combatEdgeTriggers) {
        this.combatEdgeTriggers = combatEdgeTriggers;
    }
    //endregion Getters/Setters

    //region Boolean Comparison Methods
    public boolean hasEdgeRemaining() {
        return getCount() > 0;
    }
    //endregion Boolean Comparison Methods

    public boolean doMASCRoll(final EdgeStyle style, final Vector<Report> reports, final Entity entity,
                              final int mascTarget, final int rollModifier) {
        List<Integer> roll = new ArrayList<>();
        boolean success = doIndividualMASCRoll(style, reports, false, entity, mascTarget, roll, rollModifier);

        if (style.isEnabled() && hasEdgeRemaining()
                && getCombatEdgeTriggers().contains(CombatEdgeTrigger.MASC_FAILURE)) {
            decrementCount();
            // Report to notify user that masc check was rerolled
            Report report = new Report(6501, entity.getId(), ReportType.HIDDEN);
            report.indent(2);
            report.addDesc(entity);
            reports.add(report);

            // Report to notify user how much edge pilot has left
            report = new Report(6510, entity.getId(), ReportType.HIDDEN, getCount());
            report.indent(2);
            report.addDesc(entity);
            reports.addElement(report);

            // Recheck MASC failure
            return doIndividualMASCRoll(style, reports, true, entity, mascTarget, roll, rollModifier);
        }

        return success;
    }

    public boolean doIndividualMASCRoll(final EdgeStyle style, final Vector<Report> reports,
                                        final boolean reroll, final Entity entity, final int mascTarget,
                                        final List<Integer> roll, final int rollModifier) {
        if (reroll && style.isDestiny()) {

        } else {
            roll.clear();
            //roll.addAll(Compute.individualDice(2, 6));
        }
        final int rollSum = roll.get(0) - rollModifier;
        final boolean success = rollSum >= mascTarget;

        Report report = new Report(2370, entity.getId(), ReportType.HIDDEN, mascTarget, rollSum);
        report.indent();
        report.choose(success);
        reports.addElement(report);

        return success;
    }

    //region File I/O

    //endregion File I/O
}
