package mods.juxs.core.radio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import mods.juxs.lib.Reference;
import mods.juxs.lib.Sounds;
import mods.juxs.network.SongPacket;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class RadioInit {
    public static HashMap<String,Integer> songs=new HashMap<String,Integer>();
    public static ArrayList<String> songList= new ArrayList<String>();
    public static ArrayList<Location> juxboxes=new ArrayList<Location>();
    public static int curr;
    public static int ticks=0;
    public static boolean next=false,removeBox=false;
    public static Location temp;
    public static void addSong(String s, int lengthIT){
        songs.put(s, lengthIT);
        songList.add(s);
    }
    public static int timeRemaining(){
        return songs.get(songList.get(curr))-ticks;
    }
    public static void buildRadioList(){
        Sounds.buildList();
        for(int i=0;i<Sounds.songs.size();i++)
            try{   
                RadioInit.addSong(Sounds.songs.get(i).replaceAll("/", ".").substring(0,Sounds.songs.get(i).length()-4),Sounds.timeInTicks.get(i));
                //System.out.println(Sounds.songs.get(i));
            }catch(Exception e){}
        randomize();
    }
    public static void next(){
    	next=true;
    	
    }
    public static void remove(Location l){
    	temp=l;
    	removeBox=true;
    }
    public static void addBox(Location a){
        juxboxes.add(a);
        //System.out.printf("added box at %d %d %d",a.x,a.y,a.z);
        if(juxboxes.size()==1)
            startRadio();
    }
    public static void removeBox(Location a){
        for(Location b:juxboxes){
            if(a.x==b.x&&a.y==b.y&&a.z==b.z){
                juxboxes.remove(b);
                break;
            }
        }
        //if(FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER)
            //System.out.printf("removed box at %d %d %d",a.x,a.y,a.z);
    }
    public static void onTick(){
        ticks++;
        if(removeBox){
        	removeBox(temp);
        	removeBox=false;
        	temp=null;
        }
        if(songs.get(songList.get(curr))==ticks||next){
            try {
                playNext();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(next)next=false;
        }
        //if(ticks%20==0)
            //System.out.println(ticks/20);
    }
    public static void randomize(){
        Collections.shuffle(songList);
    }
    public static String getRand(){
        Collections.shuffle(songList);
        return songList.get(0);
    }
    public static void startRadio(){
        ticks=0;
        try {
            playNext();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean isPlaying(){
        if(juxboxes.size()==0)
            return false;
        return true;
    }
    public static void playNext() throws IOException{
        curr= (curr+1)%songs.size();
        if(FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER)
            for(Location a :juxboxes){
                System.out.printf("Playing at %d %d %d",a.x,a.y,a.z);
                new SongPacket(songList.get(curr),Reference.CHANNEL+"PLAY",a.x,a.y,a.z);
            }
        ticks=0;
    }
    

}
