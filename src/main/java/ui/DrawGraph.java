package ui;

import model.ModelHolder;
import model.Solution;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawGraph extends JPanel {
    private static final int PREF_W = 800;
    private static final int PREF_H = 800;
    private static final Color GRAPH_COLOR = Color.green;
    private static final Color GRAPH_POINT_COLOR = new Color(150, 50, 50, 180);
    private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
    private static final int GRAPH_POINT_WIDTH = 12;

    private Solution solution;

    public DrawGraph(Solution solution) {
        this.solution = solution;
    }

    private Point cityToPoint(int cityIndex) {
        List<Integer[]> model = ModelHolder.getModel();
        Integer[] city = ModelHolder.getModel().get(cityIndex);
        Point point = new Point(city[0] / 3, city[1] / 3); // todo scale better
        return point;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < solution.getCitiesOrder().length; i++) {
            for (int j = 0; j < solution.getBreakpoints().length; j++) {
                if (i == solution.getBreakpoints()[j]) {
                    graphPoints.add(cityToPoint(0));
                }
            }
            graphPoints.add(cityToPoint(i));
        }
        graphPoints.add(cityToPoint(0));

        Stroke oldStroke = g2.getStroke();
        g2.setColor(GRAPH_COLOR);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(GRAPH_POINT_COLOR);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
            int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;;
            int ovalW = GRAPH_POINT_WIDTH;
            int ovalH = GRAPH_POINT_WIDTH;
            g2.fillOval(x, y, ovalW, ovalH);
        }
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

        JFrame frame = new JFrame("DrawGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

}