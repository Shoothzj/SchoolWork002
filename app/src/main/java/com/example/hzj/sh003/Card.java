package com.example.hzj.sh003;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by hzj on 16-2-5.
 */
public class Card extends FrameLayout{

    private int num = 0;

    public Card(Context context) {
        super(context);
        label = new TextView(getContext());
        label.setTextSize(32);
        label.setBackgroundColor(0x33ffffff);
        label.setGravity(Gravity.CENTER);
        LayoutParams layoutParams = new LayoutParams(-1,-1);
        layoutParams.setMargins(10,10,0,0);
        addView(label, layoutParams);
        setNum(0);
    }

    public int getNum(){
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if(num<=0){
            label.setText("");
        }else {
            label.setText(num + "");
        }
    }

    private TextView label;

    public boolean equals(Card o) {
        return getNum()==o.getNum();
    }
}
