package hr_fisher.banking;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.user.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

public class F1D1 implements BankingMethod {
    @Override
    public boolean shouldBank() {
        return Inventory.getCount(Variables.chosenFishingType.getPossibleFish()) > 0;
    }

    @Override
    public void bank() {
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
}
