/*
 * MegaMek - Copyright (C) 2004-2005 Ben Mazur (bmazur@sev.org)
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
package megamek.common.weapons.infantry;

import megamek.common.AmmoType;

/**
 * @author Ben Grills
 * @since Sept 7, 2005
 */
public class InfantryArchaicVibroAxeWeapon extends InfantryWeapon {
    public InfantryArchaicVibroAxeWeapon() {
        super();

        name = "Blade (Vibro-axe)";
        setInternalName(name);
        addLookupName("InfantryVibroAxe");
        addLookupName("Vibro Axe");
        ammoType = AmmoType.T_NA;
        cost = 150;
        bv = 0.39;
        tonnage = .005; 
        flags = flags.or(F_NO_FIRES).or(F_INF_POINT_BLANK).or(F_INF_ARCHAIC);
        infantryDamage = 0.42;
        infantryRange = 0;
        rulesRefs = "272, TM";
        techAdvancement.setTechBase(TECH_BASE_ALL)
                .setISAdvancement(2435, 2445, 2600, DATE_NONE, DATE_NONE)
                .setISApproximate(true, false, false, false, false)
                .setPrototypeFactions(F_LC)
                .setProductionFactions(F_LC)
                .setTechRating(RATING_E)
                .setAvailability(RATING_C, RATING_D, RATING_D, RATING_C);
    }
}
