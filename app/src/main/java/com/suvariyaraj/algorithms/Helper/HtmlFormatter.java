package com.suvariyaraj.algorithms.Helper;

import android.util.Log;

/**
 * Created by GOODBOY-PC on 09/06/16.
 */
public class HtmlFormatter {

    public static String htmlFormatter(String input){
        Log.d("mycheck2", ""+input);


        input = input.replace("<","&lt;");
        input = input.replace(" ","&nbsp;");

        //For integers
        String str=new String();
        for(int i=0;i<input.length();i++) {
             if(input.charAt(i)=='"'){
                int p=i;
                for(int j=p+1;j<input.length()-1;j++){
                    if(input.charAt(j)=='"') {
                        str +=   input.substring(i, j+1) ;
                        i=j;break;
                    }
                }
             }
             else if (input.charAt(i) >= '0' && input.charAt(i) <= '9') {
                 str+= "<font color=#f58d29>" + input.charAt(i) + "</font>";
             }
             else{
                str+=input.charAt(i);
             }
        }
        input=str;
        str="";

        input = input.replace("int&nbsp;","<font color=#8959a8>int</font>&nbsp;");
        input = input.replace("float ","<font color=red>float</font> ");
        input = input.replace("\n","<br/>");
        input = input.replace("#include","<font color=#8959a8>#include</font>");
        input = input.replace("struct","<font color=#8959a8>struct</font>");
        input = input.replace("typedef","<font color=#8959a8>typedef</font>");
        input = input.replace("void","<font color=#8959a8>void</font>");
        input = input.replace("while","<font color=#8959a8>while</font>");
        input = input.replace("switch","<font color=#8959a8>switch</font>");
        input = input.replace("case","<font color=#8959a8>case</font>");
        input = input.replace("break","<font color=#8959a8>break</font>");
        input = input.replace("default","<font color=#8959a8>default</font>");
        input = input.replace("if","<font color=#8959a8>if</font>");
        input = input.replace("return","<font color=#8959a8>return</font>");
        input = input.replace("else","<font color=#8959a8>else</font>");
        input = input.replace("printf","<font color=#4271ae>printf</font>");
        input = input.replace("scanf","<font color=#4271ae>scanf</font>");


        //For string in inverted comas
        for(int i=0;i<input.length();i++){
            if(input.charAt(i)=='"'){
                int p=i;
                for(int j=p+1;j<input.length()-1;j++){
                    if(input.charAt(j)=='"') {
                        str +=  "<font color=#718c00>" + input.substring(i, j+1) + "</font>";
                        i=j;break;
                    }
                }
            }
            else{
                str+=input.charAt(i);
            }
        }
        input=str;
        str="";

        Log.d("mycheckfinAL", ""+input);
        return input;
    }
}
