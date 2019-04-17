import java.util.HashMap;
import java.util.Map;

public enum FootprintsColor {
    PURPLE_FOOTPRINT( 1),
    YELLOW_FOOTPRINT(2),
    GREEN_FOOTPRINT( 3),
    RED_FOOTPRINT(5),
    ORANGE_FOOTPRINT( 4);

    private static Map map = new HashMap();
    private final int number;

    FootprintsColor(final int number) {
        this.number = number;
    }


    static {
        for (FootprintsColor footprintsColor : FootprintsColor.values()) {
            map.put(footprintsColor.number, footprintsColor);
        }
    }

    public static FootprintsColor valueOf(int number) {
        return (FootprintsColor) map.get(number);
    }

    public int getValue() {
        return number;
    }
}
