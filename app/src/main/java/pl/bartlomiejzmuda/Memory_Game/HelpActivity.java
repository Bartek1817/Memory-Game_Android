package pl.bartlomiejzmuda.Memory_Game;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import pl.bartlomiejzmuda.Memory_Game.R;

import java.util.List;

import static pl.bartlomiejzmuda.Memory_Game.StartActivity.buttonMusic;
import static pl.bartlomiejzmuda.Memory_Game.StartActivity.isPlaying;
import static pl.bartlomiejzmuda.Memory_Game.StartActivity.player;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
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
        super.onPause();
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                player.stop();
                isPlaying = false;
            }
        }
    }

    public void click(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.textViewAuthor:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bartlomiejzmuda.cba.pl"));
                startActivity(intent);
                break;
            case R.id.imageViewIconBZ:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bartlomiejzmuda.cba.pl"));
                startActivity(intent);
                break;
            case R.id.textViewBack:
                intent = new Intent(HelpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
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
