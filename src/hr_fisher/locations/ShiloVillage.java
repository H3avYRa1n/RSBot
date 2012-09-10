package hr_fisher.locations;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import org.powerbot.game.api.wrappers.Tile;
import hr_fisher.user.Util;

public class ShiloVillage extends Location {

    public static final Tile[] TILES_TO_BANK = new Tile[] {
            new Tile(2857, 2972, 0), new Tile(2852, 2971, 0), new Tile(2849, 2967, 0),
            new Tile(2848, 2962, 0), new Tile(2851, 2958, 0), new Tile(2851, 2954, 0)
    };

    public static final Util.FishingTypes[] TYPES_AVAILABLE = new Util.FishingTypes[] {
            Util.FishingTypes.TYPE_BAIT,
            Util.FishingTypes.TYPE_FLY
    };

    public static final int[] FISHING_SPOT_IDS = new int[]{317};

    public ShiloVillage() {
        super(TILES_TO_BANK, TYPES_AVAILABLE, FISHING_SPOT_IDS, "Shilo Village");
    }
}
