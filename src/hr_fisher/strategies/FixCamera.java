package hr_fisher.strategies;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.widget.Camera;

public class FixCamera extends Strategy implements Runnable {
    @Override
    public void run() {
        Camera.setPitch(94);
    }

    @Override
    public boolean validate() {
        return Camera.getPitch() != 94;
    }
}
