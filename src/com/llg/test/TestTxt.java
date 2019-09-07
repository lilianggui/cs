package com.llg.test;

import com.llg.main.Main;

import java.io.*;
import java.net.URLDecoder;

public class TestTxt {

//    public static void main(String ars[]){
//        String classPath = TestTxt.class.getResource("/daily.txt").getPath();
//        RandomAccessFile raf = null;
//        try {
//            classPath = URLDecoder.decode(classPath, "UTF-8");
//            raf = new RandomAccessFile(classPath, "r");
//            long len = raf.length();
//            String lastLine = "";
//            if (len != 0L) {
//                long pos = len - 1;
//                while (pos > 0) {
//                    pos--;
//                    raf.seek(pos);
//                    if (raf.readByte() == '\n') {
//                        lastLine = raf.readLine();
//                        break;
//                    }
//                }
//            }
//            System.out.println(lastLine);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                raf.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//
//    }
//    public static void main(String ars[]) {
//        FileInputStream fis = null;
//        InputStreamReader isr = null;
//        BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
//        try {
//            String str = "";
//            String str1 = "";
//            String filePath = TestTxt.class.getResource("/daily.txt").getPath();
//            filePath = URLDecoder.decode(filePath, "UTF-8");
//            fis = new FileInputStream(filePath);// FileInputStream
//            // 从文件系统中的某个文件中获取字节
//            isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
//            br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
//            while ((str = br.readLine()) != null) {
//                str1 += str + "\n";
//            }
//            // 当读取的一行不为空时,把读到的str的值赋给str1
//            System.out.println(str1);// 打印出str1
//        } catch (FileNotFoundException e) {
//            System.out.println("找不到指定文件");
//        } catch (IOException e) {
//            System.out.println("读取文件失败");
//        } finally {
//            try {
//                br.close();
//                isr.close();
//                fis.close();
//                // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


//    public static void main(String argv[])throws Exception{
//        String filePath = Main.class.getResource("/daily.txt").getPath();
//        filePath = URLDecoder.decode(filePath, "UTF-8");
//        rewriteendline(filePath,"23123");
//    }

//    public static void writeendline(String filepath, String string)
//            throws Exception {
//
//        RandomAccessFile file = new RandomAccessFile(filepath, "rw");
//        long len = file.length();
//        long start = file.getFilePointer();
//        long nextend = start + len - 1;
//        byte[] buf = new byte[1];
//        file.seek(nextend);
//        file.read(buf, 0, 1);
//
//        if (buf[0] == '\n')
//
//            file.writeBytes(string);
//        else
//
//            file.writeBytes("\r\n"+string);
//
//        file.close();
//
//    }
//
//    public static void rewriteendline(String filepath, String string)
//            throws Exception {
//
//        RandomAccessFile file = new RandomAccessFile(filepath, "rw");
//        long len = file.length();
//        long start = file.getFilePointer();
//        long nextend = start + len - 1;
//
//        int i = -1;
//        file.seek(nextend);
//        byte[] buf = new byte[1];
//
//        while (nextend > start) {
//
//            i = file.read(buf, 0, 1);
//            if (buf[0] == '\r') {
//                file.setLength(nextend - start);
//                break;
//            }
//            nextend--;
//            file.seek(nextend);
//
//        }
//        file.close();
//        writeendline(filepath, string);
//
//    }
}
