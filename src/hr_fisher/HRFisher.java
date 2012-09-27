/*
   Script Name: HR Fisher
   Version: 1.3
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

    September 19, 2012 - v1.3 -
        + ADDED: Depositing all fish into bank from inventory, not just ones from selected fishing type
        + FIXED: Not fishing correctly at Karamja for harpoons

    September 23, 2012 - v1.4 -
        + ADDED: 4.1 framework implementation.
        + ADDED: Faster fish-dropping speed for power-fishing.

    September 24, 2012 - v1.4.1 -
        + FIXED: The bunch of problems that were caused as a result of the new framework.

    September 26, 2012 - v1.4.2 -
        + IMPROVED: Walking method for walking to fishing spot and bank
        + FIXED: Many of the bugs resulting from the change in framework

*/

package hr_fisher;

import hr_fisher.strategies.*;
import hr_fisher.user.FishingGUI;
import hr_fisher.user.FishingPaint;
import hr_fisher.user.Variables;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.bot.Context;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@Manifest(
        name = "HR Fisher v1.4.1",
        description = "Fishes almost all types of fish in many different locations.",
        authors = "H3avY Ra1n",
        version = 1.42,
        website = "http://www.powerbot.org/community/topic/793227-hrfisher-aiofisher/"
)

public class HRFisher extends ActiveScript implements PaintListener, MessageListener, MouseListener {

    private Tree jobs = null;

    @Override
    public void onStart() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FishingGUI gui = new FishingGUI();
                gui.setVisible(true);
            }
        });

        FishingPaint.setupImage();
    }

    @Override
    public int loop() {

        if (!Variables.hasStarted) {
            return Random.nextInt(200, 400);
        } else {

            if (jobs == null) {

                switch (Variables.bankingType) {
                    case Variables.TYPE_BANK:
                        jobs = new Tree(new Node[]{new DepositFish(),  new WalkToBank(),
                                new WithdrawNeededItems(), new WalkToFishingSpot(), new Fish(), new FixCamera(), new Antiban()});
                        break;
                    case Variables.TYPE_POWERFISH:
                        jobs = new Tree(new Node[]{new FixCamera(), new WithdrawNeededItems(), new WalkToFishingSpot(),
                                new Fish(), new DropFish(), new FixCamera(), new Antiban()});
                        break;
                    case Variables.TYPE_STILES:
                        jobs = new Tree(new Node[]{new WithdrawNeededItems(), new WalkToFishingSpot(),
                                new Fish(), new UseStiles(), new FixCamera(), new Antiban()});
                        break;
                    default:
                        jobs = new Tree(new Node[]{new WithdrawNeededItems(), new WalkToFishingSpot(),
                                new F1D1(), new Fish(), new FixCamera(), new Antiban()});
                }
            }

            final Node job = jobs.state();

            if (job != null) {
                jobs.set(job);
                getContainer().submit(job);
                job.join();
            }

            return 300;
        }
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
            } else if(message.contains("Your quick") && message.contains(fishNames[i])) {
                Variables.fishCaught[i] += 2;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (FishingPaint.HIDE_BUTTON.contains(e.getPoint())) {
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
}
