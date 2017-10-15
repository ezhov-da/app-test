package ru.ezhov.test;

import org.junit.Test;
import ru.ezhov.test.timer.OwnTimerTask;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * тестируем работу таймера
 *
 * @author ezhov_da
 */
public class OwnTimerTaskTest {

    private static final Logger LOG = Logger.getLogger(OwnTimerTaskTest.class.getName());

    @Test
    public void testScheduleDelay() {
        System.out.println("=================================================");
        Map<Integer, OwnTimerTask> map = new HashMap<Integer, OwnTimerTask>();
        OwnTimerTask ownTimerTask1 = new OwnTimerTask("тест промежутка 1");
        OwnTimerTask ownTimerTask2 = new OwnTimerTask("тест промежутка 2");
        map.put(1, ownTimerTask1);
        map.put(2, ownTimerTask2);
        Timer timer = new Timer();
        timer.schedule(map.get(1), 2500);
        timer.schedule(map.get(2), 1000);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        ownTimerTask2.cancel();
        map.remove(2);
        timer.schedule(new OwnTimerTask("новый объект промежутка"), 1000);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        System.out.println("=================================================");
    }

    @Test
    public void testScheduleTime() {
        System.out.println("=================================================");
        Map<Integer, OwnTimerTask> map = new HashMap<Integer, OwnTimerTask>();
        OwnTimerTask ownTimerTask1 = new OwnTimerTask("тест от времени 1");
        OwnTimerTask ownTimerTask2 = new OwnTimerTask("тест от времени 2");
        map.put(1, ownTimerTask1);
        map.put(2, ownTimerTask2);
        Timer timer = new Timer();
        timer.schedule(map.get(1), new Date(System.currentTimeMillis() + 1000));
        timer.schedule(map.get(2), new Date(System.currentTimeMillis() + 2000));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        System.out.println("=================================================");
    }

    @Test
    public void testScheduleFirstTimePeriod() {
        System.out.println("=================================================");
        Map<Integer, OwnTimerTask> map = new HashMap<Integer, OwnTimerTask>();
        OwnTimerTask ownTimerTask1 = new OwnTimerTask("тест от времени и периода 1");
        OwnTimerTask ownTimerTask2 = new OwnTimerTask("тест от времени и периода 2");
        map.put(1, ownTimerTask1);
        map.put(2, ownTimerTask2);
        Timer timer = new Timer();
        timer.schedule(map.get(1), new Date(System.currentTimeMillis() + 2000), 500);
        timer.schedule(map.get(2), new Date(System.currentTimeMillis() + 2000), 1000);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        System.out.println("=================================================");
    }

    @Test
    public void testScheduleDelayPeriod() {
        System.out.println("=================================================");
        Map<Integer, OwnTimerTask> map = new HashMap<Integer, OwnTimerTask>();
        OwnTimerTask ownTimerTask1 = new OwnTimerTask("тест от простоя и периода 1");
        OwnTimerTask ownTimerTask2 = new OwnTimerTask("тест от простоя и периода 2");
        map.put(1, ownTimerTask1);
        map.put(2, ownTimerTask2);
        Timer timer = new Timer();
        timer.schedule(map.get(1), 1000, 500);
        timer.schedule(map.get(2), 500, 1000);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        System.out.println("=================================================");
    }
}
