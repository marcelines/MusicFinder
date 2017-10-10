package pt.marciopinto.projecto_final;

import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by marciopinto on 29/06/15.
 */
public class Video implements Serializable {



    private int id;
    private String link;

    public Video () {

    }

    public Video (int id, String link) {

        this.id = id;
        this.link = link;
    }

    public int getId() {

        return id;
    }

    public String getLink() {

        return link;
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setLink(String link) {

        this.link = link;
    }

    public void adicionaVideo(int id, String link){
        this.id = id;
        this.link = link;
    }





    @Override
    public String toString() {

        return id + " - " + link+"\n";
    }
}
