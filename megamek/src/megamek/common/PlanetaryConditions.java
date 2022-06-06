/*
 * Copyright (C) 2005 Ben Mazur (bmazur@sev.org)
 * Copyright (C) 2018-2021 - The MegaMek Team. All Rights Reserved.
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
package megamek.common;

import megamek.common.annotations.Nullable;
import megamek.common.enums.*;
import megamek.common.util.EncodeControl;
import megamek.utilities.xml.MMXMLUtility;
import org.apache.logging.log4j.LogManager;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * This class will hold all the information on planetary conditions and a variety of helper
 * functions for those conditions
 *
 * Unimplemented:
 *      Earthquake (TO:AR 53)
 *      Meteor Showers (TO:AR 54)
 *      All of Atmosphere (TO:AR 54)
 *      Light: Glare, Solar Flare (TO:AR 56)
 *      Weather: Lightning Storm (TO:AR 57)
 */
public class PlanetaryConditions implements Serializable {
    //region Variable Declarations
    private static final long serialVersionUID = 6838624193286089781L;

    // Atmosphere
    private Atmosphere atmosphere;
    private AtmosphericPressure atmosphericPressure;

    // Gravity
    private float gravity;

    // Temperature
    private double temperature;
    private double oldTemperature;

    // Light
    private Light light;

    // Wind
    private Wind windStrength;
    private boolean shiftingWindStrength;
    private Wind minimumWindStrength;
    private Wind maximumWindStrength;
    private HexCardinalDirection windDirection;
    private boolean shiftingWindDirection;

    // Weather
    private Weather weather;
    private Weather oldWeather;
    private boolean sleeting;
    private boolean sandBlowing;
    private boolean sandstorm;

    // Fog
    private Fog fog;

    // Misc
    private int earthquakeMagnitude;
    private boolean emi; // Electromagnetic Interference
    private boolean meteorShower;
    private int meteorCount;
    private boolean terrainAffected; // Can weather alter the terrain (add snow, mud, etc.)
    private boolean runOnce;

    private final ResourceBundle resources = ResourceBundle.getBundle("megamek.common.messages", new EncodeControl());
    //endregion Variable Declarations

    //region Constructors
    public PlanetaryConditions() {
        // Atmosphere
        setAtmosphere(Atmosphere.BREATHABLE);
        setAtmosphericPressure(AtmosphericPressure.STANDARD);

        // Gravity
        setGravity(1f);

        // Temperature
        setTemperature(25d);
        setOldTemperature(25d);

        // Light
        setLight(Light.DAY);

        // Wind
        setWindStrength(Wind.CALM);
        setShiftingWindStrength(false);
        setMinimumWindStrength(Wind.CALM);
        setMaximumWindStrength(Wind.TORNADO_F4);
        setWindDirection(HexCardinalDirection.RANDOMIZE);
        setShiftingWindDirection(false);

        // Weather
        setWeather(Weather.CLEAR);
        setOldWeather(Weather.CLEAR);
        setSleeting(false);
        setSandBlowing(false);
        setSandstorm(false);

        // Fog
        setFog(Fog.NONE);

        // Misc
        setEarthquakeMagnitude(0);
        setEMI(false);
        setMeteorShower(false);
        setMeteorCount(0);
        setTerrainAffected(true);
        setRunOnce(false);
    }

