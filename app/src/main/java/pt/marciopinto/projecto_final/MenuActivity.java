package pt.marciopinto.projecto_final;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MenuActivity extends ActionBarActivity {

    public static void iniciarVideos() {

        Global.videos = new ArrayList<Video>();

        Global.videos.add(new Video(1, "hLQl3WQQoQ0"));
        Global.videos.add(new Video(2, "JByDbPn6A1o"));
        Global.videos.add(new Video(3, "pEEMi2j6lYE"));
        Global.videos.add(new Video(4, "uelHwf8o7_U"));
        Global.videos.add(new Video(5, "CaI18sMcnsE"));
        Global.videos.add(new Video(6, "vciPaw6u22Q"));
        Global.videos.add(new Video(7, "OFtNChII78k"));
        Global.videos.add(new Video(8, "LWdwkdYPz-o"));
        Global.videos.add(new Video(9, "LdLQf1Ef9Ns"));
        Global.videos.add(new Video(10, "aBt8fN7mJNg"));
        Global.videos.add(new Video(24, "3KN3v7cJiDg"));
        Global.videos.add(new Video(27, "dJ-QLl5qjLg"));
        Global.videos.add(new Video(32, "Lq2ANOkfsIA"));
        Global.videos.add(new Video(41, "oDLJ7cTm7OE"));
        Global.videos.add(new Video(42, "ZgZyDQxM-Zo"));
        Global.videos.add(new Video(50, "s03U1v86Obo"));
        Global.videos.add(new Video(52, "IcrbM1l_BoI"));
        Global.videos.add(new Video(53, "IcrbM1l_BoI"));
        Global.videos.add(new Video(54, "IcrbM1l_BoI"));
        Global.videos.add(new Video(55, "OFtNChII78k"));
        Global.videos.add(new Video(60, "pEEMi2j6lYE"));
        Global.videos.add(new Video(61, "pEEMi2j6lYE"));
        Global.videos.add(new Video(62, "pEEMi2j6lYE"));
        Global.videos.add(new Video(64, "Tu5-h4Ye0J0"));
        Global.videos.add(new Video(65, "btPJPFnesV4"));
        Global.videos.add(new Video(69, "wFpbhooe6Ns"));


    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_main);




        /*Global.iniciarVideos();

        String mostra = Global.videos.toString();
        int tamanho = Global.videos.size();

        for (int i= 0; i <= tamanho;i++) {

            Toast.makeText(this, mostra,Toast.LENGTH_SHORT).show();
        }*/





        View rlUltimas = findViewById(R.id.rlUltimas);
        View rlDestaques = findViewById(R.id.rlDestaques);
        View rlPesquisa = findViewById(R.id.rlPesquisa);
        View rlFavoritos = findViewById(R.id.rlFavoritos);

        final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.som);
        rlUltimas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.layout = "ultimas";
                gotoLista("http://reality6.com/musicservicejson.php?tipo=ultimas");
                mp.start();



            }
        });

        rlDestaques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.layout = "destaques";
                gotoLista("http://reality6.com/musicservicejson.php?tipo=destaques");
                mp.start();
            }
        });

        rlPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.layout = "pesquisa";
                //gotoPesquisa();
                gotoPesquisa("http://reality6.com/musicservicejson.php");
                mp.start();

            }
        });

        rlFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Global.layout = "favoritos";
                gotoFavoritos();
                mp.start();

            }
        });


    }



    private void gotoLista(String link) {

        Intent i = new Intent(this, ListaActivity.class);

        i.putExtra("url", link);

        startActivity(i);

    }
    private void gotoPesquisa (String link) {
        Intent i= new Intent (this, PesquisaActivity.class);

        i.putExtra("url", link);
        startActivity(i);

    }
    private void gotoFavoritos () {
        Intent i= new Intent (this, FavoritosActivity.class);

        startActivity(i);

    }
    @Override
    protected void onResume() {
        super.onResume();
        final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.som);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
