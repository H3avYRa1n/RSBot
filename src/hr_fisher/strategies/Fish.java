package hr_fisher.strategies;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.locations.ShiloVillage;
import hr_fisher.user.Condition;
import hr_fisher.user.Util;
import hr_fisher.user.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.methods.widget.DepositBox;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.LocalPath;

public class Fish extends Node {
    @Override
    public void execute() {
        NPC closestFishingSpot = Util.getClosestFishingSpot();

        if (closestFishingSpot != null && Calculations.distanceTo(closestFishingSpot.getLocation()) < 20) {
            if (closestFishingSpot.isOnScreen()) {
                closestFishingSpot.interact(Variables.chosenFishingType.getInteractString());
                Task.sleep(1000);
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
    public boolean activate() {
        if (DepositBox.isOpen())
            return false;
        //System.out.println(closestFishingSpot == null);
        return Variables.hasStarted && !Inventory.isFull() && Util.hasNeededItems()
                && Players.getLocal().getAnimation() == -1;

    }
}
