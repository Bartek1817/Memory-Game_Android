package pl.bartlomiejzmuda.Memory_Game;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pl.bartlomiejzmuda.Memory_Game.R;

import java.util.List;

import static pl.bartlomiejzmuda.Memory_Game.StartActivity.buttonMusic;
import static pl.bartlomiejzmuda.Memory_Game.StartActivity.isPlaying;
import static pl.bartlomiejzmuda.Memory_Game.StartActivity.player;

public class SummaryActivity extends AppCompatActivity {
    public static String level;
    public static String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Bundle extras = getIntent().getExtras();
        this.category = extras.getString("CATEGORY");
        this.level = extras.getString("LEVEL");
        int number = extras.getInt("NUMBERS");
        long time = extras.getLong("TIME");

        ((TextView) findViewById(R.id.textViewSummaryCount)).setText(String.valueOf(number));
        ((TextView) findViewById(R.id.textViewChronometer)).setText(String.format("%1$tM:%1$tS.%1$tL", time));

        ImageView imageView = (ImageView) findViewById(R.id.imageViewSilent);
        if (buttonMusic == true) {
            imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_lock_silent_mode_off));
        } else {
            imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_lock_silent_mode));
        }

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
        if (this.isFinishing()) {
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

    public void click(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.textViewBack:
                intent = new Intent(SummaryActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.textViewRestart:
                intent = new Intent(SummaryActivity.this, GameActivity.class);
                Bundle extras = new Bundle();
                extras.putString("CATEGORY", this.category);
                extras.putString("LEVEL", this.level);
                intent.putExtras(extras);
                startActivity(intent);
                break;
            case R.id.imageViewSilent:
                ImageView imageView = (ImageView) findViewById(R.id.imageViewSilent);
                if (isPlaying == false) {
                    imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_lock_silent_mode_off));
                    player = MediaPlayer.create(this, R.raw.music);
                    player.setLooping(true);
                    player.setVolume(100, 100);
                    player.start();
                    isPlaying = true;
                    buttonMusic = true;
                } else {
                    imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_lock_silent_mode));
                    player.stop();
                    isPlaying = false;
                    buttonMusic = false;
                }
                break;
        }
    }
}
