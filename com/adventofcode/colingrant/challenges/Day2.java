package com.adventofcode.colingrant.challenges;

import com.adventofcode.colingrant.common.Challenge;
import com.adventofcode.colingrant.common.Input;

public class Day2 implements Challenge
{
    public final int VALID_RED_COUNT = 12;
    public final int VALID_GREEN_COUNT = 13;
    public final int VALID_BLUE_COUNT = 14;

    @Override
    public void run(Input input) 
    {
        // Rough and ready initially - until we see what part 2 brings. 
        int idCount = 0 ;

        for ( String line : input )
        {
            boolean isValid = true; 

            String split1[] = line.split(":"); 

            String idStr = split1[0].substring("Game ".length());
            int id = Integer.parseInt(idStr); 
            String games[] = split1[1].split(";"); 
            for ( String game : games )
            {
                String cubes[] = game.split(",");
                for ( String cubeCount : cubes )
                {
                    String[] countAndColour = cubeCount.trim().split(" "); 
                    int count = Integer.parseInt(countAndColour[0]);
                    String colour = countAndColour[1].trim(); 

                    switch( colour )
                    {
                        case "red":
                            if ( count > VALID_RED_COUNT )
                            {
                                isValid = false; 
                            }
                            break;
                        case "green":
                            if ( count > VALID_GREEN_COUNT )
                            {
                                isValid = false; 
                            }
                            break;
                        case "blue":
                            if ( count > VALID_BLUE_COUNT )
                            {
                                isValid = false;
                            }
                            break; 
                    }

                    if ( !isValid )
                    {
                        break; 
                    }
                }

                if ( !isValid )
                {
                    break; 
                } 
            }
            if ( isValid )
            { 
                idCount += id; 
            }
        }
        System.out.println("ID count = " + idCount); 
    }
}
