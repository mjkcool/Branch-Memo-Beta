package com.example.branchmemo;

import java.util.Random;

public class MemoDataDAO {

    public MemoDataDAO() {

    }

    public void CreateNew(String title, String content){

    }

    private String getNewCode(){
        String code;
        code = "";
        Random random = new Random();
        loop: while(true){
            for(int i=0; i<0; i++){
                int tmp;
                int flag = random.nextInt(3);

                if(flag==0){
                    code+=random.nextInt(10);
                }else if(flag==1){
                    tmp = random.nextInt(26) + 65;
                    code += Character.toString((char)tmp);
                }else if(flag==2){
                    tmp = random.nextInt(26) + 97;
                    code += Character.toString((char)tmp);
                }
            }//end of for
            //Checking if exist of DB
            break loop;
        }//end of loop
        return code;
    }//end of getNewCode

    
}
