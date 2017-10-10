package pt.marciopinto.projecto_final;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;


public class SplashScreen extends ActionBarActivity {


    private Thread mSplashThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        setContentView(R.layout.activity_splash_screen);

        ProgressBar pbBarra = (ProgressBar)findViewById(R.id.pbBarra);

       pbBarra.getIndeterminateDrawable().setColorFilter(new LightingColorFilter(Color.WHITE, Color.WHITE));

        final SplashScreen sPlashScreen = this;




        mSplashThread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){

                        wait(2000);
                    }
                }
                catch(InterruptedException ex){
                }

                finish();

                Intent intent = new Intent();
                intent.setClass(sPlashScreen, MenuActivity.class);
                startActivity(intent);

            }
        };

        mSplashThread.start();
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent evt)
    {
        if(evt.getAction() == MotionEvent.ACTION_DOWN)
        {
            synchronized(mSplashThread){
                mSplashThread.notifyAll();
            }
        }
        return true;
    }*/
}
