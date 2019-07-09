package pl.bartlomiejzmuda.Memory_Game;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pl.bartlomiejzmuda.Memory_Game.R;

import java.util.List;

public class StartActivity extends AppCompatActivity {
    static public MediaPlayer player;
    static boolean isPlaying = false;
    static boolean buttonMusic = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if (isPlaying == false) {
            player = MediaPlayer.create(this, R.raw.music);
            player.setLooping(true);
            player.setVolume(100, 100);
            player.start();
            isPlaying = true;
        }
        Intent intent;
        intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlaying == false && buttonMusic == true) {
            player = MediaPlayer.create(this, R.raw.music);
            player.setLooping(true);
            player.setVolume(100, 100);
            player.start();
            isPlaying = true;
        }

    }

    @Override
    protected void onPause() {
        if (this.isFinishing()) { //basically BACK was pressed from this activity
            player.stop();
            isPlaying = false;

        }
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                player.stop();
                isPlaying = false;
            } else {
            }
        }
        super.onPause();
    }
}
