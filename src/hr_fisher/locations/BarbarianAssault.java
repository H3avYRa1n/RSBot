package hr_fisher.locations;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.user.Util;
import org.powerbot.game.api.wrappers.Tile;

public class BarbarianAssault extends Location {

    public static final Tile[] TILES_TO_BEGINNING = new Tile[]{
            new Tile(2500, 3516, 0), new Tile(2503, 3520, 0), new Tile(2508, 3521, 0),
            new Tile(2512, 3524, 0), new Tile(2513, 3529, 0), new Tile(2515, 3534, 0),
            new Tile(2515, 3539, 0), new Tile(2516, 3544, 0), new Tile(2516, 3549, 0),
            new Tile(2516, 3554, 0), new Tile(2516, 3559, 0), new Tile(2517, 3564, 0),
            new Tile(2518, 3569, 0), new Tile(2519, 3574, 0)};

    public static final Util.FishingTypes[] TYPES_AVAILABLE = new Util.FishingTypes[]{
            Util.FishingTypes.TYPE_BARB_FISHING
    };

    public BarbarianAssault() {
        super(TILES_TO_BEGINNING, TYPES_AVAILABLE, "Barbarian Assault");
    }

    public void walkToBank() {
    }
}
