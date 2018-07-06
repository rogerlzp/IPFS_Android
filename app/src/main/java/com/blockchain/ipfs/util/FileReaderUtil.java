package com.blockchain.ipfs.util;

import android.util.Log;

import com.socks.library.KLog;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderUtil {

    public static String readFile(File inputFile) {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(inputFile));
            String line = bfr.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = bfr.readLine();
            }
            bfr.close();
            return sb.toString();
        } catch (IOException e) {
            KLog.e(e.getMessage());
        }
        return null;
    }
}
