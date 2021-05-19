/*
 * MegaMek -
 * Copyright (C) 2005 Ben Mazur (bmazur@sev.org)
 * Copyright (C) 2018-2021 - The MegaMek Team. All Rights Reserved.
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
package megamek.common;

import megamek.common.annotations.Nullable;
import megamek.common.enums.AtmosphericPressure;
import megamek.common.enums.Fog;
import megamek.common.enums.Light;
import megamek.common.enums.Weather;
import megamek.common.enums.Wind;
import megamek.common.enums.WindDirection;

import java.io.Serializable;

/**
 * This class will hold all the information on planetary conditions and a variety of helper functions
 * for those conditions
 */
public class PlanetaryConditions implements Serializable {
    //region Variable Declarations
    private static final long serialVersionUID = 6838624193286089781L;

    //set up the specific conditions
    private Light light = Light.DAY;
    private Weather weather = Weather.CLEAR;
    private Weather oldWeather = Weather.CLEAR;
    private Wind windStrength = Wind.CALM;
    private WindDirection windDirection = WindDirection.RANDOMIZE;
    private boolean shiftingWindDirection = false;
    private boolean shiftingWindStrength = false;
    private boolean isSleeting = false;
    private AtmosphericPressure atmosphere = AtmosphericPressure.STANDARD;
    private Fog fog = Fog.NONE;
    private int temperature = 25;
    private int oldTemperature = 25;
    private float gravity = (float) 1.0;
    private boolean emi = false;
    private boolean terrainAffected = true; // Can weather alter the terrain (add snow, mud, etc.)
    private Wind minimumWindStrength = Wind.CALM;
    private Wind maximumWindStrength = Wind.TORNADO_F4;

    //misc
    private boolean sandBlowing = false;
    private boolean sandstorm = false;
    private boolean runOnce = false;
    //endregion Variable Declarations

    //region Constructors
    public PlanetaryConditions() {

    }

    /** Creates new PlanetaryConditions that is a duplicate of another */
    public PlanetaryConditions(PlanetaryConditions other) {
        setLight(other.getLight());
        setWeather(other.getWeather());
        // OldWeather?
        setWindStrength(other.getWindStrength());
        setWindDirection(other.getWindDirection());
        setShiftingWindDirection(other.shiftingWindDirection());
        setShiftingWindStrength(other.shiftingWindStrength());
        // isSleeting?
        setAtmosphere(other.getAtmosphere());
        setFog(other.getFog());
        setTemperature(other.getTemperature());
        // OldTemperature?
        setGravity(other.getGravity());
        setEMI(other.hasEMI());
        setTerrainAffected(other.isTerrainAffected());
        setMinimumWindStrength(other.getMinimumWindStrength());
        setMaximumWindStrength(other.getMaximumWindStrength());
        setSandBlowing(other.isSandBlowing());
        // Sandstorm?
        setRunOnce(other.getRunOnce());
    }
    //endregion Constructors

    //region Getters/Setters
    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Weather getOldWeather() {
        return oldWeather;
    }

    public void setOldWeather(Weather oldWeather) {
        this.oldWeather = oldWeather;
    }

    public Wind getWindStrength() {
        return windStrength;
    }

    public void setWindStrength(Wind windStrength) {
        this.windStrength = windStrength;
    }

    public WindDirection getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(WindDirection windDirection) {
        this.windDirection = windDirection;
    }

    public boolean shiftingWindDirection() {
        return shiftingWindDirection;
    }

    public void setShiftingWindDirection(boolean shiftingWindDirection) {
        this.shiftingWindDirection = shiftingWindDirection;
    }

    public boolean shiftingWindStrength() {
        return shiftingWindStrength;
    }

    public void setShiftingWindStrength(boolean shiftingWindStrength) {
        this.shiftingWindStrength = shiftingWindStrength;
    }

    public boolean isSleeting() {
        return isSleeting;
    }

    public void setIsSleeting(boolean isSleeting) {
        this.isSleeting = isSleeting;
    }

    public AtmosphericPressure getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(AtmosphericPressure atmosphere) {
        this.atmosphere = atmosphere;
    }

