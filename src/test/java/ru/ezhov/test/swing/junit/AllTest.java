package ru.ezhov.test.swing.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * AllTest, runs all the tests
 */
public class AllTest extends TestCase {

    public AllTest(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();

        suite.addTest(FooTest.suite());

        return suite;
    }
}
