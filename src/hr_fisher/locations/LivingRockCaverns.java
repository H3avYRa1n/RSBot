package hr_fisher.locations;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.user.Condition;
import hr_fisher.user.Util;
import hr_fisher.user.Variables;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.DepositBox;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.LocalPath;
import org.powerbot.game.api.wrappers.map.TilePath;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class LivingRockCaverns extends Location {

    public static final Tile[] TILES_TO_BANK = new Tile[]{
            new Tile(3647, 5085, 0), new Tile(3655, 5097, 0), new Tile(3658, 5110, 0), new Tile(3654, 5114, 0)
    };

    public static final Tile[] TILES_LEFT_TO_RIGHT = new Tile[]{
            new Tile(3649, 5084, 0), new Tile(3639, 5084, 0), new Tile(3629, 5085, 0)
    };

    public static final Util.FishingTypes[] TYPES_AVAILABLE = new Util.FishingTypes[]{
            Util.FishingTypes.TYPE_ROCKTAIL_BAIT
    };

    public static final int depositPulleyID = 45079;

    public static final Area RIGHT_FISHING_AREA = new Area(new Tile[]{
            new Tile(3642, 5079, 0), new Tile(3624, 5087, 0)
    });

    public static final Area LEFT_FISHING_AREA = new Area(new Tile[]{
            new Tile(3655, 5079, 0), new Tile(3639, 5090, 0)
    });

    public LivingRockCaverns() {
        super(TILES_TO_BANK, TYPES_AVAILABLE, "LRC");
    }

    public static void depositFish() {
        System.out.println("Still depositing.");
        if (!DepositBox.isOpen()) {
            SceneObject box = SceneEntities.getNearest(depositPulleyID);
            if (box != null && box.isOnScreen()) {
                box.interact("Deposit");
                Task.sleep(1000, 2000);
            }
        } else {
            int[] toDeposit = Variables.chosenFishingType.getPossibleFish();
            for (int i : toDeposit) {
                DepositBox.deposit(i, DepositBox.getItemCount(i));
            }
            DepositBox.close();
        }
    }

    public void walkToBank() {
        if (!Players.getLocal().isMoving()
                || Calculations.distanceTo(Walking.getDestination()) < 5) {

            if (RIGHT_FISHING_AREA.contains(Players.getLocal().getLocation())) {
                TilePath path = new TilePath(TILES_LEFT_TO_RIGHT);
                if (path != null) {
                    path.reverse();
                    if (path.traverse()) {
                        Util.waitFor(2000, new Condition() {
                            @Override
                            public boolean validate() {
                                return !Players.getLocal().isMoving()
                                        || Calculations.distanceTo(Walking.getDestination()) < 5;
                            }
                        });
                    }
                }
            } else {
                TilePath path = new TilePath(TILES_TO_BANK);
                if (path != null) {
                    if (path.traverse()) {
                        Util.waitFor(2000, new Condition() {
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
    }

    public void walkToFishingSpot() {
        if (!shouldGoToBank()) {
            if (!Players.getLocal().isMoving()
                    || Calculations.distanceTo(Walking.getDestination()) < 5) {

                NPC closestFishingSpot = Util.getClosestFishingSpot();

                if (RIGHT_FISHING_AREA.contains(Players.getLocal().getLocation())
                        || LEFT_FISHING_AREA.contains(Players.getLocal().getLocation())) {
                    if (closestFishingSpot != null && !closestFishingSpot.isOnScreen()) {

                        LocalPath path = Walking.findPath(closestFishingSpot);
                        System.out.println("path == null: " + (path == null));
                        if (path != null) {
                            if (path.traverse()) {
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
                } else if (closestFishingSpot == null || !closestFishingSpot.isOnScreen()) {
                    TilePath pathFromBank = new TilePath(TILES_TO_BANK);
                    if (pathFromBank != null) {
                        pathFromBank.reverse();
                        if (pathFromBank.traverse()) {
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
        }
    }

    public static boolean shouldGoToBank() {
        return Players.getLocal().getHpPercent() < 70;
    }
}
