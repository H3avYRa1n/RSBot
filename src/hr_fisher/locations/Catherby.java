package hr_fisher.locations;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import org.powerbot.game.api.wrappers.Tile;
import hr_fisher.user.Util;

public class Catherby extends Location {

    public static final Tile[] TILES_TO_BANK = new Tile[] {
            new Tile(2827, 2967, 0), new Tile(2832, 2967, 0), new Tile(2837, 2967, 0),
            new Tile(2842, 2968, 0), new Tile(2847, 2968, 0), new Tile(2856, 2971, 0),
            new Tile(2861, 2971, 0), new Tile(2864, 2967, 0), new Tile(2862, 2962, 0),
            new Tile(2857, 2960, 0), new Tile(2852, 2960, 0), new Tile(2847, 2963, 0),
            new Tile(2848, 2959, 0), new Tile(2852, 2955, 0) };

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
