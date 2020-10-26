package com.example.branchmemo;

import java.util.Random;

public class CodeCreater {

    public String getNewCode(){
        String code = "";
        Random random = new Random();
        loop:while(true){
            for(int i=0; i<10; i++){
                int tmp;
                int flag = random.nextInt(3);

                if(flag==0){
                    code+=Integer.toString(random.nextInt(10));
                }else if(flag==1){
                    tmp = random.nextInt(26) + 65;
                    code += Character.toString((char)tmp);
                }else if(flag==2){
                    tmp = random.nextInt(26) + 97;
                    code += Character.toString((char)tmp);
                }
            }//end of for
            //Checking if exist of DB
            if(MainActivity.memoListDatabase.memoListDao().selectCode(code) == 0)
                break loop;
        }//end of while
        return code;
    }//end of getNewCode

}
