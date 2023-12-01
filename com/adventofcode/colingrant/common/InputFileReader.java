package com.adventofcode.colingrant.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputFileReader 
{
    public static Input readInputFile(String inputFile) 
    {
        Input lines = new Input();

        String path = "./input/" + inputFile ;
        
        try ( BufferedReader bfr = new BufferedReader(new FileReader(path)) )
        {
            String line;
            do
            {
                line = bfr.readLine();
                if ( line != null )
                    lines.add(line.trim()); 

            } while ( line != null );
            
        }
        catch( IOException ioe )
        {
            System.err.println("IO Error reading file " + path); 
            ioe.printStackTrace(); 
        }

        return lines; 
    }
    
}
