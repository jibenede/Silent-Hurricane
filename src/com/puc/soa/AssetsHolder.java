package com.puc.soa;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.puc.sh.model.Animation;
import com.puc.sh.model.Audio;

public class AssetsHolder {
    public Bitmap ship;
    public Bitmap plasma;
    public Bitmap boss;

    public Bitmap stalkerA;
    public Bitmap predatorA;

    public Bitmap laser1;
    public Bitmap laser2;
    public Bitmap laser3;

    public Bitmap mainBackground;
    public Bitmap stageBackground;
    public Bitmap buttonPlay;

    public Audio music;
    public Audio intro;
    public Audio stage1_audio;

    public Animation death;

    private Context context;
    private Point mSize;

    public AssetsHolder(Context context) {
        this.context = context;

        WindowManager wManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wManager.getDefaultDisplay();
        mSize = new Point();
        display.getSize(mSize);

        AssetManager manager = context.getAssets();
        try {
            InputStream is = manager.open("images/player.png");
            Bitmap original = BitmapFactory.decodeStream(is);
            ship = Bitmap.createScaledBitmap(original, 64, 64, false);

            is = manager.open("images/plasma.png");
            original = BitmapFactory.decodeStream(is);
            plasma = Bitmap.createScaledBitmap(original, 32, 32, false);

            is = manager.open("images/boss.png");
            original = BitmapFactory.decodeStream(is);
            int dimX = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 115, context.getResources()
                            .getDisplayMetrics());
            int dimY = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 150, context.getResources()
                            .getDisplayMetrics());
            boss = Bitmap.createScaledBitmap(original, dimX, dimY, false);

            is = manager.open("images/laser1.png");
            original = BitmapFactory.decodeStream(is);
            int dim = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, Globals.LASER_SIZE, context
                            .getResources().getDisplayMetrics());
            laser1 = Bitmap.createScaledBitmap(original, dim, dim, false);

            is = manager.open("images/laser2.png");
            original = BitmapFactory.decodeStream(is);
            dim = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    Globals.LASER_SIZE, context.getResources()
                            .getDisplayMetrics());
            laser2 = Bitmap.createScaledBitmap(original, dim, dim, false);

            is = manager.open("images/laser3.png");
            original = BitmapFactory.decodeStream(is);
            dim = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    Globals.LASER_SIZE, context.getResources()
                            .getDisplayMetrics());
            laser3 = Bitmap.createScaledBitmap(original, dim, dim, false);

            is = manager.open("images/stalker_a.png");
            original = BitmapFactory.decodeStream(is);
            stalkerA = Bitmap.createScaledBitmap(original, 60, 75, false);

            is = manager.open("images/predator_a.png");
            original = BitmapFactory.decodeStream(is);
            predatorA = Bitmap.createScaledBitmap(original, 60, 75, false);

            is = manager.open("images/portada.png");
            original = BitmapFactory.decodeStream(is);
            mainBackground = Bitmap.createScaledBitmap(original, mSize.x,
                    mSize.y, false);

            is = manager.open("images/stage_background.png");
            original = BitmapFactory.decodeStream(is);
            stageBackground = Bitmap.createScaledBitmap(original, mSize.x,
                    mSize.y, false);

            is = manager.open("images/button_play.png");
            original = BitmapFactory.decodeStream(is);
            buttonPlay = Bitmap.createScaledBitmap(original, 265, 140, false);

            music = new Audio(manager.openFd("audio/audio3.mp3"));
            intro = new Audio(manager.openFd("audio/intro.mp3"));
            stage1_audio = new Audio(manager.openFd("audio/stage1_audio.mp3"));

            is = manager.open("images/explosion.png");
            original = BitmapFactory.decodeStream(is);
            death = new Animation(original, 5, 5, context);

            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

        }

    }

    public int getShipWidth() {
        return ship.getWidth();
    }

    public int getShipHeight() {
        return ship.getHeight();
    }

    private Bitmap LoadImage(String imageName, AssetManager manager) {

        return null;
    }

}
