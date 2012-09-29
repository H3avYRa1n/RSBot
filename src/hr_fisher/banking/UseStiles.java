package hr_fisher.banking;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.locations.Karamja;
import hr_fisher.user.Condition;
import hr_fisher.user.Util;
import hr_fisher.user.Variables;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.TilePath;

public class UseStiles implements BankingMethod {
    @Override
    public boolean shouldBank() {
        return Variables.hasStarted && (Inventory.isFull() || !Util.hasNeededItems());
    }

    @Override
    public void bank() {
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
}
