package com.xingcloud.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Futil {

	public static String readFile(File installation) throws IOException {  
        RandomAccessFile f = new RandomAccessFile(installation, "r");  
        byte[] bytes = new byte[(int) f.length()];  
        f.readFully(bytes);  
        f.close(); 
        return new String(bytes);  
    }  
  
    public static void writeFile(File installation,String str) throws IOException {  
        FileOutputStream out = new FileOutputStream(installation);  
        out.write(str.getBytes());
        out.close();  
    } 
}
