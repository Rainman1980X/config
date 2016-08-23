package s3f.framework.events;

import com.google.gson.Gson;

public class S3FEvent {
    private final String correlationId;
    private final String authorization;
    private final String domain;
    private final String eventName;
    private final String createdBySvc;
    private final Object eventData;

    public S3FEvent(String correlationId, String authorization, String domain, String eventName, String createdBySvc, Object eventData) {
        this.authorization = authorization;
        this.domain = domain;
        this.eventName = eventName;
        this.createdBySvc = createdBySvc;
        this.eventData = eventData;
        this.correlationId = correlationId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        S3FEvent s3FEvent = (S3FEvent) o;

        if (correlationId != null ? !correlationId.equals(s3FEvent.correlationId) : s3FEvent.correlationId != null)
            return false;
        if (authorization != null ? !authorization.equals(s3FEvent.authorization) : s3FEvent.authorization != null)
            return false;
        if (domain != null ? !domain.equals(s3FEvent.domain) : s3FEvent.domain != null) return false;
        if (eventName != null ? !eventName.equals(s3FEvent.eventName) : s3FEvent.eventName != null) return false;
        if (createdBySvc != null ? !createdBySvc.equals(s3FEvent.createdBySvc) : s3FEvent.createdBySvc != null)
            return false;
        return !(eventData != null ? !eventData.equals(s3FEvent.eventData) : s3FEvent.eventData != null);

    }

    @Override
    public int hashCode() {
        int result = correlationId != null ? correlationId.hashCode() : 0;
        result = 31 * result + (authorization != null ? authorization.hashCode() : 0);
        result = 31 * result + (domain != null ? domain.hashCode() : 0);
        result = 31 * result + (eventName != null ? eventName.hashCode() : 0);
        result = 31 * result + (createdBySvc != null ? createdBySvc.hashCode() : 0);
        result = 31 * result + (eventData != null ? eventData.hashCode() : 0);
        return result;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
