package pl.bartlomiejzmuda.Memory_Game;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;

import pl.bartlomiejzmuda.Memory_Game.data.Card;
import pl.bartlomiejzmuda.Memory_Game.data.Controller;
import pl.bartlomiejzmuda.Memory_Game.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private static Context mContext;
    private static Activity activity = null;
    public static String level;
    public static String category;
    private Chronometer chronometer;


    public ArrayList<Card> createCard(String category, String level, Controller controller) {
        // Create an array
        Integer[] array = new Integer[]{0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5};
        // Shuffle the elements in the array
        List<Integer> l = Arrays.asList(array);
        Collections.shuffle(l);
        ArrayList<Card> listCards = new ArrayList<Card>();
        Random random = new Random();
        int b = random.nextInt(4);
        if (level.equals("hard")) { // level hard
            for (int i = 0; i < l.size(); i++) {
                String nameCard = "imageViewCard" + String.valueOf(i);
                ImageView imageView = (ImageView) findViewById(getResources().getIdentifier(nameCard, "id", getPackageName()));
                Resources res = getResources();
                String nameFile = category + String.valueOf(l.get(i));
                int resID = res.getIdentifier(nameFile, "drawable", getPackageName());
                Drawable drawableFront = res.getDrawable(resID);
                nameFile = "karta" + random.nextInt(4);
                resID = res.getIdentifier(nameFile, "drawable", getPackageName());
                Drawable drawableBack = res.getDrawable(resID);
                imageView.setImageDrawable(drawableBack);
                Card card = new Card(imageView, i, drawableBack, drawableFront, l.get(i), controller);
                listCards.add(card);
            }
        } else {
            for (int i = 0; i < l.size(); i++) {
                String nameCard = "imageViewCard" + String.valueOf(i);
                ImageView imageView = (ImageView) findViewById(getResources().getIdentifier(nameCard, "id", getPackageName()));
                Resources res = getResources();
                String nameFile = category + String.valueOf(l.get(i));
                int resID = res.getIdentifier(nameFile, "drawable", getPackageName());
                Drawable drawableFront = res.getDrawable(resID);
                nameFile = "karta" + b;
                resID = res.getIdentifier(nameFile, "drawable", getPackageName());
                Drawable drawableBack = res.getDrawable(resID);
                imageView.setImageDrawable(drawableBack);
                Card card = new Card(imageView, i, drawableBack, drawableFront, l.get(i), controller);
                listCards.add(card);
            }
        }
        return listCards;
    }

    public static void hideCards(ArrayList<Card> list2) {
        list2.get(0).getImageView().setImageResource(R.drawable.kk);
        list2.get(0).getImageView().setClickable(false);
        list2.get(1).getImageView().setImageResource(R.drawable.kk);
        list2.get(1).getImageView().setClickable(false);
    }

    static void flipAllCards(final Controller controller, String level) {
        int time;
        if (level.equals("easy")) { // level easy
            time = 1750;
        } else {
            time = 500;
        }
        for (int i = 0; i < controller.getListCard().size(); i++) {
            controller.getListCard().get(i).flipCard();
        }
        new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                for (int i = 0; i < controller.getListCard().size(); i++) {
                    controller.getListCard().get(i).flipCard();
                }
            }
        }.start();
    }

    static public void summary(String category, String level, Controller controller) {
        controller.getChronometer().stop();
        long elapsedMillis = SystemClock.elapsedRealtime() - controller.getChronometer().getBase();
        Intent intent = new Intent(mContext, SummaryActivity.class);
        Bundle extras = new Bundle();
        extras.putString("CATEGORY", category);
        extras.putString("LEVEL", level);
        extras.putInt("NUMBERS", controller.getClick());
        extras.putLong("TIME", elapsedMillis);
        intent.putExtras(extras);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mContext = this;
        activity = this;
        chronometer = findViewById(R.id.chronometer);
        Bundle extras = getIntent().getExtras();
        this.category = extras.getString("CATEGORY");
        this.level = extras.getString("LEVEL");
        final Controller controller = new Controller(chronometer);
        ArrayList<Card> listCards = createCard(this.category, this.level, controller);
        controller.setListCards(listCards);
        if (level.equals("easy") || level.equals("medium")) {
            flipAllCards(controller, level);
        }
        ImageView imageView = (ImageView) findViewById(R.id.imageViewSilent);
        if (StartActivity.buttonMusic == true) {
            imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_lock_silent_mode_off));
        } else {
            imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_lock_silent_mode));
        }
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (StartActivity.isPlaying == false && StartActivity.buttonMusic == true) {
            StartActivity.player = MediaPlayer.create(this, R.raw.music);
            StartActivity.player.setLooping(true);
            StartActivity.player.setVolume(100, 100);
            StartActivity.player.start();
            StartActivity.isPlaying = true;
        }

    }

    @Override
    protected void onPause() {
        if (this.isFinishing()) {
            StartActivity.player.stop();
            StartActivity.isPlaying = false;

        }
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                StartActivity.player.stop();
                StartActivity.isPlaying = false;
            } else {
            }
        }
        super.onPause();
    }

    public void click(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.textViewBack:
                intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.textViewRestart:
                intent = new Intent(GameActivity.this, GameActivity.class);
                Bundle extras = new Bundle();
                extras.putString("CATEGORY", this.category);
                extras.putString("LEVEL", this.level);
                intent.putExtras(extras);
                startActivity(intent);
                break;
            case R.id.imageViewSilent:
                ImageView imageView = (ImageView) findViewById(R.id.imageViewSilent);
                if (StartActivity.isPlaying == false) {
                    imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_lock_silent_mode_off));
                    StartActivity.player = MediaPlayer.create(this, R.raw.music);
                    StartActivity.player.setLooping(true);
                    StartActivity.player.setVolume(100, 100);
                    StartActivity.player.start();
                    StartActivity.isPlaying = true;
                    StartActivity.buttonMusic = true;
                } else {
                    imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_lock_silent_mode));
                    StartActivity.player.stop();
                    StartActivity.isPlaying = false;
                    StartActivity.buttonMusic = false;
                }
                break;

        }
    }


}
