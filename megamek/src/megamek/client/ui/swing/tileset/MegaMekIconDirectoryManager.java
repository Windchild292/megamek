package megamek.client.ui.swing.tileset;

import megamek.MegaMek;
import megamek.client.ui.swing.util.ImageFileFactory;
import megamek.client.ui.swing.util.ScaledImageFileFactory;
import megamek.common.Configuration;
import megamek.common.util.fileUtils.DirectoryItems;

public class MegaMekIconDirectoryManager {
    /** The DirectoryItems object holding all camouflage file information */
    private static DirectoryItems camouflageDirectory;

    /**
     * True at startup and when the camouflage directory should be re-parsed.
     * Used to avoid re-parsing the directory repeatedly when there's an error.
     */
    private static boolean parseCamouflageDirectory = true;


    /** The DirectoryItems object holding all portrait file information */
    private static DirectoryItems portraitDirectory;

    /**
     * True at startup and when the portrait directory should be re-parsed.
     * Used to avoid re-parsing the directory repeatedly when there's an error. */
    private static boolean parsePortraitDirectory = true;

    // This class is not to be instantiated
    private MegaMekIconDirectoryManager() {

    }

    /**
     * Returns a DirectoryItems object containing all camo image filenames
     * found in MM's camo images folder.
     * @return a DirectoryItems object with the camo folders and filenames.
     * May be null if the directory cannot be parsed.
     */
    public static DirectoryItems getCamouflage() {
        initializeCamouflage();
        return camouflageDirectory;
    }
    /**
     * Parses MM's camo folder when first called
     * or when it was refreshed.
     *
     * @see #refreshCamouflageDirectory()
     */
    private static void initializeCamouflage() {
        // Read in and parse MM's camo folder only when first called
        // or when refreshed
        if (parseCamouflageDirectory) {
            // Set parseDirectory to false to avoid parsing repeatedly when something fails
            parseCamouflageDirectory = false;
            try {
                camouflageDirectory = new DirectoryItems(Configuration.camoDir(), "",
                        ScaledImageFileFactory.getInstance());
            } catch (Exception e) {
                MegaMek.getLogger().error(MegaMekIconDirectoryManager.class, "Could not parse the camo directory!");
                MegaMek.getLogger().error(MegaMekIconDirectoryManager.class, e);
                // This could be improved by obtaining an empty DirectoryItems to avoid returning null
            }
        }
    }

    /**
     * Re-reads MM's camo images folder and returns the updated
     * DirectoryItems object. This will update the DirectoryItems object
     * with changes to the camos (like added image files and folders)
     * while MM is running.
     *
     * @see #getCamouflage()
     */
    public static DirectoryItems refreshCamouflageDirectory() {
        parseCamouflageDirectory = true;
        return getCamouflage();
    }

    /**
     * Returns a DirectoryItems object containing all portrait image filenames
     * found in MM's portrait images folder.
     * @return a DirectoryItems object with the portrait folders and filenames.
     * May be null if the directory cannot be parsed.
     */
    public static DirectoryItems getPortraits() {
        initializePortraits();
        return portraitDirectory;
    }

    /**
     * Parses MM's portraits folder when first called
     * or when it was refreshed.
     *
     * @see #refreshPortraitDirectory()
     */
    private static void initializePortraits() {
        // Read in and parse MM's portrait folder only when first called
        // or when refreshed
        if (parsePortraitDirectory) {
            // Set parseDirectory to false to avoid parsing repeatedly when something fails
            parsePortraitDirectory = false;
            try {
                portraitDirectory = new DirectoryItems(Configuration.portraitImagesDir(), "",
                        ImageFileFactory.getInstance());
            } catch (Exception e) {
                MegaMek.getLogger().error(MegaMekIconDirectoryManager.class, "Could not parse the portraits directory!");
                MegaMek.getLogger().error(MegaMekIconDirectoryManager.class, e);
            }
        }
    }

    /**
     * Re-reads MM's portrait images folder and returns the updated
     * DirectoryItems object. This will update the DirectoryItems object
     * with changes to the portraits (like added image files and folders)
     * while MM is running.
     *
     * @see #getPortraits()
     */
    public static DirectoryItems refreshPortraitDirectory() {
        parsePortraitDirectory = true;
        return getPortraits();
    }
}
