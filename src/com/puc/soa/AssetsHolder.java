package com.puc.soa;

import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;

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
	public Bitmap raven;
	public Bitmap eclipse;

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
	public Bitmap introBackground;
	public Bitmap epilogueBackground;
	public Bitmap buttonPlay;
	public Bitmap buttonTutorial;
	public Bitmap buttonStage1;
	public Bitmap buttonStage2;
	public Bitmap buttonStage3;

	public Bitmap buttonRetry;
	public Bitmap buttonResume;
	public Bitmap buttonNextStage;
	public Bitmap buttonExit;

	public Bitmap tutorial1;
	public Bitmap tutorial2;
	public Bitmap tutorial3;

	public Bitmap bossHp;
	public Bitmap bossIcon;
	public Bitmap livesIcon;
	public Bitmap bombIcon;

	public Bitmap sword;

	public Bitmap textPause;
	public Bitmap textDefeat;
	public Bitmap textVictory;

	public Audio intro;
	public Audio boss0_theme;
	public Audio boss1_theme;
	public Audio boss2_theme;
	public Audio stage0_audio;
	public Audio stage1_audio;
	public Audio stage2_audio;
	public Audio title_screen;

	public Bitmap[] death;
	public Bitmap[] bomb;
	public Bitmap[] blueExplosion;

	private InputStream is;
	private Bitmap original;

	private int mActiveSet;
	public int mPendingReset;

	public AssetsHolder(Context context) {
		AssetManager manager = context.getAssets();
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Config.RGB_565;

			is = manager.open("images/portada.png");
			original = BitmapFactory.decodeStream(is, null, options);
			mainBackground = Bitmap.createScaledBitmap(original, Globals.CANVAS_WIDTH,
					Globals.CANVAS_HEIGHT, false);

			is = manager.open("images/intro_background.jpg");
			original = BitmapFactory.decodeStream(is, null, options);
			introBackground = Bitmap.createScaledBitmap(original, 2276, 1280, false);

			is = manager.open("images/epilogue_background.jpg");
			original = BitmapFactory.decodeStream(is, null, options);
			epilogueBackground = Bitmap.createScaledBitmap(original, 1690, 1280, false);

			is = manager.open("images/button_stages.png");
			original = BitmapFactory.decodeStream(is);
			buttonPlay = Bitmap.createScaledBitmap(original, 400, 180, false);

			is = manager.open("images/button_tutorial.png");
			original = BitmapFactory.decodeStream(is);
			buttonTutorial = Bitmap.createScaledBitmap(original, 400, 180, false);

			is = manager.open("images/button_next.png");
			original = BitmapFactory.decodeStream(is);
			buttonNextStage = Bitmap.createScaledBitmap(original, 290, 250, false);

			is = manager.open("images/button_level1.png");
			original = BitmapFactory.decodeStream(is);
			buttonStage1 = Bitmap.createScaledBitmap(original, 580, 140, false);

			is = manager.open("images/button_level2.png");
			original = BitmapFactory.decodeStream(is);
			buttonStage2 = Bitmap.createScaledBitmap(original, 580, 140, false);

			is = manager.open("images/button_level3.png");
			original = BitmapFactory.decodeStream(is);
			buttonStage3 = Bitmap.createScaledBitmap(original, 580, 140, false);

			is = manager.open("images/player.png");
			original = BitmapFactory.decodeStream(is);
			ship = Bitmap.createScaledBitmap(original, 64, 64, false);

			is = manager.open("images/plasma.png");
			original = BitmapFactory.decodeStream(is);
			plasma = Bitmap.createScaledBitmap(original, 32, 32, false);

			is = manager.open("images/bullet_heavy.png");
			original = BitmapFactory.decodeStream(is);
			heavy = Bitmap.createScaledBitmap(original, 24, 24, false);

			is = manager.open("images/boss.png");
			original = BitmapFactory.decodeStream(is);
			boss = Bitmap.createScaledBitmap(original, 220, 300, false);

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

			is = manager.open("images/raven.png");
			original = BitmapFactory.decodeStream(is);
			raven = Bitmap.createScaledBitmap(original, 80, 80, false);

			is = manager.open("images/eclipse.png");
			original = BitmapFactory.decodeStream(is);
			eclipse = Bitmap.createScaledBitmap(original, 80, 80, false);

			is = manager.open("images/button_resume.png");
			original = BitmapFactory.decodeStream(is);
			buttonResume = Bitmap.createScaledBitmap(original, 290, 250, false);

			is = manager.open("images/button_retry.png");
			original = BitmapFactory.decodeStream(is);
			buttonRetry = Bitmap.createScaledBitmap(original, 290, 250, false);

			is = manager.open("images/button_next.png");
			original = BitmapFactory.decodeStream(is);
			buttonNextStage = Bitmap.createScaledBitmap(original, 290, 250, false);

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

			is = manager.open("images/stage2_background.jpg");
			original = BitmapFactory.decodeStream(is);
			stage2Background = Bitmap.createScaledBitmap(original, Globals.CANVAS_WIDTH,
					original.getHeight() * 2, false);

			is = manager.open("images/explosion.png");
			original = BitmapFactory.decodeStream(is);
			death = AnimationUtils.cropBitmapIntoRectMatrix(context, original, 5, 5, 96, 96);

			is = manager.open("images/bomb_animation.png");
			original = BitmapFactory.decodeStream(is);
			bomb = AnimationUtils.cropBitmapIntoRectMatrix(context, original, 5, 2, 400, 400);

			is = manager.open("images/blue_explosion.png");
			original = BitmapFactory.decodeStream(is);
			blueExplosion = AnimationUtils.cropBitmapIntoRectMatrix(context, original, 3, 4, 160,
					160);

			is = manager.open("images/lives_icon.png");
			original = BitmapFactory.decodeStream(is);
			livesIcon = Bitmap.createScaledBitmap(original, 50, 20, false);

			is = manager.open("images/bomb_icon.png");
			original = BitmapFactory.decodeStream(is);
			bombIcon = Bitmap.createScaledBitmap(original, 25, 25, false);

			is = manager.open("images/tutorial1.jpg");
			original = BitmapFactory.decodeStream(is, null, options);
			tutorial1 = Bitmap.createScaledBitmap(original, Globals.CANVAS_WIDTH,
					Globals.CANVAS_HEIGHT, false);

			is = manager.open("images/tutorial2.jpg");
			original = BitmapFactory.decodeStream(is, null, options);
			tutorial2 = Bitmap.createScaledBitmap(original, Globals.CANVAS_WIDTH,
					Globals.CANVAS_HEIGHT, false);

			is = manager.open("images/tutorial3.jpg");
			original = BitmapFactory.decodeStream(is, null, options);
			tutorial3 = Bitmap.createScaledBitmap(original, Globals.CANVAS_WIDTH,
					Globals.CANVAS_HEIGHT, false);

			is = manager.open("images/text_pause.png");
			original = BitmapFactory.decodeStream(is);
			textPause = Bitmap.createScaledBitmap(original, 500, 150, false);

			is = manager.open("images/text_victory.png");
			original = BitmapFactory.decodeStream(is);
			textVictory = Bitmap.createScaledBitmap(original, 500, 180, false);

			is = manager.open("images/text_gameover.png");
			original = BitmapFactory.decodeStream(is);
			textDefeat = Bitmap.createScaledBitmap(original, 600, 370, false);

			boss0_theme = new Audio(manager.openFd("audio/boss0_theme.mp3"));
			boss1_theme = new Audio(manager.openFd("audio/boss1_theme.mp3"));
			boss2_theme = new Audio(manager.openFd("audio/boss2_theme.mp3"));

			stage0_audio = new Audio(manager.openFd("audio/stage0_audio.mp3"));
			stage1_audio = new Audio(manager.openFd("audio/stage1_audio.mp3"));
			stage2_audio = new Audio(manager.openFd("audio/stage2_audio.mp3"));

			intro = new Audio(manager.openFd("audio/intro.mp3"));
			title_screen = new Audio(manager.openFd("audio/title_screen.mp3"));

			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void reset2() {
		// boss = jawus = komashr =
		//
		// stalkerA = predatorA = stingray = dart =
		//
		// plasma = heavy =
		//
		// purpleBullet18 = greenBullet12 = yellowBullet24 = blueBullet12 =
		// blueBullet96 = redBullet36 = redBullet12 = whiteBullet48 =
		//
		// stage2Background =
		//
		// buttonRetry = buttonResume = buttonExit =
		//
		// bossHp = bossIcon = livesIcon =
		//
		// sword = null;
		//
		// boss1_theme = boss2_theme = stage1_audio = stage2_audio = null;
		//
		// mPendingReset = 0;
	}

	public void reset1() {
		// mainBackground = introBackground = buttonPlay = buttonTutorial =
		// buttonStage1 = buttonStage2 = buttonStage3 = null;
		//
		// intro = title_screen = null;
		//
		// mPendingReset = 0;
	}

	public void prepare1() {
		// AssetManager manager = context.getAssets();
		//
		// if (mActiveSet != 1) {
		// mActiveSet = 1;
		// mPendingReset = 2;
		//
		// }

	}

	public void prepare2() {
		// AssetManager manager = context.getAssets();
		//
		// if (mActiveSet != 2) {
		// mActiveSet = 2;
		// mPendingReset = 1;
		//
		// try {
		//
		// is.close();
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
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
