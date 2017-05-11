/*
 * WordJumbleFX package contains WJFX_GUI, BinSearch, Dictionary class, and 
 * PermutationGenerator classes.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/**
 * filename: WJFX_GUI.java
 * class: WJFX_GUI
 * User-interface class that contains main and other methods for the 
 * executable application
 * in an answer or answers and displays if it's correct or incorrect
 * It is a game application in which a user initiate the game by clicking
 * the Get JUMBLE button, which prompt to load a dictionary and choose a
 * word randomly from the dictionary, jumble it and have the user figure
 * out which word it is  
 * The program takes input through clicking buttons and choosing non-editable 
 * comboBox values, and check if the sequence of comboBox matches the word 
 *  
 * @author Boram Kim 
 * @version 1.1
 * 
 * Compiler: Java 8 <br>
 * OS: Windows 10 <br>
 * Hardware: HP Laptop <br>
 * @author Boram Kim 
 * 
 * Log:
 * 04/29/2017 BK adventure of making FX GUI had began
 * 05/05/2017 BK version 1 completed
 * 05/06/2017 BK added pane node into getJumble button action handler
 */
public class WJFX_GUI extends Application 
{   // declaring global variables and instances to share with other methods
    // within the class
    final int MAX = 7;
    String jumbledWord = "";
    int numSolution = 0;
    int len = 0; // word length 
    BorderPane pane = new BorderPane();
    Label tellLabel = new Label();
    Label answerLabel = new Label("");
    ArrayList<String> solutions = new ArrayList<>();
    ComboBox[] cbArray = new ComboBox[MAX];
    Dictionary dict = new Dictionary();
    String dName = "None";
    Button checkAnsBtn = new Button(); // declared globally so that I can 
    Button answerBtn = new Button();  // have them disabled upon start
    /**
     * test constructor
     */
    public WJFX_GUI()
    {
        System.out.println("Test constructor is invoked");  // 2
    }
    @Override
    public void start(Stage primaryStage) {
        System.out.println("start method is invoked");      // 3
        checkAnsBtn.setDisable(true);
        answerBtn.setDisable(true);
        pane.setTop(getHBox1(primaryStage));
        //pane.setCenter(getHBox2(primaryStage));       
        pane.setBottom(getHBox3(primaryStage));   
        pane.setLeft(labelBox(dName, jumbledWord, numSolution, ""));
        
        Scene scene3 = new Scene(pane);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(300);        
        primaryStage.setTitle("Word Jumble");
        primaryStage.setScene(scene3);
        primaryStage.show();
    }
    /**
     * labelBox contains labels for dictionary file name, jumbled word which
     * the user needs to figure out, and number of solution (dictionary word(s))
     * the jumbled word has
     * @param filename dictionary file name using for the game
     * @param jumbledWord jumbled, unreadable word
     * @param numSolution number of solution(s)
     * @param str this string is for About purpose
     * @return VBox format 
     */
    private VBox labelBox(String filename, String jumbledWord, int numSolution,
            String str)
    {   // VBox for labels
        final VBox vbox = new VBox();
        //vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(3);
        final Label dictLabel= new Label();
        final Label snLabel = new Label();
        final Label jwLabel = new Label();
        final Label aLabel = new Label();
        
        snLabel.setText("Number of Solutions: " + numSolution);
        jwLabel.setText(jumbledWord);
        jwLabel.setFont(new Font("Arial Bold", 18));
        
        dictLabel.setText("Dictionary: " + filename);
        dictLabel.setFont(new Font("Arial Bold", 13));
        aLabel.setText(str);
        vbox.getChildren().addAll(dictLabel, snLabel, jwLabel, aLabel);
        
        return vbox;        
    }
    /**
     * Auxiliary labelBox -- unused 
     * @param str string to add display as label
     * @return VBox format
     */
    private VBox labelBox2(String str)
    {// extra label box --- not used
        final VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(3);
        final Label genericLabel = new Label();
        genericLabel.setText(str);
        vbox.getChildren().add(genericLabel);
        
        return vbox;
    }
    /**
     * HBox for menu bar
     * @param primaryStage the stage
     * @return HBox that contains the menu bar and menu items
     */
    private HBox getHBox1(Stage primaryStage)
    {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(0, 0, 15, 0));
        //hBox.prefWidth(800);

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuHelp = new Menu("Help");
        MenuItem loadFile = new MenuItem("Load File");
        MenuItem exit = new MenuItem("Exit");
        MenuItem about = new MenuItem("About");
        loadFile.setOnAction((ActionEvent t) -> {
            try 
            {
                dict.openDicFile(primaryStage);
            } 
            catch (IOException ex) 
            {
                // how to handle it??
            }
            Label dictLabel = new Label();
            dName = dict.file.getName();
            pane.setLeft(labelBox(dName, jumbledWord, numSolution, ""));
        });
        exit.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });
        about.setOnAction((ActionEvent t) -> {
            //Label aboutLabel = new Label();
            //aboutLabel.setText("WordJumble Puzzle by Boram Kim");
            //aboutLabel.setContentDisplay(ContentDisplay.BOTTOM);
            //hBox.getChildren().add(aboutLabel);        
            String str = "WordJumble Puzzle by Boram Kim";
            pane.setLeft(labelBox(dName, jumbledWord, numSolution, str));
            tellLabel.setText(null);
            answerLabel.setText(null);            
            solutions.clear(); 

            for (int i = 0; i < cbArray.length; i++)
                cbArray[i].setItems(null);

        });
        menuBar.getMenus().addAll(menuFile, menuHelp);
        menuBar.setMinWidth(800);
        menuFile.getItems().addAll(loadFile,exit);
        menuHelp.getItems().addAll(about);
        hBox.getChildren().addAll(menuBar);

        return hBox;
    }
    /**
     * HBox that contains comboBoxes
     * @param primaryStage the stage
     * @param jumbledWord to be put into each comboBoxes
     * @return HBox
     */
    private HBox getHBox2(Stage primaryStage, String jumbledWord)
    {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 20, 10, 20));
        len = jumbledWord.length();
        ObservableList options = FXCollections.observableArrayList();
        // transform the string into char array
        char[] temp = new char[len];
        for (int i = 0; i < len; i++)
            temp[i] = jumbledWord.charAt(i);
        // add the chars into each comboBox
        for (char c: temp)
            options.addAll(c);
        // comboBoxes that contains char
        ComboBox<Character> cb0 = new ComboBox(options);    
        ComboBox<Character> cb1 = new ComboBox(options);
        ComboBox<Character> cb2 = new ComboBox(options);
        ComboBox<Character> cb3 = new ComboBox(options);
        ComboBox<Character> cb4 = new ComboBox(options);
        ComboBox<Character> cb5 = new ComboBox(options);
        ComboBox<Character> cb6 = new ComboBox(options);
        cb0.setVisible(false);
        cb1.setVisible(false);
        cb2.setVisible(false);
        cb3.setVisible(false);
        cb4.setVisible(false);
        cb5.setVisible(false);
        cb6.setVisible(false);
        cbArray[0] = cb0;
        cbArray[1] = cb1;
        cbArray[2] = cb2;
        cbArray[3] = cb3;
        cbArray[4] = cb4;
        cbArray[5] = cb5;
        cbArray[6] = cb6;   
        hBox.getChildren().add(cb0);
        hBox.getChildren().add(cb1);
        hBox.getChildren().add(cb2);
        hBox.getChildren().add(cb3);
        hBox.getChildren().add(cb4);
        hBox.getChildren().add(cb5);
        hBox.getChildren().add(cb6); 
        //set visible true for only the number of word length
        for (int j = 0; j < len; j++)
            cbArray[j].setVisible(true);

        return hBox;
    }
    /**
     * HBox for buttons -- Get JUMBLE, Test Answer, and See Answer 
     * @param primaryStage the stage
     * @return HBox with three buttons
     */
    private HBox getHBox3(Stage primaryStage)
    {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(20, 20, 20, 20));
        Button jumbleBtn = new Button();
        jumbleBtn.setText("Get JUMBLE");    
        checkAnsBtn.setText("Test Answer");
        answerBtn.setText("See Answer");
        hBox.getChildren().addAll(jumbleBtn, checkAnsBtn, answerBtn);
        hBox.setSpacing(15);

        jumbleBtn.setOnAction(new EventHandler<ActionEvent>()  
        {
            @Override
            public void handle(ActionEvent event) 
            { 
                checkAnsBtn.setDisable(false);
                answerBtn.setDisable(false);
                // reinitializations
                tellLabel.setText(null);
                answerLabel.setText(null);            
                solutions.clear(); 
                System.out.println("Get JUMBLE button clicked");
                //Dictionary dictObj = new Dictionary();
                ArrayList<String> dictArrayList = new ArrayList<>();
                // open and read the dictionary, and returns ArrayList object
                if (dName.equals("None"))
                {
                    try 
                    {
                        dict.openDicFile(primaryStage);
                        dName = dict.file.getName();
                    } 
                    catch (IOException ex) 
                    {
                       // how to handle...
                    }
                    dictArrayList = dict.readDic(dict.file);
                }               
                else
                {
                    dictArrayList = dict.readDic(dict.file);
                    dName = dict.file.getName();
                }
                // the word for jumble
                String theWord = dict.theWord(dictArrayList);  
                PermutationGenerator permObj = new PermutationGenerator();
                permObj.permLetters("", theWord);
                ArrayList<String> arr = permObj.getGlobalAL();
                // ArrayList of solutions (found word in the dictionary)            
                solutions = dict.foundInDic(arr);
                for (String a: solutions)
                    System.out.println("Solutions: " + a);
                // number of solutions
                int numSolution = solutions.size();
                // choosing one jumbled word from the ArrayList of permutated 
                // words 
                Random randNum = new Random();
                int n = randNum.nextInt(arr.size());
                String jumbledWord = arr.get(n); 
                System.out.println(jumbledWord);
                pane.setTop(getHBox1(primaryStage));
                pane.setCenter(getHBox2(primaryStage, jumbledWord));
                pane.setLeft(labelBox(dName, jumbledWord, numSolution, ""));
            }
        });
        checkAnsBtn.setOnAction(new EventHandler<ActionEvent>() 
        {

            @Override
            public void handle(ActionEvent event) 
            {
                // in case of multiple clicks, this prevents adding labels up
                hBox.getChildren().remove(tellLabel);
                char[] temp = new char[MAX];
                // putting the comboBox values to a char array
                for (int j = 0; j < len; j++)
                {
                    if (cbArray[j].getValue() != null)
                        temp[j] = (char) cbArray[j].getValue();
                }        

                String checkThis = "";
                // transform the char array sequence into one String var
                for (char cr: temp)
                    checkThis += cr;
                System.out.println("checkThis is: " + checkThis); // for my use
                ArrayList<String> temp2 = new ArrayList<>();
                boolean check = false;
                    for (String s: solutions)
                    {
                        checkThis = checkThis.trim(); // trim any white space
                        s = s.trim();
                        System.out.println("Solution is: " + s);
                        check = checkThis.equals(s);
                        if (check)   // detect an answer if there are multiple
                            temp2.add("true");
                    }
                    for (String st: temp2)
                        System.out.print(st + " ");  // for my use
                    // if found in the ArrayList of multiple answers -- correct
                    if (temp2.contains("true"))
                        tellLabel.setText("Correct!");
                    else
                        tellLabel.setText("Incorrect!");

                hBox.getChildren().add(tellLabel);           
            }
        });
        answerBtn.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("See Answer button clicked");
                // in case of multiple clicks, this prevents adding labels up
                hBox.getChildren().remove(answerLabel); 
                String answer = "";
                // for multiple answers, build a one String object separated 
                // by space
                for (String s: solutions)
                { 
                    System.out.println(s);
                    answer += s + " ";
                }
                answerLabel.setText(answer);

                System.out.println("answer str: " + answer);
                answerLabel.setFont(new Font("Arial Bold", 13));
                hBox.getChildren().add(answerLabel);
            }
        });

        return hBox;
    }
    /**
     * main method to launch the application
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("launch application");   // 1
        launch(args);
    }
}
