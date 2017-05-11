/*
 * WordJumbleFX package contains WJFX_GUI, BinSearch, Dictionary class, and 
 * PermutationGenerator classes.
 */
import java.util.ArrayList;

/**
 * filename: PermutationGenerator.java
 * class: PermutationGenerator
 * This class contains a permutation method and its helper method
 * The method permute a string to all possible combination and put them
 * into an ArrayList
 *  
 * @author Boram Kim 
 * @version 1.0
 * 
 * Compiler: Java 8 <br>
 * OS: Windows 10 <br>
 * Hardware: HP Laptop <br>
 * @author Boram Kim 
 * 
 * Log:
 * 05/05/2017 BK version 1 completed
 */public class PermutationGenerator 
{
    ArrayList<String> globalAL = new ArrayList<>();
    /**
     * accessor for globalAl ArrayList from outside the class
     * @return ArrayList 
     */
    protected ArrayList<String> getGlobalAL()
    {
        return globalAL;
    }
    /**
     * helper method 
     * @param s string (a word) to permute
     */
    protected void permLetters(String s)
    { // helpter method
        permLetters("", s);
    }
    // so instead of using "scramble" and "swap" method, I used this permutation
    // method that combines those two methods and permute words in order of
    // index
    /**
     * This method permute a string to all possible combination and put them
     * into an ArrayList
     * @param prefix string to to return (or add to the ArrayList)
     * @param s string to or in the process of permutation
     */
    protected void permLetters(String prefix, String s)  
    { // adds permutated characters of string s into an ArrayList
        int n = s.length();
        if (n == 0)
            globalAL.add(prefix);
        else
        {
            for (int i = 0; i < n; i++)
                permLetters(prefix + s.charAt(i), 
                        s.substring(0, i) + s.substring(i+1, n));
        }
    }

}
