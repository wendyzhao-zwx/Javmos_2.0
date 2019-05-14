package javmos.components.functions;

import javmos.JavmosGUI;
import javmos.enums.FunctionType;
import javmos.exceptions.PolynomialException;

public class Sine extends Trigonometric {

    protected double a;
    public double k;
    private JavmosGUI gui;
    private String function;

    public Sine(JavmosGUI gui, String function) throws PolynomialException {
        super(gui, function, "sine");
        String sin;
        try {
            this.function = function;

            if (this.function.contains("=")) {
                sin = this.function.substring(this.function.indexOf('=') + 1, this.function.length());
            } else {
                sin = this.function;
            }
            if (sin.contains("sin")) {
                if (sin.startsWith("sin") == true) {
                    a = 1;
                } else if (sin.startsWith("-sin") == true) {
                    a = -1;
                } else {
                    a = Double.parseDouble(sin.substring(0, sin.indexOf("sin")));
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
                equation += "-cos(";
            } else if ((a * k) == 1) {
                equation += "cos(";
            } else {
                equation += (a * k) + "cos(";
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
            if ((-1 * a * k * k) == -1) {
                equation += "-sin(";
            } else if ((-1 * a * k * k) == 1) {
                equation += "sin(";
            } else {
                equation += (-1 * a * k * k) + "sin(";
            }
            if (k == -1) {
                equation += "-x)";
            } else if (k == 1) {
                equation += "x)";
            } else {
                equation += k + "x)";
            }
        }
        return "f''(x)=" + equation;
    }

    @Override
    public double getValueAt(double x, FunctionType functionType) {
        double y = 0;
        switch (functionType) {
            case ORIGINAL:
                if (function.contains("x")) {
                    y = a * Math.sin(k * x);
                } else {
                    y = a * Math.sin(k);
                }
                break;
            case FIRST_DERIVATIVE:
                if (function.contains("x")) {
                    y = a * k * Math.cos(k * x);
                } else {
                    y = a * k * Math.cos(k);
                }
                break;
            case SECOND_DERIVATIVE:
                if (function.contains("x")) {
                    y = -1 * a * k * k * Math.sin(k * x);
                } else {
                    y = -1 * a * k * k * Math.sin(k);
                }
                break;
            default:
                if (function.contains("x")) {
                    y = -1 * a * Math.pow(k, 3) * Math.cos(k * x);
                } else {
                    y = -1 * a * Math.pow(k, 3) * Math.cos(k);
                }
                break;
        }
        return y;
    }

}
