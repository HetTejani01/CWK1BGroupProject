
package wars;

/**
 *
 * @author hinal
 */
public class Frigate  extends Ship {

    private int cannons;
    private boolean hasPinnace;

    public Frigate(String name, String captain, int skill, int commissionCost, int cannons, boolean hasPinnace) {
        super(name, captain, skill, commissionCost);
        this.cannons = cannons;
        this.hasPinnace = hasPinnace;
    }

    @Override
    public boolean canFight(EncounterType type) {
        if (type == EncounterType.BLOCKADE) {
            return hasPinnace;
        }
        return (type == EncounterType.BATTLE || type == EncounterType.SKIRMISH);
    }
    public String toString() {
        return "Frigate: " + super.toString() + " - Cannons: " + cannons + ", Has Pinnace: " + hasPinnace;
    }
}
