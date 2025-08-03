import java.util.Scanner;
import java.lang.Math;
public class GuessNumber
{
    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        int randomNum = (int) (Math.random() * 100);
        int userChoice;
        do
        {
            System.out.println("GUESS THE NUMBER (between 0 and 99)");
            userChoice = sc.nextInt();
            if (randomNum == userChoice) {
                System.out.println("----- WOOHOOOOO! -----");
                System.out.println("YOU GUESSED IT CORRECT");
                break;
            } else if (userChoice > randomNum)
                System.out.println("OOHH NO...! Number entered large");
            else if (userChoice < randomNum)
                System.out.println("OOHH NO...! Number entered small");
        }while (userChoice >=0);
    }
}
