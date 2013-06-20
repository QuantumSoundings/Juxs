package juxs.client.audio;

import java.io.FileNotFoundException;

import cpw.mods.fml.relauncher.Side;

import juxs.core.radio.RadioInit;
import juxs.lib.Sounds;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class SoundHandler {
        @ForgeSubscribe
        public void onSoundLoad(SoundLoadEvent event){
            Sounds.buildList();
            for(int i=0;i<Sounds.songs.size();i++)
                try{
                    event.manager.soundPoolStreaming.addSound(Sounds.songs.get(i), this.getClass().getResource("/" + Sounds.songs.get(i)));
                    RadioInit.addSong(Sounds.songs.get(i).replaceAll("/", ".").substring(0,Sounds.songs.get(i).length()-4),Sounds.timeInTicks.get(i));
                    System.out.println(Sounds.songs.get(i));
                }catch(Exception e){}
        }
}
