package mods.juxs.block;



import java.io.IOException;
import java.util.ArrayList;

import mods.juxs.core.radio.Location;
import mods.juxs.core.radio.RadioInit;
import mods.juxs.lib.Reference;
import mods.juxs.network.JuxProxPacket;
import mods.juxs.network.RadioUpdate;
import mods.juxs.network.SongPacket;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
    
    public JuxBox(int id) {
        super(id, Material.rock);
        this.setUnlocalizedName("JuxBox");
        this.setHardness(2F);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {

        icon[0] = ir.registerIcon("juxs:juxbox_side");
        icon[1] = ir.registerIcon("juxs:juxbox_bottem");
        icon[2] = ir.registerIcon("juxs:juxbox_top");
        
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess ba,int x,int y,int z, int s){
    	if(s>=2&&s<=5)return icon[0];
    	else if(s==1)return icon[2];
    	else return icon[1];
    }
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
    			//System.out.println(player.username+" is sneaking");
    			try {
					new RadioUpdate(Reference.CHANNEL+"NEXT",x,y,z,0);
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    		else{													//if not sneaking time until next song
    			try {
    				new RadioUpdate(Reference.CHANNEL+"TIMEUNTIL",x,y,z,0);
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
        
        return true;
    }
    /*@Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        if(FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER){
            RadioInit.addBox(new Location(par2,par3,par4));
            if(RadioInit.isPlaying()){
                //System.out.println("Waiting until next song...");
                try {
                    new RadioUpdate(Reference.CHANNEL+"TIMEUNTIL",par2,par3,par4,RadioInit.ticks,true);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        super.onBlockAdded(par1World, par2, par3, par4);
    }*/
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {
        if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
        }
        else{
        	Location player=new Location(x,y,z);
        	RadioInit.removeBox(player);
        	ArrayList<Boolean> prox= new ArrayList<Boolean>();
    		for(Location a:RadioInit.juxboxes){
    			prox.add(Location.within(20,player,a));
    		}
    		if(!prox.contains(true)){
    			//System.out.println(prox.contains(true));
    			try {
					new SongPacket("",Reference.CHANNEL+"STOP",player.x,player.y,player.z);
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
        }
        super.breakBlock(world, x, y, z, id, meta);
    }
    @Override
    public TileEntity createNewTileEntity(World world) {
    	return new TileEntityJux();
    }
}
