package com.adventofcode.colingrant.challenges;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.adventofcode.colingrant.common.Challenge;
import com.adventofcode.colingrant.common.Input;

public class Day2 implements Challenge
{
    public final int VALID_RED_COUNT = 12;
    public final int VALID_GREEN_COUNT = 13;
    public final int VALID_BLUE_COUNT = 14;

    //
    // After part 2 was revealed I wrote a parser for the file. 
    // This stores all the games together with the maximum red, green and blue cube
    // counts, since this is all we need for all games. 
    //
    private static class Game
    {
        private int id; 
        private Map<String, Integer> colourToMaxCount = new HashMap<>(); 

        public Game(int id)
        {
            this.id = id ;
            colourToMaxCount.put("red", 0);
            colourToMaxCount.put("green",0); 
            colourToMaxCount.put("blue", 0); 
        }

        public void setCount(String colour, int count)
        {
            Integer maxCount = colourToMaxCount.get(colour); 
            if ( maxCount != null )
            {
                if ( count > maxCount )
                {
                    colourToMaxCount.put(colour, count); 
                }
            }
        }

        public int getMaxCount(String colour)
        {
            return colourToMaxCount.get(colour); 
        }

        public int getId()
        {
            return id; 
        }
    }

    private static class GameParser 
    {
        public static ArrayList<Game> parseInput( Input input ) 
        {
            ArrayList<Game> gameList = new ArrayList<>();

            for ( String line : input )
            {
                String idAndCubeCounts[] = line.split(":"); 

                String idStr = idAndCubeCounts[0].substring("Game ".length());
                int id = Integer.parseInt(idStr);
                
                Game game = new Game(id); 

                String games[] = idAndCubeCounts[1].split(";"); 

                for ( String gameInput : games )
                {
                    String cubes[] = gameInput.split(",");
                    for ( String cubeCount : cubes )
                    {
                        String[] countAndColour = cubeCount.trim().split(" "); 
                        int count = Integer.parseInt(countAndColour[0]);
                        String colour = countAndColour[1].trim(); 

                        game.setCount(colour, count); 
                    }
                }
                gameList.add(game); 
            }
            return gameList; 
        }
    }


    @Override
    public void run(Input input) 
    {
        ArrayList<Game> games = GameParser.parseInput(input); 
        part1(games);
        part2(games); 
    }

    private void part1(ArrayList<Game> games)
    {
        int idTotal = 0 ;

        for ( Game game : games )
        {
            if (    (game.getMaxCount("red") <= VALID_RED_COUNT)
                &&  (game.getMaxCount("green") <= VALID_GREEN_COUNT)
                &&  (game.getMaxCount("blue") <= VALID_BLUE_COUNT)  )
            {
                idTotal += game.getId(); 
            }
        }

        System.out.println("Part1: ID Total = " + idTotal);
    }

    private void part2(ArrayList<Game> games) 
    {
        int powerTotal = 0; 
        for ( Game game : games )
        {
            powerTotal += (game.getMaxCount("red") * game.getMaxCount("green") * game.getMaxCount("blue"));
        }
        System.out.println("Part2: power total = " + powerTotal);
    }
}
