package com.ssafy.common.util;

import java.util.Random;

public class SecurityUtil {
    private SecurityUtil(){

    }
    public static String generateCode(){
        Random random = new Random();
        String code = "";
        for (int i = 0; i<3;i++){
            int index = random.nextInt(25)+65;
            code+=(char)index;
        }
        int numIndex = random.nextInt(9999)+1000;
        code += numIndex;
        return code;
    }
}
