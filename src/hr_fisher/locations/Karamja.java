package hr_fisher.locations;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.TilePath;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import hr_fisher.user.Util;
import hr_fisher.user.Variables;

public class Karamja extends Location {

    public static final Tile[] TILES_PORT_SARIM_TO_BANK = new Tile[]{
            new Tile(3027, 3218, 0), new Tile(3027, 3229, 0), new Tile(3028, 3235, 0),
            new Tile(3037, 3235, 0), new Tile(3041, 3239, 0), new Tile(3045, 3247, 0),
            new Tile(3054, 3250, 0), new Tile(3062, 3255, 0), new Tile(3064, 3266, 0),
            new Tile(3067, 3274, 0), new Tile(3071, 3270, 0), new Tile(3076, 3264, 0),
            new Tile(3079, 3260, 0), new Tile(3079, 3255, 0), new Tile(3083, 3248, 0),
            new Tile(3091, 3248, 0), new Tile(3091, 3242, 0)};

    public static final Tile[] TILES_DOCK_TO_FISHING_SPOT = new Tile[]{
            new Tile(2953, 3146, 0), new Tile(2948, 3146, 0), new Tile(2942, 3147, 0),
            new Tile(2935, 3147, 0), new Tile(2930, 3150, 0), new Tile(2924, 3151, 0),
            new Tile(2918, 3151, 0), new Tile(2915, 3153, 0), new Tile(2916, 3157, 0),
            new Tile(2918, 3161, 0), new Tile(2921, 3164, 0), new Tile(2923, 3168, 0),
            new Tile(2924, 3172, 0), new Tile(2923, 3175, 0), new Tile(2923, 3179, 0)
    };

    public static final Tile[] TILES_FISHING_SPOT_TO_STILES = new Tile[]{
            new Tile(2924, 3177, 0), new Tile(2924, 3172, 0), new Tile(2919, 3170, 0),
            new Tile(2914, 3169, 0), new Tile(2910, 3172, 0), new Tile(2905, 3171, 0),
            new Tile(2902, 3167, 0), new Tile(2900, 3162, 0), new Tile(2898, 3157, 0),
            new Tile(2895, 3153, 0), new Tile(2892, 3149, 0), new Tile(2888, 3146, 0),
            new Tile(2883, 3144, 0), new Tile(2878, 3144, 0), new Tile(2873, 3145, 0),
            new Tile(2869, 3148, 0), new Tile(2864, 3148, 0), new Tile(2860, 3145, 0),
            new Tile(2855, 3144, 0), new Tile(2850, 3142, 0)};

    public static final Util.FishingTypes[] TYPES_AVAILABLE = new Util.FishingTypes[]{
            Util.FishingTypes.TYPE_LOBSTER_CAGE,
            Util.FishingTypes.TYPE_HARPOON_TUNA
    };

    public static final Area PORT_SARIM_AREA = new Area(
            new Tile(3099, 3231, 0),
            new Tile(3048, 3202, 0),
            new Tile(3020, 3217, 0),
            new Tile(3029, 3276, 0),
            new Tile(3094, 3278, 0)
    );

    public static final Area KARAMJA_AREA = new Area(new Tile(2890, 3193, 0), new Tile(2892, 3133, 0), new Tile(2967, 3136, 0),
            new Tile(2956, 3184, 0));

    public static final Area STILES_AREA = new Area(new Tile(2931, 3181, 0), new Tile(2892, 3135, 0), new Tile(2840, 3137, 0),
            new Tile(2881, 3185, 0));

    public static final int[] PLANK_IDS = new int[]{2082, 2084};
    public static final int[] CUSTOMS_OFFICERS_IDS = new int[]{376, 377, 378, 380};
    public static final int STILES_ID = 11267;


    public Karamja() {
        super(TILES_PORT_SARIM_TO_BANK, TYPES_AVAILABLE, "Karamja");
    }

    @Override
    public void walkToBank() {
        if (KARAMJA_AREA.contains(Players.getLocal().getLocation())) {
            NPC customsOfficer = NPCs.getNearest(Karamja.CUSTOMS_OFFICERS_IDS);

            if (customsOfficer != null) {

                if (customsOfficer.isOnScreen()) {

                    Camera.turnTo(customsOfficer);
                    Widget officerTalking = Widgets.get(1184);

                    if (officerTalking.validate()) {
                        WidgetChild child = officerTalking.getChild(19);
                        if (child.validate()) {
                            child.interact("Continue");
                            Time.sleep(1000);
                        }
                    } else {

                        Widget buttonWidget = Widgets.get(1188);
                        if (buttonWidget.validate()) {
                            WidgetChild child = buttonWidget.getChild(3);
                            if (child.validate() && (child.getText().contains("Can I") || child.getText().contains("Ok."))) {
                                child.interact("Continue");
                                Time.sleep(1000);
                            } else {
                                child = buttonWidget.getChild(24);
                                if (child.validate() && child.getText().contains("Search")) {
                                    child.interact("Continue");
                                    Time.sleep(1000);
                                }
                            }
                        } else {
                            Widget playerTalking = Widgets.get(1191);

                            if (playerTalking.validate()) {
                                WidgetChild child = playerTalking.getChild(19);
                                if (child.validate()) {
                                    child.interact("Continue");
                                    Time.sleep(1000);
                                }
                            } else {
                                customsOfficer.interact("Pay-Fare");
                                Time.sleep(1000, 1500);
                            }
                        }
                    }


                }
            } else {

                TilePath path = new TilePath(Karamja.TILES_DOCK_TO_FISHING_SPOT);
                path.reverse();

                if (path.traverse()) {
                    Time.sleep(1000, 2000);
                }
            }
        } else {
            SceneObject gangPlank = SceneEntities.getNearest(PLANK_IDS);

            if (gangPlank != null && gangPlank.isOnScreen()) {
                gangPlank.interact("Cross");
                Time.sleep(1000, 2000);
            }

            super.walkToBank();
        }
    }

