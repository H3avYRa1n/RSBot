package hr_fisher.banking;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.locations.Karamja;
import hr_fisher.user.Util;
import hr_fisher.user.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.TilePath;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class KaramjaBank extends NormalBank {

    @Override
    public void bank() {
        if (Karamja.KARAMJA_AREA.contains(Players.getLocal().getLocation())) {
            NPC customsOfficer = NPCs.getNearest(Karamja.CUSTOMS_OFFICERS_IDS);

            if (customsOfficer != null) {

                if (customsOfficer.isOnScreen()) {

                    Camera.turnTo(customsOfficer);
                    Widget officerTalking = Widgets.get(1184);

                    if (officerTalking.validate()) {
                        WidgetChild child = officerTalking.getChild(19);
                        if (child.validate()) {
                            child.interact("Continue");
                            Task.sleep(1000);
                        }
                    } else {

                        Widget buttonWidget = Widgets.get(1188);
                        if (buttonWidget.validate()) {
                            WidgetChild child = buttonWidget.getChild(3);
                            if (child.validate() && (child.getText().contains("Can I") || child.getText().contains("Ok."))) {
                                child.interact("Continue");
                                Task.sleep(1000);
                            } else {
                                child = buttonWidget.getChild(24);
                                if (child.validate() && child.getText().contains("Search")) {
                                    child.interact("Continue");
                                    Task.sleep(1000);
                                }
                            }
                        } else {
                            Widget playerTalking = Widgets.get(1191);

                            if (playerTalking.validate()) {
                                WidgetChild child = playerTalking.getChild(19);
                                if (child.validate()) {
                                    child.interact("Continue");
                                    Task.sleep(1000);
                                }
                            } else {
                                customsOfficer.interact("Pay-Fare");
                                Task.sleep(1000, 1500);
                            }
                        }
                    }


                }
            } else {

                TilePath path = new TilePath(Karamja.TILES_DOCK_TO_FISHING_SPOT);
                path.reverse().traverse();
                Task.sleep(1000, 1500);
            }
        } else {
            SceneObject gangPlank = SceneEntities.getNearest(Karamja.PLANK_IDS);

            if (gangPlank != null && gangPlank.isOnScreen()) {
                gangPlank.interact("Cross");
                Task.sleep(1000, 2000);
            }

            Entity bank = Bank.getNearest();

            if(bank == null || !bank.isOnScreen()) {
                TilePath pathToBank = new TilePath(Karamja.TILES_PORT_SARIM_TO_BANK);

                if (pathToBank != null) {
                    pathToBank.traverse();
                    Task.sleep(1000, 1500);
                }
            } else {
                super.bank();
            }
        }
    }
}
