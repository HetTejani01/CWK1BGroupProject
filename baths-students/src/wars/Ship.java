

package wars;
import java.io.Serializable;
/**
 *
 * @author hinal
 */
public  abstract class Ship implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String captain;
    private int skill;
    private int commissionCost;
    private ShipState state;
  

    public Ship(String name, String captain, int skill, int commissionCost) {
        this.name = name;
        this.captain = captain;
        this.skill = skill;
        this.commissionCost = commissionCost;
        this.state = ShipState.RESERVE;
    }

    // Getters
    public String getName() { return name; }
    public String getCaptain() { return captain; }
    public int getSkill() { return skill; }
    public int getCommissionCost() { return commissionCost; }
    public ShipState getState() { return state; }
    
    // Setters
    public void setState(ShipState state) { this.state = state; }

     public abstract boolean canFight(EncounterType type);
    @Override
    public String toString() {
        return name + " (" + captain + ") - Skill: " + skill + ", State: " + state;
    }
} 
