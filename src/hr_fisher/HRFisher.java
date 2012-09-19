
/*
    Script Name: HR Fisher
    Version: 1.1.2
    Author: H3avY Ra1n

    Changelog
    ---------
    September 07, 2012 - v1.0 -
        + Initial release

    September 14, 2012 - v1.1 -
        + ADDED: Option to show/hide paint and fixed logging out unexpectedly.

    September 15, 2012 - v1.1.1 -
        + FIXED: Shilo Village fishing.
        + FIXED: Scriptnot working if items were in toolbelt.
        + ADDED: Option to fish at shark spots of swordfish spots if harpooning

    September 16, 2012 - v1.2 -
        + ADDED: Dropping random event items
        + ADDED: Piscatoris Fishing Colony spot
        + ADDED: Barbarian Assault spot
 */

package hr_fisher;

import hr_fisher.strategies.*;
import hr_fisher.user.FishingGUI;
import hr_fisher.user.FishingPaint;
import hr_fisher.user.Util;
import hr_fisher.user.Variables;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.bot.event.MessageEvent;
import org.powerbot.game.bot.event.listener.MessageListener;
import org.powerbot.game.bot.event.listener.PaintListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@Manifest(
        name = "HR Fisher v1.2",
        description = "Fishes almost all types of fish in many different locations.",
        authors = "H3avY Ra1n",
        version = 1.2,
        website = "http://www.powerbot.org/community/topic/793227-hrfisher-aiofisher/"
)

public class HRFisher extends ActiveScript implements PaintListener, MessageListener, MouseListener {

    @Override
    protected void setup() {
        provide(new WithdrawNeededItems());
        provide(new WalkToFishingSpot());
        provide(new Fish());
        provide(new CheckGUI());
        provide(new Antiban());
        provide(new FixCamera());
        //provide(new PrintInfo());

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
            } else if (message.contains("You catch some " + fishNames[i])) {
                Variables.fishCaught[i]++;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if(FishingPaint.HIDE_BUTTON.contains(e.getPoint())) {
            FishingPaint.shouldHide = !FishingPaint.shouldHide;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private class CheckGUI extends Strategy implements Runnable {

        @Override
        public void run() {
            switch (Variables.bankingType) {
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

    private class PrintInfo extends Strategy implements Runnable {
        @Override
        public void run() {
            FishingPaint.printInfo();
            stop();
        }

        @Override
        public boolean validate() {
            return !Game.isLoggedIn();
        }
    }
}
