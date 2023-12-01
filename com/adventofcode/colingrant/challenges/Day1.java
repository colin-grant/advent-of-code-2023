package com.adventofcode.colingrant.challenges;

import com.adventofcode.colingrant.common.Challenge;
import com.adventofcode.colingrant.common.Input;

public class Day1 implements Challenge
{
    private final static String[] TEXT_DIGITS = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private final static String[] NUMERIC_DIGITS = {"0","1","2","3","4","5","6","7","8","9"};

    @Override
    public void run(Input input)
    {
        part1(input); 
        part2(input); 
    }

    private void part1(Input input)
    {
        int calibrationTotal = 0 ;

        for ( String line : input )
        {
            int firstDigit = 0; 
            int lastDigit = 0;
            boolean foundFirst = false;

            for ( char x : line.toCharArray() )
            {
                if ( Character.isDigit(x) )
                {
                    if ( !foundFirst )
                    {
                        firstDigit = x - '0';
                        foundFirst = true;
                    }
                    lastDigit = x - '0';
                }
            }

            int lineDigit = (firstDigit * 10) + lastDigit;
            //System.out.println("Calibration Value: " + lineDigit);
            calibrationTotal += lineDigit; 
        }

        System.out.println("Part1 Calibration Total = " + calibrationTotal);
    }

    private void part2(Input input)
    {
        int calibrationTotal = 0 ;

        // Find first digit. 
        for ( String line : input )
        {
            int  firstDigit = getFirstDigit(line); 
            int  lastDigit = getLastDigit(line); 

            int lineDigit = (firstDigit * 10) + lastDigit ; 

            //System.out.println("Calibration Value: " + lineDigit);

            calibrationTotal += lineDigit ;
        }

        System.out.println("Part2 Calibration Total = " + calibrationTotal);
    }

    private int getFirstDigit(String line)
    {
        int minIndex = Integer.MAX_VALUE ; 
        int firstValue = 0 ; 

        for ( int i = 0 ; i < TEXT_DIGITS.length ; ++i )
        {
            int textIndex = line.indexOf(TEXT_DIGITS[i]);
            int numIndex = line.indexOf(NUMERIC_DIGITS[i]);

            if ( textIndex != -1 && textIndex < minIndex )
            {
                minIndex = textIndex ;
                firstValue = i;
            }

            if ( numIndex != -1 && numIndex < minIndex )
            {
                minIndex = numIndex ;
                firstValue = i;
            }
        }

        return firstValue; 
    }

    private int getLastDigit(String line)
    {
        int maxIndex = -1  ; 
        int lastValue = 0 ; 

        for ( int i = 0 ; i < TEXT_DIGITS.length ; ++i )
        {
            int textIndex = line.lastIndexOf(TEXT_DIGITS[i]);
            int numIndex = line.lastIndexOf(NUMERIC_DIGITS[i]);

            if ( textIndex != -1 && textIndex > maxIndex )
            {
                maxIndex = textIndex ;
                lastValue = i;
            }

            if ( numIndex != -1 && numIndex > maxIndex )
            {
                maxIndex = numIndex ;
                lastValue = i;
            }
        }

        return lastValue; 
    }

}
