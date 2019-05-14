package javmos.enums;

import java.awt.Color;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashSet;
import javmos.JavmosGUI;
import javmos.components.Point;
import javmos.components.functions.Function;

public enum RootType {

    X_INTERCEPT(new Color(255, 125, 26), "x-intercept", FunctionType.ORIGINAL, FunctionType.FIRST_DERIVATIVE),
    CRITICAL_POINT(new Color(255, 215, 0), "Critical point", FunctionType.FIRST_DERIVATIVE, FunctionType.SECOND_DERIVATIVE),
    INFLECTION_POINT(new Color(204, 0, 102), "Inflection point", FunctionType.SECOND_DERIVATIVE, FunctionType.THIRD_DERIVATIVE);

    public final int ATTEMPTS = 15;
    public final String rootName;
    public final FunctionType functionOne;
    public final FunctionType functionTwo;
    public final Color rootColor;

    RootType(Color rootColor, String rootName, FunctionType functionOne, FunctionType functionTwo) {
        this.rootColor = rootColor;
        this.rootName = rootName;
        this.functionOne = functionOne;
        this.functionTwo = functionTwo;
    }

    public Color getRootColor() {
        return rootColor;
    }

    public String getRootName() {
        return rootName;
    }

    public static RootType valueOf(String color, String name) {
        switch (name) {
            case "X_INTERCEPT":
                return X_INTERCEPT;
            case "CRITICAL_POINT":
                return CRITICAL_POINT;
            case "INFLECTION_POINT":
                return INFLECTION_POINT;
            default:
                return null;
        }
    }

    public HashSet<Point> getRoots(JavmosGUI gui, Function function, double minDomain, double maxDomain) {
        double y;
        double step = (gui.getDomainStep() < 0) ? -gui.getDomainStep() : gui.getDomainStep();
        HashSet pointSet = new HashSet();
        //loops through the domain to find the roots
        if (function.function.contains("log") || function.function.contains("ln")) {
            if (function.function.contains("(-")) {
                maxDomain = -0.001;
            } else {
                minDomain = 0.001;
            }
        }

        for (double k = (minDomain / gui.getZoom() * gui.getDomainStep()); k <= (maxDomain / gui.getZoom() * gui.getDomainStep()); k += 0.01 * step) {
            DecimalFormat df = new DecimalFormat("#.###");
            df.setRoundingMode(RoundingMode.HALF_DOWN);
            Double x = newtonsMethod(function, k, ATTEMPTS);
            if (x != null) {
                x = Double.parseDouble(df.format(x));
                y = Double.parseDouble(new DecimalFormat("#.###").format(function.getValueAt(x, FunctionType.ORIGINAL)));
                if (this.equals(RootType.X_INTERCEPT)) {
                    y = 0;
                }
                if (function.function.contains("sin") || function.function.contains("cos") || function.function.contains("tan")) {
                    if (this.equals(RootType.INFLECTION_POINT)) {
                        y = 0;
                    }
                }
                if (x == -0.0) {
                    x = 0.0;
                }
                Point p = new Point(gui, this, x, y);
                pointSet.add(p);
            }
        }
        return pointSet;
    }

    protected Double newtonsMethod(Function function, double guess, int attempts) {
        if ((Math.abs(function.getValueAt(guess, functionOne) / (function.getValueAt(guess, functionTwo)))) < 0.00000001) {
            return guess;
        }
        if (attempts == 0) {
            return null;
        }
        return newtonsMethod(function, guess - (function.getValueAt(guess, functionOne)
                / function.getValueAt(guess, functionTwo)), attempts - 1);
    }
}
