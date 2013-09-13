package com.puc.sh.model;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class Audio {
	private MediaPlayer player;

	public Audio(AssetFileDescriptor descriptor) {
		player = new MediaPlayer();
		try {
			player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(),
					descriptor.getLength());
			player.setVolume(1, 1);

			player.setLooping(true);
			player.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setVolume(float volume) {
		if (volume > 1) {
			volume = 1;
		} else if (volume < 0) {
			volume = 0;
		}
		player.setVolume(volume, volume);
	}

	public void dispose() {
		if (player.isPlaying())
			player.stop();
		player.release();
	}

	public void play() {
		player.start();
	}

	public void pause() {
		player.pause();
	}

}
