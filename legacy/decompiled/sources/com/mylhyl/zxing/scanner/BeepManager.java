package com.mylhyl.zxing.scanner;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import java.io.Closeable;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
final class BeepManager implements MediaPlayer.OnErrorListener, Closeable {
    private static final float BEEP_VOLUME = 0.1f;
    private static final long VIBRATE_DURATION = 200;
    private final Context context;
    private MediaPlayer mediaPlayer;
    private int mediaResId;
    private boolean playBeep;
    private boolean vibrate;

    BeepManager(Context context) {
        this(context, 0);
    }

    BeepManager(Context context, int i) {
        this.context = context;
        this.mediaPlayer = null;
        setMediaResId(i);
    }

    public void setMediaResId(int i) {
        this.mediaResId = i;
        updatePrefs();
    }

    synchronized void updatePrefs() {
        boolean z = shouldBeep(this.context) && this.mediaResId > 0;
        this.playBeep = z;
        if (z && this.mediaPlayer == null) {
            this.mediaPlayer = buildMediaPlayer(this.context);
        }
        this.vibrate = false;
    }

    synchronized void playBeepSoundAndVibrate() {
        if (this.playBeep && this.mediaPlayer != null) {
            this.mediaPlayer.start();
        }
        if (this.vibrate) {
            ((Vibrator) this.context.getSystemService("vibrator")).vibrate(VIBRATE_DURATION);
        }
    }

    private static boolean shouldBeep(Context context) {
        return ((AudioManager) context.getSystemService("audio")).getRingerMode() == 2;
    }

    private MediaPlayer buildMediaPlayer(Context context) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor assetFileDescriptorOpenRawResourceFd = context.getResources().openRawResourceFd(this.mediaResId);
            try {
                mediaPlayer.setDataSource(assetFileDescriptorOpenRawResourceFd.getFileDescriptor(), assetFileDescriptorOpenRawResourceFd.getStartOffset(), assetFileDescriptorOpenRawResourceFd.getLength());
                assetFileDescriptorOpenRawResourceFd.close();
                mediaPlayer.setOnErrorListener(this);
                mediaPlayer.setAudioStreamType(3);
                mediaPlayer.setLooping(false);
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
                return mediaPlayer;
            } catch (Throwable th) {
                assetFileDescriptorOpenRawResourceFd.close();
                throw th;
            }
        } catch (IOException unused) {
            mediaPlayer.release();
            return null;
        }
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public synchronized boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        if (i != 100) {
            close();
            updatePrefs();
        }
        return true;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
    }
}
