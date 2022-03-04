/*
 * Copyright (c) 2000-2002 - Ben Mazur (bmazur@sev.org)
 * Copyright (c) 2013 - Nicholas Walczak (walczak@cs.umn.edu)
 * Copyright (c) 2022 - The MegaMek Team. All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 */
package megamek.common;

import megamek.MMConstants;
import megamek.client.ui.swing.util.KeyCommandBind;
import megamek.client.ui.swing.util.MegaMekController;
import megamek.common.annotations.Nullable;
import megamek.common.preference.IPreferenceChangeListener;
import megamek.common.preference.PreferenceChangeEvent;
import megamek.common.util.fileUtils.MegaMekFile;
import megamek.utils.MegaMekXmlUtil;
import org.apache.logging.log4j.LogManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a static method to read in the defaultKeybinds.xml and set all of the
 * <code>KeyCommandbind</code>'s based on the specifications in the XML file.
 *
 * @author arlith
 */
public class KeyBindParser {
    // XML tag defines
    public static String KEY_BIND = "KeyBind";
    public static String KEY_CODE = "keyCode";
    public static String KEY_MODIFIER = "modifier";
    public static String COMMAND = "command";
    public static String IS_REPEATABLE = "isRepeatable";

    // Keybinds change event 
    private static List<IPreferenceChangeListener> listeners = new ArrayList<>();
    public static final String KEYBINDS_CHANGED = "keyBindsChanged";

    public static void parseKeyBindings(MegaMekController controller) {
        // Always register the hard-coded defaults first so that new binds get their keys
        registerDefaultKeyBinds(controller);
        
        // Get the path to the default bindings file.
        File file = new MegaMekFile(Configuration.configDir(), MMConstants.DEFAULT_KEY_BINDINGS_FILE).getFile();
        if (!file.exists() || !file.isFile()) {
            return;
        }

        // Build the XML document.
        try {
            DocumentBuilder builder = MegaMekXmlUtil.newSafeDocumentBuilder();
            System.out.println("Parsing " + file.getName());
            Document doc = builder.parse(file);
            System.out.println("Parsing finished.");

            // Get the list of units.
            NodeList listOfUnits = doc.getElementsByTagName(KEY_BIND);
            int totalBinds = listOfUnits.getLength();
            System.out.println("Total number of key binds parsed: " + totalBinds);

            for (int bindCount = 0; bindCount < totalBinds; bindCount++) {

                // Get the first element of this node.
                Element bindingList = (Element) listOfUnits.item(bindCount);

                // Get the key code
                Element elem = (Element) bindingList.getElementsByTagName(KEY_CODE).item(0);
                if (elem == null) {
                    System.err.println("Missing " + KEY_CODE + " element #" + bindCount);
                    continue;
                }
                int keyCode = Integer.parseInt(elem.getTextContent());

                // Get the modifier.
                elem = (Element) bindingList.getElementsByTagName(KEY_MODIFIER).item(0);
                if (elem == null) {
                    System.err.println("Missing " + KEY_MODIFIER + " element #" + bindCount);
                    continue;
                }
                int modifiers = Integer.parseInt(elem.getTextContent());


                // Get the command
                elem = (Element) bindingList.getElementsByTagName(COMMAND).item(0);
                if (elem == null) {
                    System.err.println("Missing " + COMMAND + " element #" + bindCount);
                    continue;
                }
                String command = elem.getTextContent();

                // Get the isRepeatable
                elem = (Element) bindingList.getElementsByTagName(IS_REPEATABLE).item(0);
                if (elem == null) {
                    LogManager.getLogger().error("Missing " + IS_REPEATABLE + " element #" + bindCount);
                    continue;
                }
                boolean isRepeatable = Boolean.parseBoolean(elem.getTextContent());

                KeyCommandBind keyBind = KeyCommandBind.getBindByCmd(command);

                if (keyBind == null) {
                    LogManager.getLogger().error("Unknown command: " + command + ", element #" + bindCount);
                } else {
                    keyBind.key = keyCode;
                    keyBind.modifiers = modifiers;
                    keyBind.isRepeatable = isRepeatable;
                    controller.registerKeyCommandBind(keyBind);
                }
            }
        } catch (Exception ex) {
            LogManager.getLogger().error("Error parsing key bindings!", ex);
            controller.removeAllKeyCommandBinds();
            registerDefaultKeyBinds(controller);
        }
    }

    /**
     * Each KeyCommand has a built-in default; if no key binding file can be found, we should
     * register those defaults.
     *
     * @param controller
     */
    public static void registerDefaultKeyBinds(MegaMekController controller) {
        for (KeyCommandBind kcb : KeyCommandBind.values()) {
            controller.registerKeyCommandBind(kcb);
        }
    }

    /**
     * Register an object that wishes to be alerted when the key binds (may) have changed.
     * When the keybinds change, a PreferenceChange with the name KeyBindParser.KEYBINDS_CHANGED
     * is fired.
     *
     * @param listener - the PreferenceListener</code> that wants to register itself.
     */
    public synchronized static void addPreferenceChangeListener(IPreferenceChangeListener listener) {
        if (!listeners.contains((listener))) {
            listeners.add(listener);
        }
    }

