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


public class FavoritosActivity extends ActionBarActivity {


    ListSongAdapter adapter;
    ListView lvFavoritos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_favoritos);

        //
        verificaLayout();

        MyHelper myHelper = new MyHelper(this);
        SQLiteDatabase db = myHelper.getWritableDatabase();

        SongDataSource sds = new SongDataSource(db);
        Global.guardadas = sds.selectMusicas();

        db.close();

        adapter = new ListSongAdapter(this, Global.guardadas);

        lvFavoritos = (ListView)findViewById(R.id.lvFavoritos);
        lvFavoritos.setDivider(null);


        if (Global.guardadas.isEmpty()) {

            Toast.makeText(this, "Não existem favoritos adicionados!", Toast.LENGTH_SHORT).show();

        } else {
            lvFavoritos.setAdapter(adapter);

            lvFavoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    detalhesMusica(position);


                }
            });

        }


    }
    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        MyHelper myHelper = new MyHelper(this);
        SQLiteDatabase db = myHelper.getWritableDatabase();

        SongDataSource sds = new SongDataSource(db);
        Global.guardadas = sds.selectMusicas();

        db.close();

        adapter = new ListSongAdapter(this, Global.guardadas);

        lvFavoritos = (ListView)findViewById(R.id.lvFavoritos);
        lvFavoritos.setDivider(null);
        lvFavoritos.setAdapter(adapter);

    }

    private void verificaLayout () {

        LinearLayout llUltimas = (LinearLayout) findViewById(R.id.llHeader);
        TextView titulo = (TextView) findViewById(R.id.tvTituloListaSong);
        TextView subTitulo = (TextView) findViewById(R.id.tvSubTituloListaSong);
        ImageView icon = (ImageView) findViewById(R.id.ivIconDestaques);

        if (Global.layout == "favoritos") {
            titulo.setText(R.string.titulo_favoritos);
            subTitulo.setText(R.string.subtitulo_favoritos);
            llUltimas.setBackgroundColor(Color.parseColor("#b6b6b6"));
            icon.setImageResource(R.drawable.icon_favoritos_lista);


        }
    }


    private void detalhesMusica(int pos) {

        Intent i = new Intent(this, DetailActivity.class);

        i.putExtra("song", Global.guardadas.get(pos));
        i.putExtra("musica", Global.guardadas.get(pos));
        startActivity(i);

    }
}
