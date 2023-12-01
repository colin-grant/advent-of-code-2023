package com.adventofcode.colingrant;

import com.adventofcode.colingrant.challenges.Day1;
import com.adventofcode.colingrant.common.InputFileReader;

public class Main 
{
    public static void main(String[] args) 
    {
        // Run one of the challenges.
        day1(); 
    }

    private static void day1()
    {
        Day1 day1 = new Day1(); 
        day1.run(InputFileReader.readInputFile("day1.txt"));
    }

}
