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

import megamek.client.ui.baseComponents.AbstractValidationButtonDialog;
import megamek.client.ui.enums.ValidationState;
import megamek.client.ui.panes.suiteOptionsPanes.SuiteOptionsPane;

import javax.swing.*;

public abstract class SuiteOptionsDialog extends AbstractValidationButtonDialog {
    //region Variable Declarations
    private JTabbedPane tabbedPane;
    private SuiteOptionsPane suiteOptionsPane;
    //endregion Variable Declarations

    //region Constructors
    protected SuiteOptionsDialog(final JFrame frame, final String name, final String title) {
        super(frame, name, title);
        initialize();
    }
    //endregion Constructors

    //region Getters/Setters
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void setTabbedPane(final JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }

    public SuiteOptionsPane getSuiteOptionsPane() {
        return suiteOptionsPane;
    }

    public void setSuiteOptionsPane(final SuiteOptionsPane suiteOptionsPane) {
        this.suiteOptionsPane = suiteOptionsPane;
    }
    //endregion Getters/Setters

    //region Initialization
    @Override
    protected JTabbedPane createCenterPane() {
        setTabbedPane(new JTabbedPane());
        getTabbedPane().setName("suiteOptionsTabbedPane");

        setSuiteOptionsPane(new SuiteOptionsPane(getFrame()));
        getTabbedPane().addTab(resources.getString("suiteOptionsPane.title"), getSuiteOptionsPane());
        return getTabbedPane();
    }
    //endregion Initialization

    //region Button Actions
    @Override
    protected void okAction() {
        super.okAction();
        getSuiteOptionsPane().save();
    }

    @Override
    protected ValidationState validateAction(final boolean display) {
        return getSuiteOptionsPane().validateData(display, getOkButton());
    }
    //endregion Button Actions
}
