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

import javax.swing.*;
import java.util.ResourceBundle;

public class MMOptionsDialog extends SuiteOptionsDialog {
    //region Variable Declarations
    //endregion Variable Declarations

    //region Constructors
    public MMOptionsDialog(final JFrame frame) {
        super(frame, "MMOptionsDialog", "MMOptionsDialog.title");
        initialize();
    }

    protected MMOptionsDialog(final JFrame frame, final ResourceBundle resources, final String name,
                              final String title) {
        super(frame, resources, name, title);
    }
    //endregion Constructors

    //region Getters/Setters
    //endregion Getters/Setters

    //region Initialization
    //endregion Initialization
}
