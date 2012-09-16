package hr_fisher.locations;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import org.powerbot.game.api.wrappers.Tile;
import hr_fisher.user.Util;

public class FishingGuild extends Location {

    public static final Tile[] TILES_TO_BANK = new Tile[] {
            new Tile(2599, 3420, 0), new Tile(2594, 3420, 0),
            new Tile(2590, 3420, 0), new Tile(2585, 3421, 0)
    };

    public static final Util.FishingTypes[] TYPES_AVAILABLE = new Util.FishingTypes[]{
            Util.FishingTypes.TYPE_NET,
            Util.FishingTypes.TYPE_LOBSTER_CAGE,
            Util.FishingTypes.TYPE_HARPOON_TUNA,
            Util.FishingTypes.TYPE_HARPOON_SHARK
    };

    public FishingGuild() {
        super(TILES_TO_BANK, TYPES_AVAILABLE, "Fishing Guild");
    }


}
