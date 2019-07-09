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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewSilent);
        if (buttonMusic == true) {
            imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_lock_silent_mode_off));
        } else {
            imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_lock_silent_mode));
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

    @Override
    protected void onResume() {
        super.onResume();
        ImageView imageView = (ImageView) findViewById(R.id.imageViewSilent);
        if (isPlaying == false && buttonMusic == true) {
            player = MediaPlayer.create(this, R.raw.music);
            player.setLooping(true);
            player.setVolume(100, 100);
            player.start();
            isPlaying = true;
        }

    }

    @Override
    public void onBackPressed() {

    }

    public void click(View view) {
        Intent intent;
        TextView textViewLevel = (TextView) findViewById(R.id.textViewChooseLevel);
        TextView textViewCategory = (TextView) findViewById(R.id.textViewChooseCategory);
        switch (view.getId()) {
            case R.id.textViewStart:
                intent = new Intent(MainActivity.this, GameActivity.class);
                Bundle extras = new Bundle();
                String category = "b";
                String level = "t";
                if (textViewCategory.getText().equals(getResources().getString(R.string.categoryBuildings))) {
                    category = "b";
                }
                if (textViewCategory.getText().equals(getResources().getString(R.string.categoryAnimals))) {
                    category = "z";
                }
                if (textViewCategory.getText().equals(getResources().getString(R.string.categoryVehileces))) {
                    category = "p";
                }
                if (textViewLevel.getText().equals(getResources().getString(R.string.levelEasy))) {
                    level = "easy";
                }
                if (textViewLevel.getText().equals(getResources().getString(R.string.levelMedium))) {
                    level = "medium";
                }
                if (textViewLevel.getText().equals(getResources().getString(R.string.levelHard))) {
                    level = "hard";
                }
                extras.putString("CATEGORY", category);
                extras.putString("LEVEL", level);
                intent.putExtras(extras);
                startActivity(intent);
                break;
            case R.id.textViewHelp:
                intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.textViewLeftCategory:
                if (textViewCategory.getText().equals(getString(R.string.categoryBuildings))) {
                    textViewCategory.setText(getString(R.string.categoryAnimals));
                    break;
                } else if (textViewCategory.getText().equals(getString(R.string.categoryAnimals))) {
                    textViewCategory.setText(getString(R.string.categoryVehileces));
                    break;
                } else {
                    textViewCategory.setText(getString(R.string.categoryBuildings));
                    break;
                }

            case R.id.textViewRightCategory:
                if (textViewCategory.getText().equals(getString(R.string.categoryBuildings))) {
                    textViewCategory.setText(getString(R.string.categoryVehileces));
                    break;
                } else if (textViewCategory.getText().equals(getString(R.string.categoryVehileces))) {
                    textViewCategory.setText(getString(R.string.categoryAnimals));
                    break;
                } else {
                    textViewCategory.setText(getString(R.string.categoryBuildings));
                    break;
                }
            case R.id.textViewLeftLevel:
                if (textViewLevel.getText().equals(getString(R.string.levelMedium))) {
                    textViewLevel.setText(getString(R.string.levelEasy));
                    break;
                } else if (textViewLevel.getText().equals(getString(R.string.levelEasy))) {
                    textViewLevel.setText(getString(R.string.levelHard));
                    break;
                } else {
                    textViewLevel.setText(getString(R.string.levelMedium));
                    break;
                }
            case R.id.textViewRightLevel:
                if (textViewLevel.getText().equals(getString(R.string.levelMedium))) {
                    textViewLevel.setText(getString(R.string.levelHard));
                    break;
                } else if (textViewLevel.getText().equals(getString(R.string.levelHard))) {
                    textViewLevel.setText(getString(R.string.levelEasy));
                    break;
                } else {
                    textViewLevel.setText(getString(R.string.levelMedium));
                    break;
                }
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
