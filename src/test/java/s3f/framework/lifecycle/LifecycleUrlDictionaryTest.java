package s3f.framework.lifecycle;

import org.junit.Test;

public class LifecycleUrlDictionaryTest {
    private final LifecycleUrlDictionary lifecycleUrlDictionary = new LifecycleUrlDictionary();

    @Test
    public void testExist() {
        String[] args = new String[1];

        args[0] = "--lifecycle-test";

        lifecycleUrlDictionary.check(args);
    }

    @Test
    public void developExist() {
        String[] args = new String[1];

        args[0] = "--lifecycle-develop";

        lifecycleUrlDictionary.check(args);
    }

    @Test
    public void stageExist() {
        String[] args = new String[1];

        args[0] = "--lifecycle-stage";

        lifecycleUrlDictionary.check(args);
    }

    @Test
    public void productionExist() {
        String[] args = new String[1];

        args[0] = "--lifecycle-production";

        lifecycleUrlDictionary.check(args);
    }

    @Test
    public void localExist() {
        String[] args = new String[1];

        args[0] = "--lifecycle-local";

        lifecycleUrlDictionary.check(args);
    }
}