    public PlanetaryConditions(final PlanetaryConditions conditions) {
        // Atmosphere
        setAtmosphere(conditions.getAtmosphere());
        setAtmosphericPressure(conditions.getAtmosphericPressure());

        // Gravity
        setGravity(conditions.getGravity());

        // Temperature
        setTemperature(conditions.getTemperature());
        setOldTemperature(conditions.getOldTemperature());

        // Light
        setLight(conditions.getLight());

        // Wind
        setWindStrength(conditions.getWindStrength());
        setShiftingWindStrength(conditions.isShiftingWindStrength());
        setMinimumWindStrength(conditions.getMinimumWindStrength());
        setMaximumWindStrength(conditions.getMaximumWindStrength());
        setWindDirection(conditions.getWindDirection());
        setShiftingWindDirection(conditions.isShiftingWindDirection());

        // Weather
        setWeather(conditions.getWeather());
        setOldWeather(conditions.getOldWeather());
        setSleeting(conditions.isSleeting());
        setSandBlowing(conditions.isSandBlowing());
        setSandstorm(conditions.isSandstorm());

        // Fog
        setFog(conditions.getFog());

        // Misc
        setEarthquakeMagnitude(conditions.getEarthquakeMagnitude());
        setEMI(conditions.isEMI());
        setMeteorShower(conditions.isMeteorShower());
        setMeteorCount(conditions.getMeteorCount());
        setTerrainAffected(conditions.isTerrainAffected());
        setRunOnce(conditions.isRunOnce());
    }
    //endregion Constructors

    //region Getters/Setters
    //region Atmosphere
    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(final Atmosphere atmosphere) {
        this.atmosphere = atmosphere;
    }

    public AtmosphericPressure getAtmosphericPressure() {
        return atmosphericPressure;
    }

    public void setAtmosphericPressure(final AtmosphericPressure atmosphericPressure) {
        this.atmosphericPressure = atmosphericPressure;
    }
    //endregion Atmosphere

    //region Gravity
    public float getGravity() {
        return gravity;
    }

    public void setGravity(final float gravity) {
        this.gravity = gravity;
    }
    //endregion Gravity

    //region Temperature
    public double getTemperature() {
        return temperature;
    }

    public String getTemperatureDisplayableName(final double temperature) {
        return isExtremeTemperature(temperature)
                ? String.format("%.1f (%s)", temperature, resources.getString(
                (temperature > 0) ? "ExtremeHeat.text" : "ExtremeCold.text"))
                : String.valueOf(temperature);
    }

    public void setTemperature(final double temperature) {
        this.temperature = temperature;
    }

    public double getOldTemperature() {
        return oldTemperature;
    }

    public void setOldTemperature(final double oldTemperature) {
        this.oldTemperature = oldTemperature;
    }
    //endregion Temperature

    //region Light
    public Light getLight() {
        return light;
    }

    public void setLight(final Light light) {
        this.light = light;
    }
    //endregion Light

    //region Wind
    public Wind getWindStrength() {
        return windStrength;
    }

    public void setWindStrength(final Wind windStrength) {
        this.windStrength = windStrength;
    }

    public boolean isShiftingWindStrength() {
        return shiftingWindStrength;
    }

    public void setShiftingWindStrength(final boolean shiftingWindStrength) {
        this.shiftingWindStrength = shiftingWindStrength;
    }

    public Wind getMinimumWindStrength() {
        return minimumWindStrength;
    }

    public void setMinimumWindStrength(final Wind minimumWindStrength) {
        this.minimumWindStrength = minimumWindStrength;
    }

    public Wind getMaximumWindStrength() {
        return maximumWindStrength;
    }

    public void setMaximumWindStrength(final Wind maximumWindStrength) {
        this.maximumWindStrength = maximumWindStrength;
    }

    public HexCardinalDirection getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(final HexCardinalDirection windDirection) {
        this.windDirection = windDirection;
    }

    public boolean isShiftingWindDirection() {
        return shiftingWindDirection;
    }

    public void setShiftingWindDirection(final boolean shiftingWindDirection) {
        this.shiftingWindDirection = shiftingWindDirection;
    }
    //endregion Wind

    //region Weather
    public Weather getWeather() {
        return weather;
    }

    public void setWeather(final Weather weather) {
        this.weather = weather;
    }

    public Weather getOldWeather() {
        return oldWeather;
    }

    public void setOldWeather(final Weather oldWeather) {
        this.oldWeather = oldWeather;
    }

    public boolean isSleeting() {
        return sleeting;
    }

