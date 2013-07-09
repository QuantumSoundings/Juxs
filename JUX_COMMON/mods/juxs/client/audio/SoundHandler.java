package mods.juxs.client.audio;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import cpw.mods.fml.relauncher.Side;


import mods.juxs.core.radio.RadioInit;
import mods.juxs.lib.Reference;
import mods.juxs.lib.Sounds;
import net.minecraft.src.ModLoader;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class SoundHandler {
        @ForgeSubscribe
        public void onSoundLoad(SoundLoadEvent event){
            Sounds.buildList();
        }
        @ForgeSubscribe
        public void onSettingsChanged(SoundEvent event){
        	JuxsSoundManager.setVolume(ModLoader.getMinecraftInstance().gameSettings.musicVolume);
        	//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("SOUND EVENT");
        }
}
