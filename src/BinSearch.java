/*
 * WordJumbleFX package contains WJFX_GUI, BinSearch, Dictionary class, and 
 * PermutationGenerator classes.
 */
import java.util.ArrayList;
/**
 * filename: BinSearch.java
 * class: BinSearch
 * This class contains a binarySearch method which will look for a matched
 * word in the dictionary comparing with a word given
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
 * 05/07/2017 BK version 1 completed
 */
public class BinSearch 
{
    final int BYHALF = 2;
    /**
     * this method uses recursive binary search to look for a matched word in 
     * the dictionary compared with a given word
     * @param dict ArrayList of all dictionary dictionary word
     * @param keyword keyword string to see the match
     * @param low lowest index int
     * @param high highest index int
     * @return 
     */
    protected int binarySearch(ArrayList<String> dict, 
            String keyword, int low, int high)
    {
        // I know that having multiple exits (returns) aren't great, but
        // for this method I had to. 
        if (low > high)
            return -1;
        int mid = (low + high) / BYHALF;
        if (dict.get(mid).compareTo(keyword) == 0)
            return 0;
        else if (dict.get(mid).compareTo(keyword) < 0)
            return binarySearch(dict, keyword, mid + 1, high);
        else
            return binarySearch(dict, keyword, low, mid - 1);
    }
}
