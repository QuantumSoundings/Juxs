package juxs.block;



import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import juxs.core.radio.Location;
import juxs.core.radio.RadioInit;
import juxs.lib.Reference;
import juxs.network.RadioUpdate;
import juxs.network.SongPacket;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.SoundLoadEvent;

public class JuxBox extends BlockContainer {
    
    public JuxBox(int id) {
        super(id, Material.rock);
        this.setUnlocalizedName("JuxBox");
        this.setHardness(2F);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {

        blockIcon = iconRegister.registerIcon("Juxs:juxbox");
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z,EntityPlayer player, int a, float b, float c, float d){
        if(player.isSneaking())return true;
           if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT)        
            try {
                new RadioUpdate(Reference.CHANNEL+"TIMEUNTIL",x,y,z,0,true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        else
            try {
                new RadioUpdate(Reference.CHANNEL+"TIMEUNTIL",x,y,z,RadioInit.timeRemaining(),true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        
        
        return true;
    }
    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        if(FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER){
            RadioInit.addBox(new Location(par2,par3,par4));
            if(RadioInit.isPlaying()){
                System.out.println("Waiting until next song...");
                try {
                    new RadioUpdate(Reference.CHANNEL+"TIMEUNTIL",par2,par3,par4,RadioInit.ticks,true);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        super.onBlockAdded(par1World, par2, par3, par4);
    }
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {
        if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
            ModLoader.getMinecraftInstance().theWorld.playRecord((String)null, x, y, z);
            try {
                new SongPacket("",Reference.CHANNEL+"STOP",x,y,z);
                new RadioUpdate(Reference.CHANNEL+"REMOVE",x,y,z,0,false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{

            try {
                RadioInit.removeBox(new Location(x,y,z));                
                new SongPacket("",Reference.CHANNEL+"STOP",x,y,z);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.breakBlock(world, x, y, z, id, meta);
    }
    @Override
    public TileEntity createNewTileEntity(World world) {
        return null;
    }

}
