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

import java.util.Locale;
import java.util.ResourceBundle;

public enum Locales {
    //region Enum Declarations
    ENGLISH_CANADA("Locales.ENGLISH_CANADA.text", "Locales.ENGLISH_CANADA.toolTipText", "en", "CA"),
    ENGLISH_US("Locales.ENGLISH_US.text", "Locales.ENGLISH_US.toolTipText", "en", "US"),
    GERMAN("Locales.GERMAN.text", "Locales.GERMAN.toolTipText", "de", ""),
    RUSSIAN("Locales.RUSSIAN.text", "Locales.RUSSIAN.toolTipText", "ru", "");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;
    private final Locale locale;
    //endregion Variable Declarations

    //region Constructors
    Locales(final String name, final String toolTipText, final String language, final String country) {
        final ResourceBundle resources = ResourceBundle.getBundle("megamek.client.messages", new EncodeControl());
        this.name = resources.getString(name);
        this.toolTipText = resources.getString(toolTipText);
        this.locale = new Locale(language, country);
    }
    //endregion Constructors

    //region Getters
    public String getToolTipText() {
        return toolTipText;
    }

    public Locale getLocale() {
        return locale;
    }
    //endregion Getters

    //region Boolean Comparison Methods
    public boolean isEnglishCanada() {
        return this == ENGLISH_CANADA;
    }

    public boolean isEnglishUS() {
        return this == ENGLISH_US;
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
