






import com.google.common.eventbus.Subscribe;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.event.RenderEvent;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.WebWalking;
import org.powbot.api.script.AbstractScript;
import org.powbot.api.script.OptionType;
import org.powbot.api.script.ScriptConfiguration;
import org.powbot.api.script.ScriptManifest;
import org.powbot.mobile.drawing.Graphics;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;

import javax.script.ScriptEngineManager;
import java.util.*;

import static org.powbot.api.Color.rgb;


@ScriptManifest(name= "FireMaking", description="Makes fire..", version = "1.0.0")
@ScriptConfiguration(
        name = "Log",
        description = "Pick which logs you'd like to burn.",
        defaultValue = "Logs",
        allowedValues = {"Logs","Oak Logs", "Willow Logs", "Maple Logs", "Yew Logs", "Redwood Logs"},
        optionType = OptionType.STRING, // Default
        enabled = true, // Default
        visible = true // Default
)
public class Firemaking extends AbstractScript {

    public static String logType;
    public static void main(String[] args) {
        new ScriptUploader().uploadAndStart("FireMaking", "N/A", "127.0.0.1", false, false);
    }


    List<Tile> StartTile = new ArrayList<Tile>();

    @Override
    public void onStart() {
        logType = getOption("Log");
    }


    public Tile anyTile() {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(StartTile.size());
        return StartTile.get(index);
    }
    //@Subscribe
    //public void renderEvent(RenderEvent event) {
    //    Graphics g = event.getGraphics();
    //    g.fillRect(10, 10, 400, 300);
    //    g.drawRect(10, 10, 400, 300);
    //     g.setColor(rgb(255,0,0));
    //    g.setTextSize(22);
    //    g.drawString("Burner", 15, 77);
    //    g.drawString("Logs: " + logType, 15, 120);
    //    g.drawString("Running for: " + ScriptManager.INSTANCE.getRuntime(true), 15, 170);
    //}
    @Override
    public void poll() {

        if(Task.isClearLane(Areas.NorthLane1)){
            StartTile.add(Areas.N1);
        } else {
            StartTile.remove(Areas.N1);
        }
        if(Task.isClearLane(Areas.NorthLane2)){
            StartTile.add(Areas.N2);
        } else {
            StartTile.remove(Areas.N2);
        }
        if(Task.isClearLane(Areas.SouthLane1)){
            StartTile.add(Areas.S1);
        } else {
            StartTile.remove(Areas.S1);
        }
        if(Task.isClearLane(Areas.SouthLane2)){
            StartTile.add(Areas.S2);
        } else {
            StartTile.remove(Areas.S2);
        }
            if (!Inventory.stream().name(logType).first().valid()) {
                Task.withdrawLogs();
            }
            if (Inventory.stream().name(logType).first().valid() && !Task.isInFireMakingArea()) {
                WebWalking.moveTo(anyTile(), true, false);

                
               Condition.sleep(1000);
            }
            if (Inventory.stream().name(logType).first().valid() && Task.isInFireMakingArea()) {
                if (Task.isFireUnderPlayer()) {
                    WebWalking.moveTo(anyTile(), true, false);
                    Condition.sleep(1000);
                } else {
                    Task.burnLogs();
                }
        }
        return;
    }







}
