package com.example.pong;

/**
 * Created by Andrew on 8/20/2017.
 */

public class Ball {
    double x;
    double y;
    double dx;
    double dy;
    int leng = 10;

    Ball(double xtemp,double ytemp){
        x = xtemp;
        y = ytemp;
    }

    public void update(){
        x+=dx;
        y+=dy;
    }

    int getX(){
        return (int)x;
    }
    int getY(){
        return (int)y;
    }
    int getdx(){
        return (int)dx;
    }
    int getdy(){
        return (int)dy;
    }
    int getleng(){
        return leng;
    }
}