    public void setSleeting(final boolean sleeting) {
        this.sleeting = sleeting;
    }

    public boolean isSandBlowing() {
        return sandBlowing;
    }

    public void setSandBlowing(final boolean sandBlowing) {
        this.sandBlowing = sandBlowing;
    }

    public boolean isSandstorm() {
        return sandstorm;
    }

    public void setSandstorm(final boolean sandstorm) {
        this.sandstorm = sandstorm;
    }
    //endregion Weather

    //region Fog
    public Fog getFog() {
        return fog;
    }

    public void setFog(final Fog fog) {
        this.fog = fog;
    }
    //endregion Fog

    //region Misc
    public int getEarthquakeMagnitude() {
        return earthquakeMagnitude;
    }

    public void setEarthquakeMagnitude(final int earthquakeMagnitude) {
        this.earthquakeMagnitude = earthquakeMagnitude;
    }

    public boolean isEMI() {
        return emi;
    }

    public void setEMI(final boolean emi) {
        this.emi = emi;
    }

    public boolean isMeteorShower() {
        return meteorShower;
    }

    public void setMeteorShower(final boolean meteorShower) {
        this.meteorShower = meteorShower;
    }

    public int getMeteorCount() {
        return meteorCount;
    }

    public void setMeteorCount(final int meteorCount) {
        this.meteorCount = meteorCount;
    }

    public boolean isTerrainAffected() {
        return terrainAffected;
    }

    public void setTerrainAffected(final boolean terrainAffected) {
        this.terrainAffected = terrainAffected;
    }

    public boolean isRunOnce() {
        return runOnce;
    }

    public void setRunOnce(final boolean runOnce) {
        this.runOnce = runOnce;
    }
    //endregion Misc
    //endregion Getters/Setters

    //region Boolean Comparison Methods
    public static boolean isExtremeTemperature(final double temperature) {
        return (temperature > 50.0) || (temperature < -30.0);
    }

    public boolean isExtremeTemperature() {
        return isExtremeTemperature(getTemperature());
    }
    //endregion Boolean Comparison Methods

    /**
     * gravity penalties to PSRs
     * According to email from TPTB, you apply a penalty for every 0.5 gravities above or below 1 (rounding up)
     */
    public int getGravityPilotingPenalty() {
        return (int) Math.floor(Math.abs(getGravity() - 1.0) / 0.5);
    }

    public void determineWind() {
        if (getWindDirection().isRandomize()) {
            // Initial wind direction. If using level 2 rules, this
            // will be the wind direction for the whole battle.
            setWindDirection(HexCardinalDirection.getRandomDirection());
        } else if (isShiftingWindDirection()) {
            // Wind direction changes on a roll of 1 or 6
            switch (Compute.d6()) {
                case 1: // rotate clockwise
                    setWindDirection(getWindDirection().rotate(1));
                    break;
                case 6: // rotate counter-clockwise
                    setWindDirection(getWindDirection().rotate(-1));
                    break;
                default:
                    break;
            }
        }

        if (isShiftingWindStrength()) {
            // Wind strength changes on a roll of 1 or 6
            switch (Compute.d6()) {
                case 1: // weaker
                    if (getWindStrength().ordinal() > getMinimumWindStrength().ordinal()) {
                        setWindStrength(Wind.values()[getWindStrength().ordinal() - 1]);
                        doSleetCheck();
                        doSandstormCheck();
                    }
                    break;
                case 6: // stronger
                    if (getWindStrength().ordinal() < getMaximumWindStrength().ordinal()) {
                        setWindStrength(Wind.values()[getWindStrength().ordinal() + 1]);
                        doSleetCheck();
                        doSandstormCheck();
                    }
                    break;
                default:
                    break;
            }
        }

        //atmospheric pressure may limit wind strength
        if (getAtmosphericPressure().isTrace() && getWindStrength().isTornado()) {
            setWindStrength(Wind.STORM);
        } else if (getAtmosphericPressure().isThin() && getWindStrength().isTornadoF4()) {
            setWindStrength(Wind.TORNADO_F13);
        }
    }

