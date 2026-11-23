package dk.easv.mrs.BLL.util;

// Java imports
import java.util.ArrayList;
import java.util.List;

// project imports
import dk.easv.mrs.BE.Music;

public class MusicSearcher {


    public List<Music> search(List<Music> searchBase, String query) {
        List<Music> searchResult = new ArrayList<>();

        for (Music music : searchBase) {
            if(compareToMovieTitle(query, music) || compareToMovieYear(query, music))
            {
                searchResult.add(music);
            }
        }

        return searchResult;
    }

    private boolean compareToMovieYear(String query, Music music) {
        return Integer.toString(music.getYear()).contains(query);
    }

    private boolean compareToMovieTitle(String query, Music music) {
        return music.getTitle().toLowerCase().contains(query.toLowerCase());
    }

}
