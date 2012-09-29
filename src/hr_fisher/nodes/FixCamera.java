package hr_fisher.nodes;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.widget.Camera;

public class FixCamera extends Node {
    @Override
    public void execute() {
        Camera.setPitch(90);
    }

    @Override
    public boolean activate() {
        return Camera.getPitch() < 90;
    }
}
