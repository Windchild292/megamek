/*
 * MegaMekConstants.java
 *
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

public final class MMConstants extends SuiteConstants {
    //region MegaMek Options
    //region Display Node
    public static final String DISPLAY_NODE = "megamek/prefs/display";
    public static final String TOOLTIP_DISTANCE_SUPPRESSION = "tooltipDistanceSuppression";
    //endregion Display Node

    //region File Paths
    public static final String FILE_PATH_NODE = "megamek/prefs/filepaths";
    //endregion File Paths

    //region Nag Tab
    public static final String NAG_NODE = "megamek/prefs/nags";
    //endregion Nag Tab

    //region Miscellaneous Options
    public static final String MISCELLANEOUS_NODE = "megamek/prefs/miscellaneous";
    //endregion Miscellaneous Options

    public static final String LAST_CONNECTION_ADDRESS = "lastConnectionAddress";
    public static final String LAST_CONNECTION_PORT = "lastConnectionPort";
    public static final String LAST_PLAYER_NAME = "lastPlayerName";
    public static final String LAST_SERVER_PASSWORD = "lastServerPassword";
    public static final String LAST_SERVER_PORT = "lastServerPort";
    public static final String META_SERVER_NAME = "metaServerName";
    public static final String CHAT_BOX_SIZE = "chatBoxSize";
    public static final String CHAT_LOUNGE_TAB_FONT_SIZE = "chatLoungeTabFontSize";
    public static final String MECH_DISPLAY_ARMOR_LARGE_FONT_SIZE = "metaServerName";
    public static final String MECH_DISPLAY_ARMOR_MEDIUM_FONT_SIZE = "metaServerName";
    public static final String MECH_DISPLAY_ARMOR_SMALL_FONT_SIZE = "metaServerName";
    public static final String MECH_DISPLAY_LARGE_FONT_SIZE = "metaServerName";
    public static final String MECH_DISPLAY_MEDIUM_FONT_SIZE = "metaServerName";
    public static final String MECH_DISPLAY_WRAP_LENGTH = "metaServerName";
    public static final String MOVE_DEFAULT_CLIMB_MODE = "metaServerName";
    public static final String MOVE_DEFAULT_COLOR = "metaServerName";
    public static final String MOVE_ILLEGAL_COLOR = "metaServerName";
    public static final String MOVE_JUMP_COLOR = "metaServerName";
    public static final String MOVE_MASC_COLOR = "metaServerName";
    public static final String MOVE_RUN_COLOR = "metaServerName";
    public static final String MOVE_BACK_COLOR = "metaServerName";
    public static final String MOVE_SPRINT_COLOR = "metaServerName";
    public static final String MOVE_FONT_TYPE = "metaServerName";
    public static final String MOVE_FONT_SIZE = "metaServerName";
    public static final String MOVE_FONT_STYLE = "metaServerName";
    public static final String MOVE_STEP_DELAY = "metaServerName";
    public static final String FIRE_SOLN_CANSEE_COLOR = "metaServerName";
    public static final String FIRE_SOLN_NOSEE_COLOR = "metaServerName";
    public static final String DARKEN_MAP_AT_NIGHT = "metaServerName";
    public static final String MAPSHEET_COLOR = "metaServerName";
    public static final String TRANSLUCENT_HIDDEN_UNITS = "metaServerName";
    public static final String ATTACK_ARROW_TRANSPARENCY = "metaServerName";
    public static final String BUILDING_TEXT_COLOR = "metaServerName";
    public static final String CHATBOX2_FONTSIZE = "metaServerName";
    public static final String CHATBOX2_BACKCOLOR = "metaServerName";
    public static final String CHATBOX2_TRANSPARANCY = "metaServerName";
    public static final String CHATBOX2_AUTOSLIDEDOWN = "metaServerName";
    public static final String ECM_TRANSPARENCY = "metaServerName";
    public static final String UNITOVERVIEW_SELECTED_COLOR = "metaServerName";
    public static final String UNITOVERVIEW_VALID_COLOR = "metaServerName";
    public static final String KEY_REPEAT_DELAY = "metaServerName";
    public static final String KEY_REPEAT_RATE = "metaServerName";
    public static final String SHOW_FPS = "metaServerName";
    public static final String BUTTONS_PER_ROW = "metaServerName";
    public static final String ARMORMINI_UNITS_PER_BLOCK = "metaServerName";
    public static final String ARMORMINI_ARMOR_CHAR = "metaServerName";
    public static final String ARMORMINI_CAP_ARMOR_CHAR = "metaServerName";
    public static final String ARMORMINI_IS_CHAR = "metaServerName";
    public static final String ARMORMINI_DESTROYED_CHAR = "metaServerName";
    public static final String ARMORMINI_COLOR_INTACT = "metaServerName";
    public static final String ARMORMINI_COLOR_PARTIAL_DMG = "metaServerName";
    public static final String ARMORMINI_COLOR_DAMAGED = "metaServerName";
    public static final String ARMORMINI_FONT_SIZE_MOD = "metaServerName";
    public static final String ROUND_REPORT_SPRITES = "metaServerName";
    public static final String LOW_FOLIAGE_COLOR = "metaServerName";
    //endregion MegaMek Options

    // This holds all required file paths not saved as part of MegaMek Options
    public static final String NAME_FACTIONS_DIRECTORY_PATH = "data/names/factions/";
    public static final String USER_NAME_FACTIONS_DIRECTORY_PATH = "userdata/data/names/factions/";
    public static final String CALLSIGN_FILE_PATH = "data/names/callsigns.csv";
    public static final String USER_CALLSIGN_FILE_PATH = "userdata/data/names/callsigns.csv";
    public static final String GIVEN_NAME_FEMALE_FILE = "data/names/femaleGivenNames.csv";
    public static final String USER_GIVEN_NAME_FEMALE_FILE = "userdata/data/names/femaleGivenNames.csv";
    public static final String HISTORICAL_ETHNICITY_FILE = "data/names/historicalEthnicity.csv";
    public static final String USER_HISTORICAL_ETHNICITY_FILE = "userdata/data/names/historicalEthnicity.csv";
    public static final String GIVEN_NAME_MALE_FILE = "data/names/maleGivenNames.csv";
    public static final String USER_GIVEN_NAME_MALE_FILE = "userdata/data/names/maleGivenNames.csv";
    public static final String SURNAME_FILE = "data/names/surnames.csv";
    public static final String USER_SURNAME_FILE = "userdata/data/names/surnames.csv";
}
