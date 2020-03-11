package color;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

public class PanelColor extends JComponent {

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        paintColor(g2);
    }

    private void paintColor(Graphics2D g2) {
        for (int v = 0; v <= 100; v++) {
            for (int s = 0; s <= 100; s++) {
                //   g2.setColor(Color.);
                g2.fillRect(s, v, s + 2, v + 2);
            }
        }
    }
}
