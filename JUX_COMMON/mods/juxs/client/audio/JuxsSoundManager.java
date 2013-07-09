package mods.juxs.client.audio;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;



import mods.juxs.core.radio.Location;
import mods.juxs.juxbox.TileEntityJux;
import mods.juxs.lib.Reference;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.ModLoader;
import net.minecraftforge.client.event.sound.PlayStreamingSourceEvent;
import net.minecraftforge.common.MinecraftForge;

public class JuxsSoundManager{
	public static HashMap<Location,String> playingSongs = new HashMap<Location,String>();
	public static String currSprintDudeSong="";
	public static boolean isPaused=false;
	public static float soundVolume;
	
	public static void playSong(int x, int y, int z, String song){
		if (ModLoader.getMinecraftInstance().sndManager.sndSystem.playing("BgMusic"))
			ModLoader.getMinecraftInstance().sndManager.sndSystem.stop("BgMusic");
		Location a= new Location(x,y,z);
		for(Location b:playingSongs.keySet()){
			if(b.compareTo(a)==0){
				ModLoader.getMinecraftInstance().sndManager.sndSystem.stop(playingSongs.get(b));
				break;
			}
		}
		playingSongs.put(new Location(x,y,z), song.substring(0,song.length()-4));
		
		((TileEntityJux) ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(x, y, z)).currPlaying=song.substring(0,song.length()-4);
    	
		File temp= new File(Reference.MOD_DIR.subSequence(0, Reference.MOD_DIR.length()-7)+"\\juxs\\"+((TileEntityJux) ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(x, y, z)).getStation()+"\\"+song);
    	
		if(temp.exists()){
    		try {
    			ModLoader.getMinecraftInstance().sndManager.sndSystem.newStreamingSource(true,song.substring(0,song.length()-4),temp.toURI().toURL(), song, false, x, y, z, 2, 64.0F);
    		} catch (MalformedURLException e) {e.printStackTrace();}
    		ModLoader.getMinecraftInstance().sndManager.sndSystem.setVolume(song.substring(song.length()-4), 0.5F * ModLoader.getMinecraftInstance().gameSettings.musicVolume);
    		MinecraftForge.EVENT_BUS.post(new PlayStreamingSourceEvent(ModLoader.getMinecraftInstance().sndManager, song.substring(0,song.length()-4), x, y, z));
    		ModLoader.getMinecraftInstance().sndManager.sndSystem.play(song.substring(0,song.length()-4));
    	}
    	else{
    		if(!ModLoader.getMinecraftInstance().isSingleplayer())
        		ModLoader.getMinecraftInstance().thePlayer.addChatMessage("I'm Sorry, but you do not have this file. Please make sure all files are in thier correct folders. Then contact a sever admin.");
    		ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Sorry no music was found in your minecraft directory.");
    		System.out.println("ERROR "+song.substring(0,song.length()-4)+" was not found.");
    	}
	}
	public static void setVolume(float volume){
		for(Location b:playingSongs.keySet()){
				ModLoader.getMinecraftInstance().sndManager.sndSystem.setVolume(playingSongs.get(b), volume);
		}
		ModLoader.getMinecraftInstance().sndManager.sndSystem.setVolume(currSprintDudeSong,volume);
	}
	public static void stopAll(){
		for(Location b: playingSongs.keySet()){
			ModLoader.getMinecraftInstance().sndManager.sndSystem.stop(playingSongs.get(b));
			ModLoader.getMinecraftInstance().sndManager.sndSystem.removeSource(playingSongs.get(b));
		}
		playingSongs.clear();
		if(currSprintDudeSong!=null||!currSprintDudeSong.isEmpty()){
			ModLoader.getMinecraftInstance().sndManager.sndSystem.stop(currSprintDudeSong);
			currSprintDudeSong="";
		}
	}
	public static void playPersonal(int x, int y, int z, String song,String playList){
		isPaused=false;
		File temp= new File(Reference.MOD_DIR.subSequence(0, Reference.MOD_DIR.length()-7)+"\\juxs\\"+playList+"\\"+song);
		currSprintDudeSong= song.substring(0,song.length()-4);
		if(temp.exists()){
    		try {
    			ModLoader.getMinecraftInstance().sndManager.sndSystem.newStreamingSource(true,currSprintDudeSong,temp.toURI().toURL(), song, false, x, y, z, 2, 25.0F);
    		} catch (MalformedURLException e) {e.printStackTrace();}
    		ModLoader.getMinecraftInstance().sndManager.sndSystem.setVolume(currSprintDudeSong, 0.5F * ModLoader.getMinecraftInstance().gameSettings.musicVolume);
    		MinecraftForge.EVENT_BUS.post(new PlayStreamingSourceEvent(ModLoader.getMinecraftInstance().sndManager,currSprintDudeSong, x, y, z));
    		ModLoader.getMinecraftInstance().sndManager.sndSystem.play(currSprintDudeSong);
    	}
	}
	public static void stopPersonal(){
		ModLoader.getMinecraftInstance().sndManager.sndSystem.stop(currSprintDudeSong);
	}
	@SideOnly(Side.CLIENT)
	public static void pauseOrPlayPersonal(){
		if(isPaused)
			ModLoader.getMinecraftInstance().sndManager.sndSystem.play(currSprintDudeSong);
		else
			ModLoader.getMinecraftInstance().sndManager.sndSystem.pause(currSprintDudeSong);
		isPaused=!isPaused;
			
	}
	public static void updateSprintDudeLocation(int x, int y, int z){
		ModLoader.getMinecraftInstance().sndManager.sndSystem.setPosition(currSprintDudeSong, x, y, z);
	}
	public static void stop(int x, int y, int z){
		Location a= new Location(x,y,z);
		Location delete = null;
		for(Location b:playingSongs.keySet()){
			if(b.compareTo(a)==0){
				ModLoader.getMinecraftInstance().sndManager.sndSystem.stop(playingSongs.get(b));
				delete=b;
				break;
			}
		}
		playingSongs.remove(delete);
		
	}
	
	
	

}
