package pl.bartlomiejzmuda.Memory_Game.data;


import android.os.CountDownTimer;
import android.widget.Chronometer;

import pl.bartlomiejzmuda.Memory_Game.GameActivity;

import java.util.ArrayList;


public class Controller {

    ArrayList<Card> listCards;
    ArrayList<Card> list2;
    int points;
    int maxpoints;
    int click;
    Chronometer chronometer;


    public Controller(Chronometer chronometer) {
        this.listCards = null;
        this.list2 = new ArrayList<Card>();
        this.points = 0;
        this.maxpoints = 6;
        this.click = 0;
        this.chronometer = chronometer;
    }

    public ArrayList<Card> getListCard() {
        return listCards;
    }

    public void setListCards(ArrayList<Card> listCards) {
        this.listCards = listCards;
    }

    public ArrayList<Card> getList2() {
        return list2;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public Chronometer getChronometer() {
        return chronometer;
    }

    public void setChronometer(Chronometer chronometer) {
        this.chronometer = chronometer;
    }

    public void checkCard(Card card) {

        list2.add(card);
        if (this.list2.size() == 2) {
            if (this.list2.get(0).getCouple() == this.list2.get(1).getCouple()) {
                this.points++;

                new CountDownTimer(400, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        GameActivity.hideCards(getList2());
                        list2.clear();
                    }
                }.start();


                if (this.points == this.maxpoints) { // end

                    GameActivity.summary(GameActivity.category, GameActivity.level, this);
                }

            } else {

                new CountDownTimer(400, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        list2.get(0).flipCard();
                        list2.get(1).flipCard();
                        list2.clear();
                    }
                }.start();
            }
        }
    }
}


