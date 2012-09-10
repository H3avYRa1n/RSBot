package hr_fisher.user;

import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class Util {
    public enum FishingTypes {
        TYPE_NET(new int[]{303}, new int[]{317, 321}, new String[]{"shrimp", "anchovies"}, "Net", "Net Fishing"),
        TYPE_BAIT(new int[]{307, 313}, new int[]{349, 345, 327}, new String[]{"pike", "herring", "sardine"}, "Bait", "Bait Fishing"),
        TYPE_FLY(new int[]{305, 314}, new int[]{335}, new String[]{"trout"}, "Lure", "Fly Fishing"),
        TYPE_HARPOON(new int[]{311}, new int[]{359, 371, 383}, new String[]{"tuna", "swordfish", "shark"}, "Harpoon", "Harpoon Fishing"),
        TYPE_LOBSTER_CAGE(new int[]{301}, new int[]{377}, new String[]{"lobster"}, "Cage", "Cage Fishing");

        private int[] itemsNeeded;
        private int[] possibleFish;
        private String interactString;
        private String fishingName;
        private String[] fishNames;

        FishingTypes(int[] itemsNeeded, int[] possibleFish, String[] fishNames, String interactString, String fishingName) {
            this.itemsNeeded = itemsNeeded;
            this.possibleFish = possibleFish;
            this.interactString = interactString;
            this.fishingName = fishingName;
            this.fishNames = fishNames;
        }

        public int[] getNeededItems() {
            return itemsNeeded;
        }

        public String getInteractString() {
            return interactString;
        }

        public int[] getPossibleFish() {
            return possibleFish;
        }

        public String getName() {
            return fishingName;
        }

        public String[] getFishNames() {
            return fishNames;
        }
    }

    public static void depositAll() {
        Item[] items = Inventory.getItems();
        for (Item i : items) {
            Bank.deposit(i.getId(), Inventory.getCount(true, i.getId()));

            final Item temp = i;
            waitFor(3000, new Condition() {
                @Override
                public boolean validate() {
                    return Inventory.getCount(true, temp.getId()) == 0;
                }
            });
        }
    }

    public static boolean waitFor(int timeOut, Condition c) {
        Timer t = new Timer(timeOut);

        while (t.isRunning()) {
            if (c.validate())
                return true;

            Time.sleep(50);
        }

        return false;
    }

    public static boolean hasNeededItems() {
        return Inventory.containsAll(Variables.chosenFishingType.getNeededItems());
    }

    public static boolean dropTuna() {
        if(!Variables.dropTuna || Inventory.getCount(Variables.TUNA_ID) == 0)
            return false;

        for(Item i : Inventory.getItems()) {
            if(i.getId() == Variables.TUNA_ID) {
                i.getWidgetChild().interact("Drop");
                Time.sleep(200, 300);
            }
        }

        return true;
    }
    public static void logout() {
        if(Bank.isOpen()) {
            Bank.close();
            Time.sleep(1000, 1500);
        }

        Game.logout(false);
        Time.sleep(5000);
        Variables.hasStarted = false;
    }

    public static int getPriceOfItem(int id) throws IOException
    {
        String price;
        URL url = new URL("http://services.runescape.com/m=itemdb_rs/viewitem.ws?obj=" + id);
        URLConnection con = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        while ((line = in.readLine()) != null)
        {
            if (line.contains("<td>"))
            {
                price = line.substring(line.indexOf(">") + 1,
                        line.indexOf("/") - 1);
                price = price.replace(",", "");
                try
                {
                    return Integer.parseInt(price);
                }
                catch (NumberFormatException e)
                {
                    return 0;
                }
            }
        }
        return -1;
    }

}
