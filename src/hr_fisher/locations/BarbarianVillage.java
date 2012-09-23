package hr_fisher.locations;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.user.Util;
import org.powerbot.game.api.wrappers.Tile;

public class BarbarianVillage extends Location {

    public static final Tile[] TILES_TO_BANK = new Tile[]{
            new Tile(3102, 3427, 0), new Tile(3103, 3432, 0), new Tile(3101, 3437, 0),
            new Tile(3098, 3441, 0), new Tile(3096, 3446, 0), new Tile(3095, 3451, 0),
            new Tile(3092, 3455, 0), new Tile(3087, 3461, 0), new Tile(3085, 3465, 0),
            new Tile(3092, 3465, 0), new Tile(3099, 3466, 0), new Tile(3099, 3475, 0),
            new Tile(3099, 3484, 0), new Tile(3092, 3486, 0), new Tile(3087, 3487, 0),
            new Tile(3087, 3491, 0), new Tile(3093, 3490, 0)
    };

    public static final Util.FishingTypes[] TYPES_AVAILABLE = new Util.FishingTypes[]{
            Util.FishingTypes.TYPE_BAIT,
            Util.FishingTypes.TYPE_FLY
    };


    public BarbarianVillage() {
        super(TILES_TO_BANK, TYPES_AVAILABLE, "Barbarian Village");
    }


}
