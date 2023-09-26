import java.util.*;
import java.io.*;

public class Main {

    public static boolean checkWord(String s1){

        try {
            Scanner fileIn = new Scanner(new File("K:\\KSU\\Fall 2022\\CSE1322L\\Assignment6_EmreDiricanli\\src\\2of12inf.txt"));
            while (fileIn.hasNextLine()) {
                String thisLine = fileIn.nextLine();
                return thisLine.equals(s1);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static void saveGame(String s, ArrayList<String> guesses){
        Scanner sc = new Scanner(System.in);
        System.out.println("Name your session...");
        String fileName = sc.nextLine();

        try{
            PrintWriter output = new PrintWriter("K:\\KSU\\Fall 2022\\CSE1322L\\Assignment6_EmreDiricanli\\src\\" + fileName + ".txt");
            output.println(s);

            int line = 0;

            while(guesses.size() > line){
                output.println(guesses.get(line));
                line++;
            }
            output.close();

        }catch (Exception e){
            System.out.println("Unable to save state");
        }

    }

    public static WordJumble restoreGame() {
        Scanner sc = new Scanner(System.in);
        System.out.println("What game do you want to restore");
        String fileName = sc.nextLine();
        try {

            Scanner fileIn = new Scanner(new File("K:\\KSU\\Fall 2022\\CSE1322L\\Assignment6_EmreDiricanli\\src\\" + fileName + ".txt"));
            ArrayList<String> guesses = new ArrayList<String>();
            String letters = fileIn.nextLine();

            while (fileIn.hasNextLine()) {
                guesses.add(fileIn.nextLine());
            }
           return new WordJumble(letters, guesses);
        }catch (Exception e){
            System.out.println(e);
        }
        return new WordJumble();
    }

    public static void playGame(WordJumble wj){
        Scanner input = new Scanner(System.in);
        String guess = "";
        try {
            do {
                System.out.println("Letters: " + wj.getLetters());
                System.out.println("Found Words: " + wj.getGuessedWordsAsString());
                System.out.println("Current Score: " + wj.getScore());
                System.out.println("Enter a word using those letters or QUIT to stop, SAVE to save progress");
                guess = input.nextLine();
                //guess.toLowerCase();

                if (guess.equals("SAVE")) {
                    saveGame(wj.getLetters(), wj.getGuessedWords());
                }
                if (!wj.hasLetters(guess)) {
                    System.out.println("You used letters you do not have");
                } else if (checkWord(guess)) {
                    System.out.println("Not a valid word");
                } else if (wj.alreadyGuessed(guess)) {
                    System.out.println("Already Guessed");
                } else {
                    wj.scoreWord(guess.length());
                    wj.saveGuess(guess);
                    System.out.println("Valid Word. You scored " + wj.scoreWord(guess.length()));
                }
            } while (!guess.equals("QUIT"));
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Type NEW for a new game, or CONTINUE to restore an old game?");
        String desicion = sc.nextLine();
        if(desicion.equalsIgnoreCase("NEW")){
            WordJumble wj = new WordJumble();
            playGame(wj);
        } else if (desicion.equalsIgnoreCase("CONTINUE")) {
            restoreGame();
        }

    }
}