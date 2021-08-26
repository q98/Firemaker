import org.powbot.api.Area;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;


import java.util.concurrent.Callable;

public class Task  {
    public static boolean hasTinderbox() {
        if (Inventory.stream().name("Tinderbox").first().valid()) {
            return true;
        } else return false;
    }

    public static boolean hasLogs() {
        if (Inventory.stream().name(Firemaking.logType).first().valid()) {
            return true;
        } else return false;
    }

    private static boolean isLightingFire() {
        if (Players.local().animation() != -1) {
            return true;
        } else return false;
    }

    public static boolean isFireUnderPlayer() {
        GameObject Fire = Objects.stream().name("Fire").nearest().first();
        if (Fire != null && Fire.tile().equals(Players.local().tile())) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isClearLane(Area area) {
        GameObject Fire = Objects.stream().name("Fire").nearest().first();
        if (Fire != null && area.contains(Fire)) {
            return false;
        } else {
            return true;
        }
    }
    public static boolean isInFireMakingArea() {
        if (Areas.GESouthArea.contains(Players.local()) || Areas.GENorthArea.contains(Players.local())) {
            return true;
        } else
            return false;
    }
    public static void burnLogs() {
        if (hasLogs() && hasTinderbox()) {
            if(!Game.tab(Game.Tab.INVENTORY)) {
                Game.tab(Game.Tab.INVENTORY);
            }
            Inventory.stream().name("Tinderbox").first().interact("Use");
        }

        if (hasLogs() && hasTinderbox()&& !isFireUnderPlayer()) {
            if(!Game.tab(Game.Tab.INVENTORY)) {
                Game.tab(Game.Tab.INVENTORY);
            }
            Inventory.stream().name(Firemaking.logType).first().interact("Use");
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !isLightingFire();

                }
            }, 950, 20);

        }
        if (Chat.canContinue()) {
            Chat.clickContinue();
        }


    }

    public static void withdrawLogs() {
        if (!Bank.opened() && !Bank.inViewport()) {
            WebWalking.walkTo(new Tile(3164, 3487), false, false);
            //MethodProvider.sleepUntil(()-> Bank.isOpen(),5000);
        } else if (!Bank.opened()) {
            Bank.open();
        }

        if (Bank.opened()) {
            if(Bank.stream().name(Firemaking.logType).first().valid()) {
                Bank.withdraw(Firemaking.logType, 27);
                //MethodProvider.sleepUntil(()-> Inventory.contains(Constants.LOG_TYPE),5000);
            }else{
              //  MethodProvider.log("No logs");

            }
        }

        if(Inventory.stream().name(Firemaking.logType).first().valid() && Bank.opened()){
            Bank.close();
            //MethodProvider.sleepUntil(()-> !Bank.isOpen(),5000);
        }




    }



}
