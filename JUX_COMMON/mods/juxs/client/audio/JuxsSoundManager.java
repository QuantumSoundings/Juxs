package mods.juxs.client.audio;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;



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
	
	public static void playSong(int x, int y, int z, String song){
		if (ModLoader.getMinecraftInstance().sndManager.sndSystem.playing("BgMusic"))
        {
			ModLoader.getMinecraftInstance().sndManager.sndSystem.stop("BgMusic");
        }
		playingSongs.put(new Location(x,y,z), song.substring(0,song.length()-4));
		((TileEntityJux) ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(x, y, z)).currPlaying=song.substring(0,song.length()-4);
    	File temp= new File(Reference.MOD_DIR+"\\juxs\\"+((TileEntityJux) ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(x, y, z)).getStation()+"\\"+song);
    	if(temp.exists()){
    		try {
    			ModLoader.getMinecraftInstance().sndManager.sndSystem.newStreamingSource(true,song.substring(0,song.length()-4),temp.toURI().toURL(), song, false, x, y, z, 2, 64.0F);
    		} catch (MalformedURLException e) {e.printStackTrace();}
    		ModLoader.getMinecraftInstance().sndManager.sndSystem.setVolume(song.substring(song.length()-4), 0.5F * ModLoader.getMinecraftInstance().gameSettings.soundVolume);
    		MinecraftForge.EVENT_BUS.post(new PlayStreamingSourceEvent(ModLoader.getMinecraftInstance().sndManager, song.substring(0,song.length()-4), x, y, z));
    		ModLoader.getMinecraftInstance().sndManager.sndSystem.play(song.substring(0,song.length()-4));
    	}
    	else{
    		System.out.println("ERROR "+song.substring(0,song.length()-4)+" was not found.");
    	}
	}
	public static void stopAll(){
		for(Location b: playingSongs.keySet()){
			ModLoader.getMinecraftInstance().sndManager.sndSystem.stop(playingSongs.get(b));
		}
		playingSongs.clear();
	}
	public static void stop(int x, int y, int z){
		Location a= new Location(x,y,z);
		Location delete = null;
		for(Location b:playingSongs.keySet()){
			if(b.compareTo(a)==0){
				ModLoader.getMinecraftInstance().sndManager.sndSystem.stop(playingSongs.get(b));
				delete=b;
			}
		}
		playingSongs.remove(delete);
	}
	
	
	

}
