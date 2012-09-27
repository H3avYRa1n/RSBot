package hr_fisher.locations;

import hr_fisher.user.Condition;
import hr_fisher.user.Util;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Time;
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

    public void walkToBank() {
        TilePath pathToBank = new TilePath(tilesToBank);

        if (pathToBank != null) {
            pathToBank.traverse();
            Task.sleep(1000, 1500);
        }
    }

    public void walkToFishingSpot() {
        NPC closestFishingSpot = Util.getClosestFishingSpot();

        if (closestFishingSpot != null && closestFishingSpot.isOnScreen()
                && Calculations.distanceTo(closestFishingSpot) < 5) {
            return;
        }

        TilePath pathToBank = new TilePath(tilesToBank);

        if (pathToBank != null) {
            pathToBank.reverse().traverse();
            Task.sleep(1000, 1500);
        }
    }

    @Override
    public String toString() {
        return locationName;
    }
}
