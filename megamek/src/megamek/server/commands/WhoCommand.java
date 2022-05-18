/*
 * Copyright (c) 2000-2002 - Ben Mazur (bmazur@sev.org).
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
package megamek.server.commands;

import megamek.common.net.connections.AbstractConnection;
import megamek.common.preference.PreferenceManager;
import megamek.server.Server;

/**
 * Lists all the players connected and some info about them.
 */
public class WhoCommand extends ServerCommand {

    /** Creates new WhoCommand */
    public WhoCommand(Server server) {
        super(server, "who",
                "Lists all of the players connected to the server.");
    }

    @Override
    public void run(int connId, String... args) {
        server.sendServerChat(connId, "Listing all connections...");
        server.sendServerChat(
                connId, "[id#] : [name], [address], [pending], [bytes sent], [bytes received]");

        final boolean includeIPAddress = PreferenceManager.getClientPreferences().getShowIPAddressesInChat();
        server.forEachConnection(connection -> server.sendServerChat(connId, getConnectionDescription(connection, includeIPAddress)));
        server.sendServerChat(connId, "end list");
    }

    private String getConnectionDescription(AbstractConnection conn, boolean includeIPAddress) {
        return conn.getId() + " : " + server.getPlayer(conn.getId()).getName() + ", "
                + (includeIPAddress ? conn.getInetAddress() : "<hidden>") + ", " + conn.hasPending()
                + ", " + conn.getBytesSent() + ", " + conn.getBytesReceived();
    }
}
