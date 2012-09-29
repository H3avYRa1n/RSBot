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
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class Antiban extends Node {
    private Timer timer;

    private static final int minTime = (20 * 1000);
    private static final int maxTime = (120 * 1000);

    public Antiban() {
        timer = new Timer(Random.nextInt(minTime, maxTime));
    }

    @Override
    public void execute() {
        int randomTask = Random.nextInt(1, 5);

        switch (randomTask) {
            case 1:
                if (!Tabs.STATS.isOpen()) {
                    Tabs.STATS.open();
                    Task.sleep(1000);

                    final int widgetID = 0;
                    WidgetChild fishingWidget = Skills.getWidgetChild(29);

                    int x = fishingWidget.getAbsoluteX() + Random.nextInt(0, fishingWidget.getWidth());
                    int y = fishingWidget.getAbsoluteY() + Random.nextInt(0, fishingWidget.getHeight());

                    Mouse.move(x, y);
                    Task.sleep(Random.nextInt(2000, 4000));

                }
                break;
            case 2:
                Camera.setAngle(Camera.getYaw() + Random.nextInt(-100, 100));
                break;
        }

        timer.setEndIn(Random.nextInt(minTime, maxTime));
    }

    @Override
    public boolean activate() {

        NPC fishingSpot = Util.getClosestFishingSpot();

        return Variables.hasStarted
                && !timer.isRunning()
                && !Variables.isDropping
                && !Players.getLocal().isMoving()
                && Players.getLocal().getAnimation() != -1
                && fishingSpot != null
                && fishingSpot.isOnScreen();
    }
}
