package hr_fisher.locations;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import org.powerbot.game.api.wrappers.Tile;
import hr_fisher.user.Util;

public class AlKharid extends Location {

    public static final Tile[] TILES_TO_BANK = new Tile[] {
            new Tile(3242, 3151, 0), new Tile(3242, 3156, 0), new Tile(3242, 3161, 0),
            new Tile(3241, 3166, 0), new Tile(3245, 3169, 0), new Tile(3250, 3170, 0),
            new Tile(3253, 3174, 0), new Tile(3258, 3174, 0), new Tile(3263, 3174, 0),
            new Tile(3269, 3167, 0)
    };

    public static final Util.FishingTypes[] TYPES_AVAILABLE = new Util.FishingTypes[] {
            Util.FishingTypes.TYPE_NET,
            Util.FishingTypes.TYPE_BAIT
    };

    public static final int[] FISHING_SPOT_IDS = new int[]{4908};

    public AlKharid() {
        super(TILES_TO_BANK, TYPES_AVAILABLE, FISHING_SPOT_IDS, "Al-Kharid");
    }


}