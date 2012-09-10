package hr_fisher.locations;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import org.powerbot.game.api.wrappers.Tile;
import hr_fisher.user.Util;

public class Catherby extends Location {

    public static final Tile[] TILES_TO_BANK = new Tile[]{
            new Tile(2857, 3427, 0), new Tile(2852, 3425, 0), new Tile(2843, 3432, 0),
            new Tile(2838, 3432, 0), new Tile(2835, 3435, 0), new Tile(2830, 3436, 0),
            new Tile(2825, 3436, 0), new Tile(2820, 3436, 0), new Tile(2815, 3436, 0),
            new Tile(2810, 3436, 0), new Tile(2808, 3441, 0),


    };

    public static final Util.FishingTypes[] TYPES_AVAILABLE = new Util.FishingTypes[]{
            Util.FishingTypes.TYPE_NET,
            Util.FishingTypes.TYPE_BAIT,
            Util.FishingTypes.TYPE_LOBSTER_CAGE,
            Util.FishingTypes.TYPE_HARPOON
    };

    public static final int[] FISHING_SPOT_IDS = new int[]{320, 321, 322};

    public Catherby() {
        super(TILES_TO_BANK, TYPES_AVAILABLE, FISHING_SPOT_IDS, "Catherby");
    }


}
