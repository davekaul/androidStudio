package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;

public class BubbleView extends androidx.appcompat.widget.AppCompatImageView implements View.OnTouchListener {
    private Random rand = new Random();
    private ArrayList<Bubble> bubbleList;
    private int size = 50;
    private int delay = 33;
    private Paint myPaint = new Paint();
    private Handler h = new Handler();

    private Bitmap background;

    //private Bitmap bitmap;


    public BubbleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        bubbleList = new ArrayList<Bubble>();
        //testBubbles();

        background = BitmapFactory.decodeResource(getResources(), R.drawable.choose3);
        setOnTouchListener(this);
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            for (Bubble b : bubbleList)
                b.update();
            invalidate();
        }
    };

    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null);
        for (Bubble b : bubbleList)
            b.draw(canvas);
        h.postDelayed(r, delay);
    }

    public void testBubbles() {
        for (int n = 0; n < 100; n++) {
            int x = rand.nextInt(600);
            int y = rand.nextInt(600);
            int s = rand.nextInt(size) + size;
            bubbleList.add(new Bubble(x, y, s));
        }
        invalidate();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        for (int n = 0; n < motionEvent.getPointerCount(); n++) {
            int x = (int) motionEvent.getX(n);
            int y = (int) motionEvent.getY(n);
            int s = rand.nextInt(size) + size;
            bubbleList.add(new Bubble(x, y, s));
        }
        return true;

    }

    private class Bubble {
        private int x;
        private int y;
        private int size;
        private int color;
        private int xspeed, yspeed;
        private final int MAX_SPEED = 15;
        private Bitmap image;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.alien);
        public Bubble(int newX, int newY, int newSize) {
            x = newX;
            y = newY;
            size = newSize;
            color = Color.argb(rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256));
            xspeed = rand.nextInt(MAX_SPEED * 2) - MAX_SPEED;
            yspeed = rand.nextInt(MAX_SPEED * 2) - MAX_SPEED;
        }

        public void draw(Canvas canvas) {
            canvas.drawBitmap(bitmap, x - size / 2, y - size / 2, null);
        }

        public void update() {
            x += xspeed;
            y += yspeed;
            if (x - size / 2 <= 0 || x + size / 2 >= getWidth())
                xspeed = -xspeed;
            if (y - size / 2 <= 0 || y + size / 2 >= getHeight())
                yspeed = -yspeed;
        }
    }

}
