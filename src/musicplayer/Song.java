/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayer;

/**
 *
 * @author tranmat000
 */
public class Song {
    public String name;
    public String uri;
    public String coverImageURI;
    Song(String name, String uri, String coverImageURI){
        this.name = name;
        this.uri = uri;
        this.coverImageURI = coverImageURI;
    }
}
