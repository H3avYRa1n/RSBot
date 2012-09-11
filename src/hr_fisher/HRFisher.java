package hr_fisher;

import hr_fisher.strategies.*;
import hr_fisher.user.FishingGUI;
import hr_fisher.user.FishingPaint;
import hr_fisher.user.Variables;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.bot.event.MessageEvent;
import org.powerbot.game.bot.event.listener.MessageListener;
import org.powerbot.game.bot.event.listener.PaintListener;
import hr_fisher.user.FishingGUI;
import hr_fisher.user.FishingPaint;
import hr_fisher.user.Variables;

import javax.swing.*;
import java.awt.*;

@Manifest(
        name = "HR Fisher",
        description = "Fishes in many different locations with many features.",
        authors = "H3avY Ra1n",
        version = 1.0,
        topic = 793227
)

public class HRFisher extends ActiveScript implements PaintListener, MessageListener {

    @Override
    protected void setup() {
        provide(new WithdrawNeededItems());
        provide(new WalkToFishingSpot());
        provide(new Fish());
        provide(new CheckGUI());
        provide(new Antiban());
        provide(new FixCamera());

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FishingGUI gui = new FishingGUI();
                gui.setVisible(true);
            }
        });

        FishingPaint.setupImage();

        //System.out.println("Has Needed Items: " + Util.hasNeededItems());
    }

    @Override
    public void onRepaint(Graphics graphics) {
        if (Variables.hasStarted)
            FishingPaint.onRepaint(graphics);
    }

    @Override
    public void messageReceived(MessageEvent e) {
        if (!Variables.hasStarted)
            return;

        String message = e.getMessage();

        String[] fishNames = Variables.chosenFishingType.getFishNames();
        for (int i = 0; i < fishNames.length; i++) {
            if (message.contains("You catch a " + fishNames[i])) {
                Variables.fishCaught[i]++;
            } else if(message.contains("You catch some " + fishNames[i])) {
                Variables.fishCaught[i]++;
            }
        }
    }

    private class CheckGUI extends Strategy implements Runnable {

        @Override
        public void run() {
            switch(Variables.bankingType) {
                case Variables.TYPE_BANK:
                    provide(new WalkToBank());
                    provide(new DepositFish());
                    break;
                case Variables.TYPE_POWERFISH:
                    provide(new DropFish());
                    break;
                case Variables.TYPE_STILES:
                    provide(new UseStiles());
                    break;
                default:
                    provide(new F1D1());
            }

            revoke(this);
        }

        @Override
        public boolean validate() {
            return Variables.hasStarted;
        }
    }
}
