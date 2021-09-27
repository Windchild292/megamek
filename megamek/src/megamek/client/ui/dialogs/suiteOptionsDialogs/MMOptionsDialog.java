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
package megamek.client.ui.dialogs.suiteOptionsDialogs;

import megamek.client.ui.enums.ValidationState;
import megamek.client.ui.panes.suiteOptionsPanes.MMOptionsPane;

import javax.swing.*;

public class MMOptionsDialog extends SuiteOptionsDialog {
    //region Variable Declarations
    private MMOptionsPane mmOptionsPane;
    //endregion Variable Declarations

    //region Constructors
    public MMOptionsDialog(final JFrame frame) {
        this(frame, "MMOptionsDialog");
    }

    protected MMOptionsDialog(final JFrame frame, final String name) {
        super(frame, name, "MMOptionsDialog.title");
    }
    //endregion Constructors

    //region Getters/Setters
    public MMOptionsPane getMMOptionsPane() {
        return mmOptionsPane;
    }

    public void setMMOptionsPane(final MMOptionsPane mmOptionsPane) {
        this.mmOptionsPane = mmOptionsPane;
    }
    //endregion Getters/Setters

    //region Initialization
    @Override
    protected JTabbedPane createCenterPane() {
        super.createCenterPane();

        setMMOptionsPane(new MMOptionsPane(getFrame()));
        getTabbedPane().addTab(resources.getString("mmOptionsPane.title"), getMMOptionsPane());
        return getTabbedPane();
    }
    //endregion Initialization

    //region Button Actions
    @Override
    protected void okAction() {
        super.okAction();
        getMMOptionsPane().save();
    }

    @Override
    protected ValidationState validateAction(final boolean display) {
        final ValidationState currentState = super.validateAction(display);
        if (currentState.isFailure()) {
            return currentState;
        }

        final ValidationState state = getMMOptionsPane().validateData(display, getOkButton());
        return state.isSuccess() ? currentState : state;
    }
    //endregion Button Actions
}
