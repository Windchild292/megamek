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
package megamek.common.enums;

import megamek.common.generators.lightGenerators.*;
import megamek.common.preference.PreferenceManager;
import megamek.common.util.EncodeControl;

import java.util.ResourceBundle;

public enum LightGenerationMethod {
    //region Enum Declarations
    NONE("LightGenerationMethod.NONE.text", "LightGenerationMethod.NONE.toolTipText"),
    ATB("LightGenerationMethod.ATB.text", "LightGenerationMethod.ATB.toolTipText"),
    TACTICAL_OPERATIONS("LightGenerationMethod.TACTICAL_OPERATIONS.text", "LightGenerationMethod.TACTICAL_OPERATIONS.toolTipText"),
    WINDCHILD("LightGenerationMethod.WINDCHILD.text", "LightGenerationMethod.WINDCHILD.toolTipText");
    //endregion Enum Declarations

    //region Variable Declarations
    private final String name;
    private final String toolTipText;
    //endregion Variable Declarations

    //region Constructors
    LightGenerationMethod(final String name, final String toolTipText) {
        final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.messages",
                PreferenceManager.getClientPreferences().getLocale(), new EncodeControl());
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
    public boolean isNone() {
        return this == NONE;
    }

    public boolean isAtB() {
        return this == ATB;
    }

    public boolean isTacticalOperations() {
        return this == TACTICAL_OPERATIONS;
    }

    public boolean isWindchild() {
        return this == WINDCHILD;
    }
    //endregion Boolean Comparison Methods

    public AbstractLightGenerator getGenerator() {
        switch (this) {
            case ATB:
                return new AtBLightGenerator();
            case TACTICAL_OPERATIONS:
                return new TacOpsLightGenerator();
            case WINDCHILD:
                return new WindchildLightGenerator();
            case NONE:
            default:
                return new DisabledLightGenerator();
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
