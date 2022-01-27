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
package megamek.client.ui.panels;

import megamek.MegaMek;
import megamek.client.ui.baseComponents.AbstractPanel;
import megamek.client.ui.enums.ValidationState;
import megamek.common.annotations.Nullable;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateSelectionPanel extends AbstractPanel {
    //region Variable Declarations
    private LocalDate currentDate;
    private JTextField txtDate;

    private final DateTimeFormatter[] dateTimeFormatters = {
            DateTimeFormatter.ofPattern(MegaMek.getMMOptions().getDisplayDateFormat())
                    .withLocale(MekHQ.getMekHQOptions().getDateLocale()),
            DateTimeFormatter.ofPattern(MegaMek.getMMOptions().getLongDisplayDateFormat())
                    .withLocale(MekHQ.getMekHQOptions().getDateLocale()),
            DateTimeFormatter.ISO_LOCAL_DATE.withLocale(MekHQ.getMekHQOptions().getDateLocale())
    }
    //endregion Variable Declarations

    //region Constructors
    public DateSelectionDialog(final JFrame frame, final @Nullable LocalDate originDate) {
        super(frame, "DateSelectionDialog", "DateSelectionDialog.title");
        setCurrentDate(originDate);
        initialize();
    }
    //endregion Constructors

    //region Getters/Setters
    public @Nullable LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(final @Nullable LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    public JTextField getTxtDate() {
        return txtDate;
    }

    public void setTxtDate(final JTextField txtDate) {
        this.txtDate = txtDate;
    }

    public @Nullable LocalDate getSelectedDate() {
        return getDateSelectionPanel().getSelectedDate();
    }
    //endregion Getters/Setters

    //region Initialization
    @Override
    protected void initialize() {

    }
    //endregion Initialization

    public ValidationState validateSelectedDate() {
        return ValidationState.SUCCESS;
    }
}
