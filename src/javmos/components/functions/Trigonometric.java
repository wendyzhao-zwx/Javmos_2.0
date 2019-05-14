package javmos.components.functions;

import javmos.JavmosGUI;

public abstract class Trigonometric extends Function{
    
    protected double a;
    protected double k;
    private String function;
    private String name;
    
    public Trigonometric(JavmosGUI gui, String function, String name) {
        super(gui, function);
        this.function = function;
        this.name = name;
    }
}
