package com.example.welkon.Utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class SomeVoidsFromData {

    //---------------Функция для загрузки фото из бд по ссылкам------------------------------------
    public static void loadImageFromData(String namePhoto, ImageView imageView1) {
        String path = Environment.getExternalStorageDirectory().toString();
        String imagePath = path + "/AudioArmy/PhotoForDB/"+namePhoto+".jpg";
        imageView1.setImageURI(Uri.parse(imagePath));
    }

    public static List<String> GetLinkImages(String links){
        List<String> temp = new ArrayList<String>();
        char[] charLinks = links.toCharArray();
        char findСomma = 44;
        int start =0;
        for (int i = 0; i < charLinks.length; i++){
            if (charLinks[i] == findСomma || i+1 == charLinks.length){
                char[] dst;
                if(i+1 == charLinks.length){
                    dst=new char[i+1 - start];
                    links.getChars(start, i+1, dst, 0);
                }else {
                    dst=new char[i - start];
                    links.getChars(start, i, dst, 0);
                }
                links.getChars(start, i, dst, 0);
                temp.add(String.valueOf(dst));
                start = i+1;
            }
        }
        return temp;
    }
}
