/*
 * MegaMek - Copyright (C) 2004 Ben Mazur (bmazur@sev.org)
 * Copyright © 2013 Edward Cullen (eddy@obsessedcomputers.co.uk)
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
package megamek.client.ui.swing;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import megamek.client.ui.Messages;
import megamek.client.ui.swing.camouflage.Camouflage;
import megamek.client.ui.swing.camouflage.CamouflageTableModel;
import megamek.client.ui.swing.camouflage.CamouflageTableMouseAdapter;
import megamek.client.ui.swing.util.ImageFileFactory;
import megamek.client.ui.swing.util.PlayerColors;
import megamek.common.Configuration;
import megamek.common.Entity;
import megamek.common.IPlayer;
import megamek.common.util.DirectoryItems;

/**
 * This dialog allows players to select the camo pattern (or color) used by
 * their units during the game. It automatically fills itself with all the color
 * choices in <code>Settings</code> and all the camo patterns in the
 * {@link Configuration#camoDir()} directory tree.
 * <p/>
 * Created on January 19, 2004
 *
 * @author James Damour
 * @version 1
 */
@Deprecated
public class CamoChoiceDialog extends JDialog implements TreeSelectionListener {
    //region Variable Declarations
    private static final long serialVersionUID = 9220162367683378065L;

    /**
     * Split pane for table and tree view.
     */
    public JSplitPane splitPane;

    private JFrame frame;
    private DirectoryItems camos;
    /**
     * Scroll panes for the camo table and the categories tree view
     */
    private JScrollPane scrCamo;
    private JButton sourceButton;
    private JTree treeCategories;
    private CamouflageTableModel camoModel;
    private JTable tableCamo;

    private Camouflage camouflage;
    private String category;
    private String filename;

    private int colorIndex;
    private IPlayer player;
    private Entity entity;

    private boolean select;
    //endregion Variable Declarations

