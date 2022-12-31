import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.io.File;

public class Battleships {
    private static int indent = 1;
    private static int shipCount = 5;

    private static String padLeft(String s, int count) {
        return " ".repeat(count) + s;  
    }

    private static void displayAsciiArt(String filePath) {
        try { 
            File txtSourceFile = new File(filePath);
            Scanner txtReader = new Scanner(txtSourceFile);
            while (txtReader.hasNextLine()) {
                String line = txtReader.nextLine();
                System.out.println(padLeft(line, indent));
                Thread.sleep(40);
            }
            txtReader.close();
        } catch(Exception error){}
    }

    private static void playerExitOrNot(Scanner userInput) {
        try { 
            System.out.print(padLeft("Are you sure you want to exit? | Please Type \"Yes\" or \"No\" : ", indent));
            String exitOrNot = userInput.nextLine().trim();
            if(exitOrNot.toLowerCase().equals("yes")){
                System.out.println(padLeft("Hope to see you again...", indent));
                System.out.println("");
                System.exit(0);
            } else if (exitOrNot.toLowerCase().equals("no")){
                startTheGame();
            } else {
                System.out.println(padLeft("\u001B[31m\u001B[1mPlease only type \"Yes\" or \"No\".\u001B[0m", indent));
                Thread.sleep(1000);
                playerExitOrNot(userInput);
            }
        } catch(Exception error){}
    }
    
    private static void clearConsole() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    private static void startTheGame() {
        try {
            System.out.println("");
            Scanner userInput = new Scanner(System.in);
            System.out.println(padLeft("Welcome From Battleships Game!", indent));
            System.out.print(padLeft("Are you ready to start? | Please Type \"Yes\" or \"No\" : ", indent));
            String startOrNot = userInput.nextLine().trim();
            if(startOrNot.toLowerCase().equals("yes")){
                settingUpShips();
                userInput.close();
                return;
            } else if (startOrNot.toLowerCase().equals("no")){
                playerExitOrNot(userInput);
            } else {
                System.out.println(padLeft("\u001B[31m\u001B[1mPlease only type \"Yes\" or \"No\".\u001B[0m", indent));
                Thread.sleep(1000);
            }
            startTheGame();
        } catch(Exception erro){}
    }

    private static void displayRules() {
        try {
            String[] rules = new String[8];
            rules[0] = "Both player and computer can access five ships.";
            rules[1] = "Player starts the battling with the computer.";
            rules[2] = "Player should guess all the opponent ships' locations in the map.";
            rules[3] = "Player hits computer's ship, the location should be indicated as '!'.";
            rules[4] = "Computer hits player's ship, the location should be indicated as 'X'.";
            rules[5] = "Both players fail to guess the opponent's location, it should be indicated as '-'.";
            rules[6] = "The ship count should be decremented by 1 based on the hits.";
            rules[7] = "Player or computer who can firstly guess all the opponent's ship location will be the winner.";
            System.out.println(padLeft("Here are the rules that player shouold know before playing!", indent));
            for(int rule=0; rule < rules.length; rule++){
                String ruleLine = "Rule " + (rule+1) + " : " + rules[rule];
                System.out.println(padLeft(ruleLine, indent));
                Thread.sleep(50);
            }
        } catch(Exception error){}
    }

