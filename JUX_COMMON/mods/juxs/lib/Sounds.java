package mods.juxs.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

import mods.juxs.JuxsTickHandler;
import mods.juxs.core.radio.RadioInit;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;

public class Sounds {
    public static String SOUND_LOCATION="resources/mods/juxs/sound/";
    public static ArrayList<String> songs=new ArrayList<String>();
    public static ArrayList<Integer> timeInTicks=new ArrayList<Integer>();
    
    public static void buildList(){ 
    	File soundFolder;
    	soundFolder= new File(Reference.MOD_DIR.substring(0,Reference.MOD_DIR.length()-7)+"/juxs");
    	if(soundFolder.exists())
    		for(File fileEntry:soundFolder.listFiles()){
    			if(fileEntry.isDirectory()){
    				RadioInit.addStation(fileEntry.getName());
    				for(File entry:fileEntry.listFiles()){
    					if(entry.getName().contains(".ogg")){
    						try {
    							String song=entry.getName();
    							Ogg temp = new Ogg(entry);
    							RadioInit.registerSong(song,(int) (temp.getSeconds()*20));
    							RadioInit.addStationSong(fileEntry.getName(),song);						
    							songs.add(song);
    						} catch (Exception e) {e.printStackTrace();}
    					}
    					else if(entry.getName().contains(".wav")){
    						AudioInputStream audioInputStream;
    						try {
    							String song=entry.getName();
    							audioInputStream = AudioSystem.getAudioInputStream(entry);
    							AudioFormat format = audioInputStream.getFormat();
    							long frames = audioInputStream.getFrameLength();
    							double durationInSeconds = (frames+0.0) / format.getFrameRate();
    							RadioInit.registerSong(song,(int) (durationInSeconds*20));
    							RadioInit.addStationSong(fileEntry.getName(),song);						
    							songs.add(song);
    						} catch (UnsupportedAudioFileException e){e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
    					}
    				}
    			}
    			if(fileEntry.getName().contains(".ogg")){
    				if(!RadioInit.containsStation("default"))
    					RadioInit.addStation("default");
				
    				try {
    					Ogg temp = new Ogg(fileEntry);
    					RadioInit.registerSong(fileEntry.getName(),(int) (temp.getSeconds()*20));
    					RadioInit.addStationSong("default",fileEntry.getName());
    					songs.add(fileEntry.getName());
    				} catch (Exception e) {e.printStackTrace();}			
    			}
    			else if(fileEntry.getName().contains(".wav")){
    				if(!RadioInit.containsStation("default"))
    					RadioInit.addStation("default");
    				AudioInputStream audioInputStream;
    				try {
    					audioInputStream = AudioSystem.getAudioInputStream(fileEntry);
    					AudioFormat format = audioInputStream.getFormat();
    					long frames = audioInputStream.getFrameLength();
    					double durationInSeconds = (frames+0.0) / format.getFrameRate();
    					RadioInit.registerSong(fileEntry.getName(),(int) (durationInSeconds*20));
    					RadioInit.addStationSong("default",fileEntry.getName());
						songs.add(fileEntry.getName());
    				} catch (UnsupportedAudioFileException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
    			}
    		}
    	else{
    		RadioInit.addStation("NO STATIONS");
    		RadioInit.addStationSong("NO STATION", "N/A.ogg");
    		JuxsTickHandler.anySongs=false;
    	}
    }
}
