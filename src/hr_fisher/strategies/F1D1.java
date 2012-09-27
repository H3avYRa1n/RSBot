package hr_fisher.strategies;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.user.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.DepositBox;
import org.powerbot.game.api.wrappers.node.Item;

public class F1D1 extends Node {

    @Override
    public void execute() {
        Variables.isDropping = true;
        for (Item i : Inventory.getItems()) {
            for (int fish : Variables.chosenFishingType.getPossibleFish()) {
                if (i.getId() == fish) {
                    i.getWidgetChild().interact("Drop");
                    Task.sleep(100, 150);
                    break;
                }
            }
        }
        Variables.isDropping = false;
    }

    @Override
    public boolean activate() {
        if (DepositBox.isOpen())
            return false;

        return Inventory.getCount(Variables.chosenFishingType.getPossibleFish()) > 0;
    }
}
