package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by qwerty on 09-Apr-17.
 */
public class FileControl {
    public void save(String string){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\qwerty\\IdeaProjects\\Puzle2.0\\src\\assets\\plik.txt"));
            bw.write(string+"\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void add(String string,String string2,String string3,String string4,String string5){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\qwerty\\IdeaProjects\\Puzle2.0\\src\\assets\\plik.txt"));
            bw.append(string+"\n");
            bw.append(string2+"\n");
            bw.append(string3+"\n");
            bw.append(string4+"\n");
            bw.append(string5+"\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String[] read(){
        try {
            String [] arrey = new String[5];
            BufferedReader bw = new BufferedReader(new FileReader("C:\\Users\\qwerty\\IdeaProjects\\Puzle2.0\\src\\assets\\plik.txt"));
            for(int i=0;i<5;i++)
            {
                arrey[i]= bw.readLine();
            }
            bw.close();
            return  arrey;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int
            targetheight){
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetheight,
                originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, targetWidth, targetheight, null);
        g.dispose();
        return resizedImage;
    }

}
