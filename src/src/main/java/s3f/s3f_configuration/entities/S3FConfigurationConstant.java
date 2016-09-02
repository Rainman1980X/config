package s3f.s3f_configuration.entities;

import org.springframework.data.annotation.Id;

import java.util.Map;

public class S3FConfigurationConstant {
    @Id
    private String id;
    private final Map<String, String> keyValuePairs;
    private final String version;
    private final String lifecycle;

    public S3FConfigurationConstant(String id, Map<String, String> keyValuePairs, String version, String lifecycle) {
        this.id = id;
        this.version = version;
        this.lifecycle = lifecycle;
        this.keyValuePairs = keyValuePairs;
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

    public Map<String, String> getKeyValuePairs() {
        return keyValuePairs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        S3FConfigurationConstant that = (S3FConfigurationConstant) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (keyValuePairs != null ? !keyValuePairs.equals(that.keyValuePairs) : that.keyValuePairs != null)
            return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        return !(lifecycle != null ? !lifecycle.equals(that.lifecycle) : that.lifecycle != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (keyValuePairs != null ? keyValuePairs.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (lifecycle != null ? lifecycle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "S3FConfigurationConstant{" +
                "id='" + id + '\'' +
                ", keyValuePairs=" + keyValuePairs +
                ", version='" + version + '\'' +
                ", lifecycle='" + lifecycle + '\'' +
                '}';
    }
}
