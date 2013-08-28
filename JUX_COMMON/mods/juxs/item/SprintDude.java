package mods.juxs.item;


import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.juxs.Juxs;
import mods.juxs.client.audio.JuxsSoundManager;
import mods.juxs.core.radio.RadioInit;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SprintDude extends Item{
	public String currPlayList;
	public String currSong;
	int curr,ticks=0;
	public boolean paused=false;
	public SprintDude(int id){
		super(id);
		this.setCreativeTab(Juxs.juxTab);
		this.setUnlocalizedName("Sprint Dude");
	}
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("juxs:sprintdude");
    }
	@Override
	@SideOnly(Side.CLIENT)
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity entity, int par4, boolean par5){
		if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
			if(currSong==null)
				currSong=RadioInit.stations.get(0).songs.get(0);
			if(currPlayList==null)
				currPlayList=RadioInit.stations.get(0).Name;
			if(!paused)
				ticks++;
			if(RadioInit.songs.get(currSong)<=ticks){
				ticks=0;
				playNext(entity);
			}
			if(ticks%20==0){
			JuxsSoundManager.updateSprintDudeLocation((int)entity.posX,(int) entity.posY,(int) entity.posZ);
			}
		}
		
		
	}
	public SprintDude instance(){
		return this;
	}
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer player)    {
		if(player.isSneaking()){
			if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
				JuxsSoundManager.pauseOrPlayPersonal();
				paused=!paused;
			}
		}
		else
			player.openGui(Juxs.instance, 1, world,(int) player.posX,(int) player.posY,(int) player.posZ);
        return par1ItemStack;
    }
	@SideOnly(Side.CLIENT)
	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
    {
		
		if(!JuxsSoundManager.isPaused)
			JuxsSoundManager.pauseOrPlayPersonal();
        return true;
    }
	@SideOnly(Side.CLIENT)
	public void playNext(Entity player){
		JuxsSoundManager.stopPersonal();
		curr=(curr+1)%RadioInit.getStation(currPlayList).songs.size();
		currSong=RadioInit.getStation(currPlayList).songs.get(curr);
		JuxsSoundManager.playPersonal((int)player.posX, (int)player.posY, (int)player.posZ, currSong,currPlayList);
	}
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List list, boolean par4){
		list.add("Currently Playing: "+currSong.substring(0,currSong.length()-4));
		list.add("Current PlayList: "+currPlayList);
		list.add(paused?"Paused":"Playing");
		
	}

}
