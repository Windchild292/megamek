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
package megamek.client.ui.enums;

import megamek.common.util.EncodeControl;

import java.util.ResourceBundle;

public enum Locale {
    //region Enum Declarations
    ENGLISH("Locale.ENGLISH.text", "Locale.ENGLISH.toolTipText"),
    ENGLISH_CANADA("Locale.ENGLISH_CANADA.text", "Locale.ENGLISH_CANADA.toolTipText"),
    GERMAN("Locale.GERMAN.text", "Locale.GERMAN.toolTipText"),
    RUSSIAN("Locale.RUSSIAN.text", "Locale.RUSSIAN.toolTipText");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;
    //endregion Variable Declarations

    //region Constructors
    Locale(final String name, final String toolTipText) {
        final ResourceBundle resources = ResourceBundle.getBundle("megamek.client.messages", new EncodeControl());
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
    public boolean isEnglish() {
        return this == ENGLISH;
    }

    public boolean isEnglishCanada() {
        return this == ENGLISH_CANADA;
    }

    public boolean isGerman() {
        return this == GERMAN;
    }

    public boolean isRussian() {
        return this == RUSSIAN;
    }
    //endregion Boolean Comparison Methods

    @Override
    public String toString() {
        return name;
    }
}
