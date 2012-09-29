package hr_fisher.nodes;/*
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
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import java.awt.*;

public class DropUselessItems extends Node {

    @Override
    public boolean activate() {

        boolean found = false;
        for(Util.FishingTypes type : Util.FishingTypes.values()) {
            if(type == Variables.chosenFishingType)
                continue;

            for(int id : type.getPossibleFish()) {
                if(Inventory.getCount(true, id) > 0)
                {
                    found = true;
                    break;
                }
            }
        }

        return Inventory.isFull() &&
                (Inventory.getCount(true, Variables.ITEMS_TO_DROP) > 0 || found);

    }

    @Override
    public void execute() {
        Variables.isDropping = true;

        Item[] items = Inventory.getItems(new Filter<Item>() {
            @Override
            public boolean accept(Item item) {
                for (int random : Variables.ITEMS_TO_DROP) {
                    if (item.getId() == random) {
                        return true;
                    }
                }

                for(Util.FishingTypes type : Util.FishingTypes.values()) {
                    if(type == Variables.chosenFishingType)
                        continue;

                    for(int id : type.getPossibleFish()) {
                        if(item.getId() == id)
                            return true;
                    }
                }

                return false;
            }
        });

        for (Item i : items) {
            i.getWidgetChild().interact("Drop");
            Task.sleep(100, 150);
        }

        Variables.isDropping = false;
    }
}
