import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
public class WordGuesser {
/*
 * fix the correct letter but not correct spot part
 * make code readable lol
 * impossible to win
 * make a score of some sort
 */

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String guess;
    String SecretWord = "edaale";
    boolean donegame = false;
    boolean alpha;
    boolean length;
    int MAXGUESSES = 6;
    int numGuesses = 0;
    int row = 0;
    char[][] guessarray = new char[11][13];

    
    //gotta add actually winnning the game lol

    while (donegame != true){
      
      do {
        System.out.println();
        System.out.println("Enter \"help\" for additional options");
        System.out.print("Your guess: ");
        guess = scanner.nextLine();
        guess = guess.toLowerCase();
        alpha = checkAllAlpha(guess);
        length = checkWordLength(guess);
      }
      //had a stroke making this while loop specifically the alpha and length part
      while (!guess.equals("help")&&!guess.equals("exit")&&!guess.equals("set")&&!alpha||!length);
      if (guess.equals("help")){
        System.out.println("This app is ment to replicate the game Wordle");
        System.out.println("To play the game, enter in a 6 letter word to try and guess what the secret word is");
        System.out.println("Getting a \"W\" means the letter is the correct letter and position");
        System.out.println("Getting a \"!\" means the letter is the correct but in the wrong position");
        System.out.println("Getting a \"X\" means the letter is not in the word");
        System.out.println("Typing the word \"exit\" will quit the game" );
        System.out.println("Typing the word \"set\" will let you set the secret word");
      }
      else if (guess.equals("exit")){
        System.out.println("Goodbye :(");
        donegame = true;
      }
      else if (guess.equals("set")){
        boolean secretalpha;
        boolean secretlength;
        String uncheckedsecretWord;
        do {
          System.out.println("What do you want the secret word to be?");
          uncheckedsecretWord = scanner.nextLine();
          secretalpha = checkAllAlpha(uncheckedsecretWord);
          secretlength = checkWordLength(uncheckedsecretWord);
        }
        while (!secretlength||!secretalpha);
        SecretWord = uncheckedsecretWord.toLowerCase();
      }

      //double check fail safe
      else if (alpha&&length){
        numGuesses++;
        System.out.println("Attempt: " + numGuesses);
        if (SecretWord.equals(guess)){
          guessarray = CreateGuessArray(row,guess,guessarray);
          row++;
          guessarray = checkSameLetter(guess, SecretWord, guessarray, row);
          row++;
          DisplayArray(guessarray, row);
          System.out.println("Congrats you won");
          System.out.println("The ammount of attmepts you had left were "+ (MAXGUESSES - numGuesses));
          donegame = true;
        }
        else if(numGuesses > MAXGUESSES){
          System.out.println("Game over");
          donegame = true;
        }
        else if (numGuesses < MAXGUESSES){
          guessarray = CreateGuessArray(row,guess,guessarray);
          row++;
          guessarray = checkSameLetter(guess, SecretWord, guessarray, row);
          row++;
          DisplayArray(guessarray, row);
          
        }
      }
    }
  }
/*Checks if the inputted word is all alphabetical
*had to make alpha because if the 6th input was a letter than it would return true even if the other 5 were not letters
*
*/
    static Boolean checkAllAlpha(String guess) {
      boolean alpha = true;
      boolean temp;
      for (int i = 0;i < guess.length(); i++){
        temp = Character.isLetter(guess.charAt(i));
        if (temp == false){
          System.out.println(guess.charAt(i) +" is not a letter");
          alpha = false;
        }
      }
    return alpha; 
    }
/*Checks if the inputted word is only 6 characters long
 * 
 * 
 * 
 */
  static boolean checkWordLength(String guess){
    if (guess.equals("exit")||guess.equals("help")||guess.equals("set")){
      return true;
    }
    else if (guess.length() == 6){
      return true;
    }
    else{
      System.out.println("The input must be 6 letters long");
      return false;
    }
  }
  //This method only creates the part of the array for the input (guess)
  static char[][] CreateGuessArray(int row, String guess,char[][]guessarray){
    guessarray[row][0] = '|';
    for (int i = 0,col = 1;col < 12;col++){
        //col is odd then add character from guess
        if (col % 2 != 0) {
            guessarray[row][col] = guess.charAt(i);
            i++;
        } 
        //if col is even then add a -
        else{
            guessarray[row][col] = '-';
        }
    }
    guessarray[row][12] = '|';
    return guessarray;
  }
//least favorite method
  static char[][] checkSameLetter(String guess,String SecretWord,char[][] guessarray, int row){        
        Map<Character, Integer> SecretDictionary = new HashMap<>();
        for (char c : SecretWord.toCharArray()){
          SecretDictionary.put(c, SecretDictionary.getOrDefault(c, 0) + 1);
        }
        //This is for if they are the same letter and in the same spot
        for (int i = 0; i < 6;i++){
          if (guess.charAt(i) == SecretWord.charAt(i)){
            SecretDictionary.put(SecretWord.charAt(i), SecretDictionary.get(SecretWord.charAt(i)) - 1);
            System.out.println("W");
          }
        }
        //This is for if they have the same letter but its in a different spot
        for (int i = 0; i < 6;i++){
          if ((SecretWord.contains(String.valueOf(guess.charAt(i))))&&(SecretDictionary.get(guess.charAt(i)))!=0){
            System.out.println(SecretDictionary.get(guess.charAt(i)));
            System.out.println("!");
          }
          //this is for if its not the same letter in the same spot or more letters than what secretdictionary has
          else {
            System.out.println("x");
          }
        }
  return guessarray;
  }
  

  static void DisplayArray(char[][] guessarray, int MaxRow){
    //display array
    for (int row = 0;row < MaxRow;row++){
      for (int col = 0;col < 13;col++){
      System.out.print(guessarray[row][col]); 
      }
      System.out.println();
    }
  }
}
