package javmos.components.functions;

import java.util.ArrayList;
import java.util.Arrays;
import javmos.JavmosGUI;
import javmos.enums.FunctionType;
import javmos.exceptions.PolynomialException;

public class Polynomial extends Function {

    public final int ATTEMPTS = 15;
    private java.lang.String function;
    private JavmosGUI gui;
    private double[] coefficients;
    private int[] degrees;
    private double[] derivCo = null;
    private int[] derivDeg;
    private double[] secDerivCo;
    private int[] secDerivDeg;

    public Polynomial(JavmosGUI gui, String function) throws PolynomialException {
        super(gui, function);
        try {
            String poly;
            this.function = function;
            if (this.function.contains("=") == true) {
                poly = this.function.substring(this.function.indexOf('=') + 1, this.function.length());
            } else {
                poly = this.function;
            }
            if (poly.indexOf("x") == -1) {
                this.degrees = new int[1];
                degrees[0] = 0;
                this.coefficients = new double[1];
                coefficients[0] = Integer.parseInt(poly);
            } else {
                String replacePoly = null;
                if (poly.charAt(0) == '-') {
                    replacePoly = "-" + poly.substring(1).replaceAll("-", "+-");
                } else {
                    replacePoly = poly.replaceAll("-", "+-");
                }
                String[] arr = replacePoly.split("\\+");
                ArrayList<String> termArr = new ArrayList<>(Arrays.asList(arr));
                this.degrees = new int[termArr.size()];
                this.coefficients = new double[termArr.size()];
                for (int k = 0; k < termArr.size(); k++) {
                    String term = termArr.get(k);
                    int temp = term.indexOf('x');
                    if (temp != -1) {
                        if (temp != 0) {
                            if (term.indexOf('-') == 0 && temp == 1) {
                                coefficients[k] = -1;
                            } else {
                                coefficients[k] = Double.parseDouble(term.substring(0, temp));
                            }
                        } else {
                            coefficients[k] = 1;
                        }
                        if (term.contains("^")) {
                            degrees[k] = Integer.parseInt(term.substring(temp + 2));
                        } else {
                            degrees[k] = 1;
                        }
                    } else {
                        coefficients[k] = Integer.parseInt(term);
                        degrees[k] = 0;
                    }
                }
            }
        } catch (Exception exception) {
            throw new PolynomialException(function + " is not a valid polynomial!");
        }
    }

    @Override
    public String getFirstDerivative() {
        derivCo = new double[this.coefficients.length];
        derivDeg = new int[degrees.length];
        String equation = "";

        for (int i = 0; i < degrees.length; i++) {
            if (degrees[i] == 0) {
                derivCo[i] = 0;
                derivDeg[i] = 0;
            } else {
                derivCo[i] = coefficients[i] * degrees[i];
                derivDeg[i] = degrees[i] - 1;
            }
        }

        if (derivCo[0] != 0) {
            equation += derivCo[0];
            if (derivDeg[0] > 1) {
                equation += "x^" + derivDeg[0];
            } else if (derivDeg[0] == 1) {
                equation += "x";
            }
        }

        for (int i = 1; i < derivCo.length; i++) {
            if (derivCo[i] != 0) {
                if (derivDeg[i] > 1) {
                    if (derivCo[i] > 0) {
                        equation += "+" + derivCo[i] + "x^" + derivDeg[i];
                    } else {
                        equation += derivCo[i] + "x^" + derivDeg[i];
                    }
                } else if (derivDeg[i] == 1) {
                    if (derivCo[i] > 0) {
                        equation += "+" + derivCo[i] + "x";
                    } else {
                        equation += derivCo[i] + "x";
                    }
                } else {
                    if (derivCo[i] > 0) {
                        equation += "+" + derivCo[i];
                    } else {
                        equation += derivCo[i];
                    }
                }
            }
        }

        if (derivDeg.length == 1 && derivDeg[0] == 0) {
            equation += "0";
        }
        return "f'(x)=" + equation;
    }

    @Override
    public String getSecondDerivative() {
        this.getFirstDerivative();
        secDerivCo = new double[derivDeg.length];
        secDerivDeg = new int[derivDeg.length];
        String equation = "";

        for (int i = 0; i < derivDeg.length; i++) {
            if (derivDeg[i] == 0) {
                secDerivCo[i] = 0;
                secDerivDeg[i] = 0;
            } else {
                secDerivCo[i] = derivCo[i] * derivDeg[i];
                secDerivDeg[i] = derivDeg[i] - 1;
            }
        }
        if (secDerivCo[0] != 0) {
            equation += secDerivCo[0];
            if (secDerivDeg[0] > 1) {
                equation += "x^" + secDerivDeg[0];
            } else if (secDerivDeg[0] == 1) {
                equation += "x";
            }
        }

        for (int i = 1; i < secDerivCo.length; i++) {
            if (secDerivCo[i] != 0) {
                if (secDerivDeg[i] > 1) {
                    if (secDerivCo[i] > 0) {
                        equation += "+" + secDerivCo[i] + "x^" + secDerivDeg[i];
                    } else {
                        equation += secDerivCo[i] + "x^" + secDerivDeg[i];
                    }
                } else if (secDerivDeg[i] == 1) {
                    if (derivCo[i] > 0) {
                        equation += "+" + derivCo[i] + "x";
                    } else {
                        equation += derivCo[i] + "x";
                    }
                } else {
                    if (derivCo[i] > 0) {
                        equation += "+" + derivCo[i];
                    } else {
                        equation += derivCo[i];
                    }
                }
            }
        }

        if (secDerivDeg.length == 1 && secDerivDeg[0] == 0) {
            equation += "0";
        }

        return "f''(x)=" + equation;
    }

    @Override
    public double getValueAt(double x, FunctionType functionType) {
        double y = 0;
        switch (functionType) {
            case ORIGINAL:
                for (int i = 0; i < degrees.length; i++) {
                    y += coefficients[i] * Math.pow(x, degrees[i]);
                }
                return y;
            case FIRST_DERIVATIVE:
                getFirstDerivative();
                for (int i = 0; i < derivDeg.length; i++) {
                    y += derivCo[i] * Math.pow(x, derivDeg[i]);
                }
                return y;
            case SECOND_DERIVATIVE:
                getSecondDerivative();
                for (int i = 0; i < secDerivDeg.length; i++) {
                    y += secDerivCo[i] * Math.pow(x, secDerivDeg[i]);
                }
                return y;
            default:
                getSecondDerivative();
                for (int i = 0; i < secDerivDeg.length; i++) {
                    y += (secDerivCo[i] * secDerivDeg[i]) * Math.pow(x, secDerivDeg[i] - 1);
                }
                return y;
        }
    }
}
