/*
 * Copyright (c) 2021 - The MegaMek Team. All Rights Reserved.
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
package megamek.client.ui.swing;

import megamek.client.ui.Messages;
import megamek.client.ui.swing.unitDisplay.UnitDisplay;
import megamek.client.ui.swing.util.UIUtil;
import megamek.client.ui.swing.widget.DetachablePane;
import org.apache.logging.log4j.LogManager;

/**
 * Displays the unit detail pane to players.
 */
public class UnitDetailPane extends DetachablePane {
    private UnitDisplay detail;

    public UnitDetailPane(UnitDisplay detail) {
        super(Messages.getString("ClientGUI.MechDisplay"), detail);
        this.detail = detail;

        var window = getWindow();
        window.setLocation(
                GUIPreferences.getInstance().getUnitDetailPosX(),
                GUIPreferences.getInstance().getUnitDetailPosY()
        );
        window.setSize(
                GUIPreferences.getInstance().getUnitDetailSizeWidth(),
                GUIPreferences.getInstance().getUnitDetailSizeHeight()
        );
        window.setResizable(true);
        UIUtil.updateWindowBounds(window);

        try {
            setState(GUIPreferences.getInstance().getUnitDetailState());
        } catch (Exception e) {
            LogManager.getLogger().error("Error setting unit detail state", e);
        }
    }

    public UnitDisplay getDetail() {
        return this.detail;
    }

    @Override
    public void setState(Mode newState) {
        var existing = getState();
        super.setState(newState);
        if (newState != existing) {
            GUIPreferences.getInstance().setUnitDetailState(newState);
        }
    }
}
