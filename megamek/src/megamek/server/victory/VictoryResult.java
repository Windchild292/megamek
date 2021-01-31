/*
 * Copyright (C) 2007-2008 Ben Mazur (bmazur@sev.org)
 * Copyright (c) 2021 - The MegaMek Team. All Rights Reserved.
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
package megamek.server.victory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import megamek.common.IPlayer;
import megamek.common.Report;

/**
 * The VictoryResult stores player scores and a flag if a game-ending victory has been achieved or not
 */
public class VictoryResult extends AbstractGameConclusionResult {
    //region Variable Declarations
    private boolean victory;
    private Throwable throwable;
    private List<Report> reports;
    private Map<Integer, Double> playerScores;
    private Map<Integer, Double> teamScores;
    private double highScore;
    //endregion Variable Declarations

    //region Constructors
    public VictoryResult(boolean victory) {
        this(victory, IPlayer.PLAYER_NONE, IPlayer.TEAM_NONE);
    }

    public VictoryResult(boolean victory, int team, IPlayer player) {
        this(victory, team, player.getId());
    }

    public VictoryResult(boolean victory, int player, int team) {
        setVictory(victory);
        setThrowable(new Throwable());
        setReports(new ArrayList<>());
        setPlayerScores(new HashMap<>());
        setTeamScores(new HashMap<>());
        setHighScoreDirect(0);
        if (player != IPlayer.PLAYER_NONE) {
            addPlayerScore(player, 1.0);
        }
        if (team != IPlayer.TEAM_NONE) {
            addTeamScore(team, 1.0);
        }
    }

    public static VictoryResult noResult() {
        return new VictoryResult(false, IPlayer.PLAYER_NONE, IPlayer.TEAM_NONE);
    }

    public static VictoryResult drawResult() {
        return new VictoryResult(true, IPlayer.PLAYER_NONE, IPlayer.TEAM_NONE);
    }
    //endregion Constructors

    //region Getters/Setters
    public boolean victory() {
        return victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public Map<Integer, Double> getPlayerScores() {
        return playerScores;
    }

    public double getPlayerScore(int id) {
        return (getPlayerScores().get(id) == null) ? 0.0 : getPlayerScores().get(id);
    }

    public void setPlayerScores(Map<Integer, Double> playerScores) {
        this.playerScores = playerScores;
    }

    public void addPlayerScore(int id, double score) {
        getPlayerScores().put(id, score);
        setHighScore();
    }

    public Map<Integer, Double> getTeamScores() {
        return teamScores;
    }

    public double getTeamScore(int id) {
        return (getTeamScores().get(id) == null) ? 0.0 : getTeamScores().get(id);
    }

    public void setTeamScores(Map<Integer, Double> teamScores) {
        this.teamScores = teamScores;
    }

    public void addTeamScore(int id, double score) {
        getTeamScores().put(id, score);
        setHighScore();
    }

    public double getHighScore() {
        return highScore;
    }

    public void setHighScore() {
        // used to calculate winner
        setHighScoreDirect(Double.MIN_VALUE);
        for (Double score : getPlayerScores().values()) {
            if (score > getHighScore()) {
                setHighScoreDirect(score);
            }
        }
        for (Double score : getTeamScores().values()) {
            if (score > getHighScore()) {
                setHighScoreDirect(score);
            }
        }
    }

    public void setHighScoreDirect(double highScore) {
        this.highScore = highScore;
    }
    //endregion Getters/Setters

    //region Boolean Comparison Methods
    public boolean isDraw() {
        return victory() && (getWinningPlayer() == IPlayer.PLAYER_NONE)
                && (getWinningTeam() == IPlayer.TEAM_NONE);
    }
    //endregion Boolean Comparison Methods

    public int getWinningPlayer() {
        double max = Double.MIN_VALUE;
        int maxPlayer = IPlayer.PLAYER_NONE;
        for (Map.Entry<Integer, Double> player : getPlayerScores().entrySet()) {
            if (player.getValue() == max) {
                return IPlayer.PLAYER_NONE;
            }
            if (player.getValue() > max) {
                maxPlayer = player.getKey();
                max = player.getValue();
            }
        }
        return maxPlayer;
    }

    public int getWinningTeam() {
        double max = Double.MIN_VALUE;
        int maxTeam = IPlayer.TEAM_NONE;
        for (Map.Entry<Integer, Double> team : getTeamScores().entrySet()) {
            if (team.getValue() == max) {
                return IPlayer.TEAM_NONE;
            }
            if (team.getValue() > max) {
                maxTeam = team.getKey();
                max = team.getValue();
            }
        }
        return maxTeam;
    }

    public boolean isWinningPlayer(int id) {
        // two decimal compare..
        return ((getPlayerScore(id) * 100) % 100) == ((getHighScore() * 100) % 100);
    }

    public boolean isWinningTeam(int id) {
        // two decimal compare..
        return ((getTeamScore(id) * 100) % 100) == ((getHighScore() * 100) % 100);
    }

    private String getTrace() {
        StringWriter sw = new StringWriter();
        PrintWriter pr = new PrintWriter(sw);
        getThrowable().printStackTrace(pr);
        pr.flush();
        return sw.toString();
    }

    @Override
    public String toString() {
        return "Victory provided to you by: " + getTrace();
    }
}
