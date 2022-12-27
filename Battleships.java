import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Battleships {
    private static int indent = 2;

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
        rules[7] = "Player or computer whose access ship count turn to zero first will be the winner.";

        System.out.println(padLeft("Here are the rules that player shouold know before playing.", indent));
        for(int rule=0; rule < rules.length; rule++){
            String ruleLine = "Rule " + rule + " : " + rules[rule];
            System.out.println(padLeft(ruleLine, indent));
            Thread.sleep(1000);
        }
    }

    private static void displayMap() {
    }

    private static void settingUpShips() throws InterruptedException{
        System.out.println("");
        displayRules();
    } 

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        System.out.println("");
        displayAsciiArt("./AsciiArt/battleShips.txt");
        System.out.println("");
        displayAsciiArt("./AsciiArt/title.txt");

        welcomeFromGame();
    }
}
