package s3f.s3f_configuration.dto;

import java.util.Map;

public class S3FConfigurationRootDto {
    private final Map<String, String> keyValuePairs;
    private final String version;
    private final String lifecycle;
    private final String service;

    public S3FConfigurationRootDto(Map<String, String> keyValuePairs, String version, String lifecycle, String service) {
        this.keyValuePairs = keyValuePairs;
        this.version = version;
        this.lifecycle = lifecycle;
        this.service = service;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        S3FConfigurationRootDto that = (S3FConfigurationRootDto) o;

        if (keyValuePairs != null ? !keyValuePairs.equals(that.keyValuePairs) : that.keyValuePairs != null)
            return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (lifecycle != null ? !lifecycle.equals(that.lifecycle) : that.lifecycle != null) return false;
        return !(service != null ? !service.equals(that.service) : that.service != null);

    }

    @Override
    public int hashCode() {
        int result = keyValuePairs != null ? keyValuePairs.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (lifecycle != null ? lifecycle.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "S3FConfigurationRoot{" +
                "keyValuePairs=" + keyValuePairs +
                ", version='" + version + '\'' +
                ", lifecycle='" + lifecycle + '\'' +
                ", service='" + service + '\'' +
                '}';
    }
}
