package hr_fisher.strategies;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.locations.LivingRockCaverns;
import hr_fisher.user.Condition;
import hr_fisher.user.Util;
import hr_fisher.user.Variables;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.DepositBox;
import org.powerbot.game.api.wrappers.Entity;

public class DepositFish extends Node {

    @Override
    public void execute() {
        if (Variables.chosenLocation instanceof LivingRockCaverns) {
            System.out.println("Depositing fish.");
            LivingRockCaverns.depositFish();
            return;
        }

        if (!Bank.isOpen()) {
            Bank.open();
            Util.waitFor(2000, new Condition() {
                @Override
                public boolean validate() {
                    return Bank.isOpen();
                }
            });
        } else {
            for (Util.FishingTypes fishingTypes : Util.FishingTypes.values()) {
                int[] itemsToDeposit = fishingTypes.getPossibleFish();
                for (int i : itemsToDeposit) {
                    Bank.deposit(i, Inventory.getCount(i));
                }
            }
        }
    }

    @Override
    public boolean activate() {

        if (Variables.chosenLocation instanceof LivingRockCaverns) {
            if (!Variables.hasStarted) {
                return false;
            }
            return DepositBox.isOpen() || (Inventory.isFull() && Inventory.getCount(true, Variables.chosenFishingType.getPossibleFish()) > 0);
        }

        Entity closestBank = Bank.getNearest();

        boolean shouldBank = false;
        for (Util.FishingTypes fishingTypes : Util.FishingTypes.values()) {
            int[] itemsToDeposit = fishingTypes.getPossibleFish();
            for (int i : itemsToDeposit) {
                if(Inventory.getCount(true, i) > 0)
                    shouldBank = true;
            }
        }
        return Variables.hasStarted && closestBank != null && closestBank.isOnScreen()
                && (Inventory.isFull() || (shouldBank));

    }
}
