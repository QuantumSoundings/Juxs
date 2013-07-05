package mods.juxs.juxbox;

import java.io.IOException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import mods.juxs.core.radio.Location;
import mods.juxs.core.radio.RadioInit;
import mods.juxs.lib.Reference;
import mods.juxs.network.MessagePacket;
import mods.juxs.network.RadioUpdatePacket;
import mods.juxs.network.RequestPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityJux extends TileEntity {
	public boolean done = false;
	public String currStation;
	public String currPlaying;
	public boolean isDisabled=false;
	
	public TileEntityJux(){
		super();

	}
	@Override public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		currStation=RadioInit.findTheHardWay(new Location(xCoord,yCoord,zCoord));
		nbt.setBoolean("disabled", isDisabled);
		nbt.setString("station", currStation);	
		System.out.println("I WROTE STUFF TO NBT"+FMLCommonHandler.instance().getEffectiveSide()+currStation);
		
	}
	@Override public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		isDisabled=nbt.getBoolean("disabled");
		new MessagePacket(Reference.CHANNEL+"MESS","DISABLED",xCoord,yCoord,zCoord);
		currStation=nbt.getString("station");
		System.out.println("I READ STUFF FROM NBT"+FMLCommonHandler.instance().getEffectiveSide()+currStation);
		if(!RadioInit.findTheHardWay(new Location(xCoord,yCoord,zCoord)).equals("")){
			RadioInit.addStationBox(currStation, new Location(this.xCoord,yCoord,zCoord));
			RadioInit.removeTheHardWay(new Location(xCoord,yCoord,zCoord));
			new RequestPacket(Reference.CHANNEL+"REQUEST",xCoord,yCoord,zCoord);
		}
	}
	@Override public void updateEntity() {
		if(!done) {
			System.out.println("I UPDATED");
			int x = xCoord, y = yCoord, z = zCoord;
			System.out.printf("This te claims to be at %d %d %d %s %n",x,y,z,FMLCommonHandler.instance().getEffectiveSide()+"");
			if(FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER){
	            if(!RadioInit.containsStation(currStation)){
	            	RadioInit.addStationBox(RadioInit.stations.get(0).Name,new Location(x,y,z)); 
		            currStation=RadioInit.stations.get(0).Name;
	            }
	            else{
	            	RadioInit.addStationBox(currStation,new Location(x,y,z));
	            	new RequestPacket(Reference.CHANNEL+"REQUEST",x,y,z);
	            }
	        }
			done = true;
		}
	}
	@Override public boolean canUpdate() { return true; }
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}
	public String getStation(){
		if(currStation==null)
			currStation=RadioInit.stations.get(0).Name;
		return currStation;
		
	}
	public String getPlaying(){
		if(currPlaying==null)
			currPlaying="N/A";
		return currPlaying;
	}
}
