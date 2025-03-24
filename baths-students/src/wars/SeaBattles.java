package wars;

import java.util.*;
import java.io.*;
/**
 * This class implements the behaviour expected from the BATHS
 system as required for 5COM2007 Cwk1B BATHS - Feb 2025
 * 
 * @author A.A.Marczyk 
 * @version 16/02/25
 */

public class SeaBattles implements BATHS, Serializable {
    private static final long serialVersionUID = 1L;

    // may have one HashMap and select on stat
private Fleet fleet = new Fleet();              // Fleet to manage ships
    private GameState gameState;                    // Game state tracking
    private Map<Integer, Encounter> encounters = new HashMap<>(); // Encounters
    private String admiral;
    private double warChest;


//**************** BATHS ************************** 
    /** Constructor requires the name of the admiral
     * @param adm the name of the admiral
     */  
    public SeaBattles(String adm)
    {
      
        gameState = new GameState(adm);
       setupShips();
       setupEncounters();
    }
    
    /** Constructor requires the name of the admiral and the
     * name of the file storing encounters
     * @param admir the name of the admiral
     * @param filename name of file storing encounters
     */  
    public SeaBattles(String admir, String filename)  //Task 3
    {
      
        gameState = new GameState(admir);
       setupShips();
       // setupEncounters();
       // uncomment for testing Task 
         readEncounters(filename);
    }
    
    
    /**Returns a String representation of the state of the game,including the name of the 
     * admiral, state of the warChest,whether defeated or not, and the ships currently in 
     * the squadron,(or, "No ships" if squadron is empty), ships in the reserve fleet
     * @return a String representation of the state of the game,including the name of the 
     * admiral, state of the warChest,whether defeated or not, and the ships currently in 
     * the squadron,(or, "No ships" if squadron is empty), ships in the reserve fleet
     **/
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(gameState.toString()).append("\n");
        sb.append("Squadron:\n").append(getSquadron()).append("\n");
        sb.append("Reserve Fleet:\n").append(getReserveFleet()).append("\n");
        sb.append("Sunk Ships:\n").append(getSunkShips());
        return sb.toString();
    }
    
    
    /** returns true if War Chest <=0 and the admiral's squadron has no ships which 
     * can be retired. 
     * @returns true if War Chest <=0 and the admiral's fleet has no ships 
     * which can be retired. 
     */
    public boolean isDefeated()
    {
        return gameState.isDefeated() && fleet.getActiveShipsCount() == 0;
    }
    
    /** returns the amount of money in the War Chest
     * @returns the amount of money in the War Chest
     */
    public double getWarChest()
    {
        return  gameState.getWarChest();
    }
    
    
    /**Returns a String representation of all ships in the reserve fleet
     * @return a String representation of all ships in the reserve fleet
     **/
    public String getReserveFleet()
    {   //assumes reserves is a Hashmap
       
        return fleet.listReserveFleet();
    }
    
    /**Returns a String representation of the ships in the admiral's squadron
     * or the message "No ships commissioned"
     * @return a String representation of the ships in the admiral's fleet
     **/
    public String getSquadron()
    {
   
        
        return fleet.listSquadron();
    }
    
    /**Returns a String representation of the ships sunk (or "no ships sunk yet")
     * @return a String representation of the ships sunk
     **/
    public String getSunkShips()
    {
       
        return fleet.listSunkShips();
    }
    
    /**Returns a String representation of the all ships in the game
     * including their status
     * @return a String representation of the ships in the game
     **/
    public String getAllShips()
    {
  
        
        return fleet.listAllShips();
    }
    
    
    /** Returns details of any ship with the given name
     * @return details of any ship with the given name
     **/
    public String getShipDetails(String nme)
    {
       Ship ship = fleet.getShip(nme);
        
        
        return (ship != null) ? ship.toString() : "No such ship";
    }     
 
    // ***************** Fleet Ships ************************   
    /** Allows a ship to be commissioned to the admiral's squadron, if there 
     * is enough money in the War Chest for the commission fee.The ship's 
     * state is set to "active"
     * @param nme represents the name of the ship
     * @return "Ship commissioned" if ship is commissioned, "Not found" if 
     * ship not found, "Not available" if ship is not in the reserve fleet, "Not 
     * enough money" if not enough money in the warChest
     **/        
    public String commissionShip(String nme)
    {
        
        if (fleet.isInSquadron(nme)) {
        return "Not available"; // Ship is already in squadron
    }

    Ship ship = fleet.getShip(nme);
    if (ship == null) return "Not found"; // Ship does not exist
    
    if (ship.getCommissionCost() <= gameState.getWarChest()) {
        boolean success = fleet.commissionShip(nme, gameState.getWarChest());
        if (success) {
            gameState.updateWarChest(-ship.getCommissionCost());
            return "Ship commissioned";
        } else {
            return "Not available";
        }
    } else {
        return "Not enough money";
    }
}
    /** Returns true if the ship with the name is in the admiral's squadron, false otherwise.
     * @param nme is the name of the ship
     * @return returns true if the ship with the name is in the admiral's squadron, false otherwise.
     **/
    public boolean isInSquadron(String nme)
    {
         return fleet.isInSquadron(nme);
    }
    
    /** Decommissions a ship from the squadron to the reserve fleet (if they are in the squadron)
     * pre-condition: isInSquadron(nme)
     * @param nme is the name of the ship
     * @return true if ship decommissioned, else false
     **/
    public boolean decommissionShip(String nme)
    {
        if (fleet.decommissionShip(nme)) {
            Ship ship = fleet.getShip(nme);
            gameState.updateWarChest(ship.getCommissionCost() / 2); // Return half commission fee
            return true;
        }
        return false;
    }
    
    
  
    /**Restores a ship to the squadron by setting their state to ACTIVE 
     * @param ref the name of the ship to be restored
     */
    public void restoreShip(String nme)
    {
  fleet.restoreShip(nme);
        
    }
    
