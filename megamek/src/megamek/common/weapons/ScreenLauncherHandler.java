/**
 * MegaMek - Copyright (C) 2005 Ben Mazur (bmazur@sev.org)
 * 
 *  This program is free software; you can redistribute it and/or modify it 
 *  under the terms of the GNU General Public License as published by the Free 
 *  Software Foundation; either version 2 of the License, or (at your option) 
 *  any later version.
 * 
 *  This program is distributed in the hope that it will be useful, but 
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 *  or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 *  for more details.
 */
package megamek.common.weapons;

import megamek.common.*;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.enums.GamePhase;
import megamek.server.GameManager;

import java.util.Vector;

/**
 * @author Jay Lawson
 */
public class ScreenLauncherHandler extends AmmoWeaponHandler {
    private static final long serialVersionUID = -2536312899803153911L;

    public ScreenLauncherHandler(ToHitData t, WeaponAttackAction w, Game g, GameManager m) {
        super(t, w, g, m);
    }

    /**
     * handle this weapons firing
     * 
     * @return a <code>boolean</code> value indicating whether this should be kept or not
     */
    @Override
    public boolean handle(GamePhase phase, Vector<Report> vPhaseReport) {
        if (!this.cares(phase)) {
            return true;
        }

        // Report weapon attack and its to-hit value.
        Report r = new Report(3115);
        r.indent();
        r.newlines = 0;
        r.subject = subjectId;
        r.add(wtype.getName());
        r.messageId = 3120;
        r.add(target.getDisplayName(), true);
        vPhaseReport.addElement(r);
        if (toHit.getValue() == TargetRoll.IMPOSSIBLE) {
            r = new Report(3135);
            r.subject = subjectId;
            r.add(toHit.getDesc());
            vPhaseReport.addElement(r);
            return false;
        } else if (toHit.getValue() == TargetRoll.AUTOMATIC_FAIL) {
            r = new Report(3140);
            r.newlines = 0;
            r.subject = subjectId;
            r.add(toHit.getDesc());
            vPhaseReport.addElement(r);
        } else if (toHit.getValue() == TargetRoll.AUTOMATIC_SUCCESS) {
            r = new Report(3145);
            r.newlines = 0;
            r.subject = subjectId;
            r.add(toHit.getDesc());
            vPhaseReport.addElement(r);
        }

        addHeat();

        // deliver screen
        Coords coords = target.getPosition();
        gameManager.deliverScreen(coords, vPhaseReport);

        // damage any entities in the hex
        for (Entity entity :  game.getEntitiesVector(coords)) {
            // if fighter squadron all fighters are damaged
            if (entity instanceof FighterSquadron) {
                entity.getSubEntities().forEach(
                    ent -> {
                        ToHitData squadronToHit = new ToHitData();
                        squadronToHit.setHitTable(ToHitData.HIT_NORMAL);
                        HitData hit = ent.rollHitLocation(squadronToHit.getHitTable(), ToHitData.SIDE_FRONT);
                        hit.setCapital(false);
                        vPhaseReport.addAll(gameManager.damageEntity(ent, hit, attackValue));
                        gameManager.creditKill(ent, ae);
                    });
            } else {
                ToHitData hexToHit = new ToHitData();
                hexToHit.setHitTable(ToHitData.HIT_NORMAL);
                HitData hit = entity.rollHitLocation(hexToHit.getHitTable(), ToHitData.SIDE_FRONT);
                hit.setCapital(false);
                vPhaseReport.addAll(gameManager.damageEntity(entity, hit, attackValue));
                gameManager.creditKill(entity, ae);
            }
        }
        return false;
    }
}
