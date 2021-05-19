package megamek.common;

import megamek.common.enums.AtmosphericPressure;
import megamek.common.enums.Wind;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PlanetaryConditionsTest {

    @Test
    public void testWhyDoomed() {
        Game mockGame = mock(Game.class);
        Entity mockEntity;
        PlanetaryConditions planetaryConditions;
        Board mockBoard = mock(Board.class);
        IHex mockHex = mock(Hex.class);
        Coords mockCoords = mock(Coords.class);

        // Trace atmosphere - Entity doomed in vacuum/trace atmosphere

        planetaryConditions = new PlanetaryConditions();
        planetaryConditions.setAtmosphere(AtmosphericPressure.TRACE);
        when(mockGame.getPlanetaryConditions()).thenReturn(planetaryConditions);
        mockEntity = mock(Infantry.class);
        when(mockEntity.doomedInVacuum()).thenReturn(true);
        assertEquals("vacuum", planetaryConditions.whyDoomed(mockGame, mockEntity));
        reset(mockEntity, mockGame);

        // Trace atmosphere - Entity not doomed in vacuum/trace atmosphere

        planetaryConditions = new PlanetaryConditions();
        planetaryConditions.setAtmosphere(AtmosphericPressure.TRACE);
        when(mockGame.getPlanetaryConditions()).thenReturn(planetaryConditions);
        mockEntity = mock(Infantry.class);
        when(mockEntity.doomedInVacuum()).thenReturn(false);
        assertNull(planetaryConditions.whyDoomed(mockGame, mockEntity));
        reset(mockEntity, mockGame);

        // F4 Tornado - Entity is a mech (not doomed)

        planetaryConditions = new PlanetaryConditions();
        planetaryConditions.setWindStrength(Wind.TORNADO_F4);
        mockEntity = mock(Mech.class);
        when(mockGame.getPlanetaryConditions()).thenReturn(planetaryConditions);
        assertNull(planetaryConditions.whyDoomed(mockGame, mockEntity));
        reset(mockEntity, mockGame);


        // F4 Tornado - Entity is not a mech (doomed)

        planetaryConditions = new PlanetaryConditions();
        planetaryConditions.setWindStrength(Wind.TORNADO_F4);
        mockEntity = mock(Infantry.class);
        when(mockGame.getPlanetaryConditions()).thenReturn(planetaryConditions);
        assertEquals("tornado", planetaryConditions.whyDoomed(mockGame, mockEntity));
        reset(mockEntity, mockGame);

        // F1-3 Tornado - Entity movement mode is hover (doomed)

        planetaryConditions = new PlanetaryConditions();
        planetaryConditions.setWindStrength(Wind.TORNADO_F13);
        mockEntity = mock(Tank.class);
        when(mockEntity.getMovementMode()).thenReturn(EntityMovementMode.HOVER);
        when(mockGame.getPlanetaryConditions()).thenReturn(planetaryConditions);
        assertEquals("tornado", planetaryConditions.whyDoomed(mockGame, mockEntity));
        reset(mockEntity, mockGame);

        // F1-3 Tornado - Entity movement mode is WIGE (doomed)

        planetaryConditions = new PlanetaryConditions();
        planetaryConditions.setWindStrength(Wind.TORNADO_F13);
        mockEntity = mock(Tank.class);
        when(mockEntity.getMovementMode()).thenReturn(EntityMovementMode.WIGE);
        when(mockGame.getPlanetaryConditions()).thenReturn(planetaryConditions);
        assertEquals("tornado", planetaryConditions.whyDoomed(mockGame, mockEntity));
        reset(mockEntity, mockGame);

        // F1-3 Tornado - Entity movement mode is VTOL (doomed)

        planetaryConditions = new PlanetaryConditions();
        planetaryConditions.setWindStrength(Wind.TORNADO_F13);
        mockEntity = mock(VTOL.class);
        when(mockEntity.getMovementMode()).thenReturn(EntityMovementMode.VTOL);
        when(mockGame.getPlanetaryConditions()).thenReturn(planetaryConditions);
        assertEquals("tornado", planetaryConditions.whyDoomed(mockGame, mockEntity));
        reset(mockEntity, mockGame);

        // F1-3 Tornado - Entity is regular infantry (doomed)

        planetaryConditions = new PlanetaryConditions();
        planetaryConditions.setWindStrength(Wind.TORNADO_F13);
        mockEntity = mock(Infantry.class);
        when(mockEntity.isConventionalInfantry()).thenReturn(true);
        when(mockGame.getPlanetaryConditions()).thenReturn(planetaryConditions);
        assertEquals("tornado", planetaryConditions.whyDoomed(mockGame, mockEntity));
        reset(mockEntity, mockGame);

        // F1-3 Tornado - Entity is battle armor infantry (not doomed)

        planetaryConditions = new PlanetaryConditions();
        planetaryConditions.setWindStrength(Wind.TORNADO_F13);
        mockEntity = mock(BattleArmor.class);
        when(mockGame.getPlanetaryConditions()).thenReturn(planetaryConditions);
        assertNull(planetaryConditions.whyDoomed(mockGame, mockEntity));
        reset(mockEntity, mockGame);

        // Storm - Entity is regular infantry (doomed)

        planetaryConditions = new PlanetaryConditions();
        planetaryConditions.setWindStrength(Wind.STORM);
        mockEntity = mock(Infantry.class);
        when(mockEntity.isConventionalInfantry()).thenReturn(true);
        when(mockGame.getPlanetaryConditions()).thenReturn(planetaryConditions);
        assertEquals("storm", planetaryConditions.whyDoomed(mockGame, mockEntity));
        reset(mockEntity, mockGame);

        // Storm - Entity is battle armor infantry (not doomed)

        planetaryConditions = new PlanetaryConditions();
        planetaryConditions.setWindStrength(Wind.STORM);
        mockEntity = mock(BattleArmor.class);
        when(mockGame.getPlanetaryConditions()).thenReturn(planetaryConditions);
        assertNull(planetaryConditions.whyDoomed(mockGame, mockEntity));
        reset(mockEntity, mockGame);

        // Extreme temperature - Doomed in extreme temperature, but sheltered in building (not doomed)
        // FIXME: This test is really coupled with Compute.isInBuilding() implementation. It would be nice if I
        //  could mock a static class somehow and abstract the whole thing.

        planetaryConditions = new PlanetaryConditions();
        planetaryConditions.setTemperature(100);
        when(mockGame.getPlanetaryConditions()).thenReturn(planetaryConditions);
        when(mockGame.getBoard()).thenReturn(mockBoard);
        when(mockBoard.getHex(any())).thenReturn(mockHex);
        when(mockHex.containsTerrain(Terrains.BLDG_ELEV)).thenReturn(true);
        when(mockHex.containsTerrain(Terrains.BUILDING)).thenReturn(true);
        when(mockHex.terrainLevel(Terrains.BLDG_ELEV)).thenReturn(2);
        mockEntity = mock(Infantry.class);
        when(mockEntity.doomedInExtremeTemp()).thenReturn(true);
        when(mockEntity.getPosition()).thenReturn(mockCoords);
        when(mockEntity.getElevation()).thenReturn(1);
        assertNull(planetaryConditions.whyDoomed(mockGame, mockEntity));
        reset(mockEntity, mockGame, mockBoard, mockHex);

        // Extreme temperature - Doomed in extreme temperature (doomed)

        planetaryConditions = new PlanetaryConditions();
        planetaryConditions.setTemperature(100);
        when(mockGame.getPlanetaryConditions()).thenReturn(planetaryConditions);
        mockEntity = mock(Infantry.class);
        when(mockEntity.doomedInExtremeTemp()).thenReturn(true);
        assertEquals("extreme temperature", planetaryConditions.whyDoomed(mockGame, mockEntity));
        reset(mockEntity, mockGame);
    }

    @Test
    public void testIsExtremeTemperature() {
        PlanetaryConditions planetaryConditions;

        // Extreme temperature - Heat

        planetaryConditions = new PlanetaryConditions();
        planetaryConditions.setTemperature(51);
        assertTrue(planetaryConditions.isExtremeTemperature());

        // Extreme temperature - Cold

        planetaryConditions = new PlanetaryConditions();
        planetaryConditions.setTemperature(-31);
        assertTrue(planetaryConditions.isExtremeTemperature());

        // Not extreme temperature

        planetaryConditions = new PlanetaryConditions();
        planetaryConditions.setTemperature(25);
        assertFalse(planetaryConditions.isExtremeTemperature());
    }

    @Test
    public void testGetTemperatureDisplayableName() {
        // Extreme Heat

        assertEquals("51 (Extreme Heat)", PlanetaryConditions.getTemperatureDisplayableName(51));

        // Extreme Cold

        assertEquals("-31 (Extreme Cold)", PlanetaryConditions.getTemperatureDisplayableName(-31));

        // Regular temperature

        assertEquals("25", PlanetaryConditions.getTemperatureDisplayableName(25));
    }
}
