package ru.ezhov.test.lines;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.System.out;

public class FrameLine {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
        {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Throwable ex) {
                //
            }
            JFrame frame = new JFrame("_________");

            PanelFigure panel = new PanelFigure();

            frame.add(new JScrollPane(panel), BorderLayout.CENTER);
            frame.add(new PanelSp(panel), BorderLayout.SOUTH);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }
}


class PanelSp extends JPanel {
    private static String LINE_COUNT = "Кол-во линий %s:";
    private static String ONE = "Радиус круга в центре %s:";
    private static String TWO = "Радиус круга в на центровом круге %s:";
    private static String THREE = "Радиус большого круга %s:";
    private static String FOUR = "Радиус круга вершины %s:";
    private static String PAINT = "Рисовать";

    private JCheckBox checkBoxLineCountPaint = new JCheckBox(PAINT);
    private JLabel labelLineCount = new JLabel();
    private JSlider sliderLineCount = new JSlider(JSlider.HORIZONTAL, 1, 360, 1);

    private JCheckBox checkBoxSmallPaint = new JCheckBox(PAINT);
    private JLabel labelMall = new JLabel();
    private JSlider sliderSmall = new JSlider(JSlider.HORIZONTAL, 0, 1000, 1);

    private JCheckBox checkBoxVerySmallPaint = new JCheckBox(PAINT);
    private JLabel labelVerySmall = new JLabel();
    private JSlider verySmall = new JSlider(JSlider.HORIZONTAL, 0, 1000, 1);

    private JCheckBox checkBoxVeryBigPaint = new JCheckBox(PAINT);
    private JLabel labelVeryBig = new JLabel();
    private JSlider big = new JSlider(JSlider.HORIZONTAL, 0, 1000, 1);

    private JCheckBox checkBoxPickPaint = new JCheckBox(PAINT);
    private JLabel labelPick = new JLabel();
    private JSlider pick = new JSlider(JSlider.HORIZONTAL, 0, 1000, 1);
    private PanelFigure panelFigure;

    public PanelSp(PanelFigure panelFigure) {
        super(new GridLayout(5, 3));
        this.panelFigure = panelFigure;


        add(checkBoxLineCountPaint);
        add(labelLineCount);
        sliderLineCount.setValue(panelFigure.getCountLine());
        add(sliderLineCount);
        sliderLineCount.addChangeListener((ChangeEvent e) ->
        {
            panelFigure.setCountLine(sliderLineCount.getValue());
            panelFigure.repaint();
            labelLineCount.setText(String.format(LINE_COUNT, sliderLineCount.getValue()));
        });
        labelLineCount.setText(String.format(LINE_COUNT, sliderLineCount.getValue()));
        checkBoxLineCountPaint.setSelected(panelFigure.isPantLine());
        checkBoxLineCountPaint.addChangeListener((ChangeEvent e) ->
        {
            panelFigure.setPantLine(checkBoxLineCountPaint.isSelected());
            panelFigure.repaint();
        });
        //======================================================================
        add(checkBoxSmallPaint);
        add(labelMall);
        sliderSmall.setValue(panelFigure.getSizeSmallCircle());
        add(sliderSmall);
        sliderSmall.addChangeListener((ChangeEvent e) ->
        {
            panelFigure.setSizeSmallCircle(sliderSmall.getValue());
            panelFigure.repaint();
            labelMall.setText(String.format(ONE, sliderSmall.getValue()));
        });
        labelMall.setText(String.format(ONE, sliderSmall.getValue()));
        checkBoxSmallPaint.setSelected(panelFigure.isPantSmall());
        checkBoxSmallPaint.addChangeListener((ChangeEvent e) ->
        {
            panelFigure.setPantSmall(checkBoxSmallPaint.isSelected());
            panelFigure.repaint();
        });
        //======================================================================
        add(checkBoxVerySmallPaint);
        add(labelVerySmall);
        verySmall.setValue(panelFigure.getSizeVerySmallCircle());
        add(verySmall);
        verySmall.addChangeListener((ChangeEvent e) ->
        {
            panelFigure.setSizeVerySmallCircle(verySmall.getValue());
            panelFigure.repaint();
            labelVerySmall.setText(String.format(TWO, verySmall.getValue()));
        });
        labelVerySmall.setText(String.format(TWO, verySmall.getValue()));
        checkBoxVerySmallPaint.setSelected(panelFigure.isPantVerySmall());
        checkBoxVerySmallPaint.addChangeListener((ChangeEvent e) ->
        {
            panelFigure.setPantVerySmall(checkBoxVerySmallPaint.isSelected());
            panelFigure.repaint();
        });
        //======================================================================
        add(checkBoxVeryBigPaint);
        add(labelVeryBig);
        big.setValue(panelFigure.getSizeBigCircle());
        add(big);
        big.addChangeListener((ChangeEvent e) ->
        {
            panelFigure.setSizeBigCircle(big.getValue());
            panelFigure.repaint();
            labelVeryBig.setText(String.format(THREE, big.getValue()));
        });
        labelVeryBig.setText(String.format(THREE, big.getValue()));
        checkBoxVeryBigPaint.setSelected(panelFigure.isPantBig());
        checkBoxVeryBigPaint.addChangeListener((ChangeEvent e) ->
        {
            panelFigure.setPantBig(checkBoxVeryBigPaint.isSelected());
            panelFigure.repaint();
        });
        //======================================================================
        add(checkBoxPickPaint);
        add(labelPick);
        pick.setValue(panelFigure.getPick());
        add(pick);
        pick.addChangeListener((ChangeEvent e) ->
        {
            panelFigure.setPick(pick.getValue());
            panelFigure.repaint();
            labelPick.setText(String.format(FOUR, pick.getValue()));
        });
        labelPick.setText(String.format(FOUR, pick.getValue()));
        checkBoxPickPaint.setSelected(panelFigure.isPantPick());
        checkBoxPickPaint.addChangeListener((ChangeEvent e) ->
        {
            panelFigure.setPantPick(checkBoxPickPaint.isSelected());
            panelFigure.repaint();
        });

        initSliders(sliderLineCount, sliderSmall, verySmall, pick, big);
    }

    private void initSliders(JSlider... sliders) {
        Stream.of(sliders).forEach(sl ->
        {
            sl.setPaintLabels(true);
            sl.setPaintTicks(true);
            sl.setPaintTrack(true);
            sl.setLabelTable(sl.createStandardLabels(100));

        });
    }
}

class PanelFigure extends JPanel {
    private int countLine = 2;
    private int sizeSmallCircle = 300;
    private int sizeVerySmallCircle = 10;
    private int sizeBigCircle = 500;
    private int pick = 15;

    private boolean isPantLine = true;
    private boolean isPantSmall = true;
    private boolean isPantVerySmall = true;
    private boolean isPantBig = false;
    private boolean isPantPick = true;

    private List<Line> listLines;
    private boolean inside;
    private Point nowMouse;

    public PanelFigure() {
        MA ma = new MA();
        addMouseListener(ma);
        addMouseMotionListener(ma);
        listLines = new ArrayList<>();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        Map map = new HashMap<>();
        map.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        map.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

        RenderingHints rh = new RenderingHints(map);
        graphics2D.setRenderingHints(rh);

        Point center = drawСircle(graphics2D, sizeSmallCircle, isPantSmall);

        listLines.removeAll(listLines);

        double step = Math.round(360F / countLine);
        out.println("step -> " + step);
        int stepNow = 0;
        for (int i = 0; i < countLine; i++) {
            if (stepNow < 360) {
                out.println("stepNow -> " + stepNow);
                graphics2D.setColor(Color.BLUE);
                Point start = drawСircleCoordinate(
                        graphics2D, sizeVerySmallCircle,
                        calculateCenterCircleSmall(sizeSmallCircle,
                                center,
                                stepNow
                        ),
                        isPantVerySmall);
                graphics2D.setColor(Color.BLACK);
                Point end = drawСircleCoordinate(
                        graphics2D,
                        sizeVerySmallCircle,
                        calculateCenterCircleSmall(
                                sizeBigCircle,
                                center,
                                stepNow
                        ),
                        false);

                listLines.add(new Line(start, end));

                if (inside) {
                    listLines.get(i).setPoints(start, nowMouse);

                    if (isPantPick) {
                        graphics2D.setColor(Color.RED);
                        drawСircleCoordinate(graphics2D, pick, nowMouse, true);
                        graphics2D.setColor(Color.BLACK);
                    }
                } else {
                    listLines.get(i).setPoints(start, end);
                }

                stepNow += step;
            }
        }

        if (isPantLine) {
            paintLines(graphics2D);
        }

        drawСircle(graphics2D, sizeBigCircle, isPantBig);
    }

