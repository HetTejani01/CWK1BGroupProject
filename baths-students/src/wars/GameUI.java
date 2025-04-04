package wars;

import java.io.*;
import java.util.*;
/**
 * Task 2 - provide command line interface
 * 
 * @author A.A.Marczyk
 * @version 16/02/25
 */
public class GameUI
{
    private BATHS myBattles ;
    private Scanner myIn = new Scanner(System.in);



    public void doMain()
    {
        int choice;
        System.out.println("Enter admiral's name");
        String name = myIn.nextLine();
        myBattles = new SeaBattles(name); // create
        
        choice = 100;
        while (choice != 0 )
        {
            choice = getMenuItem();
            if (choice == 1)
            {
                System.out.println(myBattles.getReserveFleet());
            }
            else if (choice == 2)
            {
                System.out.println(myBattles.getSquadron());
            }
            else if (choice == 3)
            {
                System.out.println("Enter Ship name");
                myIn.nextLine();
                String ref = (myIn.nextLine()).trim();
                System.out.println(myBattles.getShipDetails(ref));
            } 
            else if (choice == 4)
            {
                System.out.println("Enter the ship name to commission:");
                myIn.nextLine(); // Clear the buffer
                String shipName = myIn.nextLine().trim();
                String result = myBattles.commissionShip(shipName);
                System.out.println(result);

            }
            else if (choice == 5)
            {
       	        System.out.println("Enter encounter number:");
                int encounterNumber = myIn.nextInt();
                myIn.nextLine(); // Clear the buffer
                String result = myBattles.fightEncounter(encounterNumber);
                System.out.println(result);
       
                  
            }
            else if (choice ==6)
            {
	        System.out.println("Enter the ship name to restore:");
                myIn.nextLine(); // Clear the buffer
                String shipName = myIn.nextLine().trim();
                myBattles.restoreShip(shipName);
                System.out.println(shipName + " has been restored.");


            }
            else if (choice == 7)
            {
               System.out.println("Enter the ship name to decommission:");
                myIn.nextLine(); // Clear the buffer
                String shipName = myIn.nextLine().trim();
                boolean success = myBattles.decommissionShip(shipName);
                if (success) {
                    System.out.println(shipName + " has been decommissioned.");
                } else {
                    System.out.println("Failed to decommission " + shipName);
                }


            }
            else if (choice==8)
            {
                System.out.println(myBattles.toString());
            }
            
               else if (choice == 9) // Task 7 - Save game
            {
                System.out.println("Enter filename to save:");
    myIn.nextLine();
    String filename = myIn.nextLine();
    myBattles.saveGame(filename);
}
            else if (choice == 10) // Task 7 - Load game
            { System.out.println("Enter filename to load:");
    myIn.nextLine();
    String filename = myIn.nextLine();
    SeaBattles restoredGame = myBattles.loadGame(filename);
    if (restoredGame != null) {
        myBattles = restoredGame;
        System.out.println("Game restored:");
        System.out.println(myBattles.toString());
    } else {
        System.out.println("Failed to restore game.");
    }
            }

   
        }
        System.out.println("Thank-you");
    }
    
    private int getMenuItem()
    {   int choice = 100;  
        System.out.println("Main Menu");
        System.out.println("0. Quit");
        System.out.println("1. List ships in the reserve fleet");
        System.out.println("2. List ships in admirals squadron"); 
        System.out.println("3. View a ship");
        System.out.println("4. Commission a ship into admiral's squadron");
        System.out.println("5. Fight an encounter");
        System.out.println("6. Restore a ship");
        System.out.println("7. Decommission a ship");
        System.out.println("8. View admiral's state");
        System.out.println("9. Save this game");
        System.out.println("10. Restore a game");
       
        
        while (choice < 0 || choice  > 10)
        {
            System.out.println("Enter the number of your choice");
            choice =  myIn.nextInt();
        }
        return choice;        
    } 
    
    public static void main(String[] args)
    {
        GameUI xxx = new GameUI();
        xxx.doMain();
    }
}
