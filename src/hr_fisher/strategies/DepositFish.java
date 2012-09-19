package hr_fisher.strategies;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.locations.LivingRockCaverns;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.DepositBox;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Entity;
import hr_fisher.user.Util;
import hr_fisher.user.Variables;

public class DepositFish extends Strategy implements Runnable {

    @Override
    public void run() {
        if(Variables.chosenLocation instanceof LivingRockCaverns) {
            System.out.println("Depositing fish.");
            LivingRockCaverns.depositFish();
            return;
        }

        if(!Bank.isOpen()) {
            Bank.open();
            Util.waitFor(2000, new Condition() {
                @Override
                public boolean validate() {
                    return Bank.isOpen();
                }
            });
        } else {
            for(Util.FishingTypes fishingTypes : Util.FishingTypes.values()) {
                int[] itemsToDeposit = fishingTypes.getPossibleFish();
                for(int i : itemsToDeposit) {
                    Bank.deposit(i, Inventory.getCount(i));
                }
            }
        }
    }

    @Override
    public boolean validate() {

        if(Variables.chosenLocation instanceof LivingRockCaverns) {
            if(!Variables.hasStarted) {
                return false;
            }
            return DepositBox.isOpen() || (Inventory.isFull() && Inventory.getCount(true, Variables.chosenFishingType.getPossibleFish()) > 0);
        }

        Entity closestBank = Bank.getNearest();

        return Variables.hasStarted && closestBank != null && closestBank.isOnScreen()
                && (Inventory.isFull() || (Bank.isOpen() && Inventory.getCount(Variables.chosenFishingType.getPossibleFish()) > 0));

    }
}
