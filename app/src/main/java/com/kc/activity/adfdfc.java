//package com.bcinfo.TripAway;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.res.AssetFileDescriptor;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.media.MediaPlayer.OnCompletionListener;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Vibrator;
//import android.view.SurfaceHolder;
//import android.view.SurfaceHolder.Callback;
//import android.view.SurfaceView;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.Toast;
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.Result;
//import com.mining.app.zxing.camera.CameraManager;
//import com.mining.app.zxing.decoding.CaptureActivityHandler;
//import com.mining.app.zxing.decoding.InactivityTimer;
//import com.mining.app.zxing.view.ViewfinderView;
//import java.io.IOException;
//import java.util.Vector;
//
//public class QRScanActivity extends Activity
//        implements SurfaceHolder.Callback
//{
//    private static final float BEEP_VOLUME = 0.1F;
//    private static final long VIBRATE_DURATION = 200L;
//    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener()
//    {
//        public void onCompletion(MediaPlayer paramAnonymousMediaPlayer)
//        {
//            paramAnonymousMediaPlayer.seekTo(0);
//        }
//    };
//    private String characterSet;
//    private Vector<BarcodeFormat> decodeFormats;
//    private CaptureActivityHandler handler;
//    private boolean hasSurface;
//    ImageView imgLight;
//    private InactivityTimer inactivityTimer;
//    private MediaPlayer mediaPlayer;
//    private boolean playBeep;
//    private boolean vibrate;
//    private ViewfinderView viewfinderView;
//
//    private void initBeepSound()
//    {
//        AssetFileDescriptor localAssetFileDescriptor;
//        if ((this.playBeep) && (this.mediaPlayer == null))
//        {
//            setVolumeControlStream(3);
//            this.mediaPlayer = new MediaPlayer();
//            this.mediaPlayer.setAudioStreamType(3);
//            this.mediaPlayer.setOnCompletionListener(this.beepListener);
//            localAssetFileDescriptor = getResources().openRawResourceFd(2131034112);
//        }
//        try
//        {
//            this.mediaPlayer.setDataSource(localAssetFileDescriptor.getFileDescriptor(), localAssetFileDescriptor.getStartOffset(), localAssetFileDescriptor.getLength());
//            localAssetFileDescriptor.close();
//            this.mediaPlayer.setVolume(0.1F, 0.1F);
//            this.mediaPlayer.prepare();
//            return;
//        }
//        catch (IOException localIOException)
//        {
//            this.mediaPlayer = null;
//        }
//    }
//
//    private void initCamera(SurfaceHolder paramSurfaceHolder)
//    {
//        try
//        {
//            CameraManager.get().openDriver(paramSurfaceHolder);
//            if (this.handler == null)
//                this.handler = new CaptureActivityHandler(this, this.decodeFormats, this.characterSet);
//            return;
//        }
//        catch (IOException paramSurfaceHolder)
//        {
//        }
//        catch (RuntimeException paramSurfaceHolder)
//        {
//        }
//    }
//
//    private void playBeepSoundAndVibrate()
//    {
//        if ((this.playBeep) && (this.mediaPlayer != null))
//            this.mediaPlayer.start();
//        if (this.vibrate)
//            ((Vibrator)getSystemService("vibrator")).vibrate(200L);
//    }
//
//    public void drawViewfinder()
//    {
//        this.viewfinderView.drawViewfinder();
//    }
//
//    public Handler getHandler()
//    {
//        return this.handler;
//    }
//
//    public ViewfinderView getViewfinderView()
//    {
//        return this.viewfinderView;
//    }
//
//    public void handleDecode(Result paramResult, Bitmap paramBitmap)
//    {
//        this.inactivityTimer.onActivity();
//        playBeepSoundAndVibrate();
//        paramResult = paramResult.getText();
//        if (paramResult.equals(""))
//            Toast.makeText(this, "Scan failed!", 0).show();
//        while (true)
//        {
//            finish();
//            return;
//            Intent localIntent = new Intent();
//            Bundle localBundle = new Bundle();
//            localBundle.putString("result", paramResult);
//            localBundle.putParcelable("bitmap", paramBitmap);
//            localIntent.putExtras(localBundle);
//            setResult(-1, localIntent);
//        }
//    }
//
//    public void onCreate(Bundle paramBundle)
//    {
//        super.onCreate(paramBundle);
//        setContentView(2130903041);
//        CameraManager.init(getApplication());
//        this.viewfinderView = ((ViewfinderView)findViewById(2131296286));
//        ((ImageView)findViewById(2131296328)).setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View paramAnonymousView)
//            {
//                QRScanActivity.this.finish();
//            }
//        });
//        this.imgLight = ((ImageView)findViewById(2131296330));
//        this.imgLight.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View paramAnonymousView)
//            {
//                CameraManager.get().RevertLight();
//                if (CameraManager.get().bLightOpen)
//                {
//                    QRScanActivity.this.imgLight.setImageDrawable(QRScanActivity.this.getResources().getDrawable(2130837586));
//                    return;
//                }
//                QRScanActivity.this.imgLight.setImageDrawable(QRScanActivity.this.getResources().getDrawable(2130837585));
//            }
//        });
//        this.hasSurface = false;
//        this.inactivityTimer = new InactivityTimer(this);
//    }
//
//    protected void onDestroy()
//    {
//        this.inactivityTimer.shutdown();
//        super.onDestroy();
//    }
//
//    protected void onPause()
//    {
//        super.onPause();
//        if (this.handler != null)
//        {
//            this.handler.quitSynchronously();
//            this.handler = null;
//        }
//        CameraManager.get().closeDriver();
//    }
//
//    protected void onResume()
//    {
//        super.onResume();
//        SurfaceHolder localSurfaceHolder = ((SurfaceView)findViewById(2131296285)).getHolder();
//        if (this.hasSurface)
//            initCamera(localSurfaceHolder);
//        while (true)
//        {
//            this.decodeFormats = null;
//            this.characterSet = null;
//            this.playBeep = true;
//            if (((AudioManager)getSystemService("audio")).getRingerMode() != 2)
//                this.playBeep = false;
//            initBeepSound();
//            this.vibrate = true;
//            return;
//            localSurfaceHolder.addCallback(this);
//            localSurfaceHolder.setType(3);
//        }
//    }
//
//    public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
//    {
//    }
//
//    public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
//    {
//        if (!this.hasSurface)
//        {
//            this.hasSurface = true;
//            initCamera(paramSurfaceHolder);
//        }
//    }
//
//    public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
//    {
//        this.hasSurface = false;
//    }
//}