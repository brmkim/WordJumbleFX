/*
 * WordJumbleFX package contains WJFX_GUI, BinSearch, Dictionary class, and 
 * PermutationGenerator classes.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/**
 * filename: Dictionary.java
 * class: Dictionary
 * Dictionary class contains all methods that deals with dictionary for the
 * WordJumble application, such as opening and reading a dictionary file,
 * making it into an ArrayList of words, generating random number within the 
 * length of the dictionary to get a random index for a word, choosing a random
 * word with that index, calling permutation class method to jumble it, and
 * put all permuted words into another ArrayList
 *  
 * @author Boram Kim 
 * @version 1.3
 * 
 * Compiler: Java 8 <br>
 * OS: Windows 10 <br>
 * Hardware: HP Laptop <br>
 * @author Boram Kim 
 * 
 * Log:
 * 05/05/2017 BK version 1 completed
 * 05/06/2017 BK changed some objects to global, fixed openDicFile method
 * 05/07/2017 BK added foundInDic method
 * 05/08/2017 BK fixed foundInDic method
 */
public class Dictionary 
{
    final String DEFAULT_FILE_NAME = "JUMBLE_filtered.dic";
    ArrayList<String> permAL = new ArrayList<>(); // global variable for
    // ArrayList of permutated words (to be used for binarySearch)
    ArrayList<String> dictArrayList;
    File file; // global variable for opening dic file (to be used for 
            // binary search method
    String theWord = ""; // global variable for the chosen word from the dict
    /**
     * this method opens a file with a dic extention and calls readDic 
     * method
     * @param stage application stage
     * @return ArrayList it's relaying returning variable of readDic method
     * @throws IOException 
     */
    protected ArrayList<String> openDicFile(Stage stage) throws IOException
    {
        String dictionaryName = DEFAULT_FILE_NAME;
        file = new File(dictionaryName);
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Dictionary File");
        String currentDir = System.getProperty("user.dir")
                 + File.separator;
        StringBuilder sb = null;
        fc.setInitialDirectory(new File(currentDir));
        // or fx.setInitialDirectory(new File("."));
        fc.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Dictionary Files", 
                    new String[] {"*.Dic", "*.DIC", "*.dic"})); 
        fc.initialDirectoryProperty();
       // File selectedFile = fc.showOpenDialog(stage);
        file = fc.showOpenDialog(stage);
        // creating an ArrayList because the getJumble event handler calls
        // this method that also calls readDic method which returns ArrayList
        ArrayList<String> al = new ArrayList<>();
        try
        {// check before reading dic file
            if (file.exists() && file.canRead());
                al = readDic(file); 
        }
        catch (NullPointerException e)
        {
            // how to handle it?
        }
        return al;
    }
    /**
     * this method read the contents of a dictionary file and transfer it into
     * an ArrayList of words
     * @param selectedFile file to read
     * @return ArrayList of all the words in the dictionary
     */
     protected ArrayList<String> readDic(File selectedFile)
    { // return value was StringBuilder
        StringBuilder sb = new StringBuilder();   
        String currentLine = "";
        dictArrayList = new ArrayList<>();
        try
        {
            FileReader fr = new FileReader(selectedFile);
            BufferedReader br = new BufferedReader(fr);
            while(currentLine != null)
            { // while the currentLine is not null, read the line from the 
                // BufferedREader as string, and append it to dictArrayList
                currentLine = br.readLine();
                dictArrayList.add(currentLine);
            }
            fr.close();
            br.close();  // gotta close FileReader and BufferedReader!
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return dictArrayList;
    }
//     protected int randomNumGen()
//     {
//         Random randNum = new Random();
//         int num = 7 + randNum.nextInt(15);  // for permutating letters
//         return num;
//     }
     /**
      * this method generate a random number within the length of a dictionary
      * It is used to generate a random index to choose a random word
      * @param dictLen length of the dictionary (end bound)
      * @return int value of random index
      */
     protected int randomNumGen2(int dictLen)
     {// for generating random number for index in dictionary
         Random rand = new Random();
         int num = rand.nextInt(dictLen); 
         return num;
     }
    /**
     * this method calls the random index from the randomNumGen2 method in the
     * same class, get the word of that index and assign it into String object
     * @param dictArrayList ArrayList of a whole dictionary
     * @return String object of the chosen random word
     */
    protected String theWord(ArrayList<String> dictArrayList)
    {// get a randomly selected word from dictArrayList
        int dictLen = dictArrayList.size();
        int randNum = randomNumGen2(dictLen);
        theWord = dictArrayList.get(randNum);

        return theWord;
    }
    /**
     * this method calls a permutation method from the PermutationGenerator 
     * class, get all possible permutation of a single word and list them
     * into an ArrayList
     * @param theWord word to permute
     * @return ArrayList of all permuted words from a single word
     */
    protected ArrayList<String> permList(String theWord)
    {// ArrayList of all permutated words
        PermutationGenerator pg = new PermutationGenerator();
        pg.permLetters("", theWord);
        permAL = pg.getGlobalAL();

        return permAL;
    }
    /**
     * this method takes an ArrayList of permuted words generated from the
     * same class, calls the binary search method from binSearch class which
     * checks any match, and append a found word (or two or more words) into
     * an ArrayList
     * @param permutatedWords ArrayList of permuted words
     * @return ArrayList of words that are found in the dictionary
     */
    protected ArrayList<String> foundInDic(ArrayList<String> permutatedWords)
    {
        int returnVal = 2;
        int low = 0; 
        int high = dictArrayList.size(); 
        ArrayList<String> foundInDic = new ArrayList<>(); 
        BinSearch binSearch = new BinSearch();
        for (int i = 0; i < permutatedWords.size(); i++)
        {   
            String keyword = permutatedWords.get(i);
            returnVal = binSearch.binarySearch(dictArrayList, 
                keyword, low, high );
            if (returnVal == 0)  // '0' means there was a match
                foundInDic.add(keyword);
        }
       // remove a duplicate -- googled the solution but I haven't quite 
       // understood how this work, since we haven't learned Set yet
       Set set = new HashSet();
       set.addAll(foundInDic);
       foundInDic.clear();
       foundInDic.addAll(set);
       
       return foundInDic;
    }
}