    /**
     * The total planetary condition modifier for fire ignition
     */
    public int getIgnitionModifier() {
        int modifier = getWeather().getIgnitionModifier() + getWindStrength().getIgnitionModifier();

        if (getTemperature() > 30.0) {
            modifier -= getTemperatureDifference(30.0,-30.0);
        } else if (getTemperature() < 30.0) {
            modifier += getTemperatureDifference(30.0, -30.0);
        }

        return modifier;
    }

    /**
     * Do a roll for these weather conditions putting out fire
     * @return true if the fire is put out, otherwise false
     */
    public boolean extinguishFire() {
        return (Compute.d6(2) + getWeather().getExtinguishModifier()) > 10;
    }

    /**
     * Returns how much higher or lower than a given range, divided by
     * ten, rounded up, the temperature is
     */
    public int getTemperatureDifference(double high, double low) {
        int i = 0;
        // if the low is more than the high, reverse
        if (low > high) {
            var temp = low;
            low = high;
            high = temp;
        }

        if (getTemperature() < low) {
            do {
                i++;
            } while ((getTemperature() + (i * 10.0)) < low);
        } else if (!((getTemperature() >= low) && (getTemperature() <= high))) {
            do {
                i++;
            } while ((getTemperature() - (i * 10.0)) > high);
        }
        return i;
    }

    /**
     * @return a <code>String</code> with the reason why you cannot start a fire here
     */
    public String cannotStartFire() {
        if (getAtmosphericPressure().isTraceOrVacuum()) {
            return "atmosphere too thin";
        } else if (getWindStrength().isTornado()) {
            return "a tornado";
        } else {
            return null;
        }
    }

    /**
     * Planetary conditions on movement, except for gravity
     * @param entity - the entity in question
     * @return an <code>int</code> with the modifier to movement
     */
    public int getMovementModifiers(final Entity entity) {
        int mod = 0;

        // weather mods are calculated based on conditional effects ie extreme temperatures, wind

        // wind mods
        switch (getWindStrength()) {
            case LIGHT_GALE:
                if (!(entity instanceof BattleArmor)
                        && (entity.getMovementMode().isLegInfantry()
                        || entity.getMovementMode().isJumpInfantry())) {
                    mod -= 1;
                }
                break;
            case MODERATE_GALE:
                if (entity.isConventionalInfantry()) {
                    mod -= 1;
                }
                break;
            case STRONG_GALE:
            case STORM:
                if (entity instanceof BattleArmor) {
                    mod -= 1;
                } else if (entity instanceof Infantry) {
                    mod -= 2;
                }
                break;
            case TORNADO_F13:
                mod -= entity.isAirborne() ? 1 : 2;
                break;
            default:
                break;
        }

        // atmospheric pressure mods
        switch (getAtmosphericPressure()) {
            case THIN:
                if (entity.getMovementMode().isHoverVTOLOrWiGE()) {
                    mod -= 2;
                }
                break;
            case HIGH:
            case VERY_HIGH:
                if (entity.getMovementMode().isHoverVTOLOrWiGE()) {
                    mod += 1;
                }
                break;
            default:
                break;
        }

        // temperature difference
        if ((entity instanceof Tank) || ((entity instanceof Infantry) && !((Infantry) entity).isXCT())
                || (entity instanceof Protomech)) {
            mod -= Math.abs(getTemperatureDifference(50, -30));
        }

        return mod;
    }

