package hr_fisher.locations;

import hr_fisher.user.Variables;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.TilePath;
import hr_fisher.user.Util;
import hr_fisher.user.Variables;

public class Location {
    public Tile[] tilesToBank;
    public Util.FishingTypes[] fishingTypes;
    public String locationName;
    public int[] fishingSpotIDs;

    public Location(Tile[] tiles, Util.FishingTypes[] typesAvailable, int[] fishingSpotIDs, String locationName) {
        tilesToBank = tiles;
        fishingTypes = typesAvailable;
        this.locationName = locationName;
        this.fishingSpotIDs = fishingSpotIDs;
    }

    public Util.FishingTypes[] getFishingTypes() {
        return fishingTypes;
    }

    public int[] getFishingSpotIDs() {
        return fishingSpotIDs;
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

            if(closestFishingSpot != null && closestFishingSpot.isOnScreen()
                    && Calculations.distance(Players.getLocal().getLocation(), closestFishingSpot.getLocation()) < 5) {
                return;
            }

            TilePath pathToBank = new TilePath(tilesToBank);
            if (pathToBank != null) {
                pathToBank.reverse();
                if (pathToBank.traverse()) {
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
