import java.util.HashMap;
import java.util.Map;

public enum Wildcards {
    HERRING( 1),
    CLOCK( 2),
    COOL_CAT( 3),
    RAINBOW_CAT(4);

    private static Map map = new HashMap();
    private final int number;


    Wildcards( int number)
    {
        this.number = number;
    }
    static {
        for (Wildcards wildcards : Wildcards.values()) {
            map.put(wildcards.number, wildcards);
        }
    }

    public static Wildcards valueOf(int number) {
        return (Wildcards) map.get(number);
    }

    public int getValue() {
        return number;
    }
}