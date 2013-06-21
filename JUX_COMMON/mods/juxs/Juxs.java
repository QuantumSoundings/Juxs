package mods.juxs;

import java.io.File;
import java.util.EnumSet;




import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import mods.juxs.block.JuxBox;
import mods.juxs.block.ModBlocks;
import mods.juxs.block.TileEntityJux;
import mods.juxs.client.audio.SoundHandler;
import mods.juxs.core.radio.RadioInit;
import mods.juxs.item.ModItems;
import mods.juxs.lib.Reference;
import mods.juxs.lib.Sounds;
import mods.juxs.network.CommonProxy;
import mods.juxs.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid=Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.VERSION)
@NetworkMod(channels={Reference.CHANNEL+"CHECK",Reference.CHANNEL+"PLAY",Reference.CHANNEL+"STOP",Reference.CHANNEL+"TIMEUNTIL",Reference.CHANNEL+"REMOVE",Reference.CHANNEL+"NEXT"},clientSideRequired=true,serverSideRequired=true,packetHandler=PacketHandler.class)
public class Juxs {
    @Instance(Reference.MOD_ID)
    public static Juxs instance;
    
    @SidedProxy(clientSide="mods.juxs.network.ClientProxy",serverSide="mods.juxs.network.CommonProxy")
    public static CommonProxy proxy;
    public static CreativeTabs juxTab;
    public static net.minecraft.block.Block juxBox;
    
    @PreInit
    public void preInit(FMLPreInitializationEvent event){
    	GameRegistry.registerTileEntity(TileEntityJux.class, "juxboxtile");
    	Reference.MOD_DIR=event.getModConfigurationDirectory().getAbsolutePath();
        System.out.println(Reference.MOD_DIR);
        juxTab = new CreativeTabs("juxTab") {
			@Override public ItemStack getIconItemStack() {
				return new ItemStack(juxBox,1,0);
			}
			@Override public String getTranslatedTabLabel() {
				return "Juxs";
			}
		};
		juxBox= new JuxBox(2451);
        
        proxy.registerSoundHandler();
        TickRegistry.registerTickHandler(new JuxsTickHandler(EnumSet.of(TickType.SERVER)), Side.SERVER);
        ModBlocks.init();
        ModItems.init();
    }
    
    @Init public void init(FMLInitializationEvent Event) { }
}
