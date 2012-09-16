package hr_fisher.strategies;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.locations.Karamja;
import hr_fisher.locations.Karamja;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.DepositBox;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.TilePath;
import hr_fisher.user.Util;

public class UseStiles extends Strategy implements Runnable {

    @Override
    public void run() {
        if(Util.dropTuna())
            return;

        NPC stiles = NPCs.getNearest(Karamja.STILES_ID);

        if(stiles != null && stiles.isOnScreen()) {
            stiles.interact("Exchange");
            Util.waitFor(5000, new Condition() {
                @Override
                public boolean validate() {
                    return !Inventory.isFull();
                }
            });
        } else {

            if(!Players.getLocal().isMoving()
                    || Calculations.distanceTo(Walking.getDestination()) < 5) {

                TilePath pathToStiles = new TilePath(Karamja.TILES_FISHING_SPOT_TO_STILES);

                if(pathToStiles != null) {
                    if(pathToStiles.traverse()) {
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
        return Inventory.isFull();
    }
}
