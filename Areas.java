import org.powbot.api.Area;
import org.powbot.api.Tile;

public class Areas {
    public static final Area GENorthArea = new Area(new Tile(3143, 3493), new Tile(
        3183, 3508));
    public static final Tile tilex = new Tile(3148, 3484, 0);
    public static final Tile tiley = new Tile(3179, 3477, 0);
    public static final Area GESouthArea = new Area(tilex, tiley);

    public static final Tile S1 = new Tile(3178,3483);
    public static final Tile S2 = new Tile(3178,3482);
    public static final Tile N1 = new Tile(3178,3497);
    public static final Tile N2 = new Tile(3178,3496);
    public static final Area SouthLane1 = new Area(new Tile(3145, 3483),S1);
    public static final Area SouthLane2 = new Area(new Tile(3145, 3482), S2);
    public static final Area NorthLane1 = new Area(new Tile(3145, 3497), N1);
    public static final Area NorthLane2 = new Area(new Tile(3145, 3496), N2);



}