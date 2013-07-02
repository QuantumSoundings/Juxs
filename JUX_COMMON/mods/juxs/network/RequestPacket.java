package mods.juxs.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

import mods.juxs.block.TileEntityJux;
import mods.juxs.core.radio.Location;
import mods.juxs.core.radio.RadioInit;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;

public class RequestPacket extends JuxPacket{
	public RequestPacket(String chan,int x,int y,int z){
		super.setChannel(chan);
        	if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
        		super.writeXYZ(x, y, z);
        		super.finalizeData();
        	}
        	else{
        		super.writeXYZ(x, y, z);
        		super.writeStation(RadioInit.findTheHardWay(new Location(x,y,z)));
        		super.writeStation(RadioInit.getStation(RadioInit.findTheHardWay(new Location(x,y,z))).getPlaying().substring(0, RadioInit.getStation(RadioInit.findTheHardWay(new Location(x,y,z))).getPlaying().length()-4));        		
        		super.finalizeData();
        	}
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER) {
                PacketDispatcher.sendPacketToAllAround(x, y, z, 50.0, 0, packet);
        } 
        else if (side == Side.CLIENT) {
                PacketDispatcher.sendPacketToServer(packet);
        }
		
	}
	public static void execute(DataInputStream in,Packet250CustomPayload packet){
		try {
			int x=in.readInt();
			int y=in.readInt();
			int z=in.readInt();
			((TileEntityJux)ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(x,y,z)).currStation=in.readUTF();
			((TileEntityJux)ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(x,y,z)).currPlaying=in.readUTF();
		} catch (IOException e) {e.printStackTrace();}
		
	}

}
