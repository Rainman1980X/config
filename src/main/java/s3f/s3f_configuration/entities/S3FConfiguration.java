package s3f.s3f_configuration.entities;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class S3FConfiguration {
    @Id
    private String id;
    private Map<String, String> keyValuePairs;
    private final String version;
    private final String lifecycle;
    private final String service;

    public S3FConfiguration(String id, Map<String,String> keyValuePairs, String version, String lifecycle, String service) {
        this.id = id;
        this.keyValuePairs = keyValuePairs;
        this.version = version;
        this.lifecycle = lifecycle;
        this.service = service;
    }

    public String getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public String getLifecycle() {
        return lifecycle;
    }

    public String getService() {
        return service;
    }

    public Map<String, String> getKeyValuePairs() {
        return keyValuePairs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        S3FConfiguration that = (S3FConfiguration) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (keyValuePairs != null ? !keyValuePairs.equals(that.keyValuePairs) : that.keyValuePairs != null)
            return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (lifecycle != null ? !lifecycle.equals(that.lifecycle) : that.lifecycle != null) return false;
        return !(service != null ? !service.equals(that.service) : that.service != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (keyValuePairs != null ? keyValuePairs.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (lifecycle != null ? lifecycle.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "S3FConfiguration{" +
                "id='" + id + '\'' +
                ", keyValuePairs=" + keyValuePairs +
                ", version='" + version + '\'' +
                ", lifecycle='" + lifecycle + '\'' +
                ", service='" + service + '\'' +
                '}';
    }
}