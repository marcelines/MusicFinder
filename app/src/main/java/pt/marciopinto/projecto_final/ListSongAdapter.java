package pt.marciopinto.projecto_final;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by marciopinto on 14/06/15.
 */
public class ListSongAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Song> musics;
    private boolean estrelaLigada;

    public ListSongAdapter(Context context, ArrayList<Song> musics) {
        this.context = context;
        this.musics = musics;
    }

    @Override
    public int getCount() {

        return musics.size();
    }

    @Override
    public Object getItem(int position) {

        return musics.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v;



        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = inflater.inflate(R.layout.list_song_, parent, false);


        } else {

            v = convertView;

        }

        if (position % 2 == 1) {

            v.setBackgroundResource(R.drawable.fundo_listas_brancas);
        } else {

            v.setBackgroundResource(R.drawable.fundo_listas_escuras);
        }

        TextView tvNome = (TextView)v.findViewById(R.id.tvNome);
        TextView tvArtista = (TextView)v.findViewById(R.id.tvArtista);
        TextView tvTempo = (TextView)v.findViewById(R.id.tvTempo);
        ImageView ivThumb = (ImageView)v.findViewById(R.id.ivThumb);
        ImageView imgThumb = (ImageView)v.findViewById(R.id.imgIconFav);


        Song s = musics.get(position);

        tvNome.setText(s.getTitle());
        tvArtista.setText(s.getArtist());
        tvTempo.setText(s.getDuration() + " m");
        String img = s.getThumbUrl();

        MyHelper myHelper = new MyHelper(context);
        SQLiteDatabase db = myHelper.getWritableDatabase();

        SongDataSource sds = new SongDataSource(db);

        if(sds.verificaFavorito(s.getId())) {

            estrelaLigada = true;
            imgThumb.setImageResource(R.drawable.fav_on);

        } else {

            estrelaLigada = false;
            imgThumb.setImageResource(R.drawable.fav_off);

        }

        db.close();

        //Toast.makeText(context, img, Toast.LENGTH_SHORT).show();
        //ivThumb.setImageBitmap(img);


        //mContext = ivThumb.getContext();

        // ----------------------------------------------------------------
        // apply rounding to image
        // see: https://github.com/vinc3m1/RoundedImageView
        // ----------------------------------------------------------------

        Picasso.with(context)
                .load(img)
                .fit()
                .placeholder(android.R.drawable.stat_sys_download)
                .into(ivThumb);

        return v;



    }

}
