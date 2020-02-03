import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class HueUtils {

    public static void setLightColor(String bridgeIP, String bridgeUser, int lightID, HueColor color) {
        // Hue bridge cannot handle too many calls at once, guarantee it sleeps for a reasonable amount of time in
        // single-threaded calls
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String lightStateURL = "http://" + bridgeIP + "/api/" + bridgeUser + "/lights/" + lightID + "/state";
        Unirest.put(lightStateURL)
                .header("Content-Type", "application/json")
                .body(color.getJsonString())
                .asEmpty();
        Unirest.shutDown();
    }

    public static void getLightState(String bridgeIP, String bridgeUser, int lightID) {
        String lightStateURL = "http://" + bridgeIP + "/api/" + bridgeUser + "/lights/" + lightID;
        HttpResponse<JsonNode> lightStateResponse = Unirest
                .get(lightStateURL)
                .asJson();

        if (lightStateResponse.getStatus() == 200) {
            JSONObject lightStateJson = lightStateResponse
                    .getBody()
                    .getObject();
            System.out.println(lightStateJson.getJSONObject("state"));
        }

        Unirest.shutDown();
    }

    public static void getLights(String bridgeIP, String bridgeUser) {
        String getLightsURL = "http://" + bridgeIP + "/api/" + bridgeUser + "/lights";
        HttpResponse<JsonNode> getLightsResponse = Unirest
                .get(getLightsURL)
                .asJson();

        if (getLightsResponse.getStatus() == 200) {
            JSONObject getLightsJson = getLightsResponse
                    .getBody()
                    .getObject();
            System.out.println(getLightsJson.toString());
        }

        Unirest.shutDown();
    }

    public static void getGroups(String bridgeIP, String bridgeUser) {
        String getGroupsURL = "http://" + bridgeIP + "/api/" + bridgeUser + "/groups";
        HttpResponse<JsonNode> getGroupsResponse = Unirest
                .get(getGroupsURL)
                .asJson();

        if (getGroupsResponse.getStatus() == 200) {
            JSONObject getGroupsJson = getGroupsResponse
                    .getBody()
                    .getObject();
            System.out.println(getGroupsJson.toString());
        }

        Unirest.shutDown();
    }

}
