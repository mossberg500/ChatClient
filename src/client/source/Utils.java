package client.source;

public class Utils {
    private static final String URL = "http://localhost";
    private static final int PORT = 8080;

    public static String getURL() {
        return URL + ":" + PORT+"/Server_war_exploded";
    }
}