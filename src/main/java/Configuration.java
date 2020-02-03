import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Configuration {

    // Defined as runMode in the configuration file
    private static Mode mode;
    // A DTO for loading mode from configuration file
    private String runMode;

    private String hueBridgeUser;
    private String hueBridgeIP;

    private String openWeatherAPIKey;
    private String zipCode;

    private List<LightDirective> lightDirectives;

    /** Constructors */
    public Configuration() {}

    /** Getters and Setters */
    public static Mode getMode() {
        return mode;
    }

    private void setMode() {
        try {
            mode = Mode.valueOf(runMode);
        } catch(IllegalArgumentException e) {
            System.out.println("WARNING: Invalid runMode, must be either DEV or PROD.  Defaulting to DEV...");
            mode = Mode.DEV;
        }
    }

    public String getRunMode() {
        return runMode;
    }

    public void setRunMode(String runMode) {
        this.runMode = runMode;
        setMode();
    }



    public String getHueBridgeUser() {
        return hueBridgeUser;
    }

    public void setHueBridgeUser(String hueBridgeUser) {
        this.hueBridgeUser = hueBridgeUser;
    }

    public String getHueBridgeIP() {
        return hueBridgeIP;
    }

    public void setHueBridgeIP(String hueBridgeIP) {
        this.hueBridgeIP = hueBridgeIP;
    }

    public String getOpenWeatherAPIKey() {
        return openWeatherAPIKey;
    }

    public void setOpenWeatherAPIKey(String openWeatherAPIKey) {
        this.openWeatherAPIKey = openWeatherAPIKey;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public List<LightDirective> getLightDirectives() {
        return lightDirectives;
    }

    public void setLightDirectives(List<LightDirective> lightDirectives) {
        this.lightDirectives = lightDirectives;
    }

    /** Static Utility Methods */
    public static Configuration loadConfigurationFromFilePath(String configurationFilePath) {
        Configuration configuration = null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            configuration = objectMapper.readValue(new File(configurationFilePath), Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return configuration;
    }

    public enum Mode {
        DEV,
        PROD
    }
}
