import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class WeatherUtils {

    /**
     * Get the current temperature from OpenWeather's API
     * Do not exceed 60 calls per minute (Account may be suspended)
     * Do not call more than once every ten minutes in production mode as their API doesn't update more frequently than that
     *
     * @param openWeatherAPIKey => The application's API key to be able to talk to OpenWeather's API
     * @param zipCode => The ZIP code of the location you want the current weather for
     * @return 0 if an error occurred or not in PRODUCTION mode, the current temperature (int) in F otherwise
     */
    // TODO: Make white the error color
    public static int getCurrentTemperature(String openWeatherAPIKey, String zipCode) {
        // Do not perform API calls if not in production mode
        if (Configuration.getMode() != Configuration.Mode.PROD) {
            return 0;
        }

        int currentTemperature = 0;

        HttpResponse<JsonNode> openWeatherResponse = Unirest
                .get("http://api.openweathermap.org/data/2.5/weather?zip=" + zipCode + ",us&units=imperial&APPID=" + openWeatherAPIKey)
                .asJson();

        if (openWeatherResponse.getStatus() == 200) {
            JSONObject weatherJson = openWeatherResponse
                    .getBody()
                    .getObject();

            // Will return a 429 status code if call limit exceeded
            if ((Integer) weatherJson.get("cod") == 200) {
                double preciseTemperature = (Double) weatherJson
                        .getJSONObject("main")
                        .get("temp");
                // Use only integer portion of temperature
                currentTemperature = (int) preciseTemperature;
            }

        }

        Unirest.shutDown();

        return currentTemperature;
    }

    @Deprecated // OpenWeather is more precise, prefer it
//    public static int getCurrentTemperatureNOAA() {
//        int currentTemperature = 0;
//        HttpResponse<JsonNode> weatherResponse = Unirest
//                .get()
//                .asJson();
//
//        if (weatherResponse.getStatus() == 200) {
//            JSONObject weatherJson = weatherResponse
//                    .getBody()
//                    .getObject();
//            currentTemperature = (int) weatherJson
//                    .getJSONObject("properties")
//                    .getJSONArray("periods")
//                    .getJSONObject(0)
//                    .get("temperature");
//        }
//
//        Unirest.shutDown();
//
//        return currentTemperature;
//    }
}
