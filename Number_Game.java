import java.util.Scanner;

class Number_Game {
    public static void main(String[] args) {
        int number = (int) (Math.random() * 100) + 1;

        Scanner sc = new Scanner(System.in);
        int newNumber;

        do {
            System.out.println("Enter Your Guess: ");
            newNumber = sc.nextInt();

            if (newNumber > number) {
                System.out.println("Too much greater! Please try with a small one...");
            } else if (newNumber < number) {
                System.out.println("Too much small ! Try Greater One!");
            } else {
                System.out.println("Congratulations ! Guess Matched");
                break;
            }

        } while ((newNumber != number));
        sc.close();
    }
}