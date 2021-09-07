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
package megamek;

import megamek.client.ui.enums.Locales;
import megamek.client.ui.swing.UITheme;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

/**
 * Suite Options are persistent options that hold throughout the suite (MM/MML/MHQ). The constants
 * used for storing these are located in {@link SuiteConstants}
 */
public abstract class SuiteOptions {
    //region Variable Declarations
    protected static final Preferences preferences = Preferences.userRoot();
    //endregion Variable Declarations

    //region Display
    public Locales getLocale() {
        return Locales.valueOf(preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).get(SuiteConstants.LOCALE, Locales.ENGLISH_US.name()));
    }

    public void setLocale(final Locales locale) {
        preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).put(SuiteConstants.LOCALE, locale.name());
    }

    public String getTheme() {
        return preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).get(SuiteConstants.THEME, UIManager.getLookAndFeel().getClass().getName());
    }

    public void setTheme(final UITheme theme) {
        preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).put(SuiteConstants.THEME, theme.getClassName());
    }

    public float getGUIScale() {
        return preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).getFloat(SuiteConstants.GUI_SCALE, 1f);
    }

    public void setGUIScale(final float guiScale) {
        preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).putFloat(SuiteConstants.GUI_SCALE, guiScale);
    }

    public int getTooltipPopupDelay() {
        return preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).getInt(SuiteConstants.TOOLTIP_POPUP_DELAY, 1000);
    }

    public void setTooltipPopupDelay(final int tooltipPopupDelay) {
        preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).putInt(SuiteConstants.TOOLTIP_POPUP_DELAY, tooltipPopupDelay);
    }

    public int getTooltipDismissDelay() {
        return preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).getInt(SuiteConstants.TOOLTIP_DISMISS_DELAY, -1);
    }

    public void setTooltipDismissDelay(final int tooltipDismissDelay) {
        preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).putInt(SuiteConstants.TOOLTIP_DISMISS_DELAY, tooltipDismissDelay);
    }

    //region Colours
    public Color getSuccessColour() {
        return new Color(preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).getInt(SuiteConstants.SUCCESS_COLOUR, 0X2BAD43));
    }

    public void setSuccessColour(final Color successColour) {
        preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).putInt(SuiteConstants.SUCCESS_COLOUR, successColour.getRGB());
    }

    public Color getWarningColour() {
        return new Color(preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).getInt(SuiteConstants.WARNING_COLOUR, Color.ORANGE.getRGB()));
    }

    public void setWarningColour(final Color warningColour) {
        preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).putInt(SuiteConstants.WARNING_COLOUR, warningColour.getRGB());
    }

    public Color getErrorColour() {
        return new Color(preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).getInt(SuiteConstants.ERROR_COLOUR, Color.RED.getRGB()));
    }

    public void setErrorColour(final Color errorColour) {
        preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).putInt(SuiteConstants.ERROR_COLOUR, errorColour.getRGB());
    }
    //endregion Colours
    //endregion Display

    //region Miscellaneous Options
    public boolean getUseCamouflageOverlay() {
        return preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).getBoolean(SuiteConstants.USE_CAMOUFLAGE_OVERLAY, true);
    }

    public void setUseCamouflageOverlay(final boolean useCamouflageOverlay) {
        preferences.node(SuiteConstants.SUITE_DISPLAY_NODE).putBoolean(SuiteConstants.USE_CAMOUFLAGE_OVERLAY, useCamouflageOverlay);
    }
    //endregion Miscellaneous Options
}
