package com.example.cstde037.tournamaker;

/**
 * Created by cstde037 on 28/11/2015.
 */
public class Round {
    private Match[] matches;
    private String name;
    boolean isLastRound;

    public Round(Match[] matches, String name) {
        this.matches = matches;
        this.name = name;
        isLastRound = false;
    }

    public Match[] getMatches() {
        return matches;
    }

    public String getName() {
        return name;
    }

    public void setAsLastRound() {
        isLastRound = true;
    }

    public boolean isLastRound() { return isLastRound; }

    public Match getMatch(int position) { return matches[position]; }

    public Team[] getWinners() {
        Team[] winners = new Team[matches.length];
        for(int i=0; i<matches.length; i++) {
            winners[i] = matches[i].getWinner();
        }
        return winners;
    }
}
