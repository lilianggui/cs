package com.llg.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class TestMenu {
    public static void main(String args[]) {
        JFrame f  = new JFrame();
        JTabbedPane  jp=new JTabbedPane(JTabbedPane.BOTTOM) ;    //设置选项卡在坐标

        JPanel p1=new JPanel() ;
        p1.add(new JLabel("dsadsa"));
        JPanel p2=new JPanel() ;
        p2.add(new JLabel("213231"));
        JPanel p3=new JPanel() ;
        JPanel p4=new JPanel() ;      //创建多个容器
        jp.add("Main", p1)  ;
        jp.add("Setting", p2)  ;
        jp.add("E-mail", p3)  ;
        f.add(jp, BorderLayout.CENTER);   //将选项卡窗体添加到 主窗体上去

        f.setVisible(true);
        f.setLocation(200,100);
        f.setTitle("菜单测试");
        f.setSize(1400,470);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

