/*
 * MegaMek - Copyright (C) 2005 Ben Mazur (bmazur@sev.org)
 *
 *  This program is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the Free
 *  Software Foundation; either version 2 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *  or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *  for more details.
 */
package megamek.client.ui.swing.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import megamek.MegaMek;
import megamek.client.ui.swing.GUIPreferences;
import megamek.common.IPlayer;
import megamek.common.util.EncodeControl;

public enum PlayerColor {
    BLUE("PlayerColour.BLUE", 0x8686BF),
    RED("PlayerColour.RED", 0xCC6666),
    GREEN("PlayerColour.GREEN", 0x87BF86),
    CYAN("PlayerColour.CYAN", 0x8FCCCC),
    PINK("PlayerColour.PINK", 0xF29DC8),
    ORANGE("PlayerColour.ORANGE", 0xF2AA61),
    GRAY("PlayerColour.GRAY", 0xBEBEBE),
    BROWN("PlayerColour.BROWN", 0x98816B),
    PURPLE("PlayerColour.PURPLE", 0x800080),
    TURQUOISE("PlayerColour.TURQUOISE", 0x40E0D0),
    MAROON("PlayerColour.MAROON", 0x800000),
    SPRING_GREEN("PlayerColour.SPRING_GREEN", 0x00FF7F),
    GOLD("PlayerColour.GOLD", 0xFFD700),
    SIENNA("PlayerColour.SIENNA", 0xA0522D),
    VIOLET("PlayerColour.VIOLET", 0xEE82EE),
    NAVY("PlayerColour.NAVY", 0x000080),
    OLIVE_DRAB("PlayerColour.OLIVE_DRAB", 0x6B8E23),
    FUCHSIA("PlayerColour.FUCHSIA", 0xFF00FF),
    FIRE_BRICK("PlayerColour.FIRE_BRICK", 0xB22222),
    DARK_GOLDENROD("PlayerColour.DARK_GOLDENROD", 0xB8860B),
    CORAL("PlayerColour.CORAL", 0xFF7F50),
    CHARTREUSE("PlayerColour.CHARTREUSE", 0x7FFF00),
    DEEP_PURPLE("PlayerColour.DEEP_PURPLE", 0x9400D3),
    YELLOW("PlayerColour.YELLOW", 0xF2F261);

    //region Variable Declarations
    private final String colourName;
    private final int colourHexCode;

    private final ResourceBundle resources = ResourceBundle.getBundle("megamek.client.GUIEnumProperties",
            new EncodeControl());
    //endregion Variable Declarations

    PlayerColor(String colourName, int colourHexCode) {
        this.colourName = resources.getString(colourName);
        this.colourHexCode = colourHexCode;
    }
    
    public int getColourHexCode() {
        return colourHexCode;
    }

    public Color getColour() {
        return getColour(true);
    }

    public Color getColour(boolean allowTransparency) {
        if (allowTransparency) {
            int transparency = GUIPreferences.getInstance().getInt(
                    GUIPreferences.ADVANCED_ATTACK_ARROW_TRANSPARENCY);
            return new Color(getColourHexCode() | (transparency << 24), true);
        } else {
            return new Color(getColourHexCode());
        }
    }

    public static void ensureUniqueColours(Enumeration<IPlayer> players) {


        List<PlayerColor> selectedColours = getSelectedColours(players);
        if () {

        }

        boolean[] colorUsed = new boolean[IPlayer.colorNames.length];
        for (Enumeration<IPlayer> i = game.getPlayers(); i.hasMoreElements(); ) {
            final IPlayer otherPlayer = i.nextElement();
            if (otherPlayer.getId() != playerId) {
                colorUsed[otherPlayer.getColorIndex()] = true;
            }
        }
        if ((null != player) && colorUsed[player.getColorIndex()]) {
            // find a replacement color;
            for (int i = 0; i < colorUsed.length; i++) {
                if (!colorUsed[i]) {
                    player.setColorIndex(i);
                    break;
                }
            }
        }
    }

    public static PlayerColor getNextUnusedColour(Enumeration<IPlayer> players, int playerId) {
        List<PlayerColor> selectedColours = getSelectedColours(players, playerId);

        for (PlayerColor colour : values()) {
            if (!selectedColours.contains(colour)) {
                return colour;
            }
        }

        MegaMek.getLogger().error(PlayerColor.class, "No unused colours remaining to select for a player colour, returning BLUE");
        return BLUE;
    }

    private static List<PlayerColor> getSelectedColours(Enumeration<IPlayer> players, int playerId) {
        List<PlayerColor> selectedColours = new ArrayList<>();
        while (players.hasMoreElements()) {
            final IPlayer p = players.nextElement();
            if (p.getId() == playerId) {
                continue;
            }
            selectedColours.add(p.getColour());
        }
        return selectedColours;
    }

    @Override
    public String toString() {
        return colourName;
    }
}
