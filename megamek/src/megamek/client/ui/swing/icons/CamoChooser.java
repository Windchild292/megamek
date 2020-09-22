/*
 * MegaMek - Copyright (C) 2004 Ben Mazur (bmazur@sev.org)
 * Copyright © 2013 Edward Cullen (eddy@obsessedcomputers.co.uk)
 * MegaMek - Copyright (C) 2020 - The MegaMek Team  
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
package megamek.client.ui.swing.icons;

import java.awt.Window;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import megamek.client.ui.Messages;
import megamek.client.ui.swing.tileset.StaticDirectoryManager;
import megamek.common.Configuration;
import megamek.common.Entity;
import megamek.common.IPlayer;
import megamek.common.icons.AbstractIcon;
import megamek.common.icons.Camouflage;

/**
 * This dialog allows players to select the camo pattern (or color) used by
 * their units during the game. It automatically fills itself with all the color
 * choices in IPlayer and all the camo patterns in the
 * {@link Configuration#camoDir()} directory tree.
 * Should be shown by using showDialog(IPlayer) or showDialog(Entity). These
 * methods return either JOptionPane.OK_OPTION or .CANCEL_OPTION.
 * 
 * @see AbstractIconChooser
 */
public class CamoChooser extends AbstractIconChooser {
    private static final long serialVersionUID = -8060324139099113292L;

    /** True when an individual camo is being selected for an entity. */
    private boolean individualCamo = false;
    
    /** When an individual camo is being selected, the player's camo is displayed as a reset option. */
    private AbstractIcon entityOwnerCamo;
    
    /** Creates a dialog that allows players to choose a camo pattern. */
    public CamoChooser(Window parent) {
        super(parent, Messages.getString("CamoChoiceDialog.select_camo_pattern"), 
                new AbstractIconRenderer(), new CamoChooserTree());
        showSearch(true);
    }
    
    /** 
     * Show the camo choice dialog and pre-select the camo or color
     * of the given player. The dialog will allow choosing camos
     * and colors. Also refreshes the camos from disk.
     */
    public int showDialog(IPlayer player) {
        refreshCamos();
        individualCamo = false;
        setSelection(player.getCamouflage());
        return showDialog();
    }

    /** 
     * Show the camo choice dialog and pre-select the camo or color
     * of the given entity. The dialog will allow choosing camos
     * and the single color of the player owning the entity.
     * Also refreshes the camos from disk.
     */
    public int showDialog(Entity entity) {
        refreshCamos();
        individualCamo = true;
        setEntity(entity);
        return showDialog();
    }
    
    /** Reloads the camo directory from disk. */
    private void refreshCamos() {
        StaticDirectoryManager.refreshCamouflageDirectory();
        refreshDirectory(new CamoChooserTree());
    }
    
    @Override
    protected List<AbstractIcon> getItems(String category) {
        List<AbstractIcon> result = new ArrayList<>();
        
        // If a player camo is being selected, the items presented in the 
        // NO_CAMO section are the available player colors. 
        // If an individual camo is being selected, then the only item presented 
        // in the NO_CAMO section is the owner's camo. This can be chosen 
        // to remove the individual camo. 
        if (category.startsWith(Camouflage.NO_CAMOUFLAGE)) {
            if (individualCamo) {
                result.add(entityOwnerCamo);
            } else {
                for (String color: IPlayer.colorNames) {
                    result.add(new Camouflage(Camouflage.NO_CAMOUFLAGE, color));
                }
            } 
            return result;
        }
        
        // In any other camo section, the camos of the selected category are
        // presented. When the includeSubDirs flag is true, all categories
        // below the selected one are also presented.
        if (includeSubDirs) {
            for (Iterator<String> catNames = StaticDirectoryManager.getCamouflage().getCategoryNames();
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
    
    @Override
    protected List<AbstractIcon> getSearchedItems(String searched) {
        // For a category that contains the search string, all its items
        // are added to the list. Additionally, all items that contain 
        // the search string are added.

        List<AbstractIcon> result = new ArrayList<>();
        String lowerSearched = searched.toLowerCase();
        
        for (Iterator<String> catNames = StaticDirectoryManager.getCamouflage().getCategoryNames();
             catNames.hasNext(); ) {
            String tcat = catNames.next(); 
            if (tcat.toLowerCase().contains(lowerSearched)) {
                addCategoryItems(tcat, result);
                continue;
            }
            for (Iterator<String> itemNames = StaticDirectoryManager.getCamouflage().getItemNames(tcat);
                 itemNames.hasNext(); ) {
                String item = itemNames.next();
                if (item.toLowerCase().contains(lowerSearched)) {
                    result.add(new Camouflage(tcat, item));
                }
            }
        }

        return result;
    }

    /** 
     * Adds the camos of the given category to the given items ArrayList.
     * Assumes that the root of the path (IPlayer.ROOT_CAMO) is passed as ""! 
     * */
    private void addCategoryItems(String category, List<AbstractIcon> items) {
        for (Iterator<String> camoNames = StaticDirectoryManager.getCamouflage().getItemNames(category);
             camoNames.hasNext(); ) {
            items.add(new Camouflage(category, camoNames.next()));
        }
    }

    /** 
     * Preselects the Tree and the Images with the given entity's camo
     * or the owner's, if the entity has no individual camo. Also stores
     * the owner's camo to present a "revert to no individual" camo option.
     */
    private void setEntity(Entity entity) {
        // Store the owner's camo to display as the only "No Camo" option
        // This may be a color
        String item = entity.getOwner().getCamouflage().getFileName();
        if (entity.getOwner().getCamouflage().getCategory().equals(Camouflage.NO_CAMOUFLAGE)) {
            item = IPlayer.colorNames[entity.getOwner().getColorIndex()];
        }
        entityOwnerCamo = new Camouflage(entity.getOwner().getCamouflage().getCategory(), item);

        // Set the camo category and filename to the entity's if it has one,
        // otherwise to the corresponding player's camo category
        if (entity.getCamouflage().isDefault()) {
            setSelection(entity.getOwner().getCamouflage());
        } else {
            setSelection(entity.getCamouflage());
        }
    }
        
}