package hr_fisher.locations;

import hr_fisher.user.Condition;
import hr_fisher.user.Util;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.TilePath;

public class Location {
    public Tile[] tilesToBank;
    public Util.FishingTypes[] fishingTypes;
    public String locationName;

    public Location(Tile[] tiles, Util.FishingTypes[] typesAvailable, String locationName) {
        tilesToBank = tiles;
        fishingTypes = typesAvailable;
        this.locationName = locationName;
    }

    public Util.FishingTypes[] getFishingTypes() {
        return fishingTypes;
    }

    public String getLocationName() {
        return locationName;
    }

    public void walkToBank() {
        if (!Players.getLocal().isMoving()
                || Calculations.distanceTo(Walking.getDestination()) < 5) {

            TilePath pathToBank = new TilePath(tilesToBank);

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

            System.out.println("Walking to fishing spot.");
            NPC closestFishingSpot = Util.getClosestFishingSpot();

            if (closestFishingSpot != null && closestFishingSpot.isOnScreen()
                    && Calculations.distanceTo(closestFishingSpot) < 5) {
                return;
            }

            TilePath pathToBank = new TilePath(tilesToBank);
            if (pathToBank != null) {
                pathToBank.reverse();
                if (pathToBank.traverse()) {
                    System.out.println("Path traversed.");
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
