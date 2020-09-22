package megamek.client.ui.swing.icons;

import java.util.Iterator;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import megamek.client.ui.swing.tileset.MegaMekIconDirectoryManager;
import megamek.common.icons.AbstractIcon;

public class PortraitChooserTree extends AbstractIconChooserTree {
    private static final long serialVersionUID = 1274949831997174959L;
    
    public PortraitChooserTree() {
        super();
        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(AbstractIcon.ROOT_CATEGORY);
        if (MegaMekIconDirectoryManager.getPortraits() != null) {
            Iterator<String> catNames = MegaMekIconDirectoryManager.getPortraits().getCategoryNames();
            while (catNames.hasNext()) {
                String catName = catNames.next();
                if ((catName != null) && !catName.equals("")) {
                    String[] names = catName.split("/");
                    addCategoryToTree(root, names);
                }
            }
        }
        setModel(new DefaultTreeModel(root));
    }
}
