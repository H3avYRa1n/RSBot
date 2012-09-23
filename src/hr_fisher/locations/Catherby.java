package hr_fisher.locations;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.user.Util;
import org.powerbot.game.api.wrappers.Tile;

public class Catherby extends Location {

    public static final Tile[] TILES_TO_BANK = new Tile[]{
            new Tile(2859, 3425, 0), new Tile(2854, 3425, 0), new Tile(2849, 3427, 0),
            new Tile(2844, 3430, 0), new Tile(2839, 3432, 0), new Tile(2834, 3433, 0),
            new Tile(2829, 3435, 0), new Tile(2824, 3436, 0), new Tile(2819, 3435, 0),
            new Tile(2814, 3435, 0), new Tile(2809, 3436, 0), new Tile(2808, 3441, 0)
    };

    public static final Util.FishingTypes[] TYPES_AVAILABLE = new Util.FishingTypes[]{
            Util.FishingTypes.TYPE_NET,
            Util.FishingTypes.TYPE_BAIT,
            Util.FishingTypes.TYPE_LOBSTER_CAGE,
            Util.FishingTypes.TYPE_HARPOON_SHARK,
            Util.FishingTypes.TYPE_HARPOON_TUNA
    };

    public Catherby() {
        super(TILES_TO_BANK, TYPES_AVAILABLE, "Catherby");
    }


}