    /**
     * is the given entity type doomed in these conditions?
     * @return a string given the reason for being doomed, null if not doomed
     */
    public @Nullable String whyDoomed(final Game game, final Entity entity) {
        if (getAtmosphericPressure().isTraceOrVacuum() && entity.doomedInVacuum()) {
            return "vacuum";
        } else if (getWindStrength().isTornadoF4() && !(entity instanceof Mech)) {
            return "tornado";
        } else if (getWindStrength().isTornadoF13()
                && (entity.isConventionalInfantry() || entity.getMovementMode().isHoverVTOLOrWiGE())) {
            return "tornado";
        } else if (getWindStrength().isStorm() && entity.isConventionalInfantry()) {
            return "storm";
        } else if (entity.doomedInExtremeTemp() && isExtremeTemperature() && !Compute.isInBuilding(game, entity)) {
            return "extreme temperature";
        } else {
            return null;
        }
    }

    /**
     * visual range based on conditions
     */
    public int getVisualRange(Entity entity, boolean targetIlluminated) {
        boolean Spotlight;
        boolean isMechVee;
        boolean isLargeCraft = false;
        boolean isAero = false;

        // Needed for MekWars for Maximum Visual Range.
        if (entity == null) {
            isMechVee = true;
            Spotlight = targetIlluminated;
        } else {
            Spotlight = entity.isUsingSearchlight();
            isMechVee = ((entity instanceof Mech) && !entity.isAero()) || (entity instanceof Tank);
            isLargeCraft = (entity instanceof Dropship) || (entity instanceof Jumpship);
            isAero = entity.isAero() && !isLargeCraft;
        }
        // Anything else is infantry

        // Beyond altitude 9, Aeros can't see. No need to repeat this test.
        if (isAero && (entity.getAltitude() > 9)) {
            return 0;
        }

        // New rulings per v3.02 errata. Spotlights are easier, yay!
        // Illuminated? Flat 45 hex distance
        if (targetIlluminated && !getLight().isDay()) {
            return 45;
        } else if (Spotlight && !getLight().isDay()) {
            //Using a searchlight? Flat 30 hex range
            if (isMechVee || isAero || isLargeCraft) {
                return 30;
            }
            // Except infantry/handheld, 10 hexes
            return 10;
        } else if (getLight().isPitchBlack()) {
            if (isMechVee || (isAero && (entity.getAltitude() < 2))) {
                return 3;
            } else if (isAero) {
                return 5;
            } else if (isLargeCraft) {
                return 4;
            } else {
                return 1;
            }
        } else if (getLight().isMoonlessNight() || getFog().isHeavy()) {
            if (isMechVee || (isAero && (entity.getAltitude() < 2))) {
                return 5;
            } else if (isAero) {
                return 10;
            } else if (isLargeCraft) {
                return 8;
            } else {
                return 2;
            }
        } else if (getWeather().isHeavyHail() || getWeather().isSleet() || getWeather().isHeavySnow()
                || (isSandBlowing() && getWindStrength().isModerateGaleOrStronger())
                || getLight().isFullMoon() || getWeather().isGustingRain() || getWeather().isIceStorm()
                || getWeather().isDownpour()) {
            if (isMechVee || (isAero && (entity.getAltitude() < 2))) {
                return 10;
            } else if (isAero) {
                return 20;
            } else if (isLargeCraft) {
                return 15;
            } else {
                return 5;
            }
        } else if (getLight().isDusk() || getWeather().isHeavyRain() || getWeather().isSnowFlurries()
                || (getWeather().isModerateSnow() && getWindStrength().isModerateGaleOrStronger())) {
            if (isMechVee || (isAero && (entity.getAltitude() < 2))) {
                return 15;
            } else if (isAero) {
                return 30;
            } else if (isLargeCraft) {
                return 20;
            } else {
                return 8;
            }
        } else if (getWeather().isModerateSnow() || getWeather().isModerateRain()) {
            if (isMechVee || (isAero && (entity.getAltitude() < 2))) {
                return 20;
            } else if (isAero) {
                return 50;
            } else if (isLargeCraft) {
                return 25;
            } else {
                return 10;
            }
        } else if (!getLight().isDay() || getWeather().isLightSnow() || getWeather().isLightRain()
                || getWeather().isLightHail() || getFog().isLight()) {
            if (isMechVee || (isAero && (entity.getAltitude() < 2))) {
                return 30;
            } else if (isAero) {
                return 60;
            } else if (isLargeCraft) {
                return 35;
            } else {
                return 15;
            }
        } else {
            if (isMechVee || (isAero && (entity.getAltitude() < 2))) {
                return 60;
            } else if (isAero) {
                return 120;
            } else if (isLargeCraft) {
                return 70;
            } else {
                return 30;
            }
        }
    }

