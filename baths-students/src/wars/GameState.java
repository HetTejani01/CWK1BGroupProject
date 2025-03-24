
package wars;
import java.io.Serializable;
/**
 *
 * @author hinal
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    
    
    private String admiral;
    private double warChest;
    private boolean defeated;

    public GameState(String admiral) {
        this.admiral = admiral;
        this.warChest = 1000;
        this.defeated = false;
    }

    public void updateWarChest(double amount) {
        warChest += amount;
    }

    public double getWarChest() {
        return warChest;
    }

    public boolean isDefeated() {
        return warChest <= 0;
    }

    @Override
    public String toString() {
        return "Admiral: " + admiral + ", War Chest: " + warChest + ", Defeated: " + defeated;
    }
}

