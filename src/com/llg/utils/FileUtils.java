package com.llg.utils;

import com.llg.test.TestTxt;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<String> getFileLine(String filePath,Integer lineCount){
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
        List<String> lines = new ArrayList<>();
        List<String> tempLines = new ArrayList<>();
        try {
            //String filePath = TestTxt.class.getResource("/daily.txt").getPath();

            fis = new FileInputStream(filePath);// FileInputStream
            isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
            br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
            String line = "";
            while ((line = br.readLine()) != null) {
                if(!line.replaceAll("\\s+","").equals("")){
                    tempLines.add(line);
                }
            }
            if(lineCount != null){
                int size = tempLines.size();
                if(size > lineCount){
                    lines = tempLines.subList(size-lineCount,size);
                }else{
                    lines = tempLines;
                }
            }else{
                lines = tempLines;
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        } finally {
            try {
                br.close();
                isr.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    public static String readFirstLine(String filePath){
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
        try {
            fis = new FileInputStream(filePath);// FileInputStream
            isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
            br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
            String line = "";
            if ((line = br.readLine()) != null) {
                return line;
            }
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        } finally {
            try {
                br.close();
                isr.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String readLastLine(String filePath){
        RandomAccessFile raf = null;
        try {
            filePath = URLDecoder.decode(filePath, "UTF-8");
            File file = new File(filePath);
            if (!file.exists() || file.isDirectory() || !file.canRead()) {
                return null;
            }
            raf = new RandomAccessFile(file, "r");
            long len = raf.length();
            if (len == 0L) {
                return "";
            } else {
                long pos = len - 1;
                while (pos > 0) {
                    pos--;
                    raf.seek(pos);
                    if (raf.readByte() == '\n') {
                        break;
                    }
                }
                if (pos == 0) {
                    raf.seek(0);
                }
                byte[] bytes = new byte[(int) (len - pos)];
                raf.read(bytes);
                return new String(bytes, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (Exception e2) {
                }
            }
        }
        return null;
    }

    public static void replaceLastLine(String line,String filePath){
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(filePath, "rw");
            long len = file.length();
            long start = file.getFilePointer();
            long nextend = start + len - 1;
            int i = -1;
            file.seek(nextend);
            byte[] buf = new byte[1];
            while (nextend > start) {
                i = file.read(buf, 0, 1);
                if (buf[0] == '\r') {
                    file.setLength(nextend - start);
                    break;
                }
                nextend--;
                file.seek(nextend);
            }
            file.close();
            writeendline(filePath, line);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                file.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void writeendline(String filepath, String string) {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(filepath, "rw");
            long len = file.length();
            long start = file.getFilePointer();
            long nextend = start + len - 1;
            byte[] buf = new byte[1];
            file.seek(nextend);
            file.read(buf, 0, 1);
            if (buf[0] == '\n'){
                file.writeBytes(string);
            } else{
                file.writeBytes("\r\n"+string);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                file.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    public static void insertLines(String line,String filePath){
        List<String> lines = new ArrayList<>();
        lines.add(line);
        FileUtils.insertLines(lines,filePath,true);
    }

    public static void insertLines(List<String> lines,String filePath,boolean appen){
        FileOutputStream out = null;
        OutputStreamWriter outWriter = null;
        BufferedWriter bufWrite = null;
        try {
            filePath = URLDecoder.decode(filePath, "UTF-8");
            out = new FileOutputStream(filePath,appen);
            outWriter = new OutputStreamWriter(out, "UTF-8");
            bufWrite = new BufferedWriter(outWriter);
            for (int i = 0;i<lines.size();i++) {
                if(i > 0 || appen){
                    bufWrite.newLine();
                }
                bufWrite.write(lines.get(i));
            }
            bufWrite.close();
            outWriter.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                out.close();
                outWriter.close();
                bufWrite.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
