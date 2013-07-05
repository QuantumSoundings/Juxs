package mods.juxs.core.radio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import mods.juxs.Juxs;
import mods.juxs.lib.Reference;
import mods.juxs.lib.Sounds;
import mods.juxs.network.SongPacket;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class RadioInit {
    public static HashMap<String,Integer> songs=new HashMap<String,Integer>();
    public static ArrayList<RadioStation> stations= new ArrayList<RadioStation>();
   
    
    public static void addStation(String name){
    	stations.add(new RadioStation(name));
    	System.out.println("[RadioInit]["+FMLCommonHandler.instance().getEffectiveSide().toString()+"] Added station "+name+" "+getStation(name).Name);
    }
    public static boolean containsStation(String s){
    	for(RadioStation r:stations)
    		if(r.Name.equals(s))
    			return true;
    	return false;
    }
    public static void addStationSong(String station,String song){
    	for(int i=0;i<stations.size();i++){
    		if(stations.get(i).Name.equals(station)){
    			stations.get(i).addSong(song);
    			//System.out.println("[RadioInit]["+FMLCommonHandler.instance().getEffectiveSide().toString()+"] Added song "+song+" "+getStation(station).songs.toString());
    		}
    	
    	}
    }
    public static void addStationBox(String station, Location a){
    	for(RadioStation s:stations){
    		if(s.Name.equals(station)) s.addBox(a);
    	}
    	printBoxes();
    }
    public static void printBoxes(){
    	for(RadioStation r:stations){
    		System.out.println(r.Name+" "+r.boxes.toString());
    	}
    }
    public static void clearBoxes(){
    	for(RadioStation s:stations)
    		s.boxes=new ArrayList<Location>();
    }
    public static void removeStationBox(String station, Location a){
    	for(RadioStation s:stations){
    		if(s.Name.equals(station)) s.removeBox(a);
    	}
    }
    public static void removeTheHardWay(Location a){
    	for(RadioStation s:stations)
    		for(Location b:s.boxes)
    			if(a.x==b.x&&a.y==b.y&&a.z==b.z){
    				System.out.println("[RadioInit]["+FMLCommonHandler.instance().getEffectiveSide().toString()+"] Found box in station "+s.Name);
    				s.removeBox(a);
    				break;
    			}
    }
    public static String findTheHardWay(Location a){
    	for(RadioStation s:stations)
    		for(Location b:s.boxes)
    			if(a.x==b.x&&a.y==b.y&&a.z==b.z){
    				return s.Name;
    			}
    			
    	return "";
    }
    public static ArrayList<Location> getNearBy(Location a){
    	ArrayList<Location> near = new ArrayList<Location>();
    	for(RadioStation s:stations)
    		for(Location b:s.boxes)
    			if(Location.within(50, a, b)&&!(b.x==a.x&&b.y==a.y&&b.z==a.z))
    				near.add(b);
    	return near;
    }
    public static RadioStation getStation(String name){
    	for(int i=0;i<stations.size();i++){
    		if(stations.get(i).Name.equals(name)){return stations.get(i);}
    	}
    	return null;
    }
    public static String getNextStation(String from){
    	int indexOfFrom=stations.indexOf(getStation(from));
    	int indexOfTo=(indexOfFrom+1)%stations.size();
    	return stations.get(indexOfTo).Name;
    }
    public static String getPrevStation(String from){
    	int indexOfFrom=stations.indexOf(getStation(from));
    	int indexOfTo=(indexOfFrom+stations.size()-1)%stations.size();
    	return stations.get(indexOfTo).Name;
    }
    public static void registerSong(String song, int time){
    	songs.put(song,time);
    }
    public static boolean isRegistered(String song){
    	if(songs.containsKey(song))
    		return true;
    	return false;
    }

    public static void onTick(){
    	for(int i =0;i<stations.size();i++){
    		stations.get(i).onTick();
    	}
        
    }

    /*public static void startRadio(){
        try {
            playNext();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public static void playNext() throws IOException{
        curr= (curr+1)%songs.size();
        if(FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER)
            for(Location a :juxboxes){
                System.out.printf("Playing at %d %d %d",a.x,a.y,a.z);
                new SongPacket(songList.get(curr),Reference.CHANNEL+"PLAY",a.x,a.y,a.z);
            }
        ticks=0;
    }
    */

}
