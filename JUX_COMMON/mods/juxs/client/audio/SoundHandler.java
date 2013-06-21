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
                    event.manager.soundPoolStreaming.addSound(Sounds.songs.get(i), new File(Reference.MOD_DIR+"/juxs/"+Sounds.songs.get(i))/*this.getClass().getResource("/" + Sounds.songs.get(i))*/);
                    RadioInit.addSong(Sounds.songs.get(i).replaceAll("/", ".").substring(0,Sounds.songs.get(i).length()-4),Sounds.timeInTicks.get(i));
                    System.out.println(Sounds.songs.get(i));
                }catch(Exception e){
                	e.printStackTrace();
                }
        }
}
