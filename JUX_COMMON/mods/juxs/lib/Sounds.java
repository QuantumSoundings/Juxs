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

import mods.juxs.core.radio.RadioInit;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;

public class Sounds {
    public static String SOUND_LOCATION="resources/mods/juxs/sound/";
    public static ArrayList<String> songs=new ArrayList<String>();
    public static ArrayList<Integer> timeInTicks=new ArrayList<Integer>();
    
    public static void buildList(){   
    	//System.out.println("Building List");
		File soundFolder= new File(Reference.MOD_DIR+"/juxs");
		for(File fileEntry:soundFolder.listFiles()){
			//System.out.println("LSKfjsldkfja;sldkfj;alskdjf;l");
			if(fileEntry.isDirectory()){
				RadioInit.addStation(fileEntry.getName());
				for(File entry:fileEntry.listFiles()){
					if(entry.getName().contains(".ogg")){
						try {
							Ogg temp = new Ogg(entry);
							RadioInit.registerSong(entry.getName(),(int) (temp.getSeconds()*20));
							//System.out.println("Registered song "+entry.getName());
							RadioInit.addStationSong(fileEntry.getName(),entry.getName());
					
							String song=fileEntry.getName()+"/"+entry.getName();
						
							songs.add(((fileEntry.getName()+"/"+entry.getName())));
						} catch (Exception e) {e.printStackTrace();}
					}
					else if(entry.getName().contains(".wav")){
						AudioInputStream audioInputStream;
						try {
							audioInputStream = AudioSystem.getAudioInputStream(entry);
							AudioFormat format = audioInputStream.getFormat();
							long frames = audioInputStream.getFrameLength();
							double durationInSeconds = (frames+0.0) / format.getFrameRate();
							RadioInit.registerSong(entry.getName(),(int) (durationInSeconds*20));
							//System.out.println("Registered song "+entry.getName());
							RadioInit.addStationSong(fileEntry.getName(),entry.getName());
					
							String song=fileEntry.getName()+"/"+entry.getName();
						
							songs.add(((fileEntry.getName()+"/"+entry.getName())));
						} catch (UnsupportedAudioFileException e){e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
					}
				}
			}
			if(fileEntry.getName().contains(".ogg")){
				if(!RadioInit.containsStation("default")){
					RadioInit.addStation("default");
				}
				
				try {
					Ogg temp = new Ogg(fileEntry);
					RadioInit.registerSong(fileEntry.getName(),(int) (temp.getSeconds()*20));
					RadioInit.addStationSong("default",fileEntry.getName());
					songs.add(fileEntry.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}			
			}
			else if(fileEntry.getName().contains(".wav")){
				if(!RadioInit.containsStation("default")){
					RadioInit.addStation("default");
				}
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
		//System.out.println(RadioInit.songs.toString());
    }
}
