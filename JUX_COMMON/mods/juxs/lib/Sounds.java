package mods.juxs.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;

public class Sounds {
    public static String SOUND_LOCATION="resources/mods/juxs/sound/";
    public static ArrayList<String> songs=new ArrayList<String>();
    public static ArrayList<Integer> timeInTicks=new ArrayList<Integer>();
    
    public static void buildList(){   
        try {
            Scanner s= new Scanner(new File(Reference.MOD_DIR+"/juxs/songList.txt"));
            while(s.hasNextLine()){
	            String time=s.nextLine();
	            if(!(time.length()>0))
	                break;
	            songs.add(/*SOUND_LOCATION+*/time.substring(0,time.indexOf(';')));
	            time=time.substring(time.indexOf(';')+1,time.length());
	            timeInTicks.add((Integer.parseInt(time.substring(0,time.indexOf(':')))*60*20)+(Integer.parseInt(time.substring(time.indexOf(':')+1))*20));
            }
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
