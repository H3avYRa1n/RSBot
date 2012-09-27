package hr_fisher.strategies;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.locations.Karamja;
import hr_fisher.user.Condition;
import hr_fisher.user.Util;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.DepositBox;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.TilePath;

public class UseStiles extends Node {

    @Override
    public void execute() {
        if (Util.dropTuna())
            return;

        NPC stiles = NPCs.getNearest(Karamja.STILES_ID);

        if (stiles != null && stiles.isOnScreen()) {
            stiles.interact("Exchange");
            Util.waitFor(5000, new Condition() {
                @Override
                public boolean validate() {
                    return !Inventory.isFull();
                }
            });
        } else {

            TilePath pathToStiles = new TilePath(Karamja.TILES_FISHING_SPOT_TO_STILES);

            if (pathToStiles != null) {
                pathToStiles.traverse();
            }
        }
    }

    @Override
    public boolean activate() {
        if (DepositBox.isOpen())
            return false;
        return Inventory.isFull();
    }
}
