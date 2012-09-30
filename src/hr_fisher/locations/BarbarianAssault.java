package hr_fisher.locations;

// Bait Priority Order:
// bait, feathers, offcut, roe, caviar

import hr_fisher.user.Util;
import org.powerbot.game.api.wrappers.Tile;

public class BarbarianAssault extends Location {

    public static final Tile[] TILES_TO_BEGINNING = new Tile[] {
            new Tile(2519, 3571, 0), new Tile(2518, 3566, 0), new Tile(2517, 3561, 0),
            new Tile(2517, 3556, 0), new Tile(2517, 3551, 0), new Tile(2517, 3546, 0),
            new Tile(2515, 3541, 0), new Tile(2513, 3536, 0), new Tile(2512, 3531, 0),
            new Tile(2511, 3526, 0), new Tile(2508, 3522, 0), new Tile(2505, 3518, 0),
            new Tile(2499, 3515, 0), new Tile(2498, 3510, 0) };

    public static final Util.FishingTypes[] TYPES_AVAILABLE = new Util.FishingTypes[]{
            Util.FishingTypes.TYPE_BARB_FISHING
    };

    public BarbarianAssault() {
        super(TILES_TO_BEGINNING, TYPES_AVAILABLE, "Barbarian Assault");
    }

    public void walkToBank() {
    }
}
