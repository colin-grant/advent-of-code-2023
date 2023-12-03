package com.adventofcode.colingrant.challenges;

import java.util.ArrayList;
import java.util.Iterator;

import com.adventofcode.colingrant.common.Challenge;
import com.adventofcode.colingrant.common.Input;

public class Day3 implements Challenge 
{
    private static class NumberValue
    {
        public final int value;
        public final int row;
        public final int startCol;
        public final int endCol;

        public NumberValue(int value, int row, int startCol, int endCol)
        {
            this.value = value;
            this.row = row;
            this.startCol = startCol;
            this.endCol = endCol; 
        }
    }

    private static class SymbolValue
    {
        public final char symbol;
        public final int row;
        public final int col;

        public SymbolValue(char value, int row, int col)
        {
            this.symbol = value;
            this.row = row;
            this.col = col;
        }
    }

    private static class Grid 
    {
        private ArrayList<ArrayList<NumberValue>> numbers = new ArrayList<>();
        private ArrayList<ArrayList<SymbolValue>> symbols = new ArrayList<>(); 

        public void addRow()
        {
            numbers.add(new ArrayList<NumberValue>()); 
            symbols.add(new ArrayList<SymbolValue>());
        }

        public int numRows()
        {
            return numbers.size(); // numbers and symbols must have same number of rows. 
        }

        public ArrayList<NumberValue> getNumberRow(int row)
        {
            return numbers.get(row); 
        }

        public ArrayList<SymbolValue> getSymbolRow(int row)
        {
            return symbols.get(row); 
        }

        public void addNumber(NumberValue numValue) 
        {
            numbers.get(numValue.row).add(numValue); 
        }

        public void addSymbol(SymbolValue symValue) 
        {
            symbols.get(symValue.row).add(symValue); 
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
        Grid grid = parseInput(input); 

        ArrayList<NumberValue> engineParts = new ArrayList<>(); 

        // Go through the grid rows one at a time finding all the valid numbers. 
        for ( int row = 0 ; row < grid.numRows() ; ++row )
        {
            ArrayList<SymbolValue> symbols = grid.getSymbolRow(row);
            for( SymbolValue symbol : symbols )
            {
                if ( row > 0 )
                {
                    findPartsForPosition(grid, row-1, symbol.col, engineParts);
                }
                findPartsForPosition(grid, row, symbol.col, engineParts); 
                if ( row < (grid.numRows() - 1) )
                {
                    findPartsForPosition(grid, row+1, symbol.col, engineParts);
                }
            }
        }

        // Now total up the engine part numbers. 
        int total = 0 ;
        for ( NumberValue enginePart : engineParts )
        {
            total += enginePart.value; 
        }

        System.out.println("Part1: Total = " + total); 
    }

    private void part2(Input input)
    {
        Grid grid = parseInput(input);

        int total = 0 ; 
        
        // Go through the grid rows one at a time finding all the gears 
        for ( int row = 0 ; row < grid.numRows() ; ++row )
        {
            ArrayList<SymbolValue> symbols = grid.getSymbolRow(row);
            for( SymbolValue symbol : symbols )
            {
                if ( symbol.symbol == '*' )
                {
                    ArrayList<NumberValue> numbers = getGearRatios(grid, row, symbol.col);

                    if ( numbers.size() == 2 )
                    {
                        total += (numbers.get(0).value * numbers.get(1).value);
                    }
                }
            }
        }
        System.out.println("Part2: Total = " + total); 
    }

    private ArrayList<NumberValue> getGearRatios(Grid grid, int row, int col)
    {
        ArrayList<NumberValue> gearRatios = new ArrayList<>(); 

        if ( row > 0 )
        {
            findAdjacentNumbers(grid, row-1, col, gearRatios); 
        }
        findAdjacentNumbers(grid, row, col, gearRatios); 
        if ( row < (grid.numRows()-1))
        {
            findAdjacentNumbers(grid, row+1, col, gearRatios); 
        }
        return gearRatios; 
    }

    private void findAdjacentNumbers(Grid grid, int row, int col, ArrayList<NumberValue> numberList)
    {
        for ( NumberValue numValue : grid.getNumberRow(row) )
        {
            if ( numValue.startCol <= (col+1) && numValue.endCol >= (col-1) )
            {
                numberList.add(numValue); 
            }
        }
    }

    private void findPartsForPosition(Grid grid, int row, int col, ArrayList<NumberValue> partsList)
    {
        Iterator<NumberValue> it = grid.getNumberRow(row).iterator(); 

        while( it.hasNext() )
        {
            NumberValue numValue = it.next(); 
            if ( numValue.startCol <= (col+1) && numValue.endCol >= (col-1) )
            {
                it.remove(); 
                partsList.add(numValue); 
            }
        }
    }

    private Grid parseInput(Input input) 
    {
        final int STATE_SEARCHING = 0; 
        final int STATE_READING_NUMBER = 1; 

        Grid grid = new Grid(); 

        for ( int row = 0 ; row < input.size() ; ++row )
        {
            String line = input.get(row); 

            grid.addRow(); 

            int parserState = STATE_SEARCHING; 

            char[] chars = line.toCharArray();
            int number = 0 ;
            int numberStartCol = 0; 

            for ( int col = 0 ; col < chars.length ; ++col )
            {
                char currentChar = chars[col]; 
                if ( Character.isDigit(currentChar) )
                {
                    if ( parserState != STATE_READING_NUMBER )
                    {
                        numberStartCol = col; 
                        parserState = STATE_READING_NUMBER; 
                    }
                    number = (number * 10) + (currentChar -'0'); 
                }
                else
                {
                    if ( parserState == STATE_READING_NUMBER )
                    {
                        // Store the number. 
                        NumberValue numValue = new NumberValue(number, row, numberStartCol, col-1);
                        grid.addNumber(numValue); 
                        number = 0; 
                        parserState = STATE_SEARCHING; 
                    }
                    if ( currentChar != '.' )
                    {
                        // This is a symbol. 
                        SymbolValue symValue = new SymbolValue(currentChar, row, col);
                        grid.addSymbol(symValue);
                    }
                }

            }
            // Check if we were reading a number when the line ended. 
            if ( parserState == STATE_READING_NUMBER )
            {
                // Store the number. 
                NumberValue numValue = new NumberValue(number, row, numberStartCol, chars.length-1);
                grid.addNumber(numValue); 
            }
        }
        return grid; 
    }
    
}
