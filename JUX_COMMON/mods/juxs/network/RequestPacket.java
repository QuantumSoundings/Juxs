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

public class RequestPacket {
	public RequestPacket(String chan,int x,int y,int z){
		Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel=chan;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        try{
        	if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
        	dos.writeInt(x);
        	dos.writeInt(y);
        	dos.writeInt(z);
        	packet.data= bos.toByteArray();
        	packet.length=bos.size();
        	}
        	else{
        		dos.writeInt(x);
        		dos.writeInt(y);
        		dos.writeInt(z);
        		dos.writeUTF(RadioInit.findTheHardWay(new Location(x,y,z)));
        		dos.writeUTF(RadioInit.getStation(RadioInit.findTheHardWay(new Location(x,y,z))).getPlaying().substring(0, RadioInit.getStation(RadioInit.findTheHardWay(new Location(x,y,z))).getPlaying().length()-4));
        		packet.data=bos.toByteArray();
        		packet.length=bos.size();
        	}
        }catch(Exception e){e.printStackTrace();}
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER) {
                PacketDispatcher.sendPacketToAllAround(x, y, z, 50.0, 0, packet);
        } else if (side == Side.CLIENT) {
                // We are on the client side.
                PacketDispatcher.sendPacketToServer(packet);
        } else {
                // We are on the Bukkit server.
        } 
		
	}
	public static void execute(DataInputStream in,Packet250CustomPayload packet){
		try {
			int x=in.readInt();
			int y=in.readInt();
			int z=in.readInt();
			((TileEntityJux)ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(x,y,z)).currStation=in.readUTF();
			((TileEntityJux)ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(x,y,z)).currPlaying=in.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
