package javmos.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.LinkedList;
import javax.swing.JPanel;
import javmos.JavmosGUI;
import javmos.components.functions.Function;
import javmos.listeners.PointClickListener;

public class JavmosPanel extends JPanel {

    private final JavmosGUI gui;
    public final LinkedList<JavmosComponent> components;
    public Function function;

    public JavmosPanel(JavmosGUI gui) {
        this.gui = gui;
        this.components = new LinkedList();
    }

    public Function getFunction() {
        for (JavmosComponent i : components) {
            if (i instanceof Function) {
                return (Function) i;
            }
        }
        return null;
    }

    public void setPlane(CartesianPlane plane) {
        components.addFirst(plane);
    }

    public void setFunction(Function function) {
        this.function = function;
        components.add(function);
        HashSet[] set = {function.getXIntercepts(), function.getCriticalPoints(), function.getInflectionPoints()};
        PointClickListener mListen = (PointClickListener) getListeners(MouseListener.class)[0];
        mListen.removePoints();
        for (HashSet h : set) {
            for (int i = 0; i < h.size(); i++) {
                components.add((Point) h.toArray()[i]);
                mListen.setPoints((Point) h.toArray()[i]);
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;
        setPlane(new CartesianPlane(gui));
        for (int i = 0; i < components.size(); i++) {
            components.get(i).draw(g2d);
        }
    }
}
