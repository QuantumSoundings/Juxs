package mods.juxs.block;

import java.io.IOException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import mods.juxs.core.radio.Location;
import mods.juxs.core.radio.RadioInit;
import mods.juxs.lib.Reference;
import mods.juxs.network.RadioUpdatePacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityJux extends TileEntity {
	private boolean done = false;
	public String currStation;
	public String currPlaying;
	@Override public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setString("station", currStation);
		
		
	}
	@Override public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		currStation=nbt.getString("station");
		
		
	}
	@Override public void updateEntity() {
		if(!done) {
			int x = xCoord, y = yCoord, z = zCoord;
			System.out.printf("This te claims to be at %d %d %d %n",x,y,z);
			if(FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER){
	            RadioInit.addStationBox(currStation,new Location(x,y,z));
	        }
			done = true;
		}
	}
	@Override public boolean canUpdate() { return true; }
	public boolean isUseableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		return true;
	}
	public String getStation(){
		//if(currStation==null)
		//	currStation="default";
		return currStation;
		
	}
	public String getPlaying(){
		if(currPlaying==null)
			currPlaying="N/A";
		return currPlaying;
	}
}
