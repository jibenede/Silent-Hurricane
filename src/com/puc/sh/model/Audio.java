package com.puc.sh.model;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class Audio {
    private MediaPlayer player;
    private AssetFileDescriptor mDescriptor;

    public Audio(AssetFileDescriptor descriptor) {
        mDescriptor = descriptor;
    }

    public void prepare() {
        player = new MediaPlayer();

        try {
            player.setDataSource(mDescriptor.getFileDescriptor(),
                    mDescriptor.getStartOffset(), mDescriptor.getLength());
            player.setVolume(1, 1);

            player.setLooping(true);
            player.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

    public void stop() {
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
