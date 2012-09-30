package hr_fisher.nodes;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.user.Util;
import hr_fisher.user.Variables;
import org.powerbot.core.script.job.state.Node;

public class Bank extends Node {

    @Override
    public boolean activate() {
        return Variables.chosenLocation.bankingMethod.shouldBank();
    }

    @Override
    public void execute() {
        Variables.chosenLocation.bankingMethod.bank();
    }
}
