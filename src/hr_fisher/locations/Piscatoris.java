package hr_fisher.locations;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.user.Util;
import org.powerbot.game.api.wrappers.Tile;

public class Piscatoris extends Location {

    public static final Tile[] TILES_TO_BANK = new Tile[]{new Tile(2310, 3700, 0), new Tile(2315, 3698, 0), new Tile(2320, 3698, 0),
            new Tile(2325, 3697, 0), new Tile(2330, 3697, 0), new Tile(2333, 3693, 0),
            new Tile(2333, 3688, 0), new Tile(2329, 3687, 0)};


    public static final Util.FishingTypes[] TYPES_AVAILABLE = new Util.FishingTypes[]{
            Util.FishingTypes.TYPE_NET,
            Util.FishingTypes.TYPE_HARPOON_TUNA
    };

    public Piscatoris() {
        super(TILES_TO_BANK, TYPES_AVAILABLE, "Piscatoris");
    }
}