    /**
     * Create a dialog that allows players to choose a camouflage pattern.
     *
     * @param parent the <code>JFrame</code> that displays this dialog.
     */
    public CamoChoiceDialog(JFrame parent, JButton button) {
        // Initialize our superclass and record our parent frame.
        super(parent, Messages
                .getString("CamoChoiceDialog.select_camo_pattern"), true); //$NON-NLS-1$
        frame = parent;
        sourceButton = button;

        // Parse the camo directory.
        try {
            camos = new DirectoryItems(Configuration.camoDir(), "", //$NON-NLS-1$
                    ImageFileFactory.getInstance());
        } catch (Exception e) {
            camos = null;
        }

        camouflage = new Camouflage();

        category = Camouflage.ROOT_CAMO;
        filename = Camouflage.NO_CAMO;
        colorIndex = -1;

        scrCamo = new JScrollPane();
        tableCamo = new JTable();
        camoModel = new CamouflageTableModel();
        tableCamo.setModel(camoModel);
        tableCamo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableCamo.setRowHeight(76);
        tableCamo.getColumnModel().getColumn(0).setCellRenderer(camoModel.getRenderer());
        tableCamo.addMouseListener(new CamouflageTableMouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    select();
                }
            }
        });
        scrCamo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrCamo.setViewportView(tableCamo);
        scrCamo.setMinimumSize(new Dimension(240, 240));

        treeCategories = new JTree();
        treeCategories.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        JScrollPane scrCategories = new JScrollPane();
        scrCategories.setViewportView(treeCategories);
        scrCategories.setMinimumSize(new Dimension(240, 240));
        setMinimumSize(new Dimension(480, 240));

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(Camouflage.ROOT_CAMO);
        root.add(new DefaultMutableTreeNode(Camouflage.NO_CAMO));
        if (camos != null) {
            if (camos.getItemNames("").hasNext()) {
                root.add(new DefaultMutableTreeNode(Camouflage.ROOT_CAMO));
            }

            Iterator<String> catNames = camos.getCategoryNames();
            while (catNames.hasNext()) {
                String catName = catNames.next();
                if ((catName != null) && !catName.equals("")) {
                    String[] names = catName.split("/");
                    addCategoryToTree(root, names);
                }
            }
        }
        treeCategories.setModel(new DefaultTreeModel(root));
        treeCategories.addTreeSelectionListener(this);

        JButton btnSelect = new JButton();
        btnSelect.setText(Messages.getString("CamoChoiceDialog.Select"));
        btnSelect.addActionListener(evt -> select());

        JButton btnCancel = new JButton();
        btnCancel.setText(Messages.getString("CamoChoiceDialog.Cancel"));
        btnCancel.addActionListener(evt -> cancel());

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
                scrCategories, scrCamo);
        splitPane.setResizeWeight(0.5);

        // set layout
        setLayout(new GridBagLayout());
        GridBagConstraints c;

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1.0;
        c.weighty = 1.0;
        getContentPane().add(splitPane, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.5;
        getContentPane().add(btnSelect, c);

        c.gridx++;
        getContentPane().add(btnCancel, c);

        pack();
        setLocationRelativeTo(parent);
    }

    /**
     * This recursive method is a hack: DirectoryItems flattens the directory
     * structure, but it provides useful functionality, so this method will
     * reconstruct the directory structure for the JTree.
     *
     * @param node
     * @param names
     */
    private void addCategoryToTree(DefaultMutableTreeNode node, String[] names) {
        // Shouldn't happen
        if (names.length == 0) {
            return;
        }

        boolean matched = false;
        for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) e.nextElement();
            String nodeName = (String) childNode.getUserObject();
            if (nodeName.equals(names[0])) {
                if (names.length > 1) {
                    addCategoryToTree(childNode,
                            Arrays.copyOfRange(names, 1, names.length));
                    matched = true;
                }
            }
        }

        // If we didn't match, lets create nodes for each name
        if (!matched) {
            DefaultMutableTreeNode root = node;
            for (String name : names) {
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(name);
                root.add(newNode);
                root = newNode;
            }
        }
    }

    private void cancel() {
        setVisible(false);
    }

    private void select() {
        category = camoModel.getCategory();
        if (category.equals(Camouflage.NO_CAMO) && (entity == null)) {
            colorIndex = tableCamo.getSelectedRow();
        }

        if (tableCamo.getSelectedRow() != -1) {
            filename = (String) camoModel.getValueAt(tableCamo.getSelectedRow(), 0);
        }

        if ((sourceButton == null) && (entity != null)) {
            if (category.equals(Camouflage.NO_CAMO)) {
                entity.setCamouflage(new Camouflage(null, null));
            } else {
                entity.setCamouflage(getCamouflage());
            }
        } else {
            if (colorIndex >= 0) {
                player.setColorIndex(colorIndex);
            }
            player.setCamoCategory(category);
            player.setCamoFileName(filename);
            sourceButton.setIcon(generateIcon(category, filename));
        }

        select = true;
        setVisible(false);
    }

    public Camouflage getCamouflage() {
        return camouflage;
    }

    // Windchild
    @Deprecated
    public String getCategory() {
        return category;
    }

    @Deprecated
    public String getFileName() {
        return filename;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    private void fillTable(String category) {
        camoModel.reset();
        camoModel.setCategory(category);
        if (Camouflage.NO_CAMO.equals(category)) {
            // If we are setting colors for a player, allow all colors
            if (entity == null) {
                for (String color : IPlayer.colorNames) {
                    camoModel.addCamouflage(new Camouflage(category, color));
                }
            // If we are setting individual camo, then selecting colors other
            // than the player color has no effect
            } else {
                camoModel.addCamouflage(new Camouflage(category, IPlayer.colorNames[player.getColorIndex()]));
            }
        } else {
            // Translate the "root camo" category name.
            Iterator<String> camoNames;
            if (Camouflage.ROOT_CAMO.equals(category)) {
                camoNames = camos.getItemNames(Camouflage.ROOT_CAMO_CATEGORY);
            } else {
                camoNames = camos.getItemNames(category);
            }

            // Get the camo names for this category.
            while (camoNames.hasNext()) {
                camoModel.addCamouflage(new Camouflage(category, camoNames.next()));
            }
        }
        if (camoModel.getRowCount() > 0) {
            tableCamo.setRowSelectionInterval(0, 0);
        }
        scrCamo.repaint();
    }

    public void setPlayer(IPlayer p) {
        player = p;

        category = player.getCamoCategory();
        if (category.equals(Camouflage.NO_CAMO) && (null != entity) && (p.getColorIndex() >= 0)) {
            filename = (String) camoModel.getValueAt(
                    p.getColorIndex(), 0);
        } else {
            filename = player.getCamoFileName();
        }
        if (sourceButton != null) {
            sourceButton.setIcon(generateIcon(category, filename));
        }
        // This cumbersome code takes the category name and transforms it into
        // a TreePath so it can be selected in the dialog
        String[] names = category.split(Pattern.quote("/"));
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeCategories.getModel().getRoot();
        for (String name : names) {
            for (Enumeration<?> e = node.children(); e.hasMoreElements(); ) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) e.nextElement();
                if (name.equals(child.getUserObject())) {
                    node = child;
                    break;
                }
            }
        }
        treeCategories.setSelectionPath(new TreePath(node.getPath()));
        fillTable(category);
        int rowIndex = 0;
        for (int i = 0; i < camoModel.getRowCount(); i++) {
            if (camoModel.getValueAt(i, 0).equals(filename)) {
                rowIndex = i;
                break;
            }
        }
        
        if (rowIndex >= tableCamo.getRowCount()) {
            System.out.println("Attempting to set invalid camo index " + rowIndex + " for player " + p.getName() + ". Using default instead.");
        } else {
            tableCamo.setRowSelectionInterval(rowIndex, rowIndex);
        }
    }

    public void setEntity(Entity e) {
        entity = e;
        if (entity == null) {
            return;
        }
        category = entity.getCamoCategory() == null ? player.getCamoCategory()
                : entity.getCamoCategory();
        filename = entity.getCamoFileName() == null ? player.getCamoFileName()
                : entity.getCamoFileName();
        // This cumbersome code takes the category name and transforms it into
        // a TreePath so it can be selected in the dialog
        String[] names = category.split(Pattern.quote("/"));
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeCategories.getModel().getRoot();
        for (String name : names) {
            for (Enumeration<?> enm = node.children(); enm.hasMoreElements(); ) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) enm.nextElement();
                if (name.equals(child.getUserObject())) {
                    node = child;
                    break;
                }
            }
        }
        treeCategories.setSelectionPath(new TreePath(node.getPath()));
        fillTable(category);
        int modelRowIndex = -1;
        for (int i = 0; i < camoModel.getRowCount(); i++) {
            if (camoModel.getValueAt(i, 0).equals(filename)) {
                modelRowIndex = i;
                break;
            }
        }
        int viewRowIndex = (modelRowIndex != -1) ? tableCamo.convertRowIndexToView(modelRowIndex) : 0;
        tableCamo.setRowSelectionInterval(viewRowIndex, viewRowIndex);
    }

    Icon generateIcon(String cat, String item) {
        String actualCat = cat;
        // Replace the ROOT_CAMO string with "".
        if (Camouflage.ROOT_CAMO.equals(actualCat)) {
            actualCat = "";
        }

        int colorInd = -1;
        // no camo, just color
        if (Camouflage.NO_CAMO.equals(actualCat)) {
            for (int color = 0; color < IPlayer.colorNames.length; color++) {
                if (IPlayer.colorNames[color].equals(item)) {
                    colorInd = color;
                    break;
                }
            }
            if (colorInd == -1) {
                colorInd = 0;
            }
            BufferedImage tempImage = new BufferedImage(84, 72,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = tempImage.createGraphics();
            graphics.setColor(PlayerColors.getColor(colorInd));
            graphics.fillRect(0, 0, 84, 72);
            return new ImageIcon(tempImage);
        }

        // an actual camo
        try {
            // We need to copy the image to make it appear.
            Image image = (Image) camos.getItem(actualCat, item);

            return new ImageIcon(image);
        } catch (Exception err) {
            // Print the stack trace and display the message.
            System.out.println("Tried to load camo that doesn't exist: " + actualCat + item);

            if (this.isVisible()) {
                JOptionPane.showMessageDialog(frame, err.getMessage(),
                        Messages.getString("CamoChoiceDialog.error_getting_camo"),
                        JOptionPane.ERROR_MESSAGE);
            }
            return null;
        }
    }

    public boolean isSelected() {
        return select;
    }

    @Override
    public void valueChanged(TreeSelectionEvent ev) {
        if (ev.getSource().equals(treeCategories)) {
            TreePath[] paths = treeCategories.getSelectionPaths();
            // If nothing is selected, there's nothing to populate the table with.
            if (null == paths) {
                return;
            }
            for (TreePath path : paths) {
                Object[] values = path.getPath();
                StringBuilder category = new StringBuilder();
                for (int i = 1; i < values.length; i++) {
                    if (values[i] != null) {
                        String name = (String) ((DefaultMutableTreeNode) values[i]).getUserObject();
                        category.append(name);
                        if (!name.equals(Camouflage.NO_CAMO) && !name.equals(Camouflage.ROOT_CAMO)) {
                            category.append("/");
                        }
                    }
                }
                fillTable(category.toString());
            }
        }
    }
}