    private static void displayMap(int[][] shipLocation, String playerType) {
        System.out.println("");
        if(playerType.equals("player") || playerType.equals("blank")){
            System.out.println(padLeft("Player Board", indent));
        } else if(playerType.equals("computer")){
            System.out.println(padLeft("Computer Board", indent));
        }

        int totalRow, totalColumn, rowNumber, columnNumber;

        totalRow = 22;
        totalColumn = 21;
        rowNumber = 1;
        columnNumber = 1;

        String character = "+";

        for(int row=1; row<=totalRow; row++){
            int numRowDigit = String.valueOf(rowNumber).length();
            if(row%2 != 0 && row!=1){
                if(numRowDigit == 2){
                    System.out.print(padLeft(rowNumber+"", indent));
                } else {
                    System.out.print(padLeft(rowNumber+" ", indent));
                }
                rowNumber += 1;
            } else {
                System.out.print(padLeft("  ", indent));
            }
           
            for(int column=1; column<=totalColumn; column++){
                if(row == 1){
                    if(column%2 == 0){
                        System.out.print(padLeft(columnNumber+"", indent));
                        columnNumber += 1;
                    } else {
                        System.out.print(padLeft(" ", indent));
                    } 
                } else {
                    if(row%2 != 0){
                        if(column%2 == 0){
                            for(int index=0; index<shipLocation.length; index++){
                                if(shipLocation[index][0] == row && shipLocation[index][1] == column){
                                    if(playerType.equals("computer")){
                                        character = "\u001B[31m\u001B[1m" + "C" + "\u001B[0m";
                                    } else if(playerType.equals("player")){
                                        character = "\u001B[32m\u001B[1m" + "@" + "\u001B[0m";
                                    }
                                    break;
                                } else {
                                    character = " ";
                                }
                            }
                        } else if(column%2 != 0){
                            character = "\u2022";
                        }
                    } else if(row%2 == 0){
                        if(column%2 == 0){
                            character = "\u2022";
                        } else if(column%2 != 0){
                            character = "+";
                        }
                    }
                    
                    System.out.print(padLeft(character, indent));
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private static boolean checkDuplicateShipLocation(int[][] shipLocation, int currentIndex){
        boolean isDuplicate = false;
        for(int previousIndex=currentIndex-1; previousIndex>=0; previousIndex--){
            if(Arrays.equals(shipLocation[currentIndex], shipLocation[previousIndex])){
                isDuplicate = true;
                break;
            } else {
                isDuplicate = false;
            }
        }
        return isDuplicate;
    }

    private static int[][] generateRandomShipLocation(){
        int[][] randomShipLocation = new int[shipCount][2];
        for(int index=0; index<shipCount; index++){
            boolean hasSameArray = false;
            do {
                int randomRow = ThreadLocalRandom.current().nextInt(1, 11);
                randomShipLocation[index][0] = (randomRow * 2) + 1;
                int randomColumn = ThreadLocalRandom.current().nextInt(1,11);
                randomShipLocation[index][1] = randomColumn * 2;
                hasSameArray = checkDuplicateShipLocation(randomShipLocation, index);
            } while(hasSameArray);
        }
        return randomShipLocation;
    }

    private static void pressEnterToContinue(){
        System.out.println(padLeft("Press Enter key to start the battle.", indent));
        try {
            System.in.read();
        } catch(Exception error){}
    }

    private static int getUserInputShipRowOrColumn(String type, String shipName){
        int result = 0;
        while(true){
            Scanner userInputData = new Scanner(System.in);
            try {
                if(type == "row"){
                    System.out.print(padLeft("Row of " + shipName + " ship : ", indent));
                } else if(type == "column"){
                    System.out.print(padLeft("Column of " + shipName + " ship : ", indent));
                }
                int userInput = userInputData.nextInt(); 
                if(0<userInput && userInput<11){
                    result = userInput;
                    break;
                } else {
                    System.out.println(padLeft(
                            "\u001B[31m\u001B[1mYour input number must be between 1 and 10. Please type again.\u001B[0m",
                            indent));
                }
            } catch(Exception error){
                System.out.println(padLeft("\u001B[31m\u001B[1mYour input must be an integer. Please type again.\u001B[0m", indent));
            }
        }
        return result;
    }

    private static void settingUpShips() {
        try {

            int[][] playerShips = new int[shipCount][2];
            int[][] computerShips = generateRandomShipLocation();
            int[][] noShips = {{0,0}, {0,0}, {0,0}, {0,0}, {0,0}};
            System.out.println("");
            displayRules();
            System.out.println("");
            System.out.println(padLeft("Let's setup the battleships.", indent));
            displayMap(noShips, "blank");
            String[] shipName = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th"};
            for(int index=0; index<playerShips.length; index++){
                boolean hasSameArray = false;
                do{
                    System.out.println(padLeft("Please type row and column for the " + shipName[index] + " ship to put into the map.", indent));
                    int row = getUserInputShipRowOrColumn("row", shipName[index]);
                    playerShips[index][0] = (row * 2) + 1;
                    int column = getUserInputShipRowOrColumn("column", shipName[index]);
                    playerShips[index][1] = column * 2;
                    hasSameArray = checkDuplicateShipLocation(playerShips, index);
                    if(hasSameArray){
                        System.out.println(padLeft("\u001B[31m\u001B[1mTwo ship can't be put in one location. Please chooe row and column again.\u001B[0m",indent));
                    }
                } while(hasSameArray);
            }
            System.out.println(padLeft("\u001B[32m\u001B[1mSetup Completed...\u001B[0m", indent));
            pressEnterToContinue();        
            startTheBattle();
        } catch(Exception erro){}
    } 

    private static void startTheBattle(){

    }

    private static void displayTitle() {
        try {
            System.out.println("");
            displayAsciiArt("./AsciiArt/battleShips.txt");
            System.out.println("");
            displayAsciiArt("./AsciiArt/title.txt");
        } catch(Exception error){}
    } 

    public static void main(String[] args){
        clearConsole();
        try { 
            displayTitle();
            startTheGame();
        } catch(Exception error){}
    }
}
