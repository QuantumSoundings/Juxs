package juxs.block;



import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import juxs.lib.Reference;
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
        if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
            try {
                //ModLoader.getMinecraftInstance().sndManager.playStreaming("mods.Juxs.sound.feeling_good",x,y,z);
                new SongPacket("mods.Juxs.sound.feeling_good",Reference.CHANNEL+"PLAY",x,y,z);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return true;
    }
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {
        if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
            ModLoader.getMinecraftInstance().sndManager.playStreaming((String)null , x, y, z);
            try {
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
