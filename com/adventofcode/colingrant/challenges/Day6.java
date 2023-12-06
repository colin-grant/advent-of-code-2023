package com.adventofcode.colingrant.challenges;

import java.util.ArrayList;

import com.adventofcode.colingrant.common.Challenge;
import com.adventofcode.colingrant.common.Input;

public class Day6 implements Challenge
{
    public static class Race 
    {
        public final long raceTime;
        public final long bestDistance; 

        public Race(long raceTime, long bestDistance)
        {
            this.raceTime = raceTime;
            this.bestDistance = bestDistance; 
        }

    }

    @Override
    public void run(Input input)
    {
        part1(input);
        part2(input);
    }

    private void part1(Input input)
    {
        ArrayList<Race> races = parseInput(input);

        long total = 1 ;

        for ( Race race : races )
        {
            long winningRange = calculateBestTimeRange(race); 

            total *= winningRange; 
        }

        System.out.println("Part1: Total = " + total); 
    }

    private void part2(Input input)
    {
        // Using the quadratic equation paid off - allthough we need to parse the input 
        // differently! 
        String raceTimeStr = input.get(0).substring("Time:".length()).trim().replaceAll(" +",""); 
        String bestDistanceStr = input.get(1).substring("Distance:".length()).trim().replaceAll(" +",""); 

        long raceTime = Long.parseLong(raceTimeStr); 
        long bestDistance = Long.parseLong(bestDistanceStr); 

        long bestTimeRange = calculateBestTimeRange(new Race(raceTime, bestDistance));

        System.out.println("Part2: Best time range = " + bestTimeRange); 
    }

    ArrayList<Race> parseInput(Input input)
    {
        ArrayList<Race> races = new ArrayList<>();

        String[] times = input.get(0).substring("Time:".length()).trim().split(" +");
        String[] distances = input.get(1).substring("Distance:".length()).trim().split(" +");

        for ( int i = 0 ; i < times.length ; ++i )
        {
            Race race = new Race(Integer.parseInt(times[i]), Integer.parseInt(distances[i]));
            races.add(race); 
        }

        return races; 
    }

    public long calculateBestTimeRange(Race race) 
    {
        // We could go through each millisecond, but the formula for calculating the distance 
        // greater than the best time for any number of milliseconds is 
        //
        // distance_delta = speed * (race_time - speed) - best_distance
        //
        // This can be re-arranged into a quadratic equation: 
        //
        // d = -ts^2 + ts - b 
        //
        // Which we can use the quadratic equation to solve when distance_delta = 0. 
        //
        // s = (t +- sqrt(t^2 - 4b))/2 
        //
        double sqrtPart = Math.sqrt((race.raceTime * race.raceTime) - (4 * race.bestDistance));

        double p1 = (race.raceTime - sqrtPart) / 2;
        double p2 = (race.raceTime + sqrtPart) / 2;

        int minMs = (int)Math.ceil(Math.min(p1,p2)+0.0001);
        int maxMs = (int)Math.floor(Math.max(p1,p2)-0.0001); 
        
        return (maxMs-minMs) + 1 ;
    }
}
