package tabButton;

import event.TabButtonActioListener;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.ListModel;

public class TabButtons extends JComponent {

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public ListModel<String> getModel() {
        return model;
    }

    public void setModel(ListModel<String> model) {
        this.model = model;
        initButton = false;
    }

    private boolean initButton = false;
    private List<Button> buttons;
    private final int defaultWidth = 100;
    private final int splitSpace = 2;
    private final int topSpace = 5;
    private int selectedIndex = -1;
    private int onFocusIndex = -1;
    private Color color1;
    private Color color2;
    private ListModel<String> model;
    List<TabButtonActioListener> tabButtonActionListener;

    public TabButtons() {
        init();
        tabButtonActionListener = new ArrayList<>();
    }

    private void init() {
        buttons = new ArrayList<>();
        setColor1(new Color(255, 255, 255));
        setColor2(new Color(145, 0, 220));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                int newSelectedIndex = -1;
                for (int i = 0; i < buttons.size(); i++) {
                    if (buttons.get(i).getPolygon().contains(me.getPoint())) {
                        buttons.get(i).setSelected(true);
                        newSelectedIndex = i;
                        runEventOnTab(me);
                    }
                }
                if (newSelectedIndex != getSelectedIndex() && newSelectedIndex != -1) {
                    if (getSelectedIndex() != -1) {
                        buttons.get(getSelectedIndex()).setSelected(false);
                    }
                    setSelectedIndex(newSelectedIndex);
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent me) {
                onFocusIndex = -1;
                for (Button button : buttons) {
                    button.setOnFocus(false);
                }
                repaint();
            }

        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                int newFocusIndex = -1;
                for (int i = 0; i < buttons.size(); i++) {
                    if (buttons.get(i).getPolygon().contains(me.getPoint())) {
                        buttons.get(i).setOnFocus(true);
                        newFocusIndex = i;
                        break;
                    }
                }
                if (onFocusIndex >= 0) {
                    buttons.get(onFocusIndex).setOnFocus(false);
                }
                if (newFocusIndex != onFocusIndex) {
                    onFocusIndex = newFocusIndex;
                    if (onFocusIndex >= 0) {
                        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                    } else {
                        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                    }
                    runEventOnFocus(me, onFocusIndex != -1);
                    repaint();
                }
            }

        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                initButton();
            }
        });
    }

    public Button getButton(String name) {
        if (buttons.isEmpty()) {
            int x[] = {getWidth(), getWidth(), getWidth() - defaultWidth, getWidth() - defaultWidth + 20};
            int y[] = {topSpace, getHeight(), getHeight(), topSpace};
            Polygon poly = new Polygon(x, y, x.length);
            return new Button(buttons.size(), name, poly, false, false);
        } else {
            int w = defaultWidth * buttons.size();
            int x[] = {getWidth() - w + 20 - splitSpace, getWidth() - w - splitSpace, getWidth() - defaultWidth - w, getWidth() - defaultWidth - w + 20};
            int y[] = {topSpace, getHeight(), getHeight(), topSpace};
            Polygon poly = new Polygon(x, y, x.length);
            return new Button(buttons.size(), name, poly, false, false);
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        if (!initButton) {
            initButton();
        }
        painButton(g2);
    }

    private void painButton(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        Polygon polyOnFocus = null;
        Polygon polyOnSelected = null;
        for (Button b : buttons) {
            g2.setPaint(gp);
            if (b.isOnFocus()) {
                polyOnFocus = b.getOnFocusPolygon();
            } else if (!b.isSelected()) {
                g2.fillPolygon(b.getPolygon());
            }
            if (b.isSelected()) {
                polyOnSelected = b.getOnFocusPolygon();
            }
        }
        if (polyOnFocus != null) {
            g2.fillPolygon(polyOnFocus);
            g2.setColor(Color.WHITE);
            g2.drawPolygon(polyOnFocus);
        }
        g2.setPaint(gp);
        if (polyOnSelected != null) {
            g2.fillPolygon(polyOnSelected);
            g2.setColor(color2);
            g2.drawPolygon(polyOnSelected);
            //    paintBorderSelected(g2, polyOnSelected);
        }
    }

    private void paintBorderSelected(Graphics2D g2, Polygon polygon) {
        int x[] = {polygon.xpoints[2]};
        int y[] = {10, 0};
        g2.fillPolygon(x, y, x.length);
    }

    private void initButton() {
        if (model != null) {
            buttons = new ArrayList<>();
            for (int i = 0; i < model.getSize(); i++) {
                buttons.add(getButton(model.getElementAt(i)));
            }
            initButton = true;
        }
        if (selectedIndex != -1) {
            buttons.get(selectedIndex).setSelected(true);
        }
    }

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    public void addTabButtonActionListener(TabButtonActioListener event) {
        tabButtonActionListener.add(event);
    }

    private void runEventOnTab(MouseEvent mouseEvent) {
        for (TabButtonActioListener event : tabButtonActionListener) {
            event.onTab(mouseEvent);
        }
    }

    private void runEventOnFocus(MouseEvent mouseEvent, boolean isGained) {
        for (TabButtonActioListener event : tabButtonActionListener) {
            event.onFocus(mouseEvent, isGained);
        }
    }
}