    public int getDropRate() {
        // Atmospheric pressure mods
        switch (getAtmosphericPressure()) {
            case TRACE:
                return 8;
            case THIN:
                return 5;
            case HIGH:
                return 2;
            case VERY_HIGH:
                return 1;
            default:
                return 3;
        }
    }

    public boolean isRecklessConditions() {
        return !getFog().isNone() || getLight().isNight();
    }

    public void alterConditions(final PlanetaryConditions conditions) {
        // Atmosphere
        setAtmosphere(conditions.getAtmosphere());
        setAtmosphericPressure(conditions.getAtmosphericPressure());

        // Gravity
        setGravity(conditions.getGravity());

        // Temperature
        setTemperature(conditions.getTemperature());
        setOldTemperature(conditions.getOldTemperature());

        // Light
        setLight(conditions.getLight());

        // Wind
        setWindStrength(conditions.getWindStrength());
        setShiftingWindStrength(conditions.isShiftingWindStrength());
        setMinimumWindStrength(conditions.getMinimumWindStrength());
        setMaximumWindStrength(conditions.getMaximumWindStrength());
        setWindDirection(conditions.getWindDirection());
        setShiftingWindDirection(conditions.isShiftingWindDirection());

        // Weather
        setWeather(conditions.getWeather());
        setOldWeather(conditions.getOldWeather());
        setSleeting(conditions.isSleeting());
        setSandBlowing(conditions.isSandBlowing());
        setSandstorm(conditions.isSandstorm());

        // Fog
        setFog(conditions.getFog());

        // Misc
        setEarthquakeMagnitude(conditions.getEarthquakeMagnitude());
        setEMI(conditions.isEMI());
        setMeteorShower(conditions.isMeteorShower());
        setMeteorCount(conditions.getMeteorCount());
        setTerrainAffected(conditions.isTerrainAffected());
        setRunOnce(conditions.isRunOnce());

        if (!isRunOnce()) {
            setTemperatureFromWeather();
            setWindFromWeather();
            setSandstorm();
            setRunOnce(true);
        }
    }

    private void setTemperatureFromWeather() {
        switch (getWeather()) {
            case SLEET:
            case LIGHT_SNOW:
                setTemperature(-40.0);
                break;
            case MODERATE_SNOW:
            case SNOW_FLURRIES:
            case HEAVY_SNOW:
                setTemperature(-50.0);
                break;
            case ICE_STORM:
                setTemperature(-60.0);
                break;
            default:
                break;
        }
    }

    private void setWindFromWeather() {
        switch (getWeather()) {
            case SLEET:
                setSleeting(true);
                break;
            case ICE_STORM:
            case SNOW_FLURRIES:
                setWindStrength(Wind.MODERATE_GALE);
                setShiftingWindStrength(false);
                break;
            case GUSTING_RAIN:
                setWindStrength(Wind.STRONG_GALE);
                setShiftingWindStrength(false);
                break;
            default:
                break;
        }
    }

    private void doSleetCheck() {
        if (isSleeting()) {
            if (getWindStrength().isCalmOrLightGale()) {
                setSleeting(false);
                setWeather(Weather.CLEAR);
                setOldWeather(Weather.SLEET);
                setOldTemperature(getTemperature());
                setTemperature(25);
            } else if (getWindStrength().isStrongGaleOrStronger()) {
                setShiftingWindStrength(false);
                setWindStrength(Wind.MODERATE_GALE);
            }
        }

        if (getOldWeather().isSleet() && getWindStrength().isModerateGale() && !isSleeting()) {
            setSleeting(true);
            setTemperature(getOldTemperature());
            setOldWeather(Weather.CLEAR);
            setOldTemperature(25.0);
            setWeather(Weather.SLEET);
        }
    }

