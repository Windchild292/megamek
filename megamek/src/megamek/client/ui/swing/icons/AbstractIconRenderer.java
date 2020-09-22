package megamek.client.ui.swing.icons;

import megamek.common.icons.AbstractIcon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AbstractIconRenderer extends JPanel implements ListCellRenderer<AbstractIcon> {
    /** Image file extensions, .jpg .jpeg .png .gif */
    protected static final String[] EXTENSIONS = {".png", ".jpg", ".jpeg", ".gif"};

    /** This JLabel displays the selectable image */
    protected JLabel lblImage = new JLabel();

    /** This JLabel displays the name of the selectable image */
    protected JLabel lblText = new JLabel();

    /** The tooltip to be displayed */
    protected String tip = "";

    public AbstractIconRenderer() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(lblImage);
        add(lblText);
    }


    /**
     * Sets the image based on the passed category and name from
     * the DirectoryItems that the list currently displays.
     */
    private void setImage(AbstractIcon icon) {
        lblImage.setIcon(icon.getImageIcon());
        tip = "<HTML><BODY>" + icon.getCategory() + "<BR>" + icon.getFileName();
    }

    @Override
    public String getToolTipText() {
        return tip;
    }

    /** Sets the label text of the image, removing the file extension. */
    private void setText(String text) {
        // Remove the file extension
        for (String ext: EXTENSIONS) {
            if (text.toLowerCase().contains(ext)) {
                text = text.replace(ext, "");
                break;
            }
        }
        lblText.setText(text);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends AbstractIcon> list,
                                                  AbstractIcon value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            setBackground(UIManager.getColor("Table.selectionBackground"));
            setForeground(UIManager.getColor("Table.selectionForeground"));
        } else {
            setBackground(UIManager.getColor("Table.background"));
            setForeground(UIManager.getColor("Table.foreground"));
        }
        setImage(value);
        setText(value.getFileName());

        return this;
    }
}