    public Fog getFog() {
        return fog;
    }

    public void setFog(Fog fog) {
        this.fog = fog;
    }

    public int getTemperature() {
        return temperature;
    }

    public static String getTemperatureDisplayableName(final int temp) {
        return isExtremeTemperature(temp)
                ? String.format("%d (%s)", temp, Messages.getString((temp > 0)
                        ? "ExtremeHeat.text" : "ExtremeCold.text"))
                : String.valueOf(temp);
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getOldTemperature() {
        return oldTemperature;
    }

    public void setOldTemperature(int oldTemperature) {
        this.oldTemperature = oldTemperature;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public boolean hasEMI() {
        return emi;
    }

    public void setEMI(boolean emi) {
        this.emi = emi;
    }

    public boolean isTerrainAffected() {
        return terrainAffected;
    }

    public void setTerrainAffected(boolean terrainAffected) {
        this.terrainAffected = terrainAffected;
    }

    public Wind getMinimumWindStrength() {
        return minimumWindStrength;
    }

    public void setMinimumWindStrength(Wind minimumWindStrength) {
        this.minimumWindStrength = minimumWindStrength;
    }

    public Wind getMaximumWindStrength() {
        return maximumWindStrength;
    }

    public void setMaximumWindStrength(Wind maximumWindStrength) {
        this.maximumWindStrength = maximumWindStrength;
    }

    public boolean getRunOnce() {
        return runOnce;
    }

    public void setRunOnce(boolean runOnce) {
        this.runOnce = runOnce;
    }

    public boolean isSandBlowing() {
        return sandBlowing;
    }

    public void setSandBlowing(boolean sandBlowing) {
        this.sandBlowing = sandBlowing;
    }

    public boolean isSandstorm() {
        return sandstorm;
    }

    public void setSandstorm() {
        if (isSandBlowing() && !getWindStrength().isModerateGaleOrStronger()) {
            setWindStrength(Wind.MODERATE_GALE);
            setSandstormDirect(true);
        }
    }

    private void setSandstormDirect(final boolean sandstorm) {
        this.sandstorm = sandstorm;
    }
    //endregion Getters/Setters

    //region Boolean Comparison Methods
    public static boolean isExtremeTemperature(int temperature) {
        return (temperature > 50) || (temperature < -30);
    }

    public boolean isExtremeTemperature() {
        return isExtremeTemperature(getTemperature());
    }
    //endregion Boolean Comparison Methods

    @Override
    public Object clone() {
        return new PlanetaryConditions(this);
    }

    /**
     * gravity penalties to PSRs
     * According to email from TPTB, you apply a penalty for every 0.5 gravities above or below 1 (rounding up)
     */
    public int getGravityPilotingPenalty() {
        return (int) Math.floor(Math.abs(gravity - 1.0) / 0.5);
    }

    public void determineWind() {
        if (getWindDirection().isRandomize()) {
            // Initial wind direction. If using level 2 rules, this
            // will be the wind direction for the whole battle.
            windDirection = Compute.d6(1) - 1;
        } else if (shiftingWindDirection()) {
            // Wind direction changes on a roll of 1 or 6
            switch (Compute.d6()) {
                case 1: // rotate clockwise
                    windDirection = (windDirection + 1) % 6;
                    break;
                case 6: // rotate counter-clockwise
                    windDirection = (windDirection + 5) % 6;
            }
        }

        if (shiftingWindStrength()) {
            // Wind strength changes on a roll of 1 or 6
            switch (Compute.d6()) {
                case 1: // weaker
                    windStrength = Math.max(minWindStrength, --windStrength);
                    doSleetCheck();
                    doSandStormCheck();
                    break;
                case 6: // stronger
                    windStrength = Math.min(maxWindStrength, ++windStrength);
                    doSleetCheck();
                    doSandStormCheck();
                    break;
            }
        }

        //atmospheric pressure may limit wind strength
        if (getAtmosphere().isTrace() && getWindStrength().isTornado()) {
            setWindStrength(Wind.STORM);
        } else if (getAtmosphere().isThin() && getWindStrength().isTornadoF4()) {
            setWindStrength(Wind.TORNADO_F13);
        }
    }

    /**
     * modifiers for fire ignition
     */
    public int getIgniteModifiers() {
        int mod;

        switch (getWeather()) {
            case LIGHT_RAIN:
            case MODERATE_RAIN:
                mod = 1;
                break;
            case HEAVY_RAIN:
            case DOWNPOUR:
            case LIGHT_SNOW:
            case MODERATE_SNOW:
            case GUSTING_RAIN:
            case SNOW_FLURRIES:
                mod = 2;
                break;
            case HEAVY_SNOW:
            case BLIZZARD:
            case LIGHT_HAIL:
            case HEAVY_HAIL:
                mod = 3;
                break;
            default:
                mod = 0;
                break;
        }

        if (getWindStrength().isLightGale() || getWindStrength().isModerateGale()) {
            mod += 2;
        }

        if (getWindStrength().isStrongGale() || getWindStrength().isStorm() || getWeather().isIceStorm()) {
            mod += 4;
        }

        if (getTemperature() > 30) {
            mod -= getTemperatureDifference(30,-30);
        } else if (getTemperature() < 30) {
            mod += getTemperatureDifference(30, -30);
        }

        return mod;
    }

    /**
     * Do a roll for these weather conditions putting out fire
     * return boolean
     */
    public boolean putOutFire() {
        int roll = Compute.d6(2);
        switch (getWeather()) {
            case LIGHT_HAIL:
            case LIGHT_RAIN:
            case LIGHT_SNOW:
                roll = roll + 1;
                break;
            case HEAVY_HAIL:
            case MODERATE_RAIN:
            case MODERATE_SNOW:
            case SNOW_FLURRIES:
                roll = roll + 2;
                break;
            case HEAVY_RAIN:
            case GUSTING_RAIN:
            case HEAVY_SNOW:
            case BLIZZARD:
                roll = roll + 3;
                break;
            case DOWNPOUR:
                roll = roll + 4;
                break;
            default:
                roll = -1;
                break;
        }

        return roll > 10;
    }

    /**
     * Returns how much higher or lower than a given range, divided by
     * ten, rounded up, the temperature is
     */

    public int getTemperatureDifference(int high, int low) {
        int i = 0;
        //if the low is more than the high, reverse
        if (low > high) {
            int tempLow = low;
            low = high;
            high = tempLow;

        }

        if (getTemperature() < low) {
            do {
                i++;
            } while ((getTemperature() + (i * 10)) < low);
        } else if (!((getTemperature() >= low) && (getTemperature() <= high))) {
            do {
                i++;
            } while ((getTemperature() - (i * 10)) > high);
        }
        return i;
    }

    /**
     *
     * @return a <code>String</code> with the reason why you cannot start a fire here
     */
    public String cannotStartFire() {
        if (getAtmosphere().isTraceOrVacuum()) {
            return "atmosphere too thin";
        } else if (getWindStrength().isTornado()) {
            return "a tornado";
        } else {
            return null;
        }
    }

    /**
     * Planetary conditions on movement, except for gravity
     * @param en - the entity in question
     * @return an <code>int</code> with the modifier to movement
     */
    public int getMovementMods(Entity en) {
        int mod = 0;

        // weather mods are calculated based on conditional effects ie extreme temperatures, wind

        // wind mods
        switch (getWindStrength()) {
            case LIGHT_GALE:
                if (!(en instanceof BattleArmor)
                        && ((en.getMovementMode() == EntityMovementMode.INF_LEG)
                        || (en.getMovementMode() == EntityMovementMode.INF_JUMP))) {
                    mod -= 1;
                }
                break;
            case MODERATE_GALE:
                if (en.isConventionalInfantry()) {
                    mod -= 1;
                }
                break;
            case STRONG_GALE:
            case STORM:
                if (en instanceof BattleArmor) {
                    mod -= 1;
                } else if (en instanceof Infantry) {
                    mod -= 2;
                }
                break;
            case TORNADO_F13:
                mod -= en.isAirborne() ? 1 : 2;
                break;
            default:
                break;
        }

        // atmospheric pressure mods
        switch (getAtmosphere()) {
            case THIN:
                if ((en.getMovementMode() == EntityMovementMode.HOVER)
                        || (en.getMovementMode() == EntityMovementMode.WIGE)
                        || (en.getMovementMode() == EntityMovementMode.VTOL)) {
                    mod -= 2;
                }
                break;
            case HIGH:
            case VERY_HIGH:
                if ((en.getMovementMode() == EntityMovementMode.HOVER)
                        || (en.getMovementMode() == EntityMovementMode.WIGE)
                        || (en.getMovementMode() == EntityMovementMode.VTOL)) {
                    mod += 1;
                }
                break;
            default:
                break;
        }

        // temperature difference
        if ((en instanceof Tank) || ((en instanceof Infantry) && !((Infantry) en).isXCT())
                || (en instanceof Protomech)) {
            mod -= Math.abs(getTemperatureDifference(50, -30));
        }

        return mod;
    }

    /**
     * is the given entity type doomed in these conditions?
     * @return a string given the reason for being doomed, null if not doomed
     */
    public @Nullable String whyDoomed(final IGame game, final Entity entity) {
        if (getAtmosphere().isTraceOrVacuum() && entity.doomedInVacuum()) {
            return "vacuum";
        } else if (getWindStrength().isTornadoF4() && !(entity instanceof Mech)) {
            return "tornado";
        } else if (getWindStrength().isTornadoF13()
                && (entity.isConventionalInfantry()
                || ((entity.getMovementMode() == EntityMovementMode.HOVER)
                || (entity.getMovementMode() == EntityMovementMode.WIGE)
                || (entity.getMovementMode() == EntityMovementMode.VTOL)))) {
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
     *
     */
    public int getVisualRange(Entity en, boolean targetIlluminated) {

        boolean Spotlight = false;

        boolean isMechVee = false;
        boolean isLargeCraft = false;
        boolean isAero = false;

        //Needed for MekWars for Maximum Visual Range.
        if ( en == null ) {
            isMechVee = true;
            Spotlight = targetIlluminated;
        }else {
            Spotlight = en.isUsingSearchlight();
            isMechVee = (en instanceof Mech && !en.isAero()) || (en instanceof Tank);
            isLargeCraft = (en instanceof Dropship) || (en instanceof Jumpship);
            isAero = (en.isAero()) && !isLargeCraft;
        }
        //anything else is infantry

        //Beyond altitude 9, Aeros can't see. No need to repeat this test.
        if (isAero && (en.getAltitude() > 9)) {
            return 0;
        }

        //New rulings per v3.02 errata. Spotlights are easier, yay!
        //Illuminated?  Flat 45 hex distance
        if (targetIlluminated && !getLight().isDay()) {
            return 45;
        } else if (Spotlight && !getLight().isDay()) {
            //Using a searchlight?  Flat 30 hex range
            if (isMechVee || isAero || isLargeCraft) {
                return 30;
            }
            //Except infantry/handheld, 10 hexes
            return 10;
        } else if (getLight().isPitchBlack()) {
            if (isMechVee || (isAero && (en.getAltitude() < 2))) {
                return 3;
            } else if (isAero) {
                return 5;
            } else if (isLargeCraft) {
                return 4;
            } else {
                return 1;
            }
        } else if (getLight().isMoonless() || getFog().isHeavy()) {
            if (isMechVee || (isAero && (en.getAltitude() < 2))) {
                return 5;
            } else if (isAero) {
                return 10;
            } else if (isLargeCraft) {
                return 8;
            } else {
                return 2;
            }
        } else if ((weatherConditions == WE_HEAVY_HAIL)
                || (weatherConditions == WE_SLEET)
                || (weatherConditions == WE_HEAVY_SNOW)
                || (blowingSand && getWindStrength().isModerateGaleOrStronger())
                || (lightConditions == L_FULL_MOON)
                || (weatherConditions == WE_GUSTING_RAIN)
                || (weatherConditions == WE_ICE_STORM)
                || (weatherConditions == WE_DOWNPOUR)) {
            if (isMechVee || (isAero && (en.getAltitude() < 2))) {
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
            if (isMechVee || (isAero && (en.getAltitude() < 2))) {
                return 15;
            } else if (isAero) {
                return 30;
            } else if (isLargeCraft) {
                return 20;
            } else {
                return 8;
            }
        } else if (getWeather().isModerateSnow() || getWeather().isModerateRain()) {
            if (isMechVee || (isAero && (en.getAltitude() < 2))) {
                return 20;
            } else if (isAero) {
                return 50;
            } else if (isLargeCraft) {
                return 25;
            } else {
                return 10;
            }
        } else if ((lightConditions > L_DAY)
                || (weatherConditions == WE_LIGHT_SNOW)
                || (weatherConditions == WE_LIGHT_RAIN)
                || (weatherConditions == WE_LIGHT_HAIL)
                || (fog == FOG_LIGHT)) {
            if (isMechVee || (isAero && (en.getAltitude() < 2))) {
                return 30;
            } else if (isAero) {
                return 60;
            } else if (isLargeCraft) {
                return 35;
            } else {
                return 15;
            }
        } else {
            if (isMechVee || (isAero && (en.getAltitude() < 2))) {
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
        //atmospheric pressure mods
        switch (getAtmosphere()) {
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

    public void alterConditions(PlanetaryConditions conditions) {
        lightConditions = conditions.lightConditions;
        weatherConditions = conditions.weatherConditions;
        windStrength = conditions.windStrength;
        windDirection = conditions.windDirection;
        shiftWindDirection = conditions.shiftWindDirection;
        shiftWindStrength = conditions.shiftWindStrength;
        minWindStrength = conditions.minWindStrength;
        maxWindStrength = conditions.maxWindStrength;
        atmosphere = conditions.atmosphere;
        temperature = conditions.temperature;
        gravity = conditions.gravity;
        emi = conditions.emi;
        fog = conditions.fog;
        terrainAffected = conditions.terrainAffected;
        blowingSand = conditions.blowingSand;
        runOnce = conditions.runOnce;

        if (!runOnce) {
            setTempFromWeather();
            setWindFromWeather();
            setSandStorm();
            runOnce = true;
        }

    }

    private void setTempFromWeather() {
        switch (weatherConditions) {
            case WE_SLEET:
            case WE_LIGHT_SNOW:
                temperature = -40;
                break;
            case WE_MOD_SNOW:
            case WE_SNOW_FLURRIES:
            case WE_HEAVY_SNOW:
                temperature = -50;
                break;
            case WE_ICE_STORM:
                temperature = -60;
                break;
        }
    }

    private void setWindFromWeather() {
        switch (weatherConditions) {
            case WE_SLEET:
                setSleet(true);
                break;
            case WE_ICE_STORM:
            case WE_SNOW_FLURRIES:
                windStrength = WI_MOD_GALE;
                shiftWindStrength = false;
                break;
            case WE_GUSTING_RAIN:
                windStrength = WI_STRONG_GALE;
                shiftWindStrength = false;
                break;
        }
    }

    private void doSleetCheck() {
        if (isSleeting && windStrength < WI_MOD_GALE) {
            setSleet(false);
            weatherConditions = WE_NONE;
            oldWeatherConditions = WE_SLEET;
            oldTemperature = temperature;
            temperature = 25;
        }
        if (isSleeting() && windStrength > WI_MOD_GALE) {
            shiftWindStrength = false;
            windStrength = WI_MOD_GALE;
        }
        if ((oldWeatherConditions == WE_SLEET)
                && (windStrength == WI_MOD_GALE)
                && !isSleeting() ) {
            setSleet(true);
            temperature = oldTemperature;
            oldWeatherConditions = WE_NONE;
            oldTemperature = 25;
            weatherConditions = WE_SLEET;
        }
    }

    private void doSandStormCheck() {
        if (blowingSand && windStrength < WI_MOD_GALE) {
            sandStorm = blowingSand;
            blowingSand = false;
        }
        if (sandStorm && windStrength > WI_LIGHT_GALE) {
            sandStorm = blowingSand;
            blowingSand = true;
        }
    }
}