    /**
     * De-register an object from being alerted when the key binds (may) have changed.
     *
     * @param listener - the PreferenceListener</code> that wants to remove itself.
     */
    public synchronized static void removePreferenceChangeListener(IPreferenceChangeListener listener) {
        listeners.remove(listener);
    }

    private synchronized static void fireKeyBindsChangeEvent() {
        final PreferenceChangeEvent pe = new PreferenceChangeEvent(KeyBindParser.class, KEYBINDS_CHANGED, null, null);
        listeners.forEach(l -> l.preferenceChange(pe));
    }

    //region File I/O
    public void writeToFile(@Nullable File file) {
        if (file == null) {
            return;
        }
        String path = file.getPath();
        if (!path.endsWith(".xml")) {
            path += ".xml";
            file = new File(path);
        }

        try (OutputStream fos = new FileOutputStream(file);
             OutputStream bos = new BufferedOutputStream(fos);
             OutputStreamWriter osw = new OutputStreamWriter(bos, StandardCharsets.UTF_8);
             PrintWriter pw = new PrintWriter(osw)) {
            writeToXML(pw, 0);
            fireKeyBindsChangeEvent();
        } catch (Exception ex) {
            LogManager.getLogger().error("Error writing to keybindings file!", ex);
        }
    }

    public static void writeToXML(final PrintWriter pw, int indent) {
        MegaMekXmlUtil.writeXMLHeader(pw);
        MegaMekXmlUtil.writeSimpleXMLOpenTag(pw, indent++, "keyBindings");
        MegaMekXmlUtil.writeSimpleXMLOpenTag(pw, indent++, "keyBindings",
                "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "xsi:noNamespaceSchemaLocation", "keyBindingSchema.xsd",
                null, null);
        for (KeyCommandBind keyCommandBind : KeyCommandBind.values()) {
            keyCommandBind.writeToXML(pw, indent);
        }
        MegaMekXmlUtil.writeSimpleXMLCloseTag(pw, --indent, "keyBindings");
    }

    public static void parseFromFile(final MegaMekController controller) {

    }

    public static void parseFromXML() {

    }

    public static void parseKeyBindinsgs(MegaMekController controller) {
        // Always register the hard-coded defaults first so that new binds get their keys
        registerDefaultKeyBinds(controller);

        // Get the path to the default bindings file.
        File file = new MegaMekFile(Configuration.configDir(), MMConstants.DEFAULT_KEY_BINDINGS_FILE).getFile();
        if (!file.exists() || !file.isFile()) {
            return;
        }

        // Build the XML document.
        try {
            DocumentBuilder builder = MegaMekXmlUtil.newSafeDocumentBuilder();
            System.out.println("Parsing " + file.getName());
            Document doc = builder.parse(file);
            System.out.println("Parsing finished.");

            // Get the list of units.
            NodeList listOfUnits = doc.getElementsByTagName(KEY_BIND);
            int totalBinds = listOfUnits.getLength();
            System.out.println("Total number of key binds parsed: " + totalBinds);

            for (int bindCount = 0; bindCount < totalBinds; bindCount++) {

                // Get the first element of this node.
                Element bindingList = (Element) listOfUnits.item(bindCount);

                // Get the key code
                Element elem = (Element) bindingList.getElementsByTagName(KEY_CODE).item(0);
                if (elem == null) {
                    System.err.println("Missing " + KEY_CODE + " element #" + bindCount);
                    continue;
                }
                int keyCode = Integer.parseInt(elem.getTextContent());

                // Get the modifier.
                elem = (Element) bindingList.getElementsByTagName(KEY_MODIFIER).item(0);
                if (elem == null) {
                    System.err.println("Missing " + KEY_MODIFIER + " element #" + bindCount);
                    continue;
                }
                int modifiers = Integer.parseInt(elem.getTextContent());


                // Get the command
                elem = (Element) bindingList.getElementsByTagName(COMMAND).item(0);
                if (elem == null) {
                    System.err.println("Missing " + COMMAND + " element #" + bindCount);
                    continue;
                }
                String command = elem.getTextContent();

                // Get the isRepeatable
                elem = (Element) bindingList.getElementsByTagName(IS_REPEATABLE).item(0);
                if (elem == null) {
                    LogManager.getLogger().error("Missing " + IS_REPEATABLE + " element #" + bindCount);
                    continue;
                }
                boolean isRepeatable = Boolean.parseBoolean(elem.getTextContent());

                KeyCommandBind keyBind = KeyCommandBind.getBindByCmd(command);

                if (keyBind == null) {
                    LogManager.getLogger().error("Unknown command: " + command + ", element #" + bindCount);
                } else {
                    keyBind.key = keyCode;
                    keyBind.modifiers = modifiers;
                    keyBind.isRepeatable = isRepeatable;
                    controller.registerKeyCommandBind(keyBind);
                }
            }
        } catch (Exception ex) {
            LogManager.getLogger().error("Error parsing key bindings!", ex);
            controller.removeAllKeyCommandBinds();
            registerDefaultKeyBinds(controller);
        }
    }
    //endregion File I/O
}
