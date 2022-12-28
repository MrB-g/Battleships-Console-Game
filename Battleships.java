import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;

public class Battleships {
    private static int indent = 1;
    private static int shipCount = 5;

    private static String padLeft(String s, int count) {
        return " ".repeat(count) + s;  
    }

    private static void displayAsciiArt(String filePath) throws FileNotFoundException, InterruptedException {
        File txtSourceFile = new File(filePath);
        Scanner txtReader = new Scanner(txtSourceFile);
        while(txtReader.hasNextLine()){
            String line = txtReader.nextLine();
            System.out.println(padLeft(line, indent));
            Thread.sleep(40);
        }
    }

    private static void playerExitOrNot(Scanner userInput) throws FileNotFoundException, InterruptedException {
        System.out.print(padLeft("Are you sure you want to exit? | Please Type \"Yes\" or \"No\" : ", indent));
        String exitOrNot = userInput.nextLine().trim();
        if(exitOrNot.toLowerCase().equals("yes")){
            System.out.println(padLeft("Hope to see you soon...", indent));
            System.out.println("");
            System.exit(0);
        } else if (exitOrNot.toLowerCase().equals("no")){
            welcomeFromGame();
        } else {
            System.out.println(padLeft("Please only type \"Yes\" or \"No\".", indent));
            Thread.sleep(1000);
            playerExitOrNot(userInput);
        }
    }

    private static void welcomeFromGame() throws FileNotFoundException, InterruptedException {
        System.out.println("");
        Scanner userInput = new Scanner(System.in);
        System.out.println(padLeft("Welcome From Battleships Game!", indent));
        System.out.print(padLeft("Are you ready to start? | Please Type \"Yes\" or \"No\" : ", indent));
        String startOrNot = userInput.nextLine().trim();
        if(startOrNot.toLowerCase().equals("yes")){
            settingUpShips();
            return;
        } else if (startOrNot.toLowerCase().equals("no")){
            playerExitOrNot(userInput);
        } else {
            System.out.println(padLeft("Please only type \"Yes\" or \"No\".", indent));
            Thread.sleep(1000);
        }
        welcomeFromGame();
    }

    private static void displayRules() throws InterruptedException{
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

    private static int[][] generateRandomShipLocation(){
        int[][] randomShipLocation = new int[shipCount][2];
        for(int firstIndex=0; firstIndex<shipCount; firstIndex++){
            boolean hasSameArray = false;
            do {
                int randomRow = ThreadLocalRandom.current().nextInt(1, 11);
                randomShipLocation[firstIndex][0] = (randomRow * 2) + 1;
                int randomColumn = ThreadLocalRandom.current().nextInt(1,11);
                randomShipLocation[firstIndex][1] = randomColumn * 2;
                for(int index=firstIndex-1; index>=0; index--){
                    if(Arrays.equals(randomShipLocation[firstIndex], randomShipLocation[index])){
                        hasSameArray = true;
                        break;
                    } else {
                        hasSameArray = false;
                    }
                }
            } while(hasSameArray);
        }
        return randomShipLocation;
    }

    private static void settingUpShips() throws InterruptedException{
        int[][] playerShips = new int[shipCount][2];
        int[][] computerShips = generateRandomShipLocation();
        int[][] noShips = {{0,0}, {0,0}, {0,0}, {0,0}, {0,0}};

        System.out.println("");
        displayRules();
        
        System.out.println("");
        System.out.println(padLeft("Let's setup the battleships.", indent));
        displayMap(noShips, "blank");
    } 

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        System.out.println("");
        displayAsciiArt("./AsciiArt/battleShips.txt");
        System.out.println("");
        displayAsciiArt("./AsciiArt/title.txt");

        welcomeFromGame();
    }
}
