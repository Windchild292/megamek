/*  
 * MegaMek - Copyright (C) 2020 - The MegaMek Team  
 *  
 * This program is free software; you can redistribute it and/or modify it under  
 * the terms of the GNU General Public License as published by the Free Software  
 * Foundation; either version 2 of the License, or (at your option) any later  
 * version.  
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT  
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS  
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more  
 * details.  
 */  
package megamek.client.ui.swing.icons;

import java.awt.Window;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import megamek.client.ui.Messages;
import megamek.client.ui.swing.tileset.MegaMekIconDirectoryManager;
import megamek.common.Configuration;
import megamek.common.Crew;
import megamek.common.icons.AbstractIcon;
import megamek.common.icons.Portrait;

/**
 * This dialog allows players to select a portrait
 * It automatically fills itself with the portraits
 * in the {@link Configuration#portraitImagesDir()} directory tree.
 * Should be shown by using showDialog(). This method 
 * returns either JOptionPane.OK_OPTION or .CANCEL_OPTION.
 * 
 * @see AbstractIconChooser
 */
public class PortraitChooser extends AbstractIconChooser {
    private static final long serialVersionUID = 6487684461690549139L;
    
    /** Creates a dialog that allows players to choose a portrait. */
    public PortraitChooser(Window parent) {
        super(parent, Messages.getString("PortraitChoiceDialog.select_portrait"), 
                new AbstractIconRenderer(), new PortraitChooserTree());
    }
    
    @Override
    protected List<AbstractIcon> getItems(String category) {
        
        List<AbstractIcon> result = new ArrayList<>();
        
        // The portraits of the selected category are presented. 
        // When the includeSubDirs flag is true, all categories
        // below the selected one are also presented.
        if (includeSubDirs) {
            for (Iterator<String> catNames = MegaMekIconDirectoryManager.getPortraits().getCategoryNames();
                 catNames.hasNext(); ) {
                String tcat = catNames.next(); 
                if (tcat.startsWith(category)) {
                    addCategoryItems(tcat, result);
                }
            }
        } else {
            addCategoryItems(category, result);
        }
        return result;
    }
    
    /** 
     * Adds the portraits of the given category to the given items ArrayList.
     * Assumes that the root of the path (Crew.ROOT_PORTRAIT) is passed as ""! 
     */
    private void addCategoryItems(String category, List<AbstractIcon> items) {
        for (Iterator<String> portNames = MegaMekIconDirectoryManager.getPortraits().getItemNames(category);
             portNames.hasNext(); ) {
            items.add(new Portrait(category, portNames.next()));
        }
    }
    
    /** 
     * Show the portrait chooser dialog and pre-select the portrait
     * of the given crew and slot. The dialog will allow choosing a portrait.
     * Also reloads the portrait directory from disk.
     */
    public int showDialog(Crew crew, int slot) {
        refreshPortraits();
        setPilot(crew, slot);
        return showDialog();
    }
    
    /** Reloads the camouflage directory from disk. */
    private void refreshPortraits() {
        MegaMekIconDirectoryManager.refreshPortraitDirectory();
        refreshDirectory(new PortraitChooserTree());
    }
    
    /** Preselects the portrait of the given pilot. */ 
    public void setPilot(Crew pilot, int slot) {
        AbstractIcon portrait = pilot.getPortrait(slot);
        setSelection(portrait);
    }
}
