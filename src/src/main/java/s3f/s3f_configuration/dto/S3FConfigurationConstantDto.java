package s3f.s3f_configuration.dto;

import org.springframework.data.annotation.Id;

public class S3FConfigurationConstantDto {

    @Id
    private String id;
    private String version;
    private String lifecycle;
    private String constantName;
    private String constantValue;

    public S3FConfigurationConstantDto(){}

    public S3FConfigurationConstantDto(String id, String version, String lifecycle, String constantName, String constantValue) {
        this.id = id;
        this.version = version;
        this.lifecycle = lifecycle;
        this.constantName = constantName;
        this.constantValue = constantValue;
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

    public String getConstantName() {
        return constantName;
    }

    public String getConstantValue() {
        return constantValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof S3FConfigurationConstantDto)) return false;

        S3FConfigurationConstantDto that = (S3FConfigurationConstantDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (lifecycle != null ? !lifecycle.equals(that.lifecycle) : that.lifecycle != null) return false;
        if (constantName != null ? !constantName.equals(that.constantName) : that.constantName != null) return false;
        return constantValue != null ? constantValue.equals(that.constantValue) : that.constantValue == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (lifecycle != null ? lifecycle.hashCode() : 0);
        result = 31 * result + (constantName != null ? constantName.hashCode() : 0);
        result = 31 * result + (constantValue != null ? constantValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "S3FConfigurationConstantDto{" +
                "id='" + id + '\'' +
                ", version='" + version + '\'' +
                ", lifecycle='" + lifecycle + '\'' +
                ", constantName='" + constantName + '\'' +
                ", constantValue='" + constantValue + '\'' +
                '}';
    }
}
