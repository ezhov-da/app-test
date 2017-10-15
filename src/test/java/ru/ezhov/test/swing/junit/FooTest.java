package ru.ezhov.test.swing.junit;
import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.swing.*;
import java.awt.*;

public class FooTest extends TestCase {

    static Foo foo;

    public FooTest(String name) {
        super(name);
    }

    public static void main(String args[]) {
        junit.textui.TestRunner.run(FooTest.class);
    }

    public void testTypeIn() throws Exception {
        String testString = "message1";

        assertNotNull(foo);  // instantiated?

        JTextField input = (JTextField) TestUtils.getChildNamed(foo, "input");
        assertNotNull(input); // component found?

        input.setText(testString);
        input.postActionEvent();  // type in a test message + ENTER

        assertEquals(testString + "?", input.getText());
    }

    public void testColor() throws Exception {
        JMenuItem red = (JMenuItem) TestUtils.getChildNamed(foo, "red");
        assertNotNull(red); // component found?

        red.doClick();

        JTextField input = (JTextField) TestUtils.getChildNamed(foo, "input");
        assertEquals(Color.red, input.getForeground());
    }

    public void testPopUp() throws Exception {
        final JButton popup = (JButton) TestUtils.getChildNamed(foo, "popup");
        assertNotNull(popup);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                popup.doClick();
            }
        });

        JButton ok = null;

        // The dialog box will show up shortly
        for (int i = 0; ok == null; ++i) {
            Thread.sleep(200);
            ok = (JButton) TestUtils.getChildIndexed(foo, "JButton", 0);
            assertTrue(i < 10);
        }
        assertEquals(
                UIManager.getString("OptionPane.okButtonText"), ok.getText());

        ok.doClick();
    }

    public void testStory() throws Exception {
        // Type a string, change the color and popup

        String testString = "message2";

        JTextField input = (JTextField) TestUtils.getChildNamed(foo, "input");
        JMenuItem red = (JMenuItem) TestUtils.getChildNamed(foo, "red");
        JMenuItem blue = (JMenuItem) TestUtils.getChildNamed(foo, "blue");
        final JButton popup = (JButton) TestUtils.getChildNamed(foo, "popup");

        input.setText(testString);
        input.postActionEvent();

        red.doClick();
        assertEquals(testString + "?", input.getText());
        assertEquals(Color.red, input.getForeground());

        blue.doClick();
        assertEquals(testString + "?", input.getText());
        assertEquals(Color.blue, input.getForeground());

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                popup.doClick();
            }
        });

        JButton ok = null;
        JTextArea message = null;

        // The dialog box will show up shortly
        for (int i = 0; ok == null || message == null; ++i) {
            Thread.sleep(200);
            ok = (JButton) TestUtils.getChildIndexed(foo, "JButton", 0);
            message = (JTextArea) TestUtils.getChildIndexed(foo, "JTextArea", 0);
            assertTrue(i < 10);
        }
        assertEquals(
                UIManager.getString("OptionPane.okButtonText"), ok.getText());
        assertEquals(testString + "? ... done.", message.getText());

        ok.doClick();
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(FooTest.class);

        TestSetup wrapper = new TestSetup(suite) {
            protected void setUp() throws Exception {
                foo = new Foo();
                foo.setVisible(true);
            }

            protected void tearDown() throws Exception {
            }
        };

        return wrapper;
    }
}
// vim: set ai sw=4 ts=4:

