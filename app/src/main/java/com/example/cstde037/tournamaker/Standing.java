package com.example.cstde037.tournamaker;


/**
 * Created by jflor093 on 2015-11-28.
 * A Standing object exists for and is created at the beginning of each tournament; it holds all the data for a single tournament.
 * A Standing stores various arrays, all of the same length corresponding to the number of teams in the tournament. It stores the
 * Teams, the shortNames of the teams, wins, losses, goals scored, and points; each index of these arrays corresponds to the data
 * of a single team. Additionally, the orderedPoints array stores the data of points in increasing order; it is used to retrieve
 * the rank of a single team by using the index where the team is in the string array, putting that index into points, and then
 * finding the index in orderedPoints that matches the value found in points. Since several teams can have the same number of
 * points and therefore have the same rank, so if two teams have the same number of points, the rank will still be the same. So,
 * if there are two items with the same value, there is no issue with simply taking the first of the two from orderedPoints.
 */

import java.util.*;

public class Standing {
    int[] points, orderedPoints, wins, losses, goalsScored;
    String[] tNames;
    Team [] teams;
    int length;

    //Initializes data for all the teams to be zero, and sets data for the names and Team arrays.
    public Standing(Team[] teams)
    {
        int l = teams.length; //used in the constructor since "l" is shorter than "length:
        length = l;
        this.teams = new Team[l];
        tNames = new String[l];
        points = new int[l];
        orderedPoints = new int[l];
        wins = new int[l];
        losses = new int[l];
        goalsScored = new int[l];

        for(int i = 0; i<l;i++)
        {
            this.teams[i] = teams[i];
            tNames[i]= teams[i].getShortName();
            points[i] = 0;
            orderedPoints[i] = 0;
            wins[i]=0;
            losses[i]=0;
            goalsScored[i] = 0;
        }
    }
    public Team getTeam(int index)
    {
        return teams[index];
    }

    //created to get the index corresponding to a team for numerous arrays, in many methods.
    public int getTeamIndex(String name)
    {
        for(int i = 0; i<length;i++)
        {
            if(name.equals(teams[i].getShortName()))
            {
                return i;
            }
        }
        return -1;
    }

    public String getTeamName(int index)
    {
        return teams[index].getShortName();
    }

    public int getPoints(String n)
    {
        int index = getTeamIndex(n);
        return points[index];
    }

    /*Retrieves the names of the teams in order of their rank by creating a copy of the array of team names and resorting by
     *selection sort.
     */
    public String[] getOrderedTeams()
    {
        String[] orderedTeams = new String[length];
        for(int i = 0; i<length;i++)
        {
            orderedTeams[i]=tNames[i];
        }

        for(int i = 0;i<length-1;i++)
        {
            for(int j = i+1;j<length;j++)
            {
                if(getPoints(orderedTeams[i])<getPoints(orderedTeams[j]))
                {
                    String temp = orderedTeams[i];
                    orderedTeams[i] = orderedTeams[j];
                    orderedTeams[j] = temp;
                }
            }
        }
        return orderedTeams;
    }

    /*Retrieves the rank of a team by the team name. It uses the index of the team name in tName[] to find the corresponding
     *value in points, then find the index of the matching point value in orderedPoints.
     */
    public int getRank(String name)
    {
        int index = getTeamIndex(name);
        int thesePoints = points[index];
        for(int i = 0; i<length;i++)
        {
            if(thesePoints==orderedPoints[i])
            {
                index = i;
                break;
            }
        }
        return index + 1;
    }

    public int getWins(String name)
    {
        int index = getTeamIndex(name);
        return wins[index];
    }

    public int getLosses(String name)
    {
        int index = getTeamIndex(name);
        return losses[index];
    }

    public int getGoalsScored(String name)
    {
        int index = getTeamIndex(name);
        return goalsScored[index];
    }

    //Scores are updated for the array of matches at the end of each round.
    public void updateScores(Round r)
    {
        Match[] m = r.getMatches();

        for(int i = 0; i<m.length;i++)
        {
            updateScores(m[i]); //update for each individual match.
        }
    }

    public void updateScores(Match m)
    {
        Team home = m.getHomeTeam();
        Team away = m.getAwayTeam();
        int indexHome = getTeamIndex(home.getShortName());
        int indexAway = getTeamIndex(away.getShortName());
        int indexHomeOrdered = getRank(home.getShortName())-1;
        int indexAwayOrdered = getRank(away.getShortName())-1;
        //After this line, indexHome and indexHomeOrdered should correspond to the points with the same score.

        int homeScore = m.getHomeScore();
        int awayScore = m.getAwayScore();
        goalsScored[indexAway]=awayScore;
        goalsScored[indexHome]=homeScore;
        // A winning team wins 3 points, a tie gives each team 1 point, and a loss does not change the points.
        if(homeScore<awayScore)
        {
            wins[indexAway]++;
            losses[indexHome]++;
            points[indexAway] = points[indexAway]+3;
            orderedPoints[indexAwayOrdered] = points[indexAway];
        }
        else if(homeScore>awayScore)
        {
            wins[indexHome]++;
            losses[indexAway]++;
            points[indexHome] = points[indexHome]+3;
            orderedPoints[indexHomeOrdered] = points[indexHome];
        }
        else //tie
        {
            points[indexHome] = points[indexHome]+1;
            orderedPoints[indexHomeOrdered] = points[indexHome];
            points[indexAway] = points[indexAway]+1;
            orderedPoints[indexAwayOrdered] = points[indexAway];
        }
        //After the points have been allocated, the orderedPoints array must be reorganized, from largest to smallest
        //(The first place team has the greatest number of points).
        Arrays.sort(orderedPoints);
        int[] nOrdered = new int[length];
        for(int i = 0; i<length;i++)
        {
            nOrdered[i] = orderedPoints[length-1-i];
        }
        for(int i = 0; i<length;i++)
        {
            orderedPoints[i] = nOrdered[i];
        }
    }

    public Team[] getOrderedTeamsObj() //returns the teams in an array of Team objects.
    {
        Team[] orderedTeams = new Team[length];
        String[] stringTeams = getOrderedTeams();
        for(int i = 0; i<length;i++)
        {
            for(int j = 0; j<length;j++)
            {
                if(teams[j].getShortName().equals(stringTeams[i]))
                {
                    orderedTeams[i] = teams[j];
                    break;
                }
            }
        }
        return orderedTeams;
    }
}
