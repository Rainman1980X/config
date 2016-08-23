package s3f.framework.register;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class RegisterInfoTest {
    @Test
    public void equality() {
        final RegisterInfo registerInfo1 = new RegisterInfo("MachineName", "ProcessId", "ServiceName", "ServiceClass", "Port", "Lifecycle");
        final RegisterInfo registerInfo2 = new RegisterInfo("MachineName", "ProcessId", "ServiceName", "ServiceClass", "Port", "Lifecycle");

        assertThat(registerInfo1, is(registerInfo2));
    }

    @Test
    public void notEquality() {
        final RegisterInfo registerInfo1 = new RegisterInfo("MachineName1", "ProcessId1", "ServiceName1", "ServiceClass", "Port", "Lifecycle");
        final RegisterInfo registerInfo2 = new RegisterInfo("MachineName2", "ProcessId2", "ServiceName2", "ServiceClass", "Port", "Lifecycle");

        assertThat(registerInfo1, is(not(registerInfo2)));
    }
}
