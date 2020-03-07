package megamek.client;

import megamek.common.Compute;
import megamek.common.Crew;

public class RandomGenderGenerator {
    private static int percentFemale = 50;
    public RandomGenderGenerator() { }

    /**
     * randomly select gender
     *
     * @return true if female
     */
    @Deprecated // March 7th, 2020 by the addition of gender tracking to MegaMek
    public boolean isFemale() {
        return Compute.randomInt(100) < percentFemale;
    }

    /**
     * @return the G_* type containing the randomly generated gender, utilizing the static
     * <code>percentFemale</code> value
     */
    public static int generate() {
        return generate(percentFemale);
    }

    /**
     *
     * @param percentFemale the percentage to use to determine if the generated person will be female
     * @return the G_* type containing the randomly generated gender
     */
    public static int generate(int percentFemale) {
        if (Compute.randomInt(100) < percentFemale) {
            return Crew.G_FEMALE;
        } else {
            return Crew.G_MALE;
        }
    }

    public static int getPercentFemale() {
        return percentFemale;
    }

    public static void setPercentFemale(int i) {
        percentFemale = i;
    }
}
