package hr_fisher;

import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.map.TilePath;
import org.powerbot.game.api.wrappers.node.GroundItem;

@Manifest(authors = { "Gerrard" }, name = "SpiderSlasher", description = "Kills Spiders and loots Carcass's for mega gp", version = 1.0)
public class SpiderSlasher extends ActiveScript {

    private Tree jobs = null;

    @Override
    public int loop() {

        if(jobs == null) {
            // basically you create a tree which holds all the
            // nodes (now called Nodes)
            jobs = new Tree(new Node[]{
                    new Attack(), new Pickup(), new BankingMethod(),
                    new WalkingToBank(), new WalkingToSpiders()}
            );
        }

        // this gets the first strategy whose validate() (now activate()) method returns true.
        final Node job = jobs.state();

        if(job != null) {
            // These three lines just run the execute() method in the job.
            // They're all needed, so just put them.
            jobs.set(job);
            getContainer().submit(job);
            job.join();
        }

        // Return a number greater than 0 here. I suggest more than 100
        // Otherwise you'll run into problems with the script randomly
        // stopping.
        return Random.nextInt(200, 400);
    }


    public static final int SPIDER_ID = 62;
    public static final int CARCASS_ID = 6291;
    public static final int BOOTH_ID = 2213;

    public static final Area spiderArea = new Area(new Tile[] {
            new Tile(2677, 3101, 0), new Tile(2680, 3098, 0),
            new Tile(2686, 3088, 0), new Tile(2685, 3085, 0),
            new Tile(2684, 3081, 0), new Tile(2681, 3078, 0),
            new Tile(2677, 3082, 0), new Tile(2675, 3080, 0),
            new Tile(2671, 3081, 0), new Tile(2667, 3083, 0),
            new Tile(2665, 3084, 0), new Tile(2661, 3089, 0),
            new Tile(2669, 3093, 0), new Tile(2672, 3098, 0) });
    public static final Area bankArea = new Area(new Tile[] {
            new Tile(2608, 3097, 0), new Tile(2613, 3097, 0),
            new Tile(2613, 3089, 0), new Tile(2608, 3088, 0) });
    public final static Tile[] pathToBooth = { new Tile(2676, 3093, 0),
            new Tile(2677, 3098, 0), new Tile(2679, 3103, 0),
            new Tile(2677, 3108, 0), new Tile(2672, 3110, 0),
            new Tile(2667, 3111, 0), new Tile(2662, 3112, 0),
            new Tile(2657, 3111, 0), new Tile(2652, 3110, 0),
            new Tile(2651, 3105, 0), new Tile(2651, 3100, 0),
            new Tile(2648, 3096, 0), new Tile(2644, 3093, 0),
            new Tile(2642, 3088, 0), new Tile(2637, 3088, 0),
            new Tile(2632, 3088, 0), new Tile(2627, 3089, 0),
            new Tile(2626, 3094, 0), new Tile(2623, 3098, 0),
            new Tile(2621, 3103, 0), new Tile(2616, 3104, 0),
            new Tile(2612, 3101, 0), new Tile(2607, 3098, 0),
            new Tile(2606, 3093, 0), new Tile(2611, 3093, 0) };

    public class Attack extends Node {
        @Override
        public void execute() {
            NPC spider = NPCs.getNearest(new Filter<NPC>() {
                @Override
                public boolean accept(NPC n) {
                    return (n.getId() == SPIDER_ID);
                }
            });
            if (Players.getLocal().getInteracting() != spider) {
                if (spider.validate()) {
                    if (spider.isOnScreen()) {
                        if (!spider.isInCombat()) {
                            spider.interact("Attack");
                            Task.sleep(2000, 2200);
                        }
                        if (spider.isInCombat()) {
                            Task.sleep(400, 600);
                        }
                    }
                    if (spider.isOnScreen()) {
                        Camera.turnTo(spider);
                    }
                }
                if (spider.validate()) {
                    Task.sleep(300, 500);
                }
            }
        }

        @Override
        public boolean activate() {
            return spiderArea.contains(Players.getLocal().getLocation())
                    && !Inventory.isFull();
        }
    }

    public boolean LootCheck() {
        GroundItem g = GroundItems.getNearest(CARCASS_ID);
        if (g != null) {
            return true;
        }

        return false;
    }

    private class Pickup extends Node {
        @Override
        public void execute() {
            GroundItem gi = GroundItems.getNearest(CARCASS_ID);
            if (gi != null) {
                if (!gi.isOnScreen()) {
                    Walking.walk(gi);
                    Task.sleep(100, 200);
                } else {
                    gi.interact("Take");
                    Task.sleep(600, 1000);
                }
                Task.sleep(550, 750);
            }
        }

        @Override
        public boolean activate() {
            if (LootCheck() == true && !Players.getLocal().isInCombat()) {
                return true;
            } else {
                return !Inventory.isFull();
            }
        }

    }

    private class WalkingToBank extends Node {
        @Override
        public void execute() {
            TilePath pathToBank = new TilePath(pathToBooth);
            if (pathToBank != null) {
                if (pathToBank.traverse()) {
                    Task.sleep(1500, 2000);
                }
            }
        }

        @Override
        public boolean activate() {
            Entity bank = Bank.getNearest();
            return (bank == null || bank.isOnScreen() && Inventory.isFull());
        }
    }

    public class BankingMethod extends Node {
        @Override
        public void execute() {
            if (!Bank.isOpen()) {
                Bank.open();
                Task.sleep(2000, 2500);
            } else {
                if (Inventory.getCount(true, CARCASS_ID) > 0) {
                    Bank.deposit(CARCASS_ID,
                            Inventory.getCount(true, CARCASS_ID));
                    Task.sleep(500, 750);
                }
            }
        }

        @Override
        public boolean activate() {
            Entity bank = Bank.getNearest();
            return bank != null && bank.isOnScreen();
        }
    }

    public class WalkingToSpiders extends Node {

        @Override
        public void execute() {
            Walking.newTilePath(pathToBooth).reverse().traverse();
        }

        @Override
        public boolean activate() {

            return false;
        }

    }
}