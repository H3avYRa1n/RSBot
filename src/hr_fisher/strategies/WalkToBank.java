package hr_fisher.strategies;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.locations.LivingRockCaverns;
import hr_fisher.user.Util;
import hr_fisher.user.Variables;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.DepositBox;

public class WalkToBank extends Node {
    @Override
    public void execute() {
        if (Util.dropTuna())
            return;

        Variables.chosenLocation.walkToBank();
    }

    @Override
    public boolean activate() {

        if (DepositBox.isOpen())
            return false;

        return Variables.hasStarted && !Bank.isOpen() && (Inventory.isFull()
                || !Util.hasNeededItems()
                || (Variables.chosenLocation instanceof LivingRockCaverns
                && LivingRockCaverns.shouldGoToBank()));
    }
}
