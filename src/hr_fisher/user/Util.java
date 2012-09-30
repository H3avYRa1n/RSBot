package hr_fisher.user;

import hr_fisher.locations.LivingRockCaverns;
import hr_fisher.locations.ShiloVillage;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class Util {

    public enum FishingTypes {
        TYPE_NET(new int[]{}, new int[]{317, 321, 7944}, new String[]{"shrimp", "anchovies", "monkfish"}, "Net", "Net Fishing"),
        TYPE_BAIT(new int[]{313}, new int[]{349, 345, 327}, new String[]{"pike", "herring", "sardine"}, "Bait", "Bait Fishing"),
        TYPE_FLY(new int[]{314}, new int[]{335, 331}, new String[]{"trout", "salmon"}, "Lure", "Fly Fishing"),
        TYPE_HARPOON_TUNA(new int[]{}, new int[]{359, 371}, new String[]{"tuna", "swordfish"}, "Harpoon", "Harpoon (Swordies)"),
        TYPE_HARPOON_SHARK(new int[]{}, new int[]{383}, new String[]{"shark"}, "Harpoon", "Harpoon (Sharks)"),
        TYPE_LOBSTER_CAGE(new int[]{}, new int[]{377}, new String[]{"lobster"}, "Cage", "Cage Fishing"),
        TYPE_ROCKTAIL_BAIT(new int[]{15263}, new int[]{15270}, new String[]{"rocktail"}, "Bait", "Rocktail Fishing"),
        TYPE_BARB_FISHING(new int[]{}, new int[]{11328, 11330, 11332}, new String[]{"leaping trout", "leaping salmon", "leaping sturgeon"}, "Use-rod", "Barb Fishing");

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

            Task.sleep(50);
        }

        return false;
    }

    public static boolean hasNeededItems() {
        if (Variables.chosenFishingType.getNeededItems().length > 0)
            return Inventory.containsAll(Variables.chosenFishingType.getNeededItems());

        if(Variables.chosenFishingType == FishingTypes.TYPE_BARB_FISHING) {
            return Inventory.containsOneOf(313, 314, 11334);
        }

        return true;
    }

    public static boolean dropTuna() {
        if (!Variables.dropTuna || Inventory.getCount(Variables.TUNA_ID) == 0)
            return false;

        for (Item i : Inventory.getItems()) {
            if (i.getId() == Variables.TUNA_ID) {
                i.getWidgetChild().interact("Drop");
                Task.sleep(200, 300);
            }
        }

        return true;
    }

    public static void logout() {
        if (Bank.isOpen()) {
            Bank.close();
            Task.sleep(1000, 1500);
        }

        Game.logout(false);
        Task.sleep(5000);
        Variables.hasStarted = false;
    }

    public static int getPriceOfItem(int id) throws IOException {
        String price;
        URL url = new URL("http://services.runescape.com/m=itemdb_rs/viewitem.ws?obj=" + id);
        URLConnection con = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            if (line.contains("<td>")) {
                price = line.substring(line.indexOf(">") + 1,
                        line.indexOf("/") - 1);
                price = price.replace(",", "");
                try {
                    return Integer.parseInt(price);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }
        return -1;
    }

    public static NPC getClosestFishingSpot() {

        return NPCs.getNearest(new Filter<NPC>() {
            public boolean accept(NPC npc) {
                String[] actions = npc.getActions();
                for (String s : actions) {
                    if (s != null && s.contains(Variables.chosenFishingType.getInteractString())) {

                        if (Variables.chosenLocation instanceof LivingRockCaverns)
                            return npc.getName().contains("Rocktail");

                        else if (Variables.chosenLocation instanceof ShiloVillage)
                            return ShiloVillage.SOUTHERN_AREA.contains(npc.getLocation());

                        else if (Variables.chosenFishingType == Util.FishingTypes.TYPE_HARPOON_SHARK) {
                            if (npc.getId() == 322 || npc.getId() == 313)
                                return true;

                            return false;

                        } else if (Variables.chosenFishingType == Util.FishingTypes.TYPE_HARPOON_TUNA) {
                            if (npc.getId() == 320 || npc.getId() == 312 || npc.getId() == 324)
                                return true;

                            return false;
                        }
                        return true;
                    }
                }

                return false;
            }
        });
    }

    public static void dropFish(int i) {
        for (Item item : Inventory.getItems()) {
            if (item.getId() == i) {
                item.getWidgetChild().interact("Drop");
                Task.sleep(200, 300);
            }
        }
    }

}
