package juxs;

import java.io.File;

import juxs.client.audio.SoundHandler;

import juxs.block.ModBlocks;
import juxs.item.ModItems;
import juxs.lib.Reference;
import juxs.network.CommonProxy;
import juxs.network.PacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;

@Mod(
    modid=Reference.MOD_ID,
    name=Reference.MOD_NAME,
    version=Reference.VERSION
)
@NetworkMod(channels={Reference.CHANNEL+"PLAY",Reference.CHANNEL+"STOP"},clientSideRequired=true,serverSideRequired=true,packetHandler=PacketHandler.class)
public class Juxs {
    @Instance("jordan_Juxs")
    public static Juxs instance;
    
    @SidedProxy(clientSide="juxs.network.ClientProxy",serverSide="juxs.network.CommonProxy")
    public static CommonProxy proxy;
    
    
    @PreInit
    public void load(FMLPreInitializationEvent Event){
        
        proxy.registerSoundHandler();
        ModBlocks.init();
        ModItems.init();
    }
    @Init
    public void Init(FMLInitializationEvent Event){
        
    }
    @PostInit
    public void Init(FMLPostInitializationEvent Event){
        
    }
}
