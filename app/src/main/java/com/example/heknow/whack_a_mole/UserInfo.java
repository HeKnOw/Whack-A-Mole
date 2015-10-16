package com.example.heknow.whack_a_mole;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by HeKnOw on 9/24/15.
 */
public class UserInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);
        final Context context = this;
        Intent i = getIntent();
        final global_user user = (global_user)i.getSerializableExtra("user");
        TextView agetv = (TextView)findViewById(R.id.ageTv);
        TextView levelTv = (TextView)findViewById(R.id.levelTv);
        TextView usernameTv = (TextView)findViewById(R.id.usernameTv);
        TextView genderTv = (TextView)findViewById(R.id.genderTv);
        Button restartBtn = (Button)findViewById(R.id.returnBtn);
        String level = Integer.toString(user.getLevel());

        genderTv.setText(user.gender);
        usernameTv.setText(user.getName());
        levelTv.setText(level);
        agetv.setText(user.getAge());

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

    }
}
