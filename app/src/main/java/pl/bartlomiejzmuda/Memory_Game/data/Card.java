package pl.bartlomiejzmuda.Memory_Game.data;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;


public class Card {
    ImageView imageView;
    int id;
    int x;
    int y;
    Drawable back;
    Drawable front;
    boolean inverted;
    int couple;
    Controller controller;


    public Card(ImageView imageView, int id, Drawable back, Drawable front, int couple, final Controller controller) {
        this.imageView = imageView;
        this.id = id;
        this.x = 0;
        this.y = 0;
        this.back = back;
        this.front = front;
        this.inverted = false;
        this.couple = couple;
        this.controller = controller;


        this.imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (inverted == false && controller.getList2().size() < 2) { // blokada żeby tylko 2 karty mogł być odwrócone
                    flipCard();
                    controller.setClick(controller.getClick() + 1);
                    controller.checkCard(Card.this);
                }
            }
        });

    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Drawable getBack() {
        return back;
    }

    public void setBack(Drawable back) {
        this.back = back;
    }

    public Drawable getFront() {
        return front;
    }

    public void setFront(Drawable front) {
        this.front = front;
    }

    public boolean isInverted() {
        return inverted;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    public int getCouple() {
        return couple;
    }

    public void setCouple(int couple) {
        this.couple = couple;
    }

    public void flipCard() {
        if (!inverted) {
            getImageView().setImageDrawable(getFront()); // front
            imageView.setClickable(false);
        } else {
            getImageView().setImageDrawable(getBack());  //back
            imageView.setClickable(true);
        }
        setInverted(!inverted);
    }

}
