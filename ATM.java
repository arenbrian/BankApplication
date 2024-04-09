import java.awt.*;
import java.util.Scanner;
public class ATM
{
   public static void main(String[] args)
   {
       Scanner input = new Scanner(System.in);

       Bank theBank = new Bank("Bank of Florida");

       User aUser = theBank.addUser("Mary", "Poppins", "2222");

       Account newAccount = new Account("Checking", aUser, theBank);
       aUser.addAccount(newAccount);
       theBank.addAccount(newAccount);

       User curUser;
       while (true)
       {
           curUser = ATM.mainMenuPrompt(theBank, input);

           ATM.printUserMenu(curUser, input);
       }
   }

   public static User mainMenuPrompt(Bank theBank, Scanner input)
   {
       String userID;
       String pin;
       User authUser;

       do {

           System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
           System.out.print("Enter user ID: ");
           userID = input.nextLine();
           System.out.print("Enter pin: ");
           pin = input.nextLine();

           authUser = theBank.userLogin(userID, pin);
           if (authUser == null) {
               System.out.println("Incorrect user ID or pin. " +
                       "Please try again.");
           }

       } while (authUser == null) ;
       return authUser;


   }

   public static void printUserMenu(User theUser, Scanner input)
   {
       theUser.printAccountsSummary();

       int choice;

       do{
           System.out.printf("Welcome %s, what would you like to do?\n ",
                   theUser.getFirstName());
           System.out.println(" 1) Show account transaction history");
           System.out.println("  2) Withdraw");
           System.out.println("  3) Deposit");
           System.out.println("  4) Transfer");
           System.out.println("  5) Quit");
           System.out.println();
           System.out.println("Enter choice: ");
           choice = input.nextInt();

           if (choice < 1 || choice > 5)
           {
               System.out.println("Invalid choice. Try again");
           }

       } while(choice < 1 || choice > 5);

       switch (choice)
       {
           case 1:
               ATM.showTransHistory(theUser, input);
               break;
           case 2:
               ATM.withdrawFunds(theUser, input);
               break;
           case 3:
               ATM.depositFunds(theUser, input);
               break;
           case 4:
               ATM.transferFunds(theUser, input);
               break;
       }
       if (choice != 5)
       {
           ATM.printUserMenu(theUser, input);
       }
   }

   public static void showTransHistory(User theUser, Scanner input)
   {
       int theAcct;

       do
       {
           System.out.printf("Enter the number (1-%d) of the account" +
                           "whose transactions you want to see: ",
                   theUser.numAccounts());
           theAcct = input.nextInt()-1;
           if (theAcct < 0 || theAcct >= theUser.numAccounts())
           {
               System.out.println("Invalid account. Try again.");
           }
       } while(theAcct < 0 || theAcct >= theUser.numAccounts());

       theUser.printAcctTransHistory(theAcct);

   }

   public static void transferFunds(User theUser, Scanner input)
   {
       int fromAcct;
       int toAcct;
       double amount;
       double acctBalance;

       do
       {
           System.out.printf("Enter the number (1-%d) of the account\n" +
                   "to transfer from: ", theUser.numAccounts());
           fromAcct = input.nextInt()-1;
           if (fromAcct < 0 || fromAcct >= theUser.numAccounts())
           {
               System.out.println("Invalid account. Try again.");
           }
       } while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBalance = theUser.getAcctBalance(fromAcct);

       do
       {
           System.out.printf("Enter the number (1-%d) of the account\n" +
                   "to transfer to: ",theUser.numAccounts());
           toAcct = input.nextInt()-1;
           if (toAcct < 0 || toAcct >= theUser.numAccounts())
           {
               System.out.println("Invalid account. Try again.");
           }
       } while(toAcct < 0 || toAcct >= theUser.numAccounts());

       do
       {
           System.out.printf("Enter the amount to (Max $%.02f): $",
                   acctBalance);
           amount = input.nextDouble();
           if (amount < 0)
           {
              System.out.println("Amount must be greater than 0");
           }
           else if (amount > acctBalance)
           {

               System.out.printf("Amount must not be greater than\n" +
                       "balance of $%.02f.\n", acctBalance);
           }
       } while(amount < 0 || amount > acctBalance);

       theUser.addAcctTransaction(fromAcct, -1 * amount, String.format(
               "Transfer to account %s", theUser.getAcctUUID(toAcct)));
       theUser.addAcctTransaction(toAcct, amount, String.format(
               "Transfer to account %s", theUser.getAcctUUID(fromAcct)));
   }

   public static void withdrawFunds(User theUser, Scanner input)
   {
       int fromAcct;
       double amount;
       double acctBalance;
       String memo;

       do
       {
           System.out.printf("Enter the number (1-%d) of the account\n" +
                   "to withdraw from: ", theUser.numAccounts());
           fromAcct = input.nextInt()-1;
           if (fromAcct < 0 || fromAcct >= theUser.numAccounts())
           {
               System.out.println("Invalid account. Try again.");
           }
       } while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
       acctBalance = theUser.getAcctBalance(fromAcct);

       do
       {
           System.out.printf("Enter the amount to withdraw (max $%.02f): $",
                   acctBalance);
           amount = input.nextDouble();
           if (amount < 0)
           {
               System.out.println("Amount must be greater than 0");
           }
           else if (amount > acctBalance)
           {

               System.out.printf("Amount must not be greater than\n" +
                       "balance of $%.02f.\n", acctBalance);
           }
       } while(amount < 0 || amount > acctBalance);

       input.nextLine();

       System.out.print("Enter a memo: ");
       memo = input.nextLine();

       theUser.addAcctTransaction(fromAcct, -1 * amount, memo);

   }

   public static void depositFunds(User theUser, Scanner input)
   {
       int toAcct;
       double amount;
       double acctBalance;
       String memo;

       do
       {
           System.out.printf("Enter the number (1-%d) of the account\n" +
                   "to deposit in: ", theUser.numAccounts());
           toAcct = input.nextInt()-1;
           if (toAcct < 0 || toAcct >= theUser.numAccounts())
           {
               System.out.println("Invalid account. Try again.");
           }
       } while(toAcct < 0 || toAcct >= theUser.numAccounts());
       acctBalance = theUser.getAcctBalance(toAcct);

       do
       {
           System.out.printf("Enter the amount to deposit (max $%.02f): $",
                   acctBalance);
           amount = input.nextDouble();
           if (amount < 0)
           {
               System.out.println("Amount must be greater than 0");
           }
       } while(amount < 0);

       input.nextLine();

       System.out.print("Enter a memo: ");
       memo = input.nextLine();

       theUser.addAcctTransaction(toAcct, amount, memo);
   }
}