    public void setSandstorm() {
        if (isSandBlowing() && getWindStrength().isCalmOrLightGale()) {
            setWindStrength(Wind.MODERATE_GALE);
            setSandstorm(true);
        }
    }

    private void doSandstormCheck() {
        if (isSandBlowing() && getWindStrength().isCalmOrLightGale()) {
            setSandstorm(isSandBlowing());
            setSandBlowing(false);
        } else if (isSandstorm() && getWindStrength().isModerateGaleOrStronger()) {
            setSandstorm(isSandBlowing());
            setSandBlowing(true);
        }
    }

    //region File I/O
    public void writeToXML(final PrintWriter pw, int indent) {
        MMXMLUtility.writeSimpleXMLOpenTag(pw, indent++, "planetaryConditions");
        //region Atmosphere
        if (!getAtmosphere().isBreathable()) {
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "atmosphere", getAtmosphere().name());
        }

        if (!getAtmosphericPressure().isStandard()) {
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "atmosphericPressure", getAtmosphericPressure().name());
        }
        //endregion Atmosphere

        //region Gravity
        if (getGravity() != 1.0f) {
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "gravity", getGravity());
        }
        //endregion Gravity

        //region Temperature
        MMXMLUtility.writeSimpleXMLTag(pw, indent, "temperature", getTemperature());
        if (getOldTemperature() != getTemperature()) {
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "oldTemperature", getOldTemperature());
        }
        //endregion Temperature

        //region Light
        if (!getLight().isDay()) {
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "light", getLight().name());
        }
        //endregion Light

        //region Wind
        if (!getWindStrength().isCalm()) {
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "windStrength", getWindStrength().name());
        }

        if (isShiftingWindStrength()) {
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "windDirection", isShiftingWindStrength());
        }

        if (!getMinimumWindStrength().isCalm()) {
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "minimumWindStrength", getMinimumWindStrength().name());
        }

        if (!getMaximumWindStrength().isTornadoF4()) {
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "maximumWindStrength", getMaximumWindStrength().name());
        }

        if (!getWindDirection().isRandomize()) {
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "windDirection", getWindDirection().name());
        }

        if (isShiftingWindDirection()) {
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "shiftingWindDirection", isShiftingWindDirection());
        }
        //endregion Wind

        //region Fog
        if (!getFog().isNone()) {
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "fog", getFog().name());
        }
        //endregion Fog

        //region Misc
        if (getEarthquakeMagnitude() != 0) {
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "earthquake", getEarthquakeMagnitude());
        }

        if (isEMI()) {
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "emi", isEMI());
        }

        if (isMeteorShower()) {
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "meteorShower", isMeteorShower());
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "meteorCount", getMeteorCount());
        }

        if (!isTerrainAffected()) {
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "terrainAffected", isTerrainAffected());
        }

        if (isRunOnce()) {
            MMXMLUtility.writeSimpleXMLTag(pw, indent, "runOnce", isRunOnce());
        }
        //endregion Misc
        MMXMLUtility.writeSimpleXMLCloseTag(pw, --indent, "planetaryConditions");
    }

    public static @Nullable PlanetaryConditions generateInstanceFromXML(final NodeList nl) {
        final PlanetaryConditions planetaryConditions = new PlanetaryConditions();

        try {
            for (int x = 0; x < nl.getLength(); x++) {
                final Node wn = nl.item(x);
                switch (wn.getNodeName()) {
                    //region Atmosphere
                    case "atmosphere":
                        planetaryConditions.setAtmosphere(Atmosphere.valueOf(wn.getTextContent().trim()));
                        break;
                    case "atmosphericPressure":
                        planetaryConditions.setAtmosphericPressure(AtmosphericPressure.valueOf(wn.getTextContent().trim()));
                        break;
                    //endregion Atmosphere

                    //region Gravity
                    case "gravity":
                        planetaryConditions.setGravity(Float.parseFloat(wn.getTextContent().trim()));
                        break;
                    //endregion Gravity

                    //region Temperature
                    case "temperature":
                        planetaryConditions.setTemperature(Double.parseDouble(wn.getTextContent().trim()));
                        break;
                    case "oldTemperature":
                        planetaryConditions.setOldTemperature(Double.parseDouble(wn.getTextContent().trim()));
                        break;
                    //endregion Temperature

                    //region Light
                    case "light":
                        planetaryConditions.setLight(Light.valueOf(wn.getTextContent().trim()));
                        break;
                    //endregion Light

                    //region Wind
                    case "windStrength":
                        planetaryConditions.setWindStrength(Wind.parseFromString(wn.getTextContent().trim()));
                        break;
                    case "shiftingWindStrength":
                        planetaryConditions.setShiftingWindStrength(Boolean.parseBoolean(wn.getTextContent().trim()));
                        break;
                    case "minimumWindStrength":
                        planetaryConditions.setMinimumWindStrength(Wind.parseFromString(wn.getTextContent().trim()));
                        break;
                    case "maximumWindStrength":
                        planetaryConditions.setMaximumWindStrength(Wind.parseFromString(wn.getTextContent().trim()));
                        break;
                    case "windDirection":
                        planetaryConditions.setWindDirection(HexCardinalDirection.parseFromString(wn.getTextContent().trim()));
                        break;
                    case "shiftingWindDirection":
                        planetaryConditions.setShiftingWindDirection(Boolean.parseBoolean(wn.getTextContent().trim()));
                        break;
                    //endregion Wind

                    //region Weather
                    case "weather":
                        planetaryConditions.setWeather(Weather.valueOf(wn.getTextContent().trim()));
                        break;
                    case "oldWeather":
                        planetaryConditions.setOldWeather(Weather.valueOf(wn.getTextContent().trim()));
                        break;
                    case "sleeting":
                        planetaryConditions.setSleeting(Boolean.parseBoolean(wn.getTextContent().trim()));
                        break;
                    case "sandBlowing":
                        planetaryConditions.setSandBlowing(Boolean.parseBoolean(wn.getTextContent().trim()));
                        break;
                    case "sandstorm":
                        planetaryConditions.setSandstorm(Boolean.parseBoolean(wn.getTextContent().trim()));
                        break;
                    //endregion Weather

                    //region Fog
                    case "fog":
                        planetaryConditions.setFog(Fog.valueOf(wn.getTextContent().trim()));
                        break;
                    //endregion Fog

                    //region Misc
                    case "earthquake":
                        planetaryConditions.setEarthquakeMagnitude(Integer.parseInt(wn.getTextContent().trim()));
                        break;
                    case "emi":
                        planetaryConditions.setEMI(Boolean.parseBoolean(wn.getTextContent().trim()));
                        break;
                    case "meteorShower":
                        planetaryConditions.setMeteorShower(Boolean.parseBoolean(wn.getTextContent().trim()));
                        break;
                    case "meteorCount":
                        planetaryConditions.setMeteorCount(Integer.parseInt(wn.getTextContent().trim()));
                        break;
                    case "terrainAffected":
                        planetaryConditions.setTerrainAffected(Boolean.parseBoolean(wn.getTextContent().trim()));
                        break;
                    case "runOnce":
                        planetaryConditions.setRunOnce(Boolean.parseBoolean(wn.getTextContent().trim()));
                        break;
                    //endregion Misc

                    default:
                        break;
                }
            }
        } catch (Exception ex) {
            LogManager.getLogger().error(ex);
            return null;
        }

        return planetaryConditions;
    }
    //endregion File I/O

    @Override
    public Object clone() {
        return new PlanetaryConditions(this);
    }
}