    @Override
    public void walkToFishingSpot() {

        NPC fishingSpot = NPCs.getNearest(new Filter<NPC>() {
            public boolean accept(NPC npc) {
                String[] actions = npc.getActions();
                for(String s : actions) {
                    if( s != null && s.contains(Variables.chosenFishingType.getInteractString())) {
                        return true;
                    }
                }

                return false;
            }
        });

        if(fishingSpot != null && fishingSpot.isOnScreen() && Calculations.distanceTo(fishingSpot) < 10) {
            return;
        }

        if (Karamja.STILES_AREA.contains(Players.getLocal().getLocation())) {


            TilePath pathToFishingSpot = new TilePath(Karamja.TILES_FISHING_SPOT_TO_STILES).reverse();

            if (pathToFishingSpot != null) {
                if (pathToFishingSpot.traverse()) {
                    Util.waitFor(3000, new Condition() {
                        @Override
                        public boolean validate() {
                            return !Players.getLocal().isMoving() || Calculations.distanceTo(Walking.getDestination()) < 5;
                        }
                    });
                }
            }

        } else if (Karamja.KARAMJA_AREA.contains(Players.getLocal().getLocation())) {
            TilePath pathToFishingSpot = new TilePath(Karamja.TILES_DOCK_TO_FISHING_SPOT);

            NPC closestFishingSpot = NPCs.getNearest(new Filter<NPC>() {
                public boolean accept(NPC npc) {
                    String[] actions = npc.getActions();
                    for(String s : actions) {
                        if( s != null && s.contains(Variables.chosenFishingType.getInteractString())) {
                            return true;
                        }
                    }

                    return false;
                }
            });

            if (Calculations.distanceTo(pathToFishingSpot.getEnd()) < 10 && !closestFishingSpot.isOnScreen()) {

                Camera.turnTo(closestFishingSpot);
            } else if (pathToFishingSpot.traverse()) {
                Time.sleep(1000, 2000);
            }

        } else {
            SceneObject gangPlank = SceneEntities.getNearest(Karamja.PLANK_IDS);

            if (gangPlank != null) {
                gangPlank.interact("Cross");
                Time.sleep(1000, 1500);
            } else {
                NPC closestFisherman = NPCs.getNearest(Karamja.CUSTOMS_OFFICERS_IDS);

                if (closestFisherman != null && closestFisherman.isOnScreen()) {

                    Widget fishermanTalkingWidget = Widgets.get(1184);

                    if (fishermanTalkingWidget.validate()) {
                        WidgetChild child = fishermanTalkingWidget.getChild(19);

                        if (child.validate()) {
                            child.interact("Continue");
                            Time.sleep(500, 1000);
                        }
                    } else {
                        Widget buttonWidget = Widgets.get(1188);
                        if (buttonWidget.validate()) {
                            WidgetChild child = buttonWidget.getChild(3);
                            if (child.validate()) {
                                child.interact("Continue");
                                Time.sleep(500, 1000);
                            }
                        } else {
                            Widget playerTalkingWidget = Widgets.get(1191);
                            if (playerTalkingWidget.validate()) {
                                WidgetChild child = playerTalkingWidget.getChild(19);
                                if (child.validate()) {
                                    child.interact("Continue");
                                    Time.sleep(500, 1000);
                                }
                            } else {
                                closestFisherman.interact("Pay");
                                Time.sleep(1000, 1500);
                            }
                        }
                    }
                } else {

                    TilePath pathToDock = new TilePath(Karamja.TILES_PORT_SARIM_TO_BANK);

                    if (pathToDock != null) {
                        pathToDock.reverse();

                        if (pathToDock.traverse()) {
                            Util.waitFor(2000, new Condition() {
                                @Override
                                public boolean validate() {
                                    return !Players.getLocal().isMoving() || Calculations.distanceTo(Walking.getDestination()) < 5;
                                }
                            });
                        }
                    }
                }
            }

        }
    }
}

