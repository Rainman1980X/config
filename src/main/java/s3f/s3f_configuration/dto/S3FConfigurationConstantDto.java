package s3f.s3f_configuration.dto;

import org.springframework.data.annotation.Id;

import java.util.Map;

public class S3FConfigurationConstantDto {
    private final Map<String, String> keyValuePairs;
    private final String version;
    private final String lifecycle;

    public S3FConfigurationConstantDto( Map<String, String> keyValuePairs, String version, String lifecycle) {
        this.version = version;
        this.lifecycle = lifecycle;
        this.keyValuePairs = keyValuePairs;
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

        S3FConfigurationConstantDto that = (S3FConfigurationConstantDto) o;

        if (keyValuePairs != null ? !keyValuePairs.equals(that.keyValuePairs) : that.keyValuePairs != null)
            return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        return !(lifecycle != null ? !lifecycle.equals(that.lifecycle) : that.lifecycle != null);

    }

    @Override
    public int hashCode() {
        int result = keyValuePairs != null ? keyValuePairs.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (lifecycle != null ? lifecycle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "S3FConfigurationConstantDto{" +
                "keyValuePairs=" + keyValuePairs +
                ", version='" + version + '\'' +
                ", lifecycle='" + lifecycle + '\'' +
                '}';
    }
}
