package hr_fisher.strategies;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.user.Util;
import hr_fisher.user.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.DepositBox;

public class WalkToFishingSpot extends Node {

    @Override
    public void execute() {
        if (!Players.getLocal().isMoving() || Calculations.distanceTo(Walking.getDestination()) < 5) {
            Variables.chosenLocation.walkToFishingSpot();
            Task.sleep(2000, 2500);
        }
    }

    @Override
    public boolean activate() {

        if (!Variables.hasStarted)
            return false;

        if (DepositBox.isOpen()) {
            int[] fish = Variables.chosenFishingType.getPossibleFish();
            if (DepositBox.getItemCount(fish) > 0) {
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
