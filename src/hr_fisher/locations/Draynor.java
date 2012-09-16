package hr_fisher.locations;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.user.Util;
import org.powerbot.game.api.wrappers.Tile;
import hr_fisher.user.Util;

public class Draynor extends Location {

    public static final Tile[] TILES_TO_BANK = new Tile[] {
            new Tile(3086, 3228, 0),
            new Tile(3091, 3233, 0),
            new Tile(3092, 3243, 0)
    };

    public static final Util.FishingTypes[] TYPES_AVAILABLE = new Util.FishingTypes[] {
            Util.FishingTypes.TYPE_NET,
            Util.FishingTypes.TYPE_BAIT
    };

    public Draynor() {
        super(TILES_TO_BANK, TYPES_AVAILABLE, "Draynor");
    }


}
