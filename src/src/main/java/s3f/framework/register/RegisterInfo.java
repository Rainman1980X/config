package s3f.framework.register;

public class RegisterInfo {
    private final String machineName;
    private final String processId;
    private final String serviceName;
    private final String serviceClass;
    private final String port;
    private final String lifecycle;

    public RegisterInfo(String machineName, String processId, String serviceName, String serviceClass, String port, String lifecycle) {
        this.machineName = machineName;
        this.processId = processId;
        this.serviceName = serviceName;
        this.serviceClass = serviceClass;
        this.port = port;
        this.lifecycle = lifecycle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegisterInfo that = (RegisterInfo) o;

        if (machineName != null ? !machineName.equals(that.machineName) : that.machineName != null) return false;
        if (processId != null ? !processId.equals(that.processId) : that.processId != null) return false;
        if (serviceName != null ? !serviceName.equals(that.serviceName) : that.serviceName != null) return false;
        if (serviceClass != null ? !serviceClass.equals(that.serviceClass) : that.serviceClass != null) return false;
        if (port != null ? !port.equals(that.port) : that.port != null) return false;
        return !(lifecycle != null ? !lifecycle.equals(that.lifecycle) : that.lifecycle != null);

    }

    @Override
    public int hashCode() {
        int result = machineName != null ? machineName.hashCode() : 0;
        result = 31 * result + (processId != null ? processId.hashCode() : 0);
        result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
        result = 31 * result + (serviceClass != null ? serviceClass.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (lifecycle != null ? lifecycle.hashCode() : 0);
        return result;
    }
}
