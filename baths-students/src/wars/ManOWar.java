
package wars;

/**
 *
 * @author hinal
 */
public class ManOWar extends Ship {

    private int decks;
    private int marines;

    public ManOWar(String name, String captain, int skill, int commissionCost, int decks, int marines) {
        super(name, captain, skill, commissionCost);
        this.decks = decks;
        this.marines = marines;
    }

    @Override
    public boolean canFight(EncounterType type) {
        return (type == EncounterType.BLOCKADE || type == EncounterType.BATTLE);
    }
     public String toString() {
        return "Man-O-War: " + super.toString() + " - Decks: " + decks + ", Marines: " + marines;
    }
}
