package com.example.hzj.sh003;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzj on 16-2-5.
 */
public class GameView extends GridLayout{
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }
    private void initGameView(){
        setColumnCount(4);
        setBackgroundColor(0xFFbbada0);
        setOnTouchListener(new OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                swipeLeft();
                            } else if (offsetX > 5) {
                                swipeRight();
                            }
                        } else {
                            if (offsetY < -5) {
                                swipeUp();
                            } else if (offsetY > 5) {
                                swipeDown();
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (Math.min(h,w)-10)/4;
        addCards(cardWidth,cardWidth);
        startGame();
    }
    private void addCards(int cardWidth,int cardHeight){
        Card c;
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                c = new Card(getContext());
                c.setNum(0);
                addView(c,cardWidth,cardHeight);
                cardsMap[x][y] = c;
            }
        }
    }
    private void startGame(){
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                cardsMap[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
        MainActivity.getMainActivity().clearScore();
    }
    public void addRandomNum(){
        emptyPoints.clear();
        for(int y=0;y<4;y++){
            for (int x=0;x<4;x++){
                if(cardsMap[x][y].getNum()<=0){
                    emptyPoints.add(new Point(x,y));
                }
            }
        }
        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
    }
    private void swipeLeft(){
        boolean merge = false;
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                for(int temp=x+1;temp<4;temp++){
                    if(cardsMap[temp][y].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[temp][y].getNum());
                            cardsMap[temp][y].setNum(0);
                            x--;
                            merge = true;
                        }else if(cardsMap[x][y].equals(cardsMap[temp][y])){
                            cardsMap[x][y].setNum(cardsMap[temp][y].getNum()*2);
                            cardsMap[temp][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeRight(){
        boolean merge = false;
        for(int y=0;y<4;y++){
            for(int x=3;x>=0;x--){
                for(int temp=x-1;temp>=0;temp--){
                    if(cardsMap[temp][y].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[temp][y].getNum());
                            cardsMap[temp][y].setNum(0);
                            x++;
                            merge = true;
                        }else if(cardsMap[x][y].equals(cardsMap[temp][y])){
                            cardsMap[x][y].setNum(cardsMap[temp][y].getNum()*2);
                            cardsMap[temp][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeUp(){
        boolean merge = false;
        for(int x=0;x<4;x++){
            for(int y=0;y<4;y++){
                for(int temp=y+1;temp<4;temp++){
                    if(cardsMap[x][temp].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][temp].getNum());
                            cardsMap[x][temp].setNum(0);
                            y--;
                            merge = true;
                        }else if(cardsMap[x][y].equals(cardsMap[x][temp])){
                            cardsMap[x][y].setNum(cardsMap[x][temp].getNum()*2);
                            cardsMap[x][temp].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeDown(){
        boolean merge = false;
        for(int x=0;x<4;x++){
            for(int y=3;y>=0;y--){
                for(int temp=y-1;temp>=0;temp--){
                    if(cardsMap[x][temp].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][temp].getNum());
                            cardsMap[x][temp].setNum(0);
                            y++;
                            merge = true;
                        }else if(cardsMap[x][y].equals(cardsMap[x][temp])){
                            cardsMap[x][y].setNum(cardsMap[x][temp].getNum()*2);
                            cardsMap[x][temp].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }
    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<>();
    public void checkComplete(){
        boolean complete = true;
        ALL:
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                if(cardsMap[x][y].getNum()==0||((x>0)&&cardsMap[x][y].getNum()==cardsMap[x-1][y].getNum())||(x<3&&cardsMap[x][y].getNum()==cardsMap[x+1][y].getNum())||(y>0&&cardsMap[x][y].getNum()==cardsMap[x][y-1].getNum())||(y<3&&cardsMap[x][y].getNum()==cardsMap[x][y+1].getNum())){
                    complete = false;
                    break ALL;
                }
            }
        }
        if(complete){
            new AlertDialog.Builder(getContext()).setTitle("Hi").setMessage("Game Over").setCancelable(false).setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }
    }
}
