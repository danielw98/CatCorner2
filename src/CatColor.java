import java.util.HashMap;
import java.util.Map;

public enum CatColor {
    PURPLE_CAT( 1),
    YELLOW_CAT(2),
    GREEN_CAT( 3),
    ORANGE_CAT(4),
    RED_CAT( 5);

    private static Map map = new HashMap();
    private final int number;

    CatColor(final int number) {
       this.number = number;
    }


    static {
        for (CatColor catColor : CatColor.values()) {
            map.put(catColor.number, catColor);
        }
    }

    public static CatColor valueOf(int number) {
        return (CatColor) map.get(number);
    }

    public int getValue() {
        return number;
    }
}
