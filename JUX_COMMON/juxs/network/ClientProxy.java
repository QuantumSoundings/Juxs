package juxs.network;

import net.minecraftforge.common.MinecraftForge;
import juxs.client.audio.SoundHandler;

public class ClientProxy extends CommonProxy{
    
    @Override
    public void registerSoundHandler(){
        MinecraftForge.EVENT_BUS.register(new SoundHandler());
    }

}
