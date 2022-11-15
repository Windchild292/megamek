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
package megamek.client.ui.panels;

import megamek.client.ui.baseComponents.AbstractPanel;
import megamek.client.ui.baseComponents.MMButton;
import megamek.common.annotations.Nullable;

import javax.swing.*;
import java.time.LocalDate;

public class DateSelectionCalendarPanel extends AbstractPanel {
    //region Variable Declarations
    private final LocalDate originDate;
    //endregion Variable Declarations

    //region Constructors
    public DateSelectionCalendarPanel(final JFrame frame, final @Nullable LocalDate originDate) {
        super(frame, "DateSelectionCalendarPanel");
        this.originDate = originDate;
        initialize();
    }
    //endregion Constructors

    //region Getters/Setters
    public @Nullable LocalDate getOriginDate() {
        return originDate;
    }

    public @Nullable LocalDate getSelectedDate() {
        return getOriginDate();
    }
    //endregion Getters/Setters

    //region Initialization
    @Override
    protected void initialize() {
        final JButton btnLastDecade = new MMButton("btnLastDecade", resources, "btnLastDecade.text",
                "btnLastDecade.toolTipText", evt -> {

        });
        final JButton btnLastYear = new MMButton("btnLastYear", resources, "btnLastYear.text",
                "btnLastYear.toolTipText", evt -> {

        });
        final JButton btnLastMonth = new MMButton("btnLastMonth", resources, "btnLastMonth.text",
                "btnLastMonth.toolTipText", evt -> {

        });

        // Month

        // Year

        final JButton btnNextMonth = new MMButton("btnNextMonth", resources, "btnNextMonth.text",
                "btnNextMonth.toolTipText", evt -> {

        });
        final JButton btnNextYear = new MMButton("btnNextYear", resources, "btnNextYear.text",
                "btnNextYear.toolTipText", evt -> {

        });
        final JButton btnNextDecade = new MMButton("btnNextDecade", resources, "btnNextDecade.text",
                "btnNextDecade.toolTipText", evt -> {

        });

        // Calendar

        // Origin
    }
    //endregion Initialization
}
