/*
 * Copyright (c) 2021-2022 - The MegaMek Team. All Rights Reserved.
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
package megamek.client.ui.dialogs;

import megamek.client.ui.baseComponents.AbstractDialog;
import megamek.client.ui.panels.DateSelectionCalendarPanel;
import megamek.common.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class DateSelectionDialog extends AbstractDialog {
    //region Variable Declarations
    private final LocalDate originDate;
    private DateSelectionCalendarPanel dateSelectionCalendarPanel;
    //endregion Variable Declarations

    //region Constructors
    public DateSelectionDialog(final JFrame frame, final @Nullable LocalDate originDate) {
        super(frame, "DateSelectionDialog", "DateSelectionDialog.title");
        this.originDate = originDate;
        initialize();
    }
    //endregion Constructors

    //region Getters/Setters
    public @Nullable LocalDate getOriginDate() {
        return originDate;
    }

    public DateSelectionCalendarPanel getDateSelectionCalendarPanel() {
        return dateSelectionCalendarPanel;
    }

    public void setDateSelectionCalendarPanel(final DateSelectionCalendarPanel dateSelectionCalendarPanel) {
        this.dateSelectionCalendarPanel = dateSelectionCalendarPanel;
    }

    public @Nullable LocalDate getSelectedDate() {
        return getDateSelectionCalendarPanel().getSelectedDate();
    }
    //endregion Getters/Setters

    //region Initialization
    @Override
    protected Container createCenterPane() {
        setDateSelectionCalendarPanel(new DateSelectionCalendarPanel(getFrame(), getOriginDate()));
        return getDateSelectionCalendarPanel();
    }
    //endregion Initialization
}
