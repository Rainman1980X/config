package s3f.s3f_configuration.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EscapeService {
    //Todo Test
    public Map<String, String> escape(Map<String, String> keyValuePairs){
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String> keyValuePair : keyValuePairs.entrySet()) {
            String key = keyValuePair.getKey().replace(".", "#");
            String value = keyValuePair.getValue().replace(".", "#");
            result.put(key, value);
        }
        return result;
    }

    //Todo Test
    public Map<String, String> unescape(Map<String, String> keyValuePairs){
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String> keyValuePair : keyValuePairs.entrySet()) {
            String key = keyValuePair.getKey().replace("#", ".");
            String value = keyValuePair.getValue().replace("#", ".");
            result.put(key, value);
        }
        return result;
    }
}
