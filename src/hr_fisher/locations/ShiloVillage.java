package hr_fisher.locations;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.user.Util;
import hr_fisher.user.Variables;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.TilePath;

public class ShiloVillage extends Location {

    public static final Tile[] TILES_TO_BANK_LEFT = new Tile[]{
            new Tile(2822, 2967, 0), new Tile(2827, 2967, 0), new Tile(2832, 2968, 0),
            new Tile(2837, 2969, 0), new Tile(2841, 2966, 0), new Tile(2845, 2963, 0),
            new Tile(2849, 2960, 0), new Tile(2852, 2956, 0)
    };

    public static final Tile[] TILES_TO_BANK_RIGHT = new Tile[]{
            new Tile(2851, 2970, 0), new Tile(2856, 2972, 0), new Tile(2861, 2970, 0),
            new Tile(2863, 2965, 0), new Tile(2861, 2960, 0), new Tile(2857, 2960, 0),
            new Tile(2852, 2960, 0), new Tile(2851, 2956, 0)
    };

    public static final Tile[] TILES_EAST_TO_WEST = new Tile[]{
            new Tile(2866, 2970, 0), new Tile(2861, 2970, 0), new Tile(2856, 2970, 0),
            new Tile(2851, 2970, 0), new Tile(2846, 2969, 0), new Tile(2841, 2968, 0),
            new Tile(2836, 2969, 0), new Tile(2835, 2969, 0), new Tile(2831, 2969, 0),
            new Tile(2827, 2969, 0), new Tile(2823, 2967, 0), new Tile(2819, 2968, 0)
    };

    public static final Util.FishingTypes[] TYPES_AVAILABLE = new Util.FishingTypes[]{
            Util.FishingTypes.TYPE_BAIT,
            Util.FishingTypes.TYPE_FLY
    };

    public static final Area SOUTHERN_AREA = new Area(
            new Tile(2817, 2971, 0), new Tile(2821, 2970, 0), new Tile(2826, 2975, 0),
            new Tile(2839, 2975, 0), new Tile(2840, 2973, 0), new Tile(2847, 2973, 0),
            new Tile(2851, 2975, 0), new Tile(2867, 2975, 0), new Tile(2867, 2971, 0),
            new Tile(2818, 2966, 0)
    );

    public static final Area EAST_RIVER_AREA = new Area(
            new Tile(2855, 2970, 0), new Tile(2855, 2974, 0),
            new Tile(2866, 2974, 0), new Tile(2866, 2969, 0)
    );

    public static final Area FISHING_AREA = new Area(
            new Tile(2820, 2974, 0), new Tile(2817, 2968, 0), new Tile(2817, 2964, 0),
            new Tile(2867, 2970, 0), new Tile(2867, 2981, 0)
    );

    public static boolean walkingWest = false;

    public ShiloVillage() {
        super(TILES_TO_BANK_LEFT, TYPES_AVAILABLE, "Shilo Village");
    }

    public void walkToBank() {

        if (!Players.getLocal().isMoving()
                || Calculations.distanceTo(Walking.getDestination()) < 5) {

            TilePath pathToBank = null;

            if (EAST_RIVER_AREA.contains(Players.getLocal().getLocation())) {
                pathToBank = new TilePath(TILES_TO_BANK_RIGHT);
            } else {
                pathToBank = new TilePath(TILES_TO_BANK_LEFT);
            }

            if (pathToBank != null) {
                if (pathToBank.traverse()) {
                    Util.waitFor(3000, new Condition() {
                        @Override
                        public boolean validate() {
                            return !Players.getLocal().isMoving()
                                    || Calculations.distanceTo(Walking.getDestination()) < 5;
                        }
                    });
                }
            }
        }

    }

    public void walkToFishingSpot() {
        if (!Players.getLocal().isMoving()
                || Calculations.distanceTo(Walking.getDestination()) < 5) {

            NPC closestFishingSpot = NPCs.getNearest(new Filter<NPC>() {
                public boolean accept(NPC npc) {
                    String[] actions = npc.getActions();
                    for (String s : actions) {
                        if (s != null && s.contains(Variables.chosenFishingType.getInteractString())) {
                            return SOUTHERN_AREA.contains(npc.getLocation());
                        }
                    }

                    return false;
                }
            });

            if (closestFishingSpot != null && closestFishingSpot.isOnScreen()
                    && Calculations.distance(Players.getLocal().getLocation(), closestFishingSpot.getLocation()) < 5) {
                return;
            }

            TilePath pathToBank = new TilePath(tilesToBank);
            if (pathToBank != null) {
                pathToBank.reverse();
                if (!FISHING_AREA.contains(Players.getLocal().getLocation()) && pathToBank.traverse()) {
                    Util.waitFor(5000, new Condition() {
                        @Override
                        public boolean validate() {
                            return !Players.getLocal().isMoving() || Calculations.distanceTo(Walking.getDestination()) < 5;
                        }
                    });
                } else {

                    if (closestFishingSpot != null) {
                        if (EAST_RIVER_AREA.contains(closestFishingSpot))
                            walkingWest = false;
                        walkingWest = true;
                    }

                    if (walkingWest) {
                        TilePath path = new TilePath(TILES_EAST_TO_WEST);
                        if (path.traverse()) {
                            Util.waitFor(5000, new Condition() {
                                @Override
                                public boolean validate() {
                                    return !Players.getLocal().isMoving() || Calculations.distanceTo(Walking.getDestination()) < 5;
                                }
                            });
                        } else {
                            walkingWest = false;
                        }
                    } else {
                        TilePath path = new TilePath(TILES_EAST_TO_WEST);
                        if (path != null) {
                            path.reverse();

                            if (path.traverse()) {
                                Util.waitFor(5000, new Condition() {
                                    @Override
                                    public boolean validate() {
                                        return !Players.getLocal().isMoving() || Calculations.distanceTo(Walking.getDestination()) < 5;
                                    }
                                });
                            } else {
                                walkingWest = true;
                            }
                        }
                    }
                }
            }
        }
    }
}
