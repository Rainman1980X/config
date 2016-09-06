package s3f.s3f_configuration.services;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EscapeServiceTest {
    private EscapeService escapeService;

    @Before
    public void setUp() {
        escapeService = new EscapeService();
    }

    @Test
    public void escape() {
        final Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("test.key.teil1", "testvalue");

        Map<String, String> escaped = escapeService.escape(keyValuePairs);

        assertThat(escaped, is(expectedEscape()));
    }

    @Test
    public void escapeNothing() {
        final Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("test", "testvalue");

        Map<String, String> escaped = escapeService.escape(keyValuePairs);

        assertThat(escaped, is(keyValuePairs));
    }

    @Test
    public void unescape() {
        final Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("file#recovery#name", "recovery#txt");

        Map<String, String> escaped = escapeService.unescape(keyValuePairs);

        assertThat(escaped, is(expectedUnEscape()));
    }

    private Map<String, String> expectedUnEscape() {
        final Map<String, String> expected = new HashMap<>();
        expected.put("file.recovery.name", "recovery.txt");
        return expected;
    }

    private Map<String, String> expectedEscape() {
        Map<String, String> expected = new HashMap<>();
        expected.put("test#key#teil1", "testvalue");

        return expected;
    }
}