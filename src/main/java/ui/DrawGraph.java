package ui;

import model.ModelHolder;
import model.Solution;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DrawGraph extends JPanel {
    private static final int PREF_W = 800;
    private static final int PREF_H = 800;
    private static final Color GRAPH_COLOR = Color.green;
    private static final Color GRAPH_POINT_COLOR = new Color(150, 50, 50, 180);
    private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
    private static final int GRAPH_POINT_SIZE = 12;

    private double scalingRatio;

    private Solution solution;

    private DrawGraph(Solution solution) {
        this.solution = solution;
        configScalingRatio();
    }

    private Point cityToPoint(int cityIndex) {
        Integer[] city = ModelHolder.getModel().get(cityIndex);
        return new Point((int) (city[0] * scalingRatio), (int) (city[1] * scalingRatio));
    }

    private void configScalingRatio() {
        int maxSize = Integer.MIN_VALUE;
        for (Integer[] city : ModelHolder.getModel()) {
            if(city[0] > maxSize) {
                maxSize = city[0];
            }
            if(city[1] > maxSize) {
                maxSize = city[1];
            }
        }
        scalingRatio = (PREF_W - 10) / (double) maxSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // fixme

//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D)g;
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        List<Point> graphPoints = new ArrayList<>();
//        graphPoints.add(cityToPoint(0));
//        for (int i = 0; i < solution.getCitiesOrder().length; i++) {
//            for (int j = 0; j < solution.getBreakpoints().length; j++) {
//                if (i == solution.getBreakpoints()[j]) {
//                    graphPoints.add(cityToPoint(0));
//                }
//            }
//            graphPoints.add(cityToPoint(solution.getCitiesOrder()[i]));
//        }
//        graphPoints.add(cityToPoint(0));
//
//        // draw edges
//        Stroke oldStroke = g2.getStroke();
//        g2.setColor(GRAPH_COLOR);
//        g2.setStroke(GRAPH_STROKE);
//        for (int i = 0; i < graphPoints.size() - 1; i++) {
//            int x1 = graphPoints.get(i).x;
//            int y1 = graphPoints.get(i).y;
//            int x2 = graphPoints.get(i + 1).x;
//            int y2 = graphPoints.get(i + 1).y;
//            g2.drawLine(x1, y1, x2, y2);
//        }
//
//        // draw nodes
//        g2.setStroke(oldStroke);
//        g2.setColor(GRAPH_POINT_COLOR);
//        for (Point graphPoint : graphPoints) {
//            int x = graphPoint.x - GRAPH_POINT_SIZE / 2;
//            int y = graphPoint.y - GRAPH_POINT_SIZE / 2;
//            g2.fillOval(x, y, GRAPH_POINT_SIZE, GRAPH_POINT_SIZE);
//        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }

    /**
     * Draws graph.
     * @param solution solution
     * @throws IOException if something wents wrong
     */
    public static void createAndShowGui(Solution solution) throws IOException {
        DrawGraph mainPanel = new DrawGraph(solution);

        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

}