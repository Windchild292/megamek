/*
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
package megamek.common;

import megamek.common.enums.TeamNumber;

import java.io.Serializable;

public class NarcPod implements Serializable {
    private static final long serialVersionUID = 8883459353515484784L;
    private TeamNumber teamNumber;
    private int location;

    public NarcPod(TeamNumber team, int location) {
        this.teamNumber = team;
        this.location = location;
    }

    public TeamNumber getTeamNumber() {
        return teamNumber;
    }

    public int getLocation() {
        return location;
    }

    public boolean equals(NarcPod other) {
        return (this.location == other.location) && (this.teamNumber == other.teamNumber);
    }
}
