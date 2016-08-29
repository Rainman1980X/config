package s3f.framework.lifecycle;

import java.util.HashMap;

public class LifecycleUrlDictionary {
    private final HashMap<String, String> keysWithUrls = new HashMap<>();

    public LifecycleUrlDictionary() {
        keysWithUrls.put("develop", "dev-s3f");
        keysWithUrls.put("production", "www-s3f");
        keysWithUrls.put("stage", "stage-s3f");
        keysWithUrls.put("test", "test-s3f");
        keysWithUrls.put("local", "local-s3f");
    }

    public void check(String[] args) {
        String lifecycleKey = null;
        if (args.length == 0) {
            throw new IllegalArgumentException("args length = 0");
        }
        for (String arg : args) {
            if (arg.startsWith("--lifecycle-")) {
                lifecycleKey = arg.replaceFirst("--lifecycle-", "");
            }
        }
        if (lifecycleKey == null) {
            throw new IllegalArgumentException("Lifecycle should not be empty.");
        }
        if (!keysWithUrls.containsKey(lifecycleKey)) {
            throw new IllegalArgumentException(lifecycleKey + " is not supported.");
        }
    }

    public String getKey(String[] args) {
        String result = "";
        for (String arg : args) {
            if (arg.startsWith("--lifecycle-")) {
                result = arg.replaceFirst("--lifecycle-", "");
                break;
            }
        }
        return result;
    }
}