package mods.juxs.block;

import java.io.IOException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import mods.juxs.core.radio.Location;
import mods.juxs.core.radio.RadioInit;
import mods.juxs.lib.Reference;
import mods.juxs.network.RadioUpdate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityJux extends TileEntity {
	private boolean done = false;
	public TileEntityJux() {
	}
	@Override public void updateEntity() {
		if(!done) {
			int x = xCoord, y = yCoord, z = zCoord;
			System.out.printf("This te claims to be at %d %d %d %n",x,y,z);
			if(FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER){
	            RadioInit.addBox(new Location(x,y,z));
	            if(RadioInit.isPlaying()){
	                try {
	                    new RadioUpdate(Reference.CHANNEL+"TIMEUNTIL",x,y,z,RadioInit.ticks);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
			done = true;
		}
	}
	@Override public boolean canUpdate() { return true; }
	public boolean isUseableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		return true;
	}
}
