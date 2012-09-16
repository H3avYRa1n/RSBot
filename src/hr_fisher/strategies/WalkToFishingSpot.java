package hr_fisher.strategies;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.user.Variables;
import hr_fisher.locations.Karamja;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.methods.widget.DepositBox;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.TilePath;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import hr_fisher.user.Util;
import hr_fisher.user.Variables;

public class WalkToFishingSpot extends Strategy implements Runnable {

    @Override
    public void run() {
        if(!Players.getLocal().isMoving() || Calculations.distanceTo(Walking.getDestination()) < 5) {
            Variables.chosenLocation.walkToFishingSpot();
        }
    }

    @Override
    public boolean validate() {

        if(!Variables.hasStarted)
            return false;

        if(DepositBox.isOpen()) {
            int[] fish = Variables.chosenFishingType.getPossibleFish();
            if(DepositBox.getItemCount(fish) > 0) {
                return false;
            }

        }

        if (Bank.isOpen() && Inventory.getCount(Variables.chosenFishingType.getPossibleFish()) > 0)
            return false;

        return Util.hasNeededItems()
                && !Inventory.isFull()
                && Players.getLocal().getAnimation() == -1;

    }
}
