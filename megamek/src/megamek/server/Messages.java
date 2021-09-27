package megamek.server;

import megamek.MegaMek;
import megamek.common.util.EncodeControl;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
    private static final String BUNDLE_NAME = "megamek.server.messages"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME,
            MegaMek.getMMOptions().getLocale().getLocale(), new EncodeControl());

    private Messages() {}

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch(MissingResourceException e) {
            System.out.println("Missing i18n entry: " + key); //$NON-NLS-1$
            return '!' + key + '!';
        }
    }
}
