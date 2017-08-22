package com.example.pong;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;

public class Player {
    //Bitmap to get character from image
    private Bitmap bitmap;

    int dir = 0;

    //coordinates
    private int x;
    private int y;
    private int leng = 70;
    private int width = 10;
    //motion speed of the character
    private int speed = 5;
    int xlim;

    //constructor
    public Player(Context context,int xtemp,int ytemp) {
        x = xtemp;
        y = ytemp;

        //Getting bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
    }

    //Method to update coordinate of character
    public void update(){
        if (dir==1&&x+leng>=xlim){
            dir=0;
        }
        else if (dir==-1&&x<=xlim){
            dir=0;
        }
        x+=speed*dir;
    }

    /*
    * These are getters you can generate it autmaticallyl
    * right click on editor -> generate -> getters
    * */
    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getleng() { return leng; }

    public int getwidth() { return width; }
}