//**********************Encounters************************* 
    /** returns true if the number represents a encounter
     * @param num is the reference number of the encounter
     * @returns true if the reference number represents a encounter, else false
     **/
     public boolean isEncounter(int num)
     {
         return encounters.containsKey(num);
     }
     
     
/** Retrieves the encounter represented by the encounter 
      * number.Finds a ship from the fleet which can fight the 
      * encounter.The results of fighting an encounter will be 
      * one of the following: 
      * 0-Encounter won by...(ship reference and name)-add prize money to War 
      * Chest and set ship's state to RESTING,  
      * 1-Encounter lost as no ship available - deduct prize from the War Chest,
      * 2-Encounter lost on battle skill and (ship name) sunk" - deduct prize 
      * from War Chest and set ship state to SUNK.
      * If an encounter is lost and admiral is completely defeated because there 
      * are no ships to decommission,add "You have been defeated " to message, 
      * -1 No such encounter
      * Ensure that the state of the war chest is also included in the return message.
      * @param encNo is the number of the encounter
      * @return a String showing the result of fighting the encounter
      */ 
    public String fightEncounter(int encNo)
    {
       
            
         Encounter encounter = encounters.get(encNo);
    if (encounter == null) {
        return "-1 No such encounter";
    }

    Ship ship = fleet.getShipForEncounter(encounter);
    if (ship == null) {
        gameState.updateWarChest(-encounter.getPrizeMoney());
        return "Encounter lost as no ship available.";
    }

    if (ship.getSkill() >= encounter.getSkillRequired()) {
        gameState.updateWarChest(encounter.getPrizeMoney());
        ship.setState(ShipState.RESTING);
        return "Encounter won by " + ship.getName();
    } else {
        gameState.updateWarChest(-encounter.getPrizeMoney());
        ship.setState(ShipState.SUNK);
        fleet.moveToSunk(ship.getName());
        
        if (isDefeated()) {
            return "You have been defeated.";
        }
        return "Encounter lost on skill level and " + ship.getName() + " sunk.";
    }
}

    /** Provides a String representation of an encounter given by 
     * the encounter number
     * @param num the number of the encounter
     * @return returns a String representation of a encounter given by 
     * the encounter number
     **/
    public String getEncounter(int num)
    {
         Encounter encounter = encounters.get(num);
        return (encounter != null) ? encounter.toString() : "No such encounter";
    }
    
    /** Provides a String representation of all encounters 
     * @return returns a String representation of all encounters
     **/
    public String getAllEncounters()
    {
  StringBuilder sb = new StringBuilder();
        for (Encounter encounter : encounters.values()) {
            sb.append(encounter.toString()).append("\n");
        }
        return sb.toString();
    }
    

    //****************** private methods for Task 4 functionality*******************
    //*******************************************************************************
     private void setupShips()
     {
       
  fleet.addToReserve(new ManOWar("Victory", "Alan Aikin", 3, 500, 3, 30));
        fleet.addToReserve(new Frigate("Sophie", "Ben Baggins", 8, 160, 16, true));
        fleet.addToReserve(new Sloop("Arrow", "Dan Dare", 5, 150, true));
    }
    
     
    private void setupEncounters()
    {
   encounters.put(1, new Encounter(1, EncounterType.BATTLE, "Trafalgar", 3, 300));
        encounters.put(2, new Encounter(2, EncounterType.SKIRMISH, "Belle Isle", 3, 120));
        encounters.put(3, new Encounter(3, EncounterType.BLOCKADE, "Brest", 3, 150));
    }
        
    // Useful private methods to "get" objects from collections/maps

    //*******************************************************************************
    //*******************************************************************************
  
    /************************ Task 3 ************************************************/

    
    //******************************** Task 3.5 **********************************
    /** reads data about encounters from a text file and stores in collection of 
     * encounters.Data in the file is editable
     * @param filename name of the file to be read
     */
    public void readEncounters(String filename)
    { 
      try (Scanner sc = new Scanner(new File(filename))) {
        while (sc.hasNext()) {
            int number = sc.nextInt();
            String type = sc.next();
            String location = sc.next();
            int skillRequired = sc.nextInt();
            int prizeMoney = sc.nextInt();

            EncounterType encounterType = EncounterType.valueOf(type.toUpperCase());
            encounters.put(number, new Encounter(number, encounterType, location, skillRequired, prizeMoney));
        }
        System.out.println("Encounters loaded successfully from " + filename);
    } catch (Exception e) {
        System.out.println("Failed to read encounters file: " + e.getMessage());
    }
    }
         
 
    
    // ***************   file write/read  *********************
    /** Writes whole game to the specified file
     * @param fname name of file storing requests
     */
    public void saveGame(String fname)
    {   try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fname))) {
        out.writeObject(this);
        System.out.println("Game saved successfully to " + fname);
    } catch (IOException e) {
        System.out.println("Error saving game: " + e.getMessage());
    }
    }
    
    /** reads all information about the game from the specified file 
     * and returns 
     * @param fname name of file storing the game
     * @return the game (as an SeaBattles object)
     */
    public SeaBattles loadGame(String fname)
    {  try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fname))) {
        return (SeaBattles) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error loading game: " + e.getMessage());
        return null;
    }
       
      
    }
    
 
}



