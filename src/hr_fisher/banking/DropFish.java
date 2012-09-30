package hr_fisher.banking;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.locations.BarbarianAssault;
import hr_fisher.user.Util;
import hr_fisher.user.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class DropFish implements BankingMethod {
    @Override
    public boolean shouldBank() {
        return (Inventory.isFull() && Inventory.getCount(true, Variables.chosenFishingType.getPossibleFish()) > 0);
    }

    @Override
    public void bank() {
        Variables.isDropping = true;
        for (Item i : Inventory.getItems()) {
            for (Util.FishingTypes fishingType : Util.FishingTypes.values()) {

                int[] fishes = fishingType.getPossibleFish();

                WidgetChild child;
                for (int fish : fishes) {
                    if (i.getId() == fish) {

                        i.getWidgetChild().interact("Drop");
                        Task.sleep(100, 150);
                        break;
                    }
                }
            }
        }

        Variables.isDropping = false;
    }
}
