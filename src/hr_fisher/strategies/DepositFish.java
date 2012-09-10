package hr_fisher.strategies;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Entity;
import hr_fisher.user.Util;
import hr_fisher.user.Variables;

public class DepositFish extends Strategy implements Runnable {

    @Override
    public void run() {

        if(!Bank.isOpen()) {
            Bank.open();
            Time.sleep(1000, 2000);
        } else {
            int[] itemsToDeposit = Variables.chosenFishingType.getPossibleFish();
            for(int i : itemsToDeposit) {
                Bank.deposit(i, Inventory.getCount(i));

                final int temp = i;
                Util.waitFor(2000, new Condition() {
                    @Override
                    public boolean validate() {
                        return Inventory.getCount(temp) == 0;
                    }
                });
            }
        }
    }

    @Override
    public boolean validate() {
        Entity closestBank = Bank.getNearest();

        return Variables.hasStarted && closestBank != null && closestBank.isOnScreen()
                && (Inventory.isFull() || (Bank.isOpen() && Inventory.getCount(Variables.chosenFishingType.getPossibleFish()) > 0));

    }
}
