package pt.marciopinto.projecto_final;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/*public class PesquisaActivity extends ActionBarActivity {

    String urlPesquisa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);


        Button btnProcurar = (Button)findViewById(R.id.btnProcurar);


        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etPesquisa = (EditText)findViewById(R.id.etPesquisa);

                if (etPesquisa.getText().toString().isEmpty()) {

                    //Toast.makeText(PesquisaActivity.this, "O campo pesquisa é de preenchimento obrigatório!", Toast.LENGTH_SHORT).show();
                    popUp();
                } else {
                    Global.layout = "pesquisa";
                    seleccionaPesquisa();
                    gotoLista(urlPesquisa);

                }
               //Toast.makeText(PesquisaActivity.this, urlPesquisa, Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void popUp () {

        AlertDialog.Builder builder = new AlertDialog.Builder(PesquisaActivity.this);

        builder.setTitle("Aviso:");
        builder.setIcon(android.R.drawable.stat_sys_warning);
        builder.setMessage("O campo pesquisa é de preenchimento obrigatório!");

        builder.setNegativeButton("Ok", null);
        //builder.setNeutralButton("Ok",null);
        AlertDialog alerta = builder.create();
        alerta.show();

    }

    public void seleccionaPesquisa() {
        EditText etPesquisa = (EditText)findViewById(R.id.etPesquisa);
        RadioGroup rgC = (RadioGroup)findViewById(R.id.radioGroup);
        String value = etPesquisa.getText().toString();

        //rgC.check(R.id.btnArtista);
        //rgC.check(((RadioButton)rgC.getChildAt(INDEX)).getId())


        if (rgC.getCheckedRadioButtonId() == R.id.btnArtista) {

            //urlPesquisa = "Artista";

           //Toast.makeText(this, urlPesquisa, Toast.LENGTH_SHORT).show();

            urlPesquisa = "http://reality6.com/musicservicejson.php?tipo=buscaartista&nome="+ value;

        } else {

            //Toast.makeText(this, "musica", Toast.LENGTH_SHORT).show();

            urlPesquisa = "http://reality6.com/musicservicejson.php?tipo=buscacancao&nome="+ value;

        }
    }

    private void gotoLista(String link) {

        Intent i = new Intent(this, ListaActivity.class);

        i.putExtra("url", link);

        startActivity(i);

    }

}
*/

public class PesquisaActivity extends ActionBarActivity {

    private ListView lvMusicas;
    ArrayList<Song> music = null;
    ArrayList<Song> filteredMusic = null;

    public static final int ESCOLHA_ARTISTA = 1;
    public static final int ESCOLHA_MUSICA = 2;

    int escolha = ESCOLHA_ARTISTA; // Porque no XML está o primeiro seleccionado por defeito

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_pesquisa);

        verificaLayout();


        final EditText etPesquisa = (EditText)findViewById(R.id.etPesquisa);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btnArtista:
                        escolha = ESCOLHA_ARTISTA;
                        break;
                    case R.id.btnMusica:
                        escolha = ESCOLHA_MUSICA;
                        break;
                    default:
                        break;
                }


                ListSongAdapter adapter = new ListSongAdapter(PesquisaActivity.this, music);
                lvMusicas.setAdapter(adapter);

                etPesquisa.setText("");

            }
        });


        lvMusicas = (ListView) findViewById(R.id.lvPesquisa);
        lvMusicas.setDivider(null);


        etPesquisa.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                filteredMusic = new ArrayList<Song>();

                for (int i = 0; i<music.size(); i++) {

                    Song song = music.get(i);

                    String texto = "";

                    if (escolha == ESCOLHA_ARTISTA) {
                        texto = song.getArtist();
                    } else if (escolha == ESCOLHA_MUSICA) {
                        texto = song.getTitle();
                    }

                    if (texto.toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredMusic.add(song);
                    }
                }

                ListSongAdapter adapter = new ListSongAdapter(PesquisaActivity.this, filteredMusic);
                lvMusicas.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lvMusicas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Song song = (Song) parent.getItemAtPosition(position);

                gotoDetail(song);
            }
        });

        String url = getIntent().getStringExtra("url");

        MyInternetTask mit = new MyInternetTask(url);
        mit.setConnectionListener(new MyInternetTask.ConnectionListener() {
            @Override
            public void onConnectionSuccess(String contentType, String result) {
                parseResult(contentType, result);
            }

            @Override
            public void onConnectionError() {
                Toast.makeText(PesquisaActivity.this, "Erro ao carregar! Tente mais tarde!",
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

        MyInternetTask mit = new MyInternetTask(url);
        mit.setConnectionListener(new MyInternetTask.ConnectionListener() {
            @Override
            public void onConnectionSuccess(String contentType, String result) {
                parseResult(contentType, result);
            }

            @Override
            public void onConnectionError() {
                Toast.makeText(PesquisaActivity.this, "Erro ao carregar! Tente mais tarde!",
                        Toast.LENGTH_LONG).show();
            }
        });
        mit.execute();

    }

    public void parseResult(String contentType, String result) {


        try {
            JSONArray jsonArray = new JSONArray(result);

            music = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i ++) {
                JSONObject jsonObject = (JSONObject)jsonArray.get(i);

                Song song = new Song();

                song.setId(jsonObject.getInt("id"));
                song.setTitle(jsonObject.getString("title"));
                song.setArtist(jsonObject.getString("artist"));
                song.setDuration(jsonObject.getString("duration"));
                song.setThumbUrl(jsonObject.getString("thumb_url"));
                song.setLyrics(jsonObject.getString("lyrics"));

                music.add(song);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListSongAdapter adapter = new ListSongAdapter(this, music);

        lvMusicas.setAdapter(adapter);

    }

    public void gotoDetail(Song song) {

        Intent i = new Intent(this, DetailActivity.class);

        i.putExtra("song", song);

        startActivity(i);

    }
    private void verificaLayout () {

        LinearLayout llUltimas = (LinearLayout) findViewById(R.id.llHeader);
        TextView titulo = (TextView) findViewById(R.id.tvTituloListaSong);
        TextView subTitulo = (TextView) findViewById(R.id.tvSubTituloListaSong);
        ImageView icon = (ImageView) findViewById(R.id.ivIconDestaques);

        Global.layout = "pesquisa";
            titulo.setText(R.string.titulo_pesquisa);
            subTitulo.setText(R.string.subtitulo_pesquisa);
            llUltimas.setBackgroundColor(Color.parseColor("#009688"));
            icon.setImageResource(R.drawable.icon_pesquisa_lista);



    }
}
