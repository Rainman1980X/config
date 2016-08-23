package s3f.framework.lifecycle;

public class LifeCycle {
    private final String value;

    public LifeCycle(String value) {
        this.value = value;
    }

    public String getKey() {
        return value;
    }
}
