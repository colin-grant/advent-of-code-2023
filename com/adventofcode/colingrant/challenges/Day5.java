package com.adventofcode.colingrant.challenges;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.adventofcode.colingrant.common.Challenge;
import com.adventofcode.colingrant.common.Input;

public class Day5 implements Challenge
{
    // Class to hold a single mapping range. 
    public static class RangeMap
    {
        public final long sourceStart ;
        public final long destStart ;

        public final long length ;

        public RangeMap(long sourceStart, long destStart, long length)
        {
            this.sourceStart = sourceStart;
            this.destStart = destStart;
            this.length = length;
        }

        public boolean inRange(long source)
        {
            return (source >= sourceStart) && (source <= (sourceStart + (length-1)));
        }
        public long mapSource(long source) 
        {
            if ( inRange(source) )
            {
                return destStart + (source-sourceStart);
            }

            return -1; 
        }
    }

    // Class to hold all the range maps for a particular category. 
    public static class MultiRangeMap
    {
        private List<RangeMap> rangeMaps = new ArrayList<>();

        private Comparator<RangeMap> rangeMapComparator = new Comparator<RangeMap>(){

            @Override
            public int compare(RangeMap o1, RangeMap o2) {
                // Sort range maps based on the start of the source range. 
                return Long.compare(o1.sourceStart, o2.sourceStart); 
            }}; 

        public void addRange(RangeMap rangeMap)
        {
            rangeMaps.add(rangeMap);
            rangeMaps.sort(rangeMapComparator); 
        }

        public long mapSource(long source)
        {
            // Default to the destination being the same as the source. 
            long dest = source ; 

            for ( RangeMap rangeMap : rangeMaps )
            {
                if ( rangeMap.inRange(source) )
                {
                    dest = rangeMap.mapSource(source);         
                    break;            
                }
            }

            return dest; 
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
        ArrayList<Long> seeds = new ArrayList<>();
        ArrayList<MultiRangeMap> maps = parseInput(input, seeds); 
        Long nearestLocation = Long.MAX_VALUE; 

        for ( long seed : seeds )
        {
            long location = getLocation(maps, seed);
            nearestLocation = Long.min(location, nearestLocation);
        }
        System.out.println("Part1: Nearest Location = " + nearestLocation);
    }

    private void part2(Input input)
    {
        // First we need to get the source list from the seed ranges. 
        ArrayList<Long> seeds = new ArrayList<>();
        ArrayList<MultiRangeMap> maps = parseInput(input, seeds); 

        boolean isFirst = true ;
        long seedRangeStart = -1 ; 
        long nearestLocation = Long.MAX_VALUE; 

        for ( long value : seeds )
        {
            if ( isFirst )
            {
                seedRangeStart = value ;
            }
            else
            {
                long length = value ;

                // There's some long ranges here so this bit may take some time. 
                for ( long seedNum = seedRangeStart ; seedNum < (seedRangeStart + length) ; ++seedNum )
                {
                    long location = getLocation(maps, seedNum); 
                    nearestLocation = Long.min(location, nearestLocation); 
                }
            }

            isFirst = !isFirst; 
        }

        System.out.println("Part2: Nearest Location = " + nearestLocation);
    }

    private long getLocation(ArrayList<MultiRangeMap> maps, long source)
    {
        long mapped = source; 
        
        for ( MultiRangeMap map : maps )
        {
            mapped = map.mapSource(source);
            source = mapped; 
        }

        return mapped;  
    }

    private ArrayList<MultiRangeMap> parseInput( Input input, ArrayList<Long> seeds )
    {
        ArrayList<MultiRangeMap> maps = new ArrayList<>();
        MultiRangeMap currentMap = null ;

        boolean isFirst = true; 

        for ( String line : input )
        {
            // Ignore the blank lines between maps. 
            if ( line.trim().isEmpty() )
                continue; 

            if ( isFirst )
            {
                // First line contains the list of seeds. 
                line = line.substring("seeds: ".length());
                String[] parts = line.split(" +"); 
                for ( String part : parts )
                {
                    seeds.add(Long.parseLong(part));
                }
                isFirst = false;
            }
            else
            {
                if ( !Character.isDigit(line.charAt(0)) )
                {
                    // Start of a new map (we don't really care about the names of the maps)
                    currentMap = new MultiRangeMap();
                    maps.add(currentMap); 
                }
                else
                {
                    // Mapping data. 
                    String[] parts = line.split(" +");
                    long destStart = Long.parseLong(parts[0]);
                    long sourceStart = Long.parseLong(parts[1]);
                    long length = Long.parseLong(parts[2]);

                    currentMap.addRange(new RangeMap(sourceStart, destStart, length));
                }
            }
        }
        return maps; 
    }
}