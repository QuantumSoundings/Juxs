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
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;

public class Sounds {
    public static String SOUND_LOCATION="resources/mods/juxs/sound/";
    public static ArrayList<String> songs=new ArrayList<String>();
    public static ArrayList<Integer> timeInTicks=new ArrayList<Integer>();
    
    public static void buildList(){   
		File soundFolder= new File(Reference.MOD_DIR+"/juxs");
		for(File fileEntry:soundFolder.listFiles()){
			if(fileEntry.getName().contains(".ogg")){
				songs.add(fileEntry.getName());
				try {
					Ogg temp = new Ogg(fileEntry);
					timeInTicks.add((int)(temp.getSeconds()*20));
				} catch (Exception e) {
					e.printStackTrace();
				}			
			}			
		}
    }
}
