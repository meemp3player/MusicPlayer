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
    public String path;
    public String coverImagePath;
    Song(String name, String path, String coverImagePath){
        this.name = name;
        this.path = path;
        this.coverImagePath = coverImagePath;
    }
}
