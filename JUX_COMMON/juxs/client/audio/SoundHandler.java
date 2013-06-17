package juxs.client.audio;

import java.io.FileNotFoundException;

import juxs.lib.Sounds;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class SoundHandler {
        @ForgeSubscribe
        public void onSoundLoad(SoundLoadEvent event){
            for(String sound:Sounds.daList)
                try{
                    event.manager.soundPoolStreaming.addSound(sound, this.getClass().getResource("/" + sound));
                }catch(Exception e){}
        }
}
