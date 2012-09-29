package hr_fisher.banking;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.user.Util;
import hr_fisher.user.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.map.TilePath;

public class NormalBank implements BankingMethod {

    public boolean shouldBank() {

        return Variables.hasStarted && (Inventory.isFull() || !Util.hasNeededItems());
    }

    @Override
    public void bank() {
        Entity bank = Bank.getNearest();

        if(bank == null || !bank.isOnScreen()) {
            TilePath pathToBank = new TilePath(Variables.chosenLocation.tilesToBank);

            if (pathToBank != null) {
                pathToBank.traverse();
                Task.sleep(1000, 1500);
            }
        } else {
            if (!Bank.isOpen()) {
                Bank.open();
            } else {
                for (Util.FishingTypes fishingTypes : Util.FishingTypes.values()) {
                    int[] itemsToDeposit = fishingTypes.getPossibleFish();
                    for (int i : itemsToDeposit) {
                        Bank.deposit(i, Inventory.getCount(i));
                    }
                }
            }
        }
    }
}
