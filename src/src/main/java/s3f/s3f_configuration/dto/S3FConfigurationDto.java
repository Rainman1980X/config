package s3f.s3f_configuration.dto;

import java.util.Map;

import org.springframework.data.annotation.Id;

public class S3FConfigurationDto {
    @Id
    private String id;
    private Map<String, String> keyValuePairs;
    private final String version;
    private final String lifecycle;
    private final String service;

    public S3FConfigurationDto(String id, Map<String, String> keyValuePairs, String version, String lifecycle,
	    String service) {
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
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	S3FConfigurationDto other = (S3FConfigurationDto) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (keyValuePairs == null) {
	    if (other.keyValuePairs != null)
		return false;
	} else if (!keyValuePairs.equals(other.keyValuePairs))
	    return false;
	if (lifecycle == null) {
	    if (other.lifecycle != null)
		return false;
	} else if (!lifecycle.equals(other.lifecycle))
	    return false;
	if (service == null) {
	    if (other.service != null)
		return false;
	} else if (!service.equals(other.service))
	    return false;
	if (version == null) {
	    if (other.version != null)
		return false;
	} else if (!version.equals(other.version))
	    return false;
	return true;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((keyValuePairs == null) ? 0 : keyValuePairs.hashCode());
	result = prime * result + ((lifecycle == null) ? 0 : lifecycle.hashCode());
	result = prime * result + ((service == null) ? 0 : service.hashCode());
	result = prime * result + ((version == null) ? 0 : version.hashCode());
	return result;
    }

    @Override
    public String toString() {
	return "S3FConfigurationDto [id=" + id + ", keyValuePairs=" + keyValuePairs + ", version=" + version
		+ ", lifecycle=" + lifecycle + ", service=" + service + "]";
    }
}