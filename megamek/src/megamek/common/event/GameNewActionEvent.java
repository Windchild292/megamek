/*
 * Copyright (c) 2005 - Ben Mazur (bmazur@sev.org).
 * Copyright (c) 2022 - The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
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
package megamek.common.event;

import megamek.common.actions.AbstractEntityAction;

/**
 * Instances of this class are sent when new Action added to the game
 * 
 * @see GameListener
 */
public class GameNewActionEvent extends GameEvent {
    private static final long serialVersionUID = 928848699583079097L;
    protected AbstractEntityAction action;

    /**
     * Construct new GameNewActionEvent
     * 
     * @param source sender
     * @param action
     */
    public GameNewActionEvent(Object source, AbstractEntityAction action) {
        super(source);
        this.action = action;
    }

    /**
     * @return the action.
     */
    public AbstractEntityAction getAction() {
        return action;
    }

    @Override
    public void fireEvent(GameListener gl) {
        gl.gameNewAction(this);    
    }

    @Override
    public String getEventName() {
        return "Game New Action";
    }
}
