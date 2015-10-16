package com.example.heknow.whack_a_mole;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by HeKnOw on 9/24/15.
 */
public class HighScore extends AppCompatActivity {

    DBmole db_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db_ = new DBmole(this, null, null, 2);
        setContentView(R.layout.highscore);
        final Context context = this;
        Intent i = getIntent();
        final boolean hardmode = i.getExtras().getBoolean("hardmode");
        final global_user user = (global_user)i.getSerializableExtra("user");
        final Button returnB = (Button)findViewById(R.id.hsReturn);

        LinearLayout gL = (LinearLayout)findViewById(R.id.hsLinearLayout);

        RelativeLayout rl = new RelativeLayout(context);
        //user.mNameLevel.put(user.getName(), user.getLevel());
        TextView tv = new TextView(this);
        tv.setText("HIGHEST SCORES");
        tv.setTextSize(30);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        gL.addView(tv);

        Vector<Button> vButton = new Vector();

        //List<Map.Entry<String,Integer>> sortedMap = entriesSortedByValues(user.mNameLevel);
        //Map<String,Integer> sortedMap = sortByValue(user.mNameLevel);
        ArrayList<global_user> sortedMap = db_.getAllUsersbyLevel();

        for(final global_user entry : sortedMap)
        {
            int r = 0;
            Button bList = new Button(context);
            bList.setText(entry.getName() + "  Level : " + entry.getLevel());
            bList.setTextSize(10);
            bList.setGravity(Gravity.CENTER);
            bList.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            bList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UserInfo.class);
                    intent.putExtra("user",entry);
                    //add a level extra
                    startActivity(intent);
                }
            });

            vButton.add(bList);
        }

        for (Button button:
             vButton) {
            gL.addView(button);
        }

        returnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

    }

}
