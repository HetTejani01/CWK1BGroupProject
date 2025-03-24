
package wars;

/**
 *
 * @author hinal
 */
public class Encounter {
   private int number;
    private EncounterType type;
    private String location;
    private int skillRequired;
    private int prizeMoney;

    public Encounter(int number, EncounterType type, String location, int skillRequired, int prizeMoney) {
        this.number = number;
        this.type = type;
        this.location = location;
        this.skillRequired = skillRequired;
        this.prizeMoney = prizeMoney;
    }

    // Getters
    public int getNumber() { return number; }
    public EncounterType getType() { return type; }
    public String getLocation() { return location; }
    public int getSkillRequired() { return skillRequired; }
    public int getPrizeMoney() { return prizeMoney; }

    @Override
    public String toString() {
        return "Encounter " + number + " (" + type + ") at " + location +
               ", Skill Required: " + skillRequired + ", Prize: " + prizeMoney;
    }
} 

