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
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.DepositBox;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class DropFish extends Node {

    @Override
    public void execute() {

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

            for (int random : Variables.RANDOM_EVENT_ITEM_IDS) {
                if (i.getId() == random) {
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

        return Inventory.isFull()
                && Inventory.getCount(true, Variables.chosenFishingType.getPossibleFish()) > 0;
    }
}
