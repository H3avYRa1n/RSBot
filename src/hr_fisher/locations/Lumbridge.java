package hr_fisher.locations;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.user.Variables;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.TilePath;
import org.powerbot.game.api.wrappers.node.SceneObject;
import hr_fisher.user.Util;

public class Lumbridge extends Location {

    public static final Tile[] TILES_TO_LADDER = new Tile[]{
            new Tile(3241, 3241, 0), new Tile(3244, 3237, 0), new Tile(3248, 3234, 0),
            new Tile(3251, 3230, 0), new Tile(3252, 3225, 0), new Tile(3247, 3225, 0),
            new Tile(3242, 3225, 0), new Tile(3237, 3225, 0), new Tile(3235, 3220, 0),
            new Tile(3230, 3219, 0), new Tile(3225, 3218, 0), new Tile(3220, 3217, 0),
            new Tile(3215, 3218, 0), new Tile(3215, 3212, 0), new Tile(3213, 3210, 0),
            new Tile(3206, 3209, 0)
    };

    public static final Tile[] TILES_LADDER_TO_BANK = new Tile[]{
            new Tile(3205, 3209, 2), new Tile(3208, 3219, 2)
    };

    public static final Util.FishingTypes[] TYPES_AVAILABLE = new Util.FishingTypes[]{
            Util.FishingTypes.TYPE_BAIT,
            Util.FishingTypes.TYPE_FLY
    };

    public static final int BOTTOM_LADDER_ID = 36773;
    public static final int MIDDLE_LADDER_ID = 36774;
    public static final int TOP_LADDER_ID = 36775;

    public Lumbridge() {
        super(TILES_LADDER_TO_BANK, TYPES_AVAILABLE, "Lumbridge");
    }

    @Override
    public void walkToBank() {

        SceneObject ladder = SceneEntities.getNearest(TOP_LADDER_ID);

        if (ladder != null) {
            TilePath path = new TilePath(TILES_LADDER_TO_BANK);

            if (path != null) {
                if (path.traverse()) {
                    Util.waitFor(2000, new Condition() {
                        @Override
                        public boolean validate() {
                            return !Players.getLocal().isMoving() || Calculations.distanceTo(Walking.getDestination()) < 5;
                        }
                    });
                }
            }
        } else {
            ladder = SceneEntities.getNearest(BOTTOM_LADDER_ID, MIDDLE_LADDER_ID);

            if (ladder != null && ladder.isOnScreen()) {
                ladder.interact("Climb-up");
                Time.sleep(1000, 1500);
            } else {
                TilePath path = new TilePath(TILES_TO_LADDER);
                if (path != null) {
                    if (path.traverse()) {
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

        SceneObject ladder = SceneEntities.getNearest(TOP_LADDER_ID);

        if (ladder != null) {

            if (ladder.isOnScreen()) {
                ladder.interact("Climb-down");
                Time.sleep(1000, 1500);
            } else {
                TilePath path = new TilePath(TILES_LADDER_TO_BANK);

                if (path != null) {
                    path.reverse();
                    if (path.traverse()) {
                        Util.waitFor(2000, new Condition() {
                            @Override
                            public boolean validate() {
                                return !Players.getLocal().isMoving() || Calculations.distanceTo(Walking.getDestination()) < 5;
                            }
                        });
                    }
                }
            }
        } else {
            ladder = SceneEntities.getNearest(MIDDLE_LADDER_ID);

            if (ladder != null && ladder.isOnScreen()) {
                ladder.interact("Climb-down");
                Time.sleep(1000, 1500);
            } else {
                TilePath pathToFishingSpot = new TilePath(TILES_TO_LADDER);
                if (pathToFishingSpot != null) {
                    pathToFishingSpot.reverse();
                    if (pathToFishingSpot.traverse()) {
                        Util.waitFor(5000, new Condition() {
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
