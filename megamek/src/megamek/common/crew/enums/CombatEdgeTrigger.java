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
package megamek.common.crew.enums;

public enum CombatEdgeTrigger {
    //region Enum Declarations
    HEAD_HIT("CombatEdgeTrigger.HEAD_HIT.text", "CombatEdgeTrigger.HEAD_HIT.toolTipText", "edge_when_headhit"),
    THROUGH_ARMOUR_CRITICAL("CombatEdgeTrigger.THROUGH_ARMOUR_CRITICAL.text", "CombatEdgeTrigger.THROUGH_ARMOUR_CRITICAL.toolTipText", "edge_when_tac"),
    KNOCKED_OUT("CombatEdgeTrigger.KNOCKED_OUT.text", "CombatEdgeTrigger.KNOCKED_OUT.toolTipText", "edge_when_ko"),
    EXPLOSION("CombatEdgeTrigger.EXPLOSION.text", "CombatEdgeTrigger.EXPLOSION.toolTipText", "edge_when_explosion"),
    MASC_FAILURE("CombatEdgeTrigger.MASC_FAILURE.text", "CombatEdgeTrigger.MASC_FAILURE.toolTipText", "edge_when_masc_fails"),
    AEROSPACE_ALTITUDE_LOSS("CombatEdgeTrigger.AEROSPACE_ALTITUDE_LOSS.text", "CombatEdgeTrigger.AEROSPACE_ALTITUDE_LOSS.toolTipText", "edge_when_aero_alt_loss"),
    AEROSPACE_EXPLOSION("CombatEdgeTrigger.AEROSPACE_EXPLOSION.text", "CombatEdgeTrigger.AEROSPACE_EXPLOSION.toolTipText", "edge_when_aero_explosion"),
    AEROSPACE_KNOCKED_OUT("CombatEdgeTrigger.AEROSPACE_KNOCKED_OUT.text", "CombatEdgeTrigger.AEROSPACE_KNOCKED_OUT.toolTipText", "edge_when_aero_ko"),
    AEROSPACE_LUCKY_CRITICAL("CombatEdgeTrigger.AEROSPACE_LUCKY_CRITICAL.text", "CombatEdgeTrigger.AEROSPACE_LUCKY_CRITICAL.toolTipText", "edge_when_aero_lucky_crit"),
    AEROSPACE_NUKE_CRITICAL("CombatEdgeTrigger.AEROSPACE_NUKE_CRITICAL.text", "CombatEdgeTrigger.AEROSPACE_NUKE_CRITICAL.toolTipText", "edge_when_aero_nuke_crit"),
    AEROSPACE_UNIT_CARGO_LOST("CombatEdgeTrigger.AEROSPACE_UNIT_CARGO_LOST.text", "CombatEdgeTrigger.AEROSPACE_UNIT_CARGO_LOST.toolTipText", "edge_when_aero_unit_cargo_lost");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;
    private final String legacyName;
    //endregion Variable Declarations

    //region Constructors
    CombatEdgeTrigger(final String name, final String toolTipText, final String legacyName) {
        this.name = name;
        this.toolTipText = toolTipText;
        this.legacyName = legacyName;
    }
    //endregion Constructors

    //region Getters
    public String getToolTipText() {
        return toolTipText;
    }

    public String getLegacyName() {
        return legacyName;
    }
    //endregion Getters

    @Override
    public String toString() {
        return name;
    }
}
