package com.example.cstde037.tournamaker;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * Created by cstde037 on 28/11/2015.
 * Tournament Singleton. Stores the data for an active tournament and is the entry-point for the back-end.
 * As such, it stores much of the tournament organization logic, namely the match scheduling algorithms and the progression of the tournament.
 *
 * Using one big Singleton that is accessible from any activity is the simplest solution (rather than using a database or passing objects via intents).
 */
public class Tournament {
    Mode mode;
    Team[] teams;
    Standing standing;
    Round[] rounds;
    int currentRound;

    private static Tournament ourInstance = new Tournament();

    public static Tournament getInstance() {
        return ourInstance;
    }

    private Tournament() {
        currentRound = 0;
    }

    public boolean isCombo() {
        return mode == Mode.COMBO;
    }

    public boolean isRoundRobin() {
        return  mode == Mode.ROUND_ROBIN;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Team[] getTeams() {
        return teams;
    }

    public void setTeams(Team[] teams) {
       this.teams = teams;
        this.standing = new Standing(teams);
    }

    /* Return whoever is highest in the standings (should only be called at the end of a Tournament). */
    public Team getWinner() {
        return standing.getOrderedTeamsObj()[0];
    }

    public Round getCurrentRound() {
        return rounds[currentRound];
    }

    public void endRound() {
        standing.updateScores(getCurrentRound());
        if(mode == Mode.KNOCKOUT && !getCurrentRound().isLastRound) {
            generateNextEliminationRound(getCurrentRound().getWinners());
        }
        else
            currentRound++;
    }

    /* Match method created to match teams to each other if a RoundRobin tournament is selected.
     * This algorithm creates a matrix of rounds where every team is matched once to every other team.
     * With n teams we will get a result of (n/2)(n-1) matches.
     * We assume that we will always receive a value n (the number of teams to be matched) which is larger than two and even.
     * The method is static so it may be accessed all throughout the class.
     * Match takes in as an input an array of strings containing the team names.
     */
    public void generateRoundRobin() {
        int numberOfTeams = teams.length;
        int totalRounds = numberOfTeams-1;
        int matchesPerRound = numberOfTeams/2;
        rounds = new Round[totalRounds];
          /* The logic behind our algorithm goes as follows:
           * In the first round, the array of teams is divided in half;
           * Our first value is then matched to our last, our second to the before last and so on.
           * In the second round, the first is then matched to the second last and so on.
           * Notice that the middle value which was previously matched to the next value after it,
           * is now matched to the value previous to it which is our new middle value.
           * This loop iterates in this manner until each team has played against every other team once.
           */
        for (int round = 0; round < totalRounds; round++) {
            Match[] matches = new Match[matchesPerRound];
            for (int match = 0; match < matchesPerRound; match++) {
                int home = (round + match) % (numberOfTeams - 1);
                int away = (numberOfTeams - 1 - match + round) % (numberOfTeams - 1);

                if (match == 0) {
                    away = numberOfTeams - 1;
                }
                // rounds[round][match] = ("team " + (home + 1) + " plays against team " + (away + 1));
                matches[match] = new Match();
                matches[match].setTeams(teams[home], teams[away]);
            }

            rounds[round] = new Round(matches, "Round " +(round+1) + " of " + totalRounds);
        }
        rounds[totalRounds-1].setAsLastRound();
    }

    /* Generates the next elimination round based on the winners of the previous round.
 * For the first round, we simply pass the complete list of teams.
/* PRE-CONDITION: the number of teams participating must be a power of 2. This simplifies the app as we do not have to account for byes.
 * Teams are matched such that, in Combination mode, the strongest seeds play the weakest seeds and
 * the teams seeded 1 and 2 can only play in the finals.
 */
    public void generateNextEliminationRound(Team[] winners) {
        if(currentRound == 0)
        {
            int numRounds = MathUtil.logTwo(winners.length);
            rounds = new Round[numRounds];
        }

        Match[] matches = new Match[winners.length/2];

        for(int i=0; i<matches.length; i++) {
            Team home = winners[i];
            Team away = winners[winners.length-i-1];
            matches[i] = new Match();
            matches[i].setTeams(home,away);
        }

        String roundName;
        boolean isLastRound;

        if(isLastRound = matches.length == 1) {
            roundName = "Finals";

        }
        else
            roundName = "1/" + (matches.length) + " finals";

        Round round = new Round(matches, roundName);

        if(isLastRound)
            round.setAsLastRound();

        rounds[currentRound] =  round;
    }

    /* In a combination tournament, this is used to determine the knockout qualifiers, and seed them accordingly for playoffs.
     * The number of teams that move on is the highest power of two that is less than the total number of teams in the Round Robin (RR).
     * For example, if 36 teams play in RR, 32 move on. If 6 play, 4 move on.
     */
    public void seedPlayoffs() {
        int numberOfQualifyingTeams = MathUtil.roundDownToPowerOfTwo(getTeams().length);
        currentRound = 0;
        Team[] qualifyingTeams = new Team[numberOfQualifyingTeams];
        System.arraycopy(standing.getOrderedTeamsObj(), 0, qualifyingTeams, 0, numberOfQualifyingTeams);
        setTeams(qualifyingTeams);
        standing = new Standing(qualifyingTeams);
        setMode(Mode.KNOCKOUT);
        generateNextEliminationRound(qualifyingTeams);
    }

    public Match[] getAllCompletedMatches()
    {
        int counter = 0;
        for(int i = 0;i<rounds.length;i++)
        {
            for(int j = 0;j<rounds[i].getMatches().length;j++) {
                if (rounds[i].getMatches()[j].isComplete()) {
                    counter++;
                }
            }
        }
        Match[] matches = new Match[counter];
        int counter2 = 0;
        for(int i = 0;i<rounds.length;i++)
        {
            for(int j = 0;j<rounds[i].getMatches().length;j++)
                if(rounds[i].getMatches()[j].isComplete())
                {
                    matches[counter2] = rounds[i].getMatches()[j];
                    counter2++;

                }
        }
        return matches;
    }

    public Standing getStanding() {return standing; }

}
