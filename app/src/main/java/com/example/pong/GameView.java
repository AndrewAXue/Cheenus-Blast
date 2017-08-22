package com.example.pong;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by Andrew on 8/20/2017.
 */

public class GameView extends SurfaceView implements Runnable {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    SharedPreferences shared;

    volatile boolean playing;
    private Thread gameThread = null;

    //adding the player to this class
    private Player player;
    private Ball ball;

    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    int height;
    int width;
    double bounce_mult = 1.008;
    double gravity = 0.15;
    double dx_on_side = 2.5;

    boolean init_pause = true;

    int bounces = 0;

    Context context;

    public GameView(Context tempcontext) {
        super(tempcontext);
        context = tempcontext;

        //initializing player object
        height = Resources.getSystem().getDisplayMetrics().heightPixels;
        width = Resources.getSystem().getDisplayMetrics().widthPixels;
        player = new Player(context,width/2-35,height-75);
        Random ballspawn = new Random();
        ball = new Ball(ballspawn.nextInt(width-200)+100,player.getY()-300);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
    }

    public boolean onTouchEvent(MotionEvent e)
    {   init_pause = false;
        int xpos=(int) e.getX();
        int ypos=(int) e.getY();
        //Log.d("DEBUG",String.valueOf(xpos)+" xpos "+String.valueOf(ypos)+" ypos\n");
        if (e.getAction()==MotionEvent.ACTION_DOWN){
            if (xpos<player.getX()){
                player.dir = -1;
                player.xlim = xpos;
            }
            if (xpos>player.getX()+player.getleng()){
                player.dir = 1;
                player.xlim = xpos;
            }
        }
        else if (e.getAction()==MotionEvent.ACTION_UP){
            player.dir = 0;
        }
        else if (e.getAction()==MotionEvent.ACTION_MOVE){
            if (xpos<player.getX()){
                player.dir = -1;
                player.xlim = xpos;
            }
            else if (xpos>player.getX()+player.getleng()){
                player.dir = 1;
                player.xlim = xpos;
            }
        }
        return true;
    }

    @Override
    public void run() {
        while (playing) {
            control();
            draw();
            if (!init_pause){
                update();
                if (ball.getdy()+ball.getY()+ball.getleng()>=player.getY()&&ball.getY()<=player.getY()+player.getwidth()&&ball.getX()+ball.getleng()>=player.getX()&&ball.getX()<=player.getX()+player.getleng()){
                    ball.dy*=-bounce_mult;
                    bounces++;
                    double percent;
                    if (ball.getX()+ball.getleng()/2<=player.getX()+player.getleng()/2){
                        percent = ((player.getX()+player.getleng()/2)-(ball.getX()+ball.getleng()/2))/(double)(player.getleng()/2);
                        if (percent<0.1){
                            percent = 0.1;
                        }
                        ball.dx-=percent*dx_on_side;
                    }
                    else{
                        percent = ((ball.getX()+ball.getleng()/2)-(player.getX()+player.getleng()/2))/(double)(player.getleng()/2);
                        if (percent<0.1){
                            percent = 0.1;
                        }
                        ball.dx+=percent*dx_on_side;
                    }
                }
                if (ball.getdy()+ball.getY()+ball.getleng()>=height){
                    //Game Over
                    Intent intent = new Intent(context,GameOverActivity.class);
                    intent.putExtra("bounces",bounces);
                    context.startActivity(intent);
                }
                if (ball.getdy()+ball.getY()<=0){
                    ball.dy*=-bounce_mult;
                }
                if (ball.getdx()+ball.getX()+ball.getleng()>=width){
                    ball.dx*=-bounce_mult;
                }
                if (ball.getdx()+ball.getX()<=0){
                    ball.dx*=-bounce_mult;
                }
                ball.dy+=gravity;
            }

        }
    }

    private void update() {
        //updating player position
        player.update();
        ball.update();
    }

    private void draw() {
        //checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();
            //drawing a background color for canvas
            canvas.drawColor(Color.RED);
            //Drawing the player
            paint.setColor(Color.BLUE);


            final float testTextSize = 24f;
            paint.setTextSize(testTextSize);
            paint.setTypeface(Typeface.create("Arial",Typeface.BOLD));

            canvas.drawText("Bounces: "+bounces,40,40,paint);
            if (init_pause){
                canvas.drawText("Press and hold on the screen to move the paddle!",5,height-150,paint);
            }
            canvas.drawRect(player.getX(),player.getY(),player.getX()+player.getleng(),player.getY()+player.getwidth(),paint);
            paint.setColor(Color.BLACK);
            canvas.drawRect(ball.getX(),ball.getY(),ball.getX()+ball.getleng(),ball.getY()+ball.getleng(),paint);
            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            gameThread.sleep(12);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}