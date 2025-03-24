
package wars;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
/**
 *
 * @author hinal
 */
public class Fleet implements Serializable {
    private static final long serialVersionUID = 1L;

    
    private Map<String, Ship> reserve = new HashMap<>();
    private Map<String, Ship> squadron = new HashMap<>();
    private Map<String, Ship> sunk = new HashMap<>();

    public void addToReserve(Ship ship) {
        reserve.put(ship.getName(), ship);
    }

    public boolean commissionShip(String name, double warChest) {
        Ship ship = reserve.get(name);
        if (ship != null && ship.getCommissionCost() <= warChest) {
            reserve.remove(name);
            ship.setState(ShipState.ACTIVE);
            squadron.put(name, ship);
            return true;
        }
        return false;
    }

    public boolean decommissionShip(String name) {
        Ship ship = squadron.get(name);
        if (ship != null && ship.getState() != ShipState.SUNK) {
            squadron.remove(name);
            reserve.put(name, ship);
            ship.setState(ShipState.RESERVE);
            return true;
        }
        return false;
    }
    
       public String listAllShips() {
         StringBuilder sb = new StringBuilder();
        sb.append("Reserve Fleet:\n");
        for (Ship ship : reserve.values()) {
            sb.append(ship).append("\n");
        }
        sb.append("Squadron:\n");
        for (Ship ship : squadron.values()) {
            sb.append(ship).append("\n");
        }
        sb.append("Sunk Ships:\n");
        for (Ship ship : sunk.values()) {
            sb.append(ship).append("\n");
        }
        return sb.toString();
    }
    

    public Ship getShip(String name) {
        if (reserve.containsKey(name)) return reserve.get(name);
        if (squadron.containsKey(name)) return squadron.get(name);
        if (sunk.containsKey(name)) return sunk.get(name);
        return null;
    }


    public String listReserveFleet() {
        StringBuilder sb = new StringBuilder("Reserve Fleet:\n");
        for (Ship ship : reserve.values()) {
            sb.append(ship).append("\n");
        }
        return sb.toString();
    }

    public String listSquadron() {
        StringBuilder sb = new StringBuilder("Squadron:\n");
        for (Ship ship : squadron.values()) {
            sb.append(ship).append("\n");
        }
        return sb.toString();
    }
    
    public String listSunkShips() {
    if (sunk.isEmpty()) {
        return "No ships sunk yet.";
    }

    StringBuilder sb = new StringBuilder("Sunk Ships:\n");
    for (Ship ship : sunk.values()) {
        sb.append(ship.toString()).append("\n");
    }
    return sb.toString();
}
    public int getActiveShipsCount() {
    int count = 0;
    for (Ship ship : squadron.values()) {
        if (ship.getState() == ShipState.ACTIVE) {
            count++;
        }
    }
    return count;
}

    public boolean isInSquadron(String name) {
    return squadron.containsKey(name);
}
    public void restoreShip(String name) {
    Ship ship = squadron.get(name);
    if (ship != null && ship.getState() == ShipState.RESTING) {
        ship.setState(ShipState.ACTIVE);
    }
}
public Ship getShipForEncounter(Encounter encounter) {
    for (Ship ship : squadron.values()) {
        if (ship.getState() == ShipState.ACTIVE && ship.canFight(encounter.getType())) {
            return ship;
        }
    }
    return null; // No suitable ship available
}
public void moveToSunk(String name) {
    Ship ship = squadron.get(name);
    if (ship != null) {
        ship.setState(ShipState.SUNK);
        squadron.remove(name);
        sunk.put(name, ship);
    }
}

} 
