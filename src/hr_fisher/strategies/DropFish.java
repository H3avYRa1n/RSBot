package hr_fisher.strategies;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.user.Util;
import hr_fisher.user.Variables;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.DepositBox;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.Item;
import hr_fisher.user.Variables;

public class DropFish extends Strategy implements Runnable {

    @Override
    public void run() {

        Variables.isDropping = true;
        for(Item i : Inventory.getItems()) {
            for(Util.FishingTypes fishingType : Util.FishingTypes.values()) {

                int[] fishes = fishingType.getPossibleFish();

                for(int fish : fishes) {
                    if(i.getId() == fish) {
                        i.getWidgetChild().interact("Drop");
                        Time.sleep(200, 300);
                        break;
                    }
                }
            }


            for(int random : Variables.RANDOM_EVENT_ITEM_IDS) {
                if(i.getId() == random) {
                    i.getWidgetChild().interact("Drop");
                    Time.sleep(200, 300);
                    break;
                }
            }
        }

        Variables.isDropping = false;
    }

    @Override
    public boolean validate() {
        if(DepositBox.isOpen())
            return false;

        return Inventory.isFull()
                && Inventory.getCount(true, Variables.chosenFishingType.getPossibleFish()) > 0;
    }
}
