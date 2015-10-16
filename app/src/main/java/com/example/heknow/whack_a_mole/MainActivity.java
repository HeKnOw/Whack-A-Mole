package com.example.heknow.whack_a_mole;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    global_user user;
    EditText username;
    EditText password;
    TextView ageLabel;
    SeekBar ageBar;
    RadioGroup radioGender;
    Switch hardmode;
    Button start;
    LinearLayout layout;
    AlertDialog.Builder builder;
    int age = 1;
    String gender;
    boolean hardBool = false;
    DBmole _db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = this;
        layout = (LinearLayout)findViewById(R.id.linearLayout);
        username = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);
        ageLabel = (TextView)findViewById(R.id.seekLabel);
        ageBar = (SeekBar)findViewById(R.id.seekBar);
        radioGender = (RadioGroup)findViewById(R.id.radioGroup);
        hardmode = (Switch)findViewById(R.id.switch1);
        start = (Button)findViewById(R.id.button);
        _db = new DBmole(this,null,null,2);
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if(extras != null){
            user = (global_user)i.getSerializableExtra("user");
            username.setText(user.getName());
        } else {
            user = new global_user();
            //user.mNameLevel = new HashMap<String,Integer>();
        }
        //user.setUpVectors();
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");
        builder.setMessage(R.string.dialog_empty);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        ageBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                age = progress;
                String ageString = Integer.toString(progress);
                ageLabel.setText(ageString);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        hardmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    hardBool = true;
                } else {
                    //do stuff when Switch if OFF
                }
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().matches("") || password.getText().toString().matches("")){
                    builder.show();
                } else {
                    if(radioGender.getCheckedRadioButtonId() == R.id.radioButton){
                        gender = "Male";
                    } else {
                        gender = "Female";
                    }
                    user.setGender(gender);
                    //hardbool
                    String ageString = Integer.toString(age);
                    user.setAge(ageString);
                    user.setName(username.getText().toString());
                    user.setPass(password.getText().toString());
                    _db.addUser(user);
                    Intent intent = new Intent(context, Game.class);
                    intent.putExtra("hardmode",hardBool);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
