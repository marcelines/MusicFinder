package pt.marciopinto.projecto_final;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by marciopinto on 16/06/15.
 */
public class SongDataSource {

    private SQLiteDatabase db;

    public SongDataSource(SQLiteDatabase db) {
        this.db = db;
    }

    public long inserirMusica(int _id,String title, String artist, String duration, String thumbUrl, String lyrics) {

        ContentValues values = new ContentValues();
        values.put("_id", _id);
        values.put("titulo", title);
        values.put("artista", artist);
        values.put("duracao", duration);
        values.put("thumb_url", thumbUrl);
        values.put("letra", lyrics);

        long insertId = db.insert("song", null, values);

        return insertId;
    }

    public long inserirMusica(Song song) {

        return inserirMusica(song.getId(), song.getTitle(), song.getArtist(),song.getDuration(),song.getThumbUrl(),song.getLyrics());
    }

    public ArrayList<Song> selectMusicas() {
        Cursor cursor = db.rawQuery("SELECT * FROM song", null);
        cursor.moveToFirst();
        ArrayList<Song> musica = new ArrayList<>();


        while (!cursor.isAfterLast()) {

            // COM NOME DA COLUNA:
            //int _id = cursor.getInt(cursor.getColumnIndex("_id"));

            // COM NUMERO DA COLUNA
            int _id = cursor.getInt(0);
            String titulo = cursor.getString(1);
            String artista = cursor.getString(2);
            String duracao = cursor.getString(3);
            String thumb_url = cursor.getString(4);
            String letra = cursor.getString(5);

            Song s = new Song(_id, titulo, artista, duracao, thumb_url, letra);
            musica.add(s);

            cursor.moveToNext();
        }
        return musica;
    }

    public void updateFavorito(int _id, String title, String artist, String duration, String thumbUrl, String lyrics) {
        ContentValues values = new ContentValues();
        values.put("_id", _id);
        values.put("titulo", title);
        values.put("artista", artist);
        values.put("duracao", duration);
        values.put("thumb_url", thumbUrl);
        values.put("letra", lyrics);

        // CRIO UM ARRAY CHAMADO PARAMS QUE VAI CONTER OS VALORES
        // QUE VAO PREENCHER OS VALORES ONDE ESTÂO OS ?
        String[] params = new String[]{ String.valueOf(_id) };

        db.update("song", values, "_id = ?", params);
    }

    public void deleteFavorito(int _id) {
        // CRIO UM ARRAY CHAMADO PARAMS QUE VAI CONTER OS VALORES
        // QUE VAO PREENCHER OS VALORES ONDE ESTÂO OS ?
        String[] params = new String[]{ String.valueOf(_id) };

        db.delete("song", "_id = ?", params);
    }

    public boolean verificaFavorito(int id) {

        Cursor cursor = db.rawQuery("SELECT * FROM song WHERE _id = " + id, null);
        if (cursor.getCount() == 0) {
            return false;
        } else {
            return true;
        }

    }

}
