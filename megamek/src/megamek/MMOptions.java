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

import megamek.client.ui.swing.boardview.BoardView1;

public final class MMOptions extends SuiteOptions {
    //region Display
    public int getTooltipDistanceSuppression() {
        return preferences.node(MMConstants.SUITE_DISPLAY_NODE).getInt(MMConstants.TOOLTIP_DISTANCE_SUPPRESSION, BoardView1.HEX_DIAG);
    }

    public void setTooltipDistanceSuppression(final int tooltipDistanceSuppression) {
        preferences.node(MMConstants.SUITE_DISPLAY_NODE).putInt(MMConstants.TOOLTIP_DISTANCE_SUPPRESSION, tooltipDistanceSuppression);
    }
    //endregion Display

    public String getLastConnectionAddress() {
        return preferences.node(MMConstants.SUITE_DISPLAY_NODE).get(MMConstants.LAST_CONNECTION_ADDRESS, "localhost");
    }

    public void setLastConnectionAddress(final String lastConnectionAddress) {
        preferences.node(MMConstants.SUITE_DISPLAY_NODE).put(MMConstants.LAST_CONNECTION_ADDRESS, lastConnectionAddress);
    }

    public int getLastConnectionPort() {
        return preferences.node(MMConstants.SUITE_DISPLAY_NODE).getInt(MMConstants.LAST_CONNECTION_PORT, 2346);
    }

    public void setLastConnectionPort(final int lastConnectionPort) {
        preferences.node(MMConstants.SUITE_DISPLAY_NODE).putInt(MMConstants.LAST_CONNECTION_PORT, lastConnectionPort);
    }

    public String getLastPlayerName() {
        return preferences.node(MMConstants.SUITE_DISPLAY_NODE).get(MMConstants.LAST_PLAYER_NAME, "");
    }

    public void setLastPlayerName(final String lastPlayerName) {
        preferences.node(MMConstants.SUITE_DISPLAY_NODE).put(MMConstants.LAST_PLAYER_NAME, lastPlayerName);
    }

    public String getLastServerPassword() {
        return preferences.node(MMConstants.SUITE_DISPLAY_NODE).get(MMConstants.LAST_SERVER_PASSWORD, "");
    }

    public void setLastServerPassword(final String lastServerPassword) {
        preferences.node(MMConstants.SUITE_DISPLAY_NODE).put(MMConstants.LAST_SERVER_PASSWORD, lastServerPassword);
    }

    public int getLastServerPort() {
        return preferences.node(MMConstants.SUITE_DISPLAY_NODE).getInt(MMConstants.LAST_SERVER_PORT, 2346);
    }

    public void setLastServerPort(final int lastServerPort) {
        preferences.node(MMConstants.SUITE_DISPLAY_NODE).putInt(MMConstants.LAST_SERVER_PORT, lastServerPort);
    }

    public String getMetaServerName() {
        return preferences.node(MMConstants.SUITE_DISPLAY_NODE).get(MMConstants.META_SERVER_NAME, "https://api.megamek.org/servers/announce");
    }

    public void setMetaServerName(final String metaServerName) {
        preferences.node(MMConstants.SUITE_DISPLAY_NODE).put(MMConstants.META_SERVER_NAME, metaServerName);
    }
}
