package tabButton;

import java.awt.Polygon;

public class Button {

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public boolean isOnFocus() {
        return onFocus;
    }

    public void setOnFocus(boolean onFocus) {
        this.onFocus = onFocus;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Button(int index, String name, Polygon polygon, boolean onFocus, boolean selected) {
        this.index = index;
        this.name = name;
        this.polygon = polygon;
        this.onFocus = onFocus;
        this.selected = selected;
    }

    public Button() {
    }

    private int index;
    private String name;
    private Polygon polygon;
    private boolean onFocus;
    private boolean selected;

    public Polygon getOnFocusPolygon() {
        int sp = 5;
        if (index == 0) {
            int x[] = {polygon.xpoints[0] - 1, polygon.xpoints[1] - 1, polygon.xpoints[2] - sp, polygon.xpoints[3] - sp};
            int y[] = {polygon.ypoints[0] - sp, polygon.ypoints[1] - sp, polygon.ypoints[2] - sp, polygon.ypoints[3] - sp};
            return new Polygon(x, y, x.length);
        } else {
            int x[] = {polygon.xpoints[0] + sp, polygon.xpoints[1] + sp, polygon.xpoints[2] - sp, polygon.xpoints[3] - sp};
            int y[] = {polygon.ypoints[0] - sp, polygon.ypoints[1] - sp, polygon.ypoints[2] - sp, polygon.ypoints[3] - sp};
            return new Polygon(x, y, x.length);
        }
    }
}
