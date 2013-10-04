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

import com.puc.sh.model.Audio;
import com.puc.sh.model.bullets.AnimationUtils;

public class AssetsHolder {
    public Bitmap ship;

    public Bitmap boss;

    public Bitmap stalkerA;
    public Bitmap predatorA;
    public Bitmap stingray;
    public Bitmap dart;

    // Bullet sprites
    public Bitmap plasma;
    public Bitmap greenBullet;
    public Bitmap laser2;
    public Bitmap laser3;
    public Bitmap fireball;
    public Bitmap blueBullet;
    public Bitmap redBullet;

    public Bitmap mainBackground;
    // public Bitmap stageBackground;
    public Bitmap stage2Background;
    public Bitmap buttonPlay;
    public Bitmap buttonStage1;
    public Bitmap buttonStage2;
    public Bitmap buttonStage3;

    public Bitmap bossHp;
    public Bitmap bossIcon;
    public Bitmap livesIcon;

    public Audio music;
    public Audio intro;
    public Audio stage1_audio;

    public Bitmap[] death;
    public Bitmap[] bomb;

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

            is = manager.open("images/bullet_green.png");
            original = BitmapFactory.decodeStream(is);
            greenBullet = Bitmap.createScaledBitmap(original, 12, 12, false);

            is = manager.open("images/laser2.png");
            original = BitmapFactory.decodeStream(is);
            laser2 = Bitmap.createScaledBitmap(original, 16, 16, false);

            is = manager.open("images/laser3.png");
            original = BitmapFactory.decodeStream(is);
            laser3 = Bitmap.createScaledBitmap(original, 12, 12, false);

            is = manager.open("images/fireball.png");
            original = BitmapFactory.decodeStream(is);
            fireball = Bitmap.createScaledBitmap(original, 24, 24, false);

            is = manager.open("images/bullet_blue.png");
            original = BitmapFactory.decodeStream(is);
            blueBullet = Bitmap.createScaledBitmap(original, 96, 96, false);

            is = manager.open("images/bullet_red.png");
            original = BitmapFactory.decodeStream(is);
            redBullet = Bitmap.createScaledBitmap(original, 36, 36, false);

            is = manager.open("images/stalker_a.png");
            original = BitmapFactory.decodeStream(is);
            stalkerA = Bitmap.createScaledBitmap(original, 60, 75, false);

            is = manager.open("images/predator_a.png");
            original = BitmapFactory.decodeStream(is);
            predatorA = Bitmap.createScaledBitmap(original, 60, 75, false);

            is = manager.open("images/stingray.png");
            original = BitmapFactory.decodeStream(is);
            stingray = Bitmap.createScaledBitmap(original, 60, 60, false);

            is = manager.open("images/dart.png");
            original = BitmapFactory.decodeStream(is);
            dart = Bitmap.createScaledBitmap(original, 80, 80, false);

            is = manager.open("images/portada.png");
            original = BitmapFactory.decodeStream(is);
            mainBackground = Bitmap.createScaledBitmap(original, mSize.x,
                    mSize.y, false);

            is = manager.open("images/stage2_background.jpg");
            original = BitmapFactory.decodeStream(is);
            stage2Background = Bitmap.createScaledBitmap(original,
                    Globals.CANVAS_WIDTH, original.getHeight() * 2, false);

            is = manager.open("images/button_play.png");
            original = BitmapFactory.decodeStream(is);
            buttonPlay = Bitmap.createScaledBitmap(original, 265, 140, false);

            is = manager.open("images/button_level1.png");
            original = BitmapFactory.decodeStream(is);
            buttonStage1 = Bitmap.createScaledBitmap(original, 265, 140, false);

            is = manager.open("images/button_level2.png");
            original = BitmapFactory.decodeStream(is);
            buttonStage2 = Bitmap.createScaledBitmap(original, 265, 140, false);

            is = manager.open("images/button_level3.png");
            original = BitmapFactory.decodeStream(is);
            buttonStage3 = Bitmap.createScaledBitmap(original, 265, 60, false);

            is = manager.open("images/boss_hp.png");
            original = BitmapFactory.decodeStream(is);
            bossHp = Bitmap.createScaledBitmap(original, 350, 35, false);

            is = manager.open("images/boss_icon.png");
            original = BitmapFactory.decodeStream(is);
            bossIcon = Bitmap.createScaledBitmap(original, 150, 35, false);

            music = new Audio(manager.openFd("audio/audio3.mp3"));
            intro = new Audio(manager.openFd("audio/intro.mp3"));
            stage1_audio = new Audio(manager.openFd("audio/stage1_audio.mp3"));

            is = manager.open("images/explosion.png");
            original = BitmapFactory.decodeStream(is);
            death = AnimationUtils.cropBitmapIntoRectMatrix(context, original,
                    5, 5, 96, 96);

            is = manager.open("images/bomb_animation.png");
            original = BitmapFactory.decodeStream(is);
            bomb = AnimationUtils.cropBitmapIntoRectMatrix(context, original,
                    5, 2, 400, 400);

            is = manager.open("images/lives_icon.png");
            original = BitmapFactory.decodeStream(is);
            livesIcon = Bitmap.createScaledBitmap(original, 50, 20, false);

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
