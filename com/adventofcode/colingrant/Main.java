package com.adventofcode.colingrant;

import com.adventofcode.colingrant.challenges.Day1;
import com.adventofcode.colingrant.challenges.Day2;
import com.adventofcode.colingrant.common.InputFileReader;

public class Main 
{
    public static void main(String[] args) 
    {
        // Run one of the challenges.
        day2(); 
    }

    private static void day1()
    {
        Day1 day1 = new Day1(); 
        day1.run(InputFileReader.readInputFile("day1.txt"));
    }

    private static void day2()
    {
        Day2 day2 = new Day2(); 
        day2.run(InputFileReader.readInputFile("day2.txt"));
    }

}
