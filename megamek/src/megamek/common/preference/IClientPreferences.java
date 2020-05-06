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
package megamek.common.preference;

import megamek.client.ui.swing.camouflage.Camouflage;

import java.io.PrintWriter;
import java.util.Locale;

/**
 * Interface for common client settings store
 */
public interface IClientPreferences extends IPreferenceStore {
    String LAST_CONNECT_ADDR = "LastConnectAddr";
    String LAST_CONNECT_PORT = "LastConnectPort";
    String LAST_PLAYER_CAMOUFLAGE = "LastPlayerCamouflage";
    String LAST_PLAYER_COLOR = "LastPlayerColor";
    String LAST_PLAYER_NAME = "LastPlayerName";
    String LAST_SERVER_PASS = "LastServerPass";
    String LAST_SERVER_PORT = "LastServerPort";
    String LOCALE = "Locale";
    String MAP_TILESET = "MapTileset";
    String MAX_PATHFINDER_TIME = "MaxPathfinderTime";
    String DATA_DIRECTORY = "DataDirectory";
    String LOG_DIRECTORY = "LogDirectory";
    String MECH_DIRECTORY = "MechDirectory";
    String MEK_HIT_LOC_LOG = "MekHitLocLog";
    String MEMORY_DUMP_ON = "MemoryDumpOn";
    String DEBUG_OUTPUT_ON = "DebugOutputOn";
    String GAMELOG_KEEP = "KeepGameLog";
    String GAMELOG_FILENAME = "GameLogFilename";
    String STAMP_FILENAMES = "StampFilenames";
    String STAMP_FORMAT = "StampFormat";
    String SHOW_UNIT_ID = "ShowUnitId";
    String UNIT_START_CHAR = "UnitStartChar";
    String DEFAULT_AUTOEJECT_DISABLED = "DefaultAutoejectDisabled";
    String USE_AVERAGE_SKILLS = "UseAverageSkills";
    String GENERATE_NAMES = "GenerateNames";
    String METASERVER_NAME = "MetaServerName";
    String GOAL_PLAYERS = "GoalPlayers";
    String GUI_NAME = "GUIName";
    String PRINT_ENTITY_CHANGE = "PrintEntityChange";
    String BOARD_WIDTH = "BoardWidth";
    String BOARD_HEIGHT = "BoardHeight";
    String MAP_WIDTH = "MapWidth";
    String MAP_HEIGHT = "MapHeight";

    boolean getPrintEntityChange();

    boolean defaultAutoejectDisabled();

    boolean useAverageSkills();
    
    boolean generateNames();

    String getLastConnectAddr();

    int getLastConnectPort();

    Camouflage getLastPlayerCamouflage();

    String getLastPlayerName();

    String getLastServerPass();

    int getLastServerPort();

    Locale getLocale();

    String getLocaleString();

    String getMapTileset();

    int getMaxPathfinderTime();

    String getDataDirectory();

    String getLogDirectory();

    String getMechDirectory();

    PrintWriter getMekHitLocLog();

    String getMetaServerName();

    void setMetaServerName(String name);

    int getGoalPlayers();

    void setGoalPlayers(int n);

    String getGameLogFilename();

    boolean stampFilenames();

    String getStampFormat();

    boolean getShowUnitId();

    char getUnitStartChar();

    boolean keepGameLog();

    boolean memoryDumpOn();
    
    boolean debugOutputOn();

    void setDefaultAutoejectDisabled(boolean state);

    void setUseAverageSkills(boolean state);
    
    void setGenerateNames(boolean state);

    void setKeepGameLog(boolean state);

    void setLastConnectAddr(String serverAddr);

    void setLastConnectPort(int port);

    void setLastPlayerCamouflage(Camouflage camouflage);

    void setLastPlayerColor(int colorIndex);

    void setLastPlayerName(String name);

    void setLastServerPass(String serverPass);

    void setLastServerPort(int port);

    void setLocale(String text);

    void setMapTileset(String filename);

    void setMaxPathfinderTime(int i);

    void setGameLogFilename(String text);

    void setStampFilenames(boolean state);

    void setStampFormat(String text);

    void setShowUnitId(boolean state);

    void setUnitStartChar(char c);

    String getGUIName();

    void setGUIName(String guiName);

    int getBoardWidth();

    int getBoardHeight();

    int getMapWidth();

    int getMapHeight();

}
