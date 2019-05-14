package javmos.components.functions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.util.HashSet;
import javmos.JavmosGUI;
import javmos.components.JavmosComponent;
import javmos.components.Point;
import javmos.enums.FunctionType;
import javmos.enums.RootType;

public abstract class Function extends JavmosComponent {

    public final int ATTEMPTS = 15;
    private JavmosGUI gui;
    public String function;

    protected Function(JavmosGUI gui, String function) {
        super(gui);
        this.gui = gui;
        this.function = function;
    }

    public abstract String getFirstDerivative();

    public abstract String getSecondDerivative();

    public abstract double getValueAt(double x, FunctionType functionType);

    public HashSet<Point> getXIntercepts() {
        return RootType.X_INTERCEPT.getRoots(gui, (Function) this, gui.getMinDomain(), gui.getMaxDomain());
    }

    public HashSet<Point> getCriticalPoints() {
        return RootType.CRITICAL_POINT.getRoots(gui, (Function) this, gui.getMinDomain(), gui.getMaxDomain());
    }

    public HashSet<Point> getInflectionPoints() {
        return RootType.INFLECTION_POINT.getRoots(gui, (Function) this, gui.getMinDomain(), gui.getMaxDomain());
    }

    @Override
    public void draw(java.awt.Graphics2D graphics2D) {
        graphics2D.setStroke(new BasicStroke(2.5f));
        graphics2D.setColor(new Color(34, 18, 144));
        double scaleX = gui.getZoom() / gui.getDomainStep();
        double scaleY = gui.getZoom() / gui.getRangeStep();
        for (double x = gui.getMinDomain(); x < gui.getMaxDomain(); x += 0.001) {
            double y1 = getValueAt(x, FunctionType.ORIGINAL);
            double y2 = getValueAt(x + 0.01, FunctionType.ORIGINAL);
            if (y1 < gui.getMaxRange() && y1 > gui.getMinRange()) {
                if ((y1 > 10 && y2 < -10) || (y1 < -10 && y2 > 10)) {
                } else {
                    graphics2D.draw(new Line2D.Double(x * scaleX + gui.getPlaneWidth() / 2, gui.getPlaneHeight() / 2 - y1 * scaleY,
                            (x + 0.01) * scaleX + gui.getPlaneWidth() / 2, gui.getPlaneHeight() / 2 - y2 * scaleY));
                }
            }
        }
        graphics2D.setColor(new Color(34, 18, 144));
    }
}
