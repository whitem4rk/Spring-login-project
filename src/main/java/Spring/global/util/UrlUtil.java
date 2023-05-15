package Spring.global.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class UrlUtil {

    public static JSONObject urlToJson(String url) {
        String decodeUrl = URLDecoder.decode(url, StandardCharsets.UTF_8);
        String[] parts = decodeUrl.split("&");
        Map<String, String> data = new HashMap<>();

        for (String part : parts) {
            String[] keyValue = part.split("=");
            String key = keyValue[0];
            String value = keyValue[1];
            data.put(key, value);
        }

        return new JSONObject(data);
    }

}
