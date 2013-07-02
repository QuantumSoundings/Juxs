package mods.juxs.client.audio;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import cpw.mods.fml.relauncher.Side;


import mods.juxs.core.radio.RadioInit;
import mods.juxs.lib.Reference;
import mods.juxs.lib.Sounds;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class SoundHandler {
        @ForgeSubscribe
        public void onSoundLoad(SoundLoadEvent event){
            Sounds.buildList();
            for(int i=0;i<Sounds.songs.size();i++)
                try{
                	if(Sounds.songs.get(i).contains("/")){
                        event.manager.soundPoolStreaming.addSound(Sounds.songs.get(i).substring(Sounds.songs.get(i).indexOf("/")+1), new File(Reference.MOD_DIR+"/juxs/"+Sounds.songs.get(i)));
                		System.out.println("[SoundHandler] Added "+Sounds.songs.get(i).substring(Sounds.songs.get(i).indexOf("/")+1));
                	}
                	else{
                		event.manager.soundPoolStreaming.addSound(Sounds.songs.get(i), new File(Reference.MOD_DIR+"/juxs/"+Sounds.songs.get(i)));
                		System.out.println("[SoundHandler] Added "+Sounds.songs.get(i)+" to sound pool.");
                	}
                }catch(Exception e){
                	e.printStackTrace();
                }
        }
}
