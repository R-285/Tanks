package com.tanks.utils;

import java.io.*;

public class Utils {

    public static int[][] levelParser(String filePath){
        int i = 0;
        String line;
        int [][] result = new int[28][32];
        try(BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))){

            while ((line = reader.readLine()) != null) {
                result[i] =str2int_arrays(line.split(" "));
                i++;
            }
        } catch(IOException e){
            e.printStackTrace();
        }


        return result;
    }

    private static int[] str2int_arrays(String[] sArr){
        int[] result = new int [sArr.length];

        for(int i = 0; i < sArr.length; i++){
            result[i] = Integer.parseInt(sArr[i]);
        }

        return result;
    }

}
