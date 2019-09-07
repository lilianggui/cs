package com.llg.test;

import javax.swing.*;
import java.awt.*;

public class TestDraw extends JFrame {
    public TestDraw() {
        this.setTitle("我的第一个窗体");
        MyPanel panel = new MyPanel();
        this.getContentPane().add(panel);
        this.setSize(300, 300);
        this.setLocation(260, 150);
        this.setLayout(null);
        panel.setBounds(20,60,50,50);


        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this.setBounds(260, 150, 300, 300);//设置窗体的坐标、大小，相当于前面2行代码
        JFrame jf = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int x = panel.getX();
                    int y = panel.getY();
                    panel.setLocation(x+1,y+1);
                    jf.repaint();
                }
            }
        }).start();

    }
    public static void main(String[] args) {
        TestDraw frame = new TestDraw();
        frame.setVisible(true);
    }
}

class MyPanel extends JPanel {
    // 覆盖JPanel的paint方法,程序自动调用paint方法，窗口最小化，再最大化，窗口大小变化
    // Graphics是绘图的重要类,可以理解成一只画笔
    public void paint(Graphics g) {
        // 1.调用父类函数完成初始化
        // 这句话不能少
        super.paint(g);
        g.setColor(Color.red);
        g.fillOval(10, 10, 24, 24);
    }
}