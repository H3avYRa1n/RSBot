package hr_fisher.banking;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.locations.Lumbridge;
import hr_fisher.user.Condition;
import hr_fisher.user.Util;
import hr_fisher.user.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.map.TilePath;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class LumbridgeBank implements BankingMethod {

    @Override
    public boolean shouldBank() {
        return Variables.hasStarted && (Inventory.isFull() || !Util.hasNeededItems());
    }

    @Override
    public void bank() {
        SceneObject ladder = SceneEntities.getNearest(Lumbridge.TOP_LADDER_ID);

        if (ladder != null) {
            TilePath path = new TilePath(Lumbridge.TILES_LADDER_TO_BANK);

            if (path != null) {
                if (path.traverse()) {
                    Util.waitFor(2000, new Condition() {
                        @Override
                        public boolean validate() {
                            return !Players.getLocal().isMoving() || Calculations.distanceTo(Walking.getDestination()) < 5;
                        }
                    });
                }
            }
        } else {
            ladder = SceneEntities.getNearest(Lumbridge.BOTTOM_LADDER_ID, Lumbridge.MIDDLE_LADDER_ID);

            if (ladder != null && ladder.isOnScreen()) {
                ladder.interact("Climb-up");
                Task.sleep(1000, 1500);
            } else {
                TilePath path = new TilePath(Lumbridge.TILES_TO_LADDER);
                if (path != null) {
                    if (path.traverse()) {
                        Util.waitFor(2000, new Condition() {
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
}
