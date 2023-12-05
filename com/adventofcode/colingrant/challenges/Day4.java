package com.adventofcode.colingrant.challenges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.adventofcode.colingrant.common.Challenge;
import com.adventofcode.colingrant.common.Input;

public class Day4 implements Challenge
{
    private static class GameCard
    {
        @SuppressWarnings("unused")
        public String cardName ; 

        public Set<Integer> winningNumbers ;

        private int count = 1; 

        public GameCard(String cardName, Set<Integer> winningNumbers, Set<Integer> userNumbers)
        {
            this.cardName = cardName;
            this.winningNumbers = winningNumbers;

            // Only store matching winning numbers. 
            this.winningNumbers.retainAll(userNumbers);
        }

        public void incrementCount() 
        {
            count += 1 ;
        }

        public int getCount()
        {
            return count; 
        }

        public int numberOfWins()
        {
            return winningNumbers.size(); 
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
        int total = 0; 

        List<GameCard> gameCards = parseInput(input);
        
        for ( GameCard gameCard : gameCards )
        {
            if ( gameCard.numberOfWins() > 0 )
            {
                total += 1 << (gameCard.numberOfWins()-1);
            }
        }

        System.out.println("Part1: Total = " + total); 
    }

    private void part2(Input input)
    {
        List<GameCard> gameCards = parseInput(input);

        for ( int i = 0 ; i < gameCards.size() ; ++i )
        {
            GameCard currentCard = gameCards.get(i); 

            int numWins = currentCard.numberOfWins(); 
            if ( numWins > 0 )
            {
                // We may have multiple cards so repeat for each one. 
                for ( int j = 0 ; j < currentCard.getCount() ; ++j )
                {
                    // Collect winning cards 
                    for ( int k = i+1 ; k < (i+numWins+1) ; ++k )
                    {
                        // We've won this card so increment the counter. 
                        gameCards.get(k).incrementCount(); 
                    }
                }
            }
        }

        // How many cards do we have? 
        int total = 0; 
        for ( GameCard gc : gameCards )
        {
            total += gc.getCount();
        }

        System.out.println("Part2: Total = " + total);
    }

    private ArrayList<GameCard> parseInput(Input input) 
    {
        ArrayList<GameCard> gameCards = new ArrayList<GameCard>(); 


        for ( String line : input )
        {
            String[] cardIdAndNumbers = line.split(":"); 
            String numbers = cardIdAndNumbers[1].trim(); 

            String[] winningAndUserNumbers = numbers.split("\\|"); 

            String winningNumbersStr = winningAndUserNumbers[0].trim(); 

            String[] winningNumbersArray = winningNumbersStr.split(" +"); 
            Set<Integer> winningNumbers = Arrays.stream(winningNumbersArray)
                                                    .map(s -> Integer.parseInt(s.trim())).collect(Collectors.toSet()); 

            String userNumbersStr = winningAndUserNumbers[1].trim(); 

            String[] userNumbersArray = userNumbersStr.split(" +");
            Set<Integer> userNumbers = Arrays.stream(userNumbersArray)
                                                    .map(s -> Integer.parseInt(s.trim())).collect(Collectors.toSet()); 

            GameCard gameCard = new GameCard(cardIdAndNumbers[0].trim(), winningNumbers, userNumbers); 
            gameCards.add(gameCard); 
        }

        return gameCards; 
    }
    

}
