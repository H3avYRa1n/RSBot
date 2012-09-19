package hr_fisher.strategies;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.locations.LivingRockCaverns;
import hr_fisher.locations.ShiloVillage;
import hr_fisher.user.Variables;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.methods.widget.DepositBox;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.LocalPath;
import hr_fisher.user.Util;
import hr_fisher.user.Variables;

public class Fish extends Strategy implements Runnable {
    @Override
    public void run() {
        NPC closestFishingSpot = null;
        if(Variables.chosenLocation instanceof ShiloVillage)
            closestFishingSpot = NPCs.getNearest(new Filter<NPC>() {
                public boolean accept(NPC npc) {
                    String[] actions = npc.getActions();
                    for (String s : actions) {
                        if (s != null && s.contains(Variables.chosenFishingType.getInteractString())) {
                            return ShiloVillage.SOUTHERN_AREA.contains(npc.getLocation());
                        }
                    }

                    return false;
                }
            });
        else closestFishingSpot = Util.getClosestFishingSpot();

        if (closestFishingSpot != null && Calculations.distanceTo(closestFishingSpot.getLocation()) < 20) {
            if (closestFishingSpot.isOnScreen()) {
                closestFishingSpot.interact(Variables.chosenFishingType.getInteractString());
                Time.sleep(1000);
                Util.waitFor(4000, new Condition() {
                    @Override
                    public boolean validate() {
                        return Players.getLocal().getAnimation() != -1;
                    }
                });
            } else {
                Camera.turnTo(closestFishingSpot);
                LocalPath path = Walking.findPath(closestFishingSpot);

                if (path != null) {
                    if (path.traverse()) {
                        Util.waitFor(3000, new Condition() {
                            @Override
                            public boolean validate() {
                                return !Players.getLocal().isMoving() || Calculations.distanceTo(Walking.getDestination()) < 5;
                            }
                        });
                    }
                }
            }
        }
    }

    @Override
    public boolean validate() {
        if(DepositBox.isOpen())
            return false;
        //System.out.println(closestFishingSpot == null);
        return Variables.hasStarted && !Inventory.isFull() && Util.hasNeededItems()
                && Players.getLocal().getAnimation() == -1;

    }
}
