package hr_fisher.user;/*
    Name:
    Version:
    Author(s):
    
    Description:
    
*/

import hr_fisher.locations.*;

public class Variables {

    public static final int[] ITEMS_TO_DROP = new int[]{6961, 6963, 14664};

    public static final int ITEM_FEATHER = 314;
    public static final int ITEM_BAIT = 313;
    public static final int ITEM_URN = 20348;

    public static final int TUNA_ID = 359;

    public static boolean hasStarted = false;
    public static boolean isDropping = false;
    public static boolean dropTuna = false;

    public static long startTime = 0;
    public static int startXP = 0;
    public static int[] fishCaught;
    public static int[] fishPrice;

    public static int startLevel = 0;

    public static Location[] locations = new Location[]{
            new AlKharid(),
            new BarbarianAssault(),
            new BarbarianVillage(),
            new Catherby(),
            new Draynor(),
            new FishingGuild(),
            new Karamja(),
            //new LivingRockCaverns(),
            new Lumbridge(),
            new Piscatoris(),
            new ShiloVillage()
    };


    public static Location chosenLocation = locations[0];

    public static Util.FishingTypes chosenFishingType = Util.FishingTypes.TYPE_BAIT;

}
