package juxs.network;

import juxs.client.audio.SoundHandler;
import juxs.core.radio.RadioInit;
import juxs.lib.Sounds;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy implements IGuiHandler{
    
    public void registerSoundHandler(){
        RadioInit.buildRadioList();
    }
    
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
        return null;
        
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        return null;
    }

}
