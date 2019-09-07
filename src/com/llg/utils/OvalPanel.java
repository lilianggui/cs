package com.llg.utils;

import javax.swing.*;
import java.awt.*;

public class OvalPanel extends JPanel{

        private Color color;
        public OvalPanel(){
            super();
        }

        public OvalPanel(Color c){
            super();
            this.color = c;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(this.color);
            g.fillOval(0, 0, 14, 14);
        }
}
