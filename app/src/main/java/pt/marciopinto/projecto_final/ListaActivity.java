package pt.marciopinto.projecto_final;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ListaActivity extends ActionBarActivity {

    private ListView lvListaMusicas;
    private ArrayList<Song> musicas = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_lista);

        verificaLayout();

        String url = getIntent().getStringExtra("url");

        lvListaMusicas = (ListView)findViewById(R.id.lvListaMusicas);
        lvListaMusicas.setDivider(null);

        MyInternetTask mit = new MyInternetTask(url);
        mit.setConnectionListener(new MyInternetTask.ConnectionListener() {
            @Override
            public void onConnectionSuccess(String contentType, String result) {
                //Toast.makeText(ListaActivity.this, result, Toast.LENGTH_LONG).show();
                getIntent();
                parseResult(contentType, result);
            }

            @Override
            public void onConnectionError() {
                Toast.makeText(ListaActivity.this, "Não foi possível estabelecer ligação!!!!",
                        Toast.LENGTH_LONG).show();
            }
        });
        mit.execute();


    }
    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        String url = getIntent().getStringExtra("url");

        lvListaMusicas = (ListView)findViewById(R.id.lvListaMusicas);
        lvListaMusicas.setDivider(null);

        MyInternetTask mit = new MyInternetTask(url);
        mit.setConnectionListener(new MyInternetTask.ConnectionListener() {
            @Override
            public void onConnectionSuccess(String contentType, String result) {
                //Toast.makeText(ListaActivity.this, result, Toast.LENGTH_LONG).show();
                getIntent();
                parseResult(contentType, result);
            }

            @Override
            public void onConnectionError() {
                Toast.makeText(ListaActivity.this, "Não foi possível estabelecer ligação!!!!",
                        Toast.LENGTH_LONG).show();
            }
        });
        mit.execute();


    }


    public void parseResult(String contentType, String result) {

        musicas = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                Song song = new Song();

                song.setId(jsonObject.getInt("id"));
                song.setTitle(jsonObject.getString("title"));
                song.setArtist(jsonObject.getString("artist"));
                song.setDuration(jsonObject.getString("duration"));
                song.setThumbUrl(jsonObject.getString("thumb_url"));
                song.setLyrics(jsonObject.getString("lyrics"));

                musicas.add(song);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*ArrayAdapter<Song> adapter = new ArrayAdapter<Song>(this,
                android.R.layout.simple_list_item_1, musicas);

        lvListaMusicas.setAdapter(adapter);

        */

        ListSongAdapter adapter = new ListSongAdapter(this, musicas);

        if (musicas.isEmpty()) {

            Toast.makeText(this, "Não existem resultados para a sua pesquisa!", Toast.LENGTH_SHORT).show();

        } else {
            lvListaMusicas.setAdapter(adapter);

            lvListaMusicas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    detalhesMusica(position);


                }
            });

        }

    }

    private void detalhesMusica(int pos) {

        Intent i = new Intent(this, DetailActivity.class);

        i.putExtra("song", musicas.get(pos));
        i.putExtra("musica", musicas.get(pos));
        startActivity(i);

    }
    private void verificaLayout (){

        LinearLayout llUltimas = (LinearLayout)findViewById(R.id.llHeader);
        TextView titulo = (TextView)findViewById(R.id.tvTituloListaSong);
        TextView subTitulo = (TextView)findViewById(R.id.tvSubTituloListaSong);
        ImageView icon = (ImageView)findViewById(R.id.ivIconDestaques);

        if (Global.layout == "ultimas"){
            titulo.setText(R.string.titulo_ultimas);
            subTitulo.setText(R.string.subtitulo_ultimas);
            llUltimas.setBackgroundColor(Color.parseColor("#a3c2dc"));
            //icon.setBackground(R.drawable.icon_ultimas_lista);
            icon.setImageResource(R.drawable.icon_ultimas_lista);


        }
        if (Global.layout == "destaques"){
            titulo.setText(R.string.titulo_destaques);
            subTitulo.setText(R.string.subtitulo_destaques);
            llUltimas.setBackgroundColor(Color.parseColor("#2196f3"));
            icon.setImageResource(R.drawable.icon_destaques_lista);

        }
        if (Global.layout == "pesquisa"){
            titulo.setText(R.string.titulo_pesquisa);
            subTitulo.setText(R.string.subtitulo_pesquisa);
            llUltimas.setBackgroundColor(Color.parseColor("#009688"));
            icon.setImageResource(R.drawable.icon_pesquisa_lista);

        }

    }

}
