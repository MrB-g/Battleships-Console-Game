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
            nextStep();
            return;
        } else if (startOrNot.toLowerCase().equals("no")){
            playerExitOrNot(userInput);
        } else {
            System.out.println(padLeft("Please only type \"Yes\" or \"No\".", indent));
            Thread.sleep(1000);
        }

        welcomeFromGame();
    }

    private static void nextStep(){
        System.out.println("Well, this is next step.");
    } 

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        System.out.println("");
        displayAsciiArt("./AsciiArt/battleShips.txt");
        System.out.println("");
        displayAsciiArt("./AsciiArt/title.txt");

        welcomeFromGame();
    }
}
