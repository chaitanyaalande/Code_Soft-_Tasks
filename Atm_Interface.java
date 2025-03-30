import java.util.Scanner;

class User {
    String name;
    long accNo;
    static double balance;

    static void depositAmount(double amount) {
        if (amount > 0) {
            balance = balance + amount;
            System.out.println("Amount Deposited Succesfully !");
        } else {
            System.out.println("Enter Sufficient Amount To Deposit");
        }
    }

    static void withdrawAmount(double amount) {
        if (balance < amount) {
            System.out.println("Insufficient Bank Balance");
        } else {
            balance = balance - amount;
            System.out.println("Amount Withdrawn Successfully !");
        }
    }

    static double checkBalance() {
        return balance;
    }

}

public class Atm_Interface extends User {
    public static void main(String[] args) {
        int choice = 0;
        double amount = 0.0;
        Scanner sc = new Scanner(System.in);
        do {

            System.out.println("***** ATM MACHINE *****");
            System.out.println();
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Check Bank Balance");
            System.out.println("0. Exit");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter The Amount To Deposit");
                    amount = sc.nextDouble();
                    depositAmount(amount);
                    break;

                case 2:
                    System.out.println("Enter The Amount To Withdraw");
                    amount = sc.nextDouble();
                    withdrawAmount(amount);
                    break;

                case 3:
                    System.out.println("Your Bank Balance Is: Rs " + checkBalance());
                    break;

                default:
                    if (choice != 0)
                        System.out.println("Please Enter A Valid Choice");
            }

        } while (choice != 0);
            sc.close();
            System.out.println("Thank you for using the ATM!");    
        }
    }

