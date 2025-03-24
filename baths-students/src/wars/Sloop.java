
package wars;

/**
 *
 * @author hinal
 */
public class Sloop  extends Ship {

    private boolean hasDoctor;

    public Sloop(String name, String captain, int skill, int commissionCost, boolean hasDoctor) {
        super(name, captain, skill, commissionCost);
        this.hasDoctor = hasDoctor;
    }

    @Override
    public boolean canFight(EncounterType type) {
        return (type == EncounterType.BATTLE || type == EncounterType.SKIRMISH);
    }
     public String toString() {
        return "Sloop: " + super.toString() + " - Has Doctor: " + hasDoctor;
    }
}
