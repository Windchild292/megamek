/*
 * Copyright (c) 2022 - The MegaMek Team. All Rights Reserved.
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
package megamek.common.util.enums;

import megamek.MegaMek;
import megamek.common.util.EncodeControl;
import megamek.common.util.randomization.AbstractRandom;
import megamek.common.util.randomization.MMRandom;
import megamek.common.util.randomization.MMSecureRandom;
import megamek.common.util.randomization.Pool36Random;
import org.apache.logging.log4j.LogManager;

import java.util.ResourceBundle;

public enum RandomizationMethod {
    //region Enum Declarations
    RANDOM("RandomizationMethod.RANDOM.text",  "RandomizationMethod.RANDOM.toolTipText"),
    SECURE_RANDOM("RandomizationMethod.SECURE_RANDOM.text",  "RandomizationMethod.SECURE_RANDOM.toolTipText"),
    POOL36("RandomizationMethod.POOL36.text",  "RandomizationMethod.POOL36.toolTipText");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;
    //endregion Variable Declarations

    //region Constructors
    RandomizationMethod(final String name, final String toolTipText) {
        final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.messages",
                MegaMek.getMMOptions().getLocale(), new EncodeControl());
        this.name = resources.getString(name);
        this.toolTipText = resources.getString(toolTipText);
    }
    //endregion Constructors

    //region Getters
    public String getToolTipText() {
        return toolTipText;
    }
    //endregion Getters

    //region Boolean Comparison Methods
    public boolean isRandom() {
        return this == RANDOM;
    }

    public boolean isSecureRandom() {
        return this == SECURE_RANDOM;
    }

    public boolean isPool36() {
        return this == POOL36;
    }
    //endregion Boolean Comparison Methods

    public AbstractRandom getGenerator() {
        switch (this) {
            case SECURE_RANDOM:
                return new MMSecureRandom();
            case POOL36:
                return new Pool36Random();
            case RANDOM:
            default:
                return new MMRandom();
        }
    }

    //region File I/O
    public static RandomizationMethod parseFromString(final String text) {
        try {
            return valueOf(text);
        } catch (Exception ignored) {

        }

        try {
            switch (Integer.parseInt(text)) {
                case 0:
                    return RANDOM;
                case 1:
                    return SECURE_RANDOM;
                case 2:
                    return POOL36;
                default:
                    break;
            }
        } catch (Exception ignored) {

        }

        LogManager.getLogger().error("Unable to parse " + text + " into a RandomizationMethod. Returning RANDOM.");

        return RANDOM;
    }
    //endregion File I/O

    @Override
    public String toString() {
        return name;
    }
}
