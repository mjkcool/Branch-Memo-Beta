package com.example.branchmemo;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Scanner;
//
//public class LocaldataDAO {
//    File file;
//    public LocaldataDAO() throws IOException {
//        file = new File("../../../../../../localcode.txt");
//        FileWriter write = new FileWriter(file, true);
//        write.write(""); write.flush();
//        write.close();
//    }
//
//    public String readcode() throws IOException {
//        Scanner scan = new Scanner(file);
//        String data = "";
//        while(scan.hasNextLine()){
//            data += scan.nextLine()+"/";
//        }
//        data = data.substring(0, data.length()-1);
//        scan.close();
//        return data; //"[code]/[code]/...[code]"
//    }
//
//    public void writecode(String code) throws IOException {
//        FileWriter write = new FileWriter(file, true);
//        write.write("\ncode"); //한줄 추가가 목적
//        write.flush();
//        write.close();
//    }
//
//    public void deletecode(){
//
//    }
//
//}
