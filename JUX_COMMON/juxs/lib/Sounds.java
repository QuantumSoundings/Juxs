package juxs.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;

public class Sounds {
    public static String SOUND_LOCATION="mods/Juxs/sound/";
    public static String NAME_PREFIX="mods.Juxs.sound.";
    /*public static String[] daList = {
        SOUND_LOCATION + "feeling_good.ogg",
        SOUND_LOCATION + "wake_up_call.ogg",
        SOUND_LOCATION + "crash_into_me.ogg"/*,
        SOUND_LOCATION + "",
        SOUND_LOCATION + "",
        SOUND_LOCATION + "",
        SOUND_LOCATION + "",
        SOUND_LOCATION + ""};
    */
    public static ArrayList<String> songs=new ArrayList<String>();
    public static ArrayList<Integer> timeInTicks=new ArrayList<Integer>();
    public static final String FEELING_GOOD=NAME_PREFIX+"feeling_good.ogg";
    public static final String WAKE_UP_CALL=NAME_PREFIX+"wake_up_call.ogg";
    public static final String CRASH_INTO_ME=NAME_PREFIX+"crash_into_me.ogg";
    public static void buildList(){   
        try {
            Scanner s= new Scanner(new File(Reference.MOD_DIR+"/resources/mod/Juxs/sound/songList.txt"));
            while(s.hasNextLine()){
            String time=s.nextLine();
            if(!(time.length()>0))
                break;
            songs.add(SOUND_LOCATION+time.substring(0,time.indexOf(';')));
            time=time.substring(time.indexOf(';')+1,time.length());
            timeInTicks.add((Integer.parseInt(time.substring(0,time.indexOf(':')))*60*20)+(Integer.parseInt(time.substring(time.indexOf(':')+1))*20));
            }
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        
        
        
        
    }
}