    private Point drawСircle(Graphics2D graphics2D, int size, boolean isPaint) {
        Dimension dimension = getSize();
        Point center = new Point(dimension.width / 2, dimension.height / 2);
        Point circle = new Point(center.x - (size / 2), center.y - (size / 2));
        if (isPaint) {
            graphics2D.drawOval(circle.x, circle.y, size, size);
        }
        return center;
    }

    private Point drawСircleCoordinate(Graphics2D graphics2D, int size, Point center, boolean isDraw) {

        Point circle = new Point(center.x - (size / 2), center.y - (size / 2));
        if (isDraw) {
            graphics2D.drawOval(circle.x, circle.y, size, size);
        }
        return center;
    }


    private Point calculateCenterCircleSmall(int sizeCircle, Point center, double angle) {
        //x0,y0 - центр, a - угол, r - радиус.
        int radius = sizeCircle / 2;
        int x = (int) (center.x + radius * Math.cos(Math.toRadians(angle)));
        int y = (int) (center.y + radius * Math.sin(Math.toRadians(angle)));
        return new Point(x, y);
    }

    private void paintLines(Graphics2D graphics2D) {
        listLines.forEach((Line l) -> graphics2D.drawLine(l.getPointStart().x, l.getPointStart().y, l.getPointEnd().x, l.getPointEnd().y));
    }

    private class MA extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {
            inside = true;
            PanelFigure.this.repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            nowMouse = e.getPoint();
            PanelFigure.this.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            inside = false;
            PanelFigure.this.repaint();
        }
    }

    public int getSizeSmallCircle() {
        return sizeSmallCircle;
    }

    public void setSizeSmallCircle(int sizeSmallCircle) {
        this.sizeSmallCircle = sizeSmallCircle;
    }

    public int getSizeVerySmallCircle() {
        return sizeVerySmallCircle;
    }

    public void setSizeVerySmallCircle(int sizeVerySmallCircle) {
        this.sizeVerySmallCircle = sizeVerySmallCircle;
    }

    public int getSizeBigCircle() {
        return sizeBigCircle;
    }

    public void setSizeBigCircle(int sizeBigCircle) {
        this.sizeBigCircle = sizeBigCircle;
    }

    public int getPick() {
        return pick;
    }

    public void setPick(int pick) {
        this.pick = pick;
    }

    public int getCountLine() {
        return countLine;
    }

    public void setCountLine(int countLine) {
        this.countLine = countLine;
    }

    public boolean isPantLine() {
        return isPantLine;
    }

    public void setPantLine(boolean pantLine) {
        isPantLine = pantLine;
    }

    public boolean isPantSmall() {
        return isPantSmall;
    }

    public void setPantSmall(boolean pantSmall) {
        isPantSmall = pantSmall;
    }

    public boolean isPantVerySmall() {
        return isPantVerySmall;
    }

    public void setPantVerySmall(boolean pantVerySmall) {
        isPantVerySmall = pantVerySmall;
    }

    public boolean isPantBig() {
        return isPantBig;
    }

    public void setPantBig(boolean pantBig) {
        isPantBig = pantBig;
    }

    public boolean isPantPick() {
        return isPantPick;
    }

    public void setPantPick(boolean pantPick) {
        isPantPick = pantPick;
    }
}


class Line {
    private Point pointStart;
    private Point pointEnd;

    public Line(Point pointStart, Point pointEnd) {
        this.pointStart = pointStart;
        this.pointEnd = pointEnd;
    }


    public Point getPointStart() {
        return pointStart;
    }

    public void setPointStart(Point pointStart) {
        this.pointStart = pointStart;
    }

    public Point getPointEnd() {
        return pointEnd;
    }

    public void setPointEnd(Point pointEnd) {
        this.pointEnd = pointEnd;
    }

    public void setPoints(Point pointStart, Point pointEnd) {
        this.pointStart = pointStart;
        this.pointEnd = pointEnd;
    }

}