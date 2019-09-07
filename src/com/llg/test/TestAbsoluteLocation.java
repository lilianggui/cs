package com.llg.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestAbsoluteLocation {


    public static void main(String[] as){
        JFrame f = new JFrame();
        f.setBounds(800,200,400,400);
        JButton jb1 = new JButton("dsadsa");
        jb1.setBounds(2,200,100,30);

        f.getContentPane().setLayout(null);
        f.getContentPane().add(jb1);

        f.setTitle("cs 1.0");
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
