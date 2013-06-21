package mods.juxs.network;

import mods.juxs.client.audio.SoundHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy{
    
    @Override
    public void registerSoundHandler(){
        MinecraftForge.EVENT_BUS.register(new SoundHandler());
    }

}
