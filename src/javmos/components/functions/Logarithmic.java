package javmos.components.functions;

import java.util.HashSet;
import javmos.JavmosGUI;
import javmos.components.Point;
import javmos.enums.FunctionType;
import javmos.exceptions.PolynomialException;

public class Logarithmic extends Function {

    private JavmosGUI gui;
    public String function;
    public double a;
    public double base;
    public double k;

    public Logarithmic(JavmosGUI gui, String function) throws PolynomialException {
        super(gui, function);
        String log;
        try {
            this.function = function;
            if (this.function.contains("=")) {
                log = this.function.substring(this.function.indexOf('=') + 1, this.function.length());
            } else {
                log = this.function;
            }

            if (log.contains("ln")) {
                if (log.startsWith("ln")) {
                    a = 1;
                } else if (log.startsWith("-ln") == true) {
                    a = -1;
                } else {
                    a = Double.parseDouble(log.substring(0, log.indexOf("ln")));
                }
                log = log.substring(log.indexOf("(") + 1, log.length());
                base = Math.exp(1);
            } else if (log.contains("log")) {
                if (log.startsWith("log") == true) {
                    a = 1;
                } else if (log.startsWith("-log") == true) {
                    a = -1;
                } else {
                    a = Double.parseDouble(log.substring(0, log.indexOf("log")));
                }
                log = log.substring(log.indexOf("g") + 1, log.length());
                if (log.startsWith("(")) {
                    base = 10;
                } else {
                    base = Double.parseDouble(log.substring(0, log.indexOf("(")));
                }
                log = log.substring(log.indexOf("(") + 1, log.length());
            }
            if (log.contains("x")) {
                if (log.charAt(0) == 'x') {
                    k = 1;
                } else if (log.startsWith("-x")) {
                    k = -1;
                } else {
                    k = Double.parseDouble(log.substring(0, log.indexOf("x")));
                }
            } else {
                k = Double.parseDouble(log.substring(log.indexOf("(") + 1, log.indexOf(")")));
            }
        } catch (Exception exception) {
            throw new PolynomialException(this.function + " is not a valid logarithm!");
        }
    }

    @Override
    public String getFirstDerivative() {
        String equation;
        if (this.function.contains("x") == false) {
            equation = "0";
        } else {
            if (base == 1 || base < 0) {
                equation = "undefined";
            } else if (base == Math.exp(1)) {
                if (a == 1) {
                    equation = "1/x";
                } else {
                    equation = a + "/x";
                }
            } else {
                if (a == 1) {
                    equation = "1/(xln(" + base + "))";
                } else {
                    equation = a + "/(xln(" + base + "))";
                }
            }
        }
        return "f'(x)=" + equation;
    }

    @Override
    public String getSecondDerivative() {
        String equation;
        switch (getFirstDerivative()) {
            case "f'(x)=0":
                equation = "0";
                break;
            case "f'(x)=undefined":
                equation = "undefined";
                break;
            default:
                if (base == Math.exp(1)) {
                    if (a == 1) {
                        equation = "-1/(x^2)";
                    } else {
                        equation = "-" + a + "/(x^2)";
                    }
                } else {
                    if (a == 1) {
                        equation = "-1/((x^2)ln(" + base + ")";
                    } else if (a > 0) {
                        equation = "-" + a + "/((x^2)ln(" + base + ")";
                    } else {
                        equation = a + "/((x^2)ln(" + base + ")";
                    }
                }   break;
        }
        return "f''(x)=" + equation;
    }

    @Override
    public double getValueAt(double x, FunctionType functionType) {
        double y = 0;
        switch (functionType) {
            case ORIGINAL:
                if (this.function.contains("x")) {
                    y = a * (Math.log(k * x) / Math.log(base));
                } else {
                    y = a * (Math.log(k) / Math.log(base));
                }
                return y;
            case FIRST_DERIVATIVE:
                if (this.function.contains("x")) {
                    y = a / (x * Math.log(base));
                }
                return y;
            case SECOND_DERIVATIVE:
                if (this.function.contains("x")) {
                    y = a / (x * Math.log(base));
                }
                return y;
            default:
                if (this.function.contains("x")) {
                    y = (2 * a) / (Math.pow(x, 3) * Math.log(base));
                }
                return y;
        }
    }

    public HashSet<Point> getCriticalPoints() {
        return new HashSet();
    }

    public HashSet<Point> getInflectionPoints() {
        return new HashSet();
    }

}
