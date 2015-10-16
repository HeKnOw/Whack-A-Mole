package com.example.heknow.whack_a_mole;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends AppCompatActivity implements View.OnClickListener {
    public ProgressBar bar;
    public ImageView mole;
    public TextView text;
    public Button submit, goBack;
    float molePx =0;
    float molePy = 0;
    int levelNumber =1;
    int barProgress = 0;
    int barMax=10;
    boolean moleIsVisible =false;
    boolean hitMole =false;
    boolean waiting =false;
    boolean nextLevel= false;
    int nextLevelHits=10;
    Random rand = new Random();
    Timer timer = new Timer();
    int levelOfDifficulty =0;
    Context context;
    Intent i;
    boolean hardmode;
    global_user user;
    DBmole db_;

    //**********SCORE VARIABLE************************
    int points= 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        db_ = new DBmole(this, null, null, 2);
        inti();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!waiting) {
                            LoopGame();
                        }

                    }
                });

            }
        }, 0, levelOfDifficulty);

    }

    //******************* INITIALIZE EVERYTHING************************
    private void inti()
    {
        context = this;
        i = getIntent();
        hardmode = i.getExtras().getBoolean("hardmode");
        user = (global_user)i.getSerializableExtra("user");

        mole = (ImageView)findViewById(R.id.imageMole);
        text = (TextView)findViewById(R.id.textView);
        bar = (ProgressBar)findViewById(R.id.progressBar);
        bar.setMax(barMax);
        submit = (Button)findViewById(R.id.button2);
        submit.setOnClickListener(this);
        goBack = (Button)findViewById(R.id.buttonBack);
        goBack.setOnClickListener(this);
        int xValue = rand.nextInt(883);
        int yValue = rand.nextInt(684)-62;
        mole.setX(xValue);
        mole.setY(yValue);
        text.setText("Level: " + String.valueOf(levelNumber));

        //************************LEVEL OF DIFFICULTY****************
        //**********TO MAKE IT HARDER REDUCE THE TIME****************
        if(hardmode)
        {
            levelOfDifficulty=400;
        }
        else
        {
            levelOfDifficulty=1000;
        }
    }

    //****************GAME LOOP************************
    private void LoopGame() {
        if(!nextLevel)
        {
            mole();
        }
        else
        {
            text.setText("WIN");
            barMax+=10;
            levelNumber++;
            nextLevelHits+=10;
            barProgress=0;
            bar.setMax(barMax);
            bar.setProgress(0);
            text.setText("Level: " + String.valueOf(levelNumber));
            nextLevel=false;
        }
    }

    //***************MOLE BEHAIVOR***********************
    private void mole()
    {
        if(!moleIsVisible) {
            mole.setVisibility(View.VISIBLE);
            moleIsVisible=true;
            int xValue = rand.nextInt(883);
            int yValue = rand.nextInt(684)-62;
            mole.setX(xValue);
            mole.setY(yValue);
        }
        else{
            mole.setVisibility(View.INVISIBLE);
            moleIsVisible=false;
            if(!hitMole && barProgress>0)
            {
                barProgress--;
                bar.setProgress(barProgress);

            }

            hitMole=false;
        }
        if(barProgress==nextLevelHits)
        {
            nextLevel=true;
        }
    }

    //******************* TRYING TO TOUCH THE MOLE******************************
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
        }
        y= y-561;
        //mole.setX(883);
        //mole.setY(-62);
        //molePx=y-561;
        // molePy= x;
        // text.setText("Level: "+ String.valueOf(molePx)+ " "+ String.valueOf(molePy));
        //molePy=mole.getY();
        molePx=mole.getX();
        molePy=mole.getY();

        //********************* IF PERSON HIT THE MOLE
        if(x> molePx+40 && x < molePx+190 && y > molePy && y < molePy+177 )
        {
            if(moleIsVisible)
            {

                barProgress++;
                //**************** RECORD SCORE*********************
                points=barProgress;

                text.setText("Level: "+ String.valueOf(levelNumber));
                moleIsVisible=false;
                mole.setVisibility(View.INVISIBLE);
                int xValue = rand.nextInt(883);
                int yValue = rand.nextInt(684)-62;
                mole.setX(xValue);
                mole.setY(yValue);
                bar.setProgress(barProgress);
                hitMole =true;

            }
        }
        //*************FOR DEBUG************************
        //x starts 40 to 177
        //moley Height = 177;
        //molex width =137;
        return false;
    }


    //**********************WHEN CLICK BUTTONS****************************
    @Override
    public void  onClick(View view){

        switch(view.getId()) {
            case R.id.button2:
                //waiting=false;
                user.setLevel(levelNumber);
                db_.updateUser(user);
                Intent intent = new Intent(context, HighScore.class);
                intent.putExtra("hardmode", hardmode);
                intent.putExtra("user",user);
                startActivity(intent);
                break;
            case R.id.buttonBack:
                Intent newIntent = new Intent(context, MainActivity.class);
                newIntent.putExtra("user",user);
                startActivity(newIntent);
                //*************************************************************************
                //***********************GO BACK TO THE PREVIOUS PAGE**********************
                //*************************************************************************

        }
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