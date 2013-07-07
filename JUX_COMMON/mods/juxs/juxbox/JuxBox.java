package mods.juxs.juxbox;



import java.io.IOException;
import java.util.ArrayList;

import mods.juxs.Juxs;
import mods.juxs.core.radio.Location;
import mods.juxs.core.radio.RadioInit;
import mods.juxs.lib.Reference;
import mods.juxs.network.MessagePacket;
import mods.juxs.network.RadioUpdatePacket;
import mods.juxs.network.RequestPacket;
import mods.juxs.network.SongPacket;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class JuxBox extends BlockContainer {
	private Icon[] icon=new Icon[3];
	//public String currStation;    
    public JuxBox(int id) {
        super(id, Material.rock);
        this.setUnlocalizedName("juxs:JuxBox");
        this.setHardness(2F);
        this.setCreativeTab(Juxs.juxTab);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {

        icon[0] = ir.registerIcon("juxs:juxbox_side");
        icon[1] = ir.registerIcon("juxs:juxbox_bottem");
        icon[2] = ir.registerIcon("juxs:juxbox_top");
        
    }
    
    /*@Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess ba,int x,int y,int z, int s){
    	if(s>=2&&s<=5)return icon[0];
    	else if(s==1)return icon[2];
    	else return icon[1];
    }*/
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int a, int b){
    	if(a>=2&&a<=5)return icon[0];
    	else if(a==1)return icon[2];
    	else return icon[1];
    	
    }

    public boolean onBlockActivated(World world, int x, int y, int z,EntityPlayer player, int a, float b, float c, float d){
    	if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT) {
    		if(player.isSneaking()){	//if sneaking changes to next song
				new RadioUpdatePacket(Reference.CHANNEL+"TIMEUNTIL",((TileEntityJux)(world.getBlockTileEntity(x,y,z))).getStation(),x,y,z);
    		}
    		else{	
    			if(!((TileEntityJux)(world.getBlockTileEntity(x,y,z))).isDisabled){
    				new RequestPacket(Reference.CHANNEL+"REQUEST",x,y,z);
    				player.openGui(Juxs.instance,0,world,x,y,z);
    			}
    			else{
    				player.addChatMessage("This JuxBox is disabled");
    			}
    			
    		}
    	}
        
        return true;
    }
    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        if(FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER){
           //System.out.println("BLOCK WAS BROKEN");
           //if((RadioInit.getNearBy(new Location(x,y,z))).size()>0){
        	 //  new MessagePacket(Reference.CHANNEL+"MESS","RADIO ALREADY NEAR BY DISABLING",x,y,z);
           //}
           //else
        	   ((TileEntityJux)world.getBlockTileEntity(x,y,z)).currStation=RadioInit.stations.get(0).Name;
        }
       super.onBlockAdded(world, x, y, z);
    }
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {
        if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
        }
        else{
        	Location player=new Location(x,y,z);
        	RadioInit.removeTheHardWay(player);
    			try {
					new SongPacket("",Reference.CHANNEL+"STOP","doesnt matter",player.x,player.y,player.z);
				} catch (IOException e) {e.printStackTrace();}
    		
       		//else if((RadioInit.getNearBy(player)).size()==1){
       			//new MessagePacket(Reference.CHANNEL+"MESS","FALSE",x,y,z);
       		//}
        }
        super.breakBlock(world, x, y, z, id, meta);
    }
    @Override
    public TileEntity createNewTileEntity(World world) {
    	return new TileEntityJux();
    }
}
