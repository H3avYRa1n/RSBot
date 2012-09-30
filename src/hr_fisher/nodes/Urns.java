package hr_fisher.nodes;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.user.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class Urns extends Node {
    @Override
    public boolean activate() {
        return Inventory.isFull() && Inventory.getCount(Variables.ITEM_URN) > 0;
    }

    @Override
    public void execute() {
        WidgetChild widget = Widgets.get(905, 14);

        if(widget.validate()) {
            widget.click(true);
            Task.sleep(750, 1000);
        } else {
            final Item urn = Inventory.getItem(Variables.ITEM_URN);

            if(urn != null) {
                if(urn.getWidgetChild().validate()) {
                    urn.getWidgetChild().interact("Teleport");
                    Task.sleep(750, 1000);
                }
            }
        }

    }
}
