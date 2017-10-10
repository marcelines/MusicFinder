package pt.marciopinto.projecto_final;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;

import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;



//public class DetailActivity extends ActionBarActivity {
public class DetailActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private Context context;
    private boolean estrelaLigada;
    private ImageView imgBtnFavorito;
    private Song s= null;
    private Video v = new Video();

    int id;




    private static final int NEW_FRIEND = -1;
    private static final int MENU_CONTEXTO_ITEM_EDITAR = 0;
    private static final int MENU_CONTEXTO_ITEM_APAGAR = 1;

    //YOUTUBE
    public static final String API_KEY = "AIzaSyCgatG96a44BC6rhD1txnS5SDH3HneSQYc";

    //http://youtu.be/<VIDEO_ID>
    String videoLink;
    String VIDEO_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_detail);

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youTubePlayer);
        youTubePlayerView.initialize(API_KEY, this);

        // TODO colocar num sitio on so inicialize uma vez
        MenuActivity.iniciarVideos();

        //String pos = getIntent().getStringExtra("pos");


        s  = (Song) getIntent().getSerializableExtra("song");


        videoLink = getVideoUrlById(s.getId());
        VIDEO_ID = videoLink;
        //Toast.makeText(this, videoLink, Toast.LENGTH_LONG).show();
        /*
        id = s.getId();

        String link = v.getLink();
        int idVideo = v.getId();

        verificaVideo();

        if (id == idVideo) {

            Toast.makeText(this,link,Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this,"Não existe vídeo",Toast.LENGTH_SHORT).show();

        }*/



        String artista = s.getArtist();
        String duracao =s.getDuration();
        String titulo =s.getTitle();
        String letra =s.getLyrics();
        String img = s.getThumbUrl();
        //Toast.makeText(DetailActivity.this, id,Toast.LENGTH_SHORT).show();


        //Toast.makeText(this, s.getId()+"", Toast.LENGTH_LONG).show();

        TextView tvNome = (TextView)findViewById(R.id.tvNome);
        TextView tvArtista = (TextView)findViewById(R.id.tvArtista);
        TextView tvTempo = (TextView)findViewById(R.id.tvTempo);
        ImageView ivThumb = (ImageView)findViewById(R.id.ivThumb);
        TextView tvLyrics = (TextView)findViewById(R.id.tvLyrics);
        imgBtnFavorito = (ImageView)findViewById(R.id.imageBtnFavoritos);




        tvNome.setText(titulo);
        tvArtista.setText(artista);
        tvTempo.setText(duracao);
        tvLyrics.setText(letra);
        tvLyrics.setMovementMethod(new ScrollingMovementMethod());

        MyHelper myHelper = new MyHelper(this);
        SQLiteDatabase db = myHelper.getWritableDatabase();

        SongDataSource sds = new SongDataSource(db);

        if(sds.verificaFavorito(s.getId())) {

            estrelaLigada = true;
            imgBtnFavorito.setImageResource(R.drawable.fav_on);

        } else {

            estrelaLigada = false;
            imgBtnFavorito.setImageResource(R.drawable.fav_off);

        }

        db.close();

        //imgBtnFavorito.setImageResource(R.drawable.fav_off);


        imgBtnFavorito.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                verificaFavoritos();
                //Toast.makeText(DetailActivity.this,"Muda", Toast.LENGTH_SHORT).show();

            }
        });

        Picasso.with(context)
                .load(img)
                .placeholder(android.R.drawable.stat_sys_download)
                .into(ivThumb);

    }

    public void verificaVideo () {

        //Global.iniciarVideos();

        String mostra = Global.videos.toString();
        int tamanho = Global.videos.size();

        Video idVideo = Global.videos.get(id);

        //Toast.makeText(this, idVideo.toString(),Toast.LENGTH_SHORT).show();


        /*for (int i= 0; i <= tamanho;i++) {

            Toast.makeText(this, mostra,Toast.LENGTH_SHORT).show();
        }*/


        //int idVideo = Global.videos.get(id);
       // if(s.getId() == Global.videos())
    }
    public void verificaFavoritos() {



        if (estrelaLigada == false) {

            Toast.makeText(DetailActivity.this,"Adicionado aos Favoritos", Toast.LENGTH_SHORT).show();
            estrelaLigada = true;
            imgBtnFavorito.setImageResource(R.drawable.fav_on);
            adicionaFavorito();

        } else {

            Toast.makeText(DetailActivity.this,"Removido dos Favoritos", Toast.LENGTH_SHORT).show();
            estrelaLigada = false;
            imgBtnFavorito.setImageResource(R.drawable.fav_off);
            removeFavorito();


        }

    }

    public void adicionaFavorito() {

        MyHelper myHelper = new MyHelper(this);
        SQLiteDatabase db = myHelper.getWritableDatabase();

        SongDataSource sds = new SongDataSource(db);
        sds.inserirMusica(s);

        db.close();

    }

    public void removeFavorito (){

        MyHelper myHelper = new MyHelper(this);
        SQLiteDatabase db = myHelper.getWritableDatabase();

        SongDataSource sds = new SongDataSource(db);
        sds.deleteFavorito(s.getId());

        db.close();
    }

    /*public void gotoDetail(int amigoDbId) {

        Intent i = new Intent(this, FavoritosActivity.class);

        if (amigoDbId != NEW_FRIEND) {
            i.putExtra("amigoDbId", amigoDbId);
        }

        startActivity(i);

    }*/



    public String getVideoUrlById(int id) {

        for (int i = 0; i < Global.videos.size(); i ++) {
            Video v = Global.videos.get(i);
            if (v.getId() == id) {
                return v.getLink();
            }
        }
        return "Não existe video para esta música!";

    }


    //YOUTUBE PLAYER
    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        /** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);


        /** Start buffering **/
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        }

    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }

    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {
        }
    };
}
