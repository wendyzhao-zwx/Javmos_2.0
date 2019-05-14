package javmos.components.functions;

import javmos.JavmosGUI;
import javmos.enums.FunctionType;
import javmos.exceptions.PolynomialException;

public class Tangent extends Trigonometric {

    protected double a;
    protected double k;
    private JavmosGUI gui;
    private String function;

    public Tangent(JavmosGUI gui, String function) throws PolynomialException {
        super(gui, function, "tangent");
        String sin;
        try {
            this.function = function;

            if (this.function.contains("=")) {
                sin = this.function.substring(this.function.indexOf('=') + 1, this.function.length());
            } else {
                sin = this.function;
            }
            if (sin.contains("tan")) {
                if (sin.startsWith("tan") == true) {
                    a = 1;
                } else if (sin.startsWith("-tan") == true) {
                    a = -1;
                } else {
                    a = Double.parseDouble(sin.substring(0, sin.indexOf("tan")));
                }
            }
            sin = sin.substring(sin.indexOf("(") + 1, sin.length());
            if (sin.contains("x")) {
                if (sin.charAt(0) == 'x') {
                    k = 1;
                } else if (sin.startsWith("-x")) {
                    k = -1;
                } else {
                    k = Double.parseDouble(sin.substring(0, sin.indexOf("x")));
                }
            } else {
                k = Double.parseDouble(sin.substring(0, sin.indexOf(")")));
            }
        } catch (Exception exception) {
            throw new PolynomialException(this.function + " is not a valid sine equation!");
        }
    }

    @Override
    public String getFirstDerivative() {
        String equation = "";
        if (this.function.contains("x") == false) {
            equation = "0";
        } else {
            if ((a * k) == -1) {
                equation += "-sec^2(";
            } else if ((a * k) == 1) {
                equation += "sec^2(";
            } else {
                equation += (a * k) + "sec^2(";
            }
            if (k == -1) {
                equation += "-x)";
            } else if (k == 1) {
                equation += "x)";
            } else {
                equation += k + "x)";
            }
        }
        return "f'(x)=" + equation;
    }

    @Override
    public String getSecondDerivative() {
        String equation = "";
        if (getFirstDerivative().equals("f'(x)=0")) {
            equation = "0";
        } else {
            if ((2 * a * k * k) == -1) {
                equation += "-sec^2(";
            } else if ((2 * a * k * k) == 1) {
                equation += "sec^2(";
            } else {
                equation += (2 * a * k * k) + "sec^2(";
            }
            if (k == -1) {
                equation += "-x)tan(-x)";
            } else if (k == 1) {
                equation += "x)tan(x)";
            } else {
                equation += k + "x)tan(" + k + "x)";
            }
        }
        return "f''(x)=" + equation;
    }

    @Override
    public double getValueAt(double x, FunctionType functionType) {
        double y;
        switch (functionType) {
            case ORIGINAL:
                if (function.contains("x")) {
                    y = a * Math.tan(k * x);
                } else {
                    y = a * Math.tan(k);
                }
                break;
            case FIRST_DERIVATIVE:
                if (function.contains("x")) {
                    y = a * k / Math.pow((Math.cos(k * x)), 2);
                } else {
                    y = a * k / Math.pow((Math.cos(k)), 2);
                }
                break;
            case SECOND_DERIVATIVE:
                if (function.contains("x")) {
                    y = 2 * a * k * k * Math.tan(k * x) / Math.pow(Math.cos(k * x), 2);
                } else {
                    y = 2 * a * k * k * Math.tan(k) / Math.pow(Math.cos(k), 2);
                }
                break;
            default:
                if (function.contains("x")) {
                    y = (2 * a * Math.pow(k, 3) * (1 / Math.pow(Math.cos(k * x), 2)) * (3 * (1 / Math.pow(Math.cos(k * x), 2)) - 2));
                } else {
                    y = 2 * a * Math.pow(k, 3) * (1 / Math.pow(Math.cos(k), 2)) * (3 * (1 / Math.pow(Math.cos(k), 2)) - 2);
                }
                break;
        }
        return y;
    }

}
