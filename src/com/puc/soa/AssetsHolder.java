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
    public Bitmap jawus;
    public Bitmap komashr;

    public Bitmap stalkerA;
    public Bitmap predatorA;
    public Bitmap stingray;
    public Bitmap dart;

    // Bullet sprites
    public Bitmap plasma;
    public Bitmap heavy;

    public Bitmap purpleBullet18;
    public Bitmap greenBullet12;
    public Bitmap yellowBullet24;
    public Bitmap blueBullet12;
    public Bitmap blueBullet96;
    public Bitmap redBullet36;
    public Bitmap redBullet12;
    public Bitmap whiteBullet48;

    public Bitmap mainBackground;
    // public Bitmap stageBackground;
    public Bitmap stage2Background;
    public Bitmap buttonPlay;
    public Bitmap buttonStage1;
    public Bitmap buttonStage2;
    public Bitmap buttonStage3;

    public Bitmap buttonRetry;
    public Bitmap buttonResume;
    public Bitmap buttonNextStage;
    public Bitmap buttonExit;

    public Bitmap bossHp;
    public Bitmap bossIcon;
    public Bitmap livesIcon;

    public Bitmap sword;

    public Audio intro;
    public Audio boss1_theme;
    public Audio boss2_theme;
    public Audio stage1_audio;
    public Audio stage2_audio;

    public Bitmap[] death;
    public Bitmap[] bomb;
    public Bitmap[] blueExplosion;

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

            is = manager.open("images/bullet_heavy.png");
            original = BitmapFactory.decodeStream(is);
            heavy = Bitmap.createScaledBitmap(original, 24, 24, false);

            is = manager.open("images/boss.png");
            original = BitmapFactory.decodeStream(is);
            int dimX = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 115, context.getResources()
                            .getDisplayMetrics());
            int dimY = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 150, context.getResources()
                            .getDisplayMetrics());
            boss = Bitmap.createScaledBitmap(original, dimX, dimY, false);

            is = manager.open("images/jawus.png");
            original = BitmapFactory.decodeStream(is);
            jawus = Bitmap.createScaledBitmap(original, 250, 270, false);

            is = manager.open("images/komashr.png");
            original = BitmapFactory.decodeStream(is);
            komashr = Bitmap.createScaledBitmap(original, 220, 300, false);

            is = manager.open("images/bullet_green.png");
            original = BitmapFactory.decodeStream(is);
            greenBullet12 = Bitmap.createScaledBitmap(original, 12, 12, false);

            is = manager.open("images/bullet_purple.png");
            original = BitmapFactory.decodeStream(is);
            purpleBullet18 = Bitmap.createScaledBitmap(original, 18, 18, false);

            is = manager.open("images/bullet_yellow.png");
            original = BitmapFactory.decodeStream(is);
            yellowBullet24 = Bitmap.createScaledBitmap(original, 24, 24, false);

            is = manager.open("images/bullet_blue_s.png");
            original = BitmapFactory.decodeStream(is);
            blueBullet12 = Bitmap.createScaledBitmap(original, 12, 12, false);

            is = manager.open("images/bullet_blue.png");
            original = BitmapFactory.decodeStream(is);
            blueBullet96 = Bitmap.createScaledBitmap(original, 96, 96, false);

            is = manager.open("images/bullet_red.png");
            original = BitmapFactory.decodeStream(is);
            redBullet12 = Bitmap.createScaledBitmap(original, 12, 12, false);

            is = manager.open("images/bullet_red.png");
            original = BitmapFactory.decodeStream(is);
            redBullet36 = Bitmap.createScaledBitmap(original, 36, 36, false);

            is = manager.open("images/bullet_white.png");
            original = BitmapFactory.decodeStream(is);
            whiteBullet48 = Bitmap.createScaledBitmap(original, 48, 48, false);

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

            is = manager.open("images/button_next.png");
            original = BitmapFactory.decodeStream(is);
            buttonNextStage = Bitmap.createScaledBitmap(original, 290, 250,
                    false);

            is = manager.open("images/button_level1.png");
            original = BitmapFactory.decodeStream(is);
            buttonStage1 = Bitmap.createScaledBitmap(original, 580, 140, false);

            is = manager.open("images/button_level2.png");
            original = BitmapFactory.decodeStream(is);
            buttonStage2 = Bitmap.createScaledBitmap(original, 580, 140, false);

            is = manager.open("images/button_level3.png");
            original = BitmapFactory.decodeStream(is);
            buttonStage3 = Bitmap.createScaledBitmap(original, 580, 140, false);

            is = manager.open("images/button_resume.png");
            original = BitmapFactory.decodeStream(is);
            buttonResume = Bitmap.createScaledBitmap(original, 290, 250, false);

            is = manager.open("images/button_retry.png");
            original = BitmapFactory.decodeStream(is);
            buttonRetry = Bitmap.createScaledBitmap(original, 290, 250, false);

            is = manager.open("images/button_exit.png");
            original = BitmapFactory.decodeStream(is);
            buttonExit = Bitmap.createScaledBitmap(original, 290, 250, false);

            is = manager.open("images/boss_hp.png");
            original = BitmapFactory.decodeStream(is);
            bossHp = Bitmap.createScaledBitmap(original, 350, 35, false);

            is = manager.open("images/boss_icon.png");
            original = BitmapFactory.decodeStream(is);
            bossIcon = Bitmap.createScaledBitmap(original, 150, 35, false);

            is = manager.open("images/sword_sprite.png");
            original = BitmapFactory.decodeStream(is);
            sword = Bitmap.createScaledBitmap(original, 30, 60, false);

            boss1_theme = new Audio(manager.openFd("audio/boss1_theme.mp3"));
            boss2_theme = new Audio(manager.openFd("audio/boss2_theme.mp3"));
            intro = new Audio(manager.openFd("audio/intro.mp3"));
            stage1_audio = new Audio(manager.openFd("audio/stage1_audio.mp3"));
            stage2_audio = new Audio(manager.openFd("audio/stage2_audio.mp3"));

            is = manager.open("images/explosion.png");
            original = BitmapFactory.decodeStream(is);
            death = AnimationUtils.cropBitmapIntoRectMatrix(context, original,
                    5, 5, 96, 96);

            is = manager.open("images/bomb_animation.png");
            original = BitmapFactory.decodeStream(is);
            bomb = AnimationUtils.cropBitmapIntoRectMatrix(context, original,
                    5, 2, 400, 400);

            is = manager.open("images/blue_explosion.png");
            original = BitmapFactory.decodeStream(is);
            blueExplosion = AnimationUtils.cropBitmapIntoRectMatrix(context,
                    original, 3, 4, 160, 160);

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
