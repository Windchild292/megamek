package megamek.client.ui.swing.icons;

import megamek.MegaMek;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.util.Arrays;
import java.util.Enumeration;

public abstract class AbstractIconChooserTree extends JTree {
    protected AbstractIconChooserTree() {
        super();

        // set up the directory tree (left panel)
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

    /**
     * This recursive method is a hack: DirectoryItems flattens the directory
     * structure, but it provides useful functionality, so this method will
     * reconstruct the directory structure for the JTree.
     *
     * @param node
     * @param names
     */
    protected void addCategoryToTree(DefaultMutableTreeNode node, String... names) {
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
                    addCategoryToTree(childNode, Arrays.copyOfRange(names, 1, names.length));
                    matched = true;
                } else {
                    // I guess we're done? This shouldn't happen, as there
                    // shouldn't be duplicates
                    MegaMek.getLogger().error(this, "");
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
}
