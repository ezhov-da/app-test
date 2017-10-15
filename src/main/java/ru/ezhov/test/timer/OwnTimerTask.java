package ru.ezhov.test.timer;

import java.util.TimerTask;

/**
 *
 * @author ezhov_da
 */
public class OwnTimerTask extends TimerTask {

    private String text;

    public OwnTimerTask(String text) {
        this.text = text;
    }

    @Override
    public void run() {
        System.out.println(text);

    }

    public void setText(String text) {
        this.text = text;
    }
}
