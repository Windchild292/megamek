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
public class InfantryArchaicClanVibroSwordWeapon extends InfantryWeapon {
	public InfantryArchaicClanVibroSwordWeapon() {
		super();

		name = "Blade (Vibro-sword) [Clan]";
		setInternalName(name);
		addLookupName("InfantryClanVibroSword");
		addLookupName("Clan Vibro Sword");
		ammoType = AmmoType.T_NA;
		cost = 500;
		bv = 0.34;
        tonnage = .004;
		flags = flags.or(F_NO_FIRES).or(F_INF_POINT_BLANK).or(F_INF_ARCHAIC);
		infantryDamage = 0.37;
		infantryRange = 0;
		rulesRefs = "272,TM";
		techAdvancement.setTechBase(TECH_BASE_CLAN)
                .setClanAdvancement(2815, 2820, DATE_NONE, DATE_NONE, DATE_NONE)
				.setClanApproximate(true, false, false, false, false)
                .setPrototypeFactions(F_CJF)
				.setProductionFactions(F_CJF)
                .setTechRating(RATING_F)
				.setAvailability(RATING_X, RATING_F, RATING_E, RATING_D);
	}
}
