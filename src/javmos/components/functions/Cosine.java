package javmos.components.functions;

import javmos.JavmosGUI;
import javmos.enums.FunctionType;
import javmos.exceptions.PolynomialException;

public class Cosine extends Trigonometric {

    protected double a;
    protected double k;
    private JavmosGUI gui;
    private String function;

    public Cosine(JavmosGUI gui, String function) throws PolynomialException {
        super(gui, function, "cosine");
        String cos;
        try {
            this.function = function;

            if (this.function.contains("=")) {
                cos = this.function.substring(this.function.indexOf('=') + 1, this.function.length());
            } else {
                cos = this.function;
            }
            if (cos.contains("cos")) {
                if (cos.startsWith("cos") == true) {
                    a = 1;
                } else if (cos.startsWith("-cos") == true) {
                    a = -1;
                } else {
                    a = Double.parseDouble(cos.substring(0, cos.indexOf("cos")));
                }
            }
            cos = cos.substring(cos.indexOf("(") + 1, cos.length());
            if (cos.contains("x")) {
                if (cos.charAt(0) == 'x') {
                    k = 1;
                } else if (cos.startsWith("-x")) {
                    k = -1;
                } else {
                    k = Double.parseDouble(cos.substring(0, cos.indexOf("x")));
                }
            } else {
                k = Double.parseDouble(cos.substring(0, cos.indexOf(")")));
            }
        } catch (Exception exception) {
            throw new PolynomialException(this.function + " is not a valid cosine equation!");
        }
    }

    @Override
    public String getFirstDerivative() {
        String equation = "";
        if (this.function.contains("x") == false) {
            equation = "0";
        } else {
            if ((-1 * a * k) == -1) {
                equation += "-sin(";
            } else if ((-1 * a * k) == 1) {
                equation += "sin(";
            } else {
                equation += (-1 * a * k) + "sin(";
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
                equation += "-cos(";
            } else if ((-1 * a * k * k) == 1) {
                equation += "cos(";
            } else {
                equation += (-1 * a * k * k) + "cos(";
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
        double y;
        switch (functionType) {
            case ORIGINAL:
                if (function.contains("x")) {
                    y = a * Math.cos(k * x);
                } else {
                    y = a * Math.cos(k);
                }
                break;
            case FIRST_DERIVATIVE:
                if (function.contains("x")) {
                    y = -1 * a * k * Math.sin(k * x);
                } else {
                    y = -1 * a * k * Math.sin(k);
                }
                break;
            case SECOND_DERIVATIVE:
                if (function.contains("x")) {
                    y = -1 * a * k * k * Math.cos(k * x);
                } else {
                    y = -1 * a * k * k * Math.cos(k);
                }
                break;
            default:
                if (function.contains("x")) {
                    y = a * Math.pow(k, 3) * Math.sin(k * x);
                } else {
                    y = a * Math.pow(k, 3) * Math.sin(k);
                }
                break;
        }
        return y;
    }

}
