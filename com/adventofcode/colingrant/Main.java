package com.adventofcode.colingrant;

import com.adventofcode.colingrant.challenges.*;
import com.adventofcode.colingrant.common.Challenge;
import com.adventofcode.colingrant.common.InputFileReader;

public class Main 
{
    public static void main(String[] args) 
    {
        // Run one of the challenges.
        runChallenge(new Day5(), "day5.txt"); 
    }

    public static void runChallenge(Challenge challenge, String inputFile)
    {
        challenge.run(InputFileReader.readInputFile(inputFile));
    }
}
