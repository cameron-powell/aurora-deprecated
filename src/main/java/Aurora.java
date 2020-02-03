import java.time.LocalTime;

public class Aurora {

    public static void main(String... args) {
        // Get the filepath to the configuration.json file from the first argument
        final String configurationFilePath = args.length == 1 ? args[0] : null;
        if (configurationFilePath == null || !configurationFilePath.contains("configuration.json")) {
            System.out.println("ERROR: Improper configuration.json filepath argument provided, exiting...");
            return;
        }

        // Load the configuration data to gain access to the configured Philips Hue Bridge
        final Configuration configuration = Configuration.loadConfigurationFromFilePath(configurationFilePath);
        if (configuration == null) {
            return;
        }

        // Cache the temperature for the specified ZIP code
        final int currentTemperature = WeatherUtils.getCurrentTemperature(
                configuration.getOpenWeatherAPIKey(),
                configuration.getZipCode());

        // Cache the current hour and minute
        final LocalTime currentTime = LocalTime.now();
        final int currentHour = currentTime.getHour(); // 0 - 23
        final int currentMinute = currentTime.getMinute(); // 0 - 59

        /** DIRECTIVE EXECUTION */
        for (LightDirective lightDirective : configuration.getLightDirectives()) {
            HueColor color;
            switch (lightDirective.getDirective()) {
                case CURRENT_TEMPERATURE:
                    color = getTemperatureColor(currentTemperature);
                    break;
                case CURRENT_HOUR:
                    color = getHourColor(currentHour);
                    break;
                case CURRENT_MINUTE:
                    color = getMinuteColor(currentMinute);
                    break;
                case RANDOM_COLOR:
                    color = HueColor.getRandomHueColor();
                    break;
                default:
                    color = HueColor.WHITE;
            }

            HueUtils.setLightColor(
                    configuration.getHueBridgeIP(),
                    configuration.getHueBridgeUser(),
                    lightDirective.getLightID(),
                    color
            );
        }
    }

    // TODO: Make white an error color
    private static HueColor getTemperatureColor(int temperature) {
        HueColor color;

        if (temperature <= 32) {
            color = HueColor.BLUE;  // <= 32 F
        } else if (temperature < 40) {
            color = HueColor.TEAL;  // 33 - 39 F
        } else if (temperature < 50) {
            color = HueColor.DARK_GREEN;  // 40 - 49 F
        } else if (temperature < 60) {
            color = HueColor.GREEN;  // 50 - 59 F
        } else if (temperature < 70) {
            color = HueColor.YELLOW_GREEN;  // 60 - 69 F
        } else if (temperature < 80) {
            color = HueColor.YELLOW;  // 70 - 79 F
        } else if (temperature < 90) {
            color = HueColor.ORANGE;  // 80 - 89 F
        } else if (temperature < 100) {
            color = HueColor.RED;  // 90 - 99 F
        } else {
            color = HueColor.WHITE; // >= 100 F
        }

        return color;
    }

    private static HueColor getHourColor(int hour) {
        HueColor color;

        switch (hour % 6) {
            case 0:
                color = HueColor.BLUE;
                break;
            case 1:
                color = HueColor.GREEN;
                break;
            case 2:
                color = HueColor.YELLOW;
                break;
            case 3:
                color = HueColor.PINK;
                break;
            case 4:
                color = HueColor.RED;
                break;
            case 5:
                color = HueColor.PURPLE;
                break;
            default:
                color = HueColor.WHITE;
                break;
        }

        return color;
    }

    private static HueColor getMinuteColor(int minute) {
        HueColor color;

        if (minute < 10) {
            color = HueColor.BLUE;
        } else if (minute < 20) {
            color = HueColor.GREEN;
        } else if (minute < 30) {
            color = HueColor.YELLOW;
        } else if (minute < 40) {
            color = HueColor.PINK;
        } else if (minute < 50) {
            color = HueColor.RED;
        } else {
            color = HueColor.PURPLE;
        }

        return color;
    }
}
