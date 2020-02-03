import java.util.concurrent.ThreadLocalRandom;

public enum HueColor {
    BLUE(254, 25600, 254, 0.1355, 0.0399, 153, "xy"),
    TEAL(254, 47104, 254, 0.1523, 0.3086, 153, "xy"),
    DARK_GREEN(254, 25600, 254, 0.1612, 0.4928, 153, "xy"),
    GREEN(254, 11350, 254, 0.1720, 0.7446, 369, "xy"),
    YELLOW_GREEN(254, 32430, 254, 0.3668, 0.5609, 153, "xy"),
    YELLOW(254, 11351, 254, 0.4603, 0.4820, 369, "xy"),
    ORANGE(254, 11350, 254, 0.5650, 0.4235, 369, "xy"),
    PINK(254, 60543, 155, 0.4813, 0.2919, 406, "xy"),
    RED(254, 47104, 254, 0.7006, 0.2993, 153, "xy"),
    PURPLE(254, 53312, 254, 0.3345, 0.1354, 184, "xy"),
    WHITE(254, 41435, 77, 0.3129, 0.3291, 153, "xy");

    private final int bri;
    private final int hue;
    private final int sat;
    private final double x;
    private final double y;
    private final int ct;
    private final String colormode;

    HueColor(int bri, int hue, int sat, double x, double y, int ct, String colormode) {
        this.bri = bri;
        this.hue = hue;
        this.sat = sat;
        this.x = x;
        this.y = y;
        this.ct = ct;
        this.colormode = colormode;
    }

    public String getJsonString() {
        return "{" +
                "\"bri\":" + bri + "," +
                "\"hue\":" + hue + "," +
                "\"sat\":" + sat + "," +
                "\"xy\":[" + x + "," + y + "]" + "," +
                "\"ct\":" + ct + "," +
                "\"colormode\":\"" + colormode + "\"" +
                "}";
    }

    public static HueColor getRandomHueColor() {
        int numColors = HueColor.values().length - 1; // Subtracting 1 removes WHITE from the options
        int rand = ThreadLocalRandom.current().nextInt(0, numColors);
        return HueColor.values()[rand];
    }
}
