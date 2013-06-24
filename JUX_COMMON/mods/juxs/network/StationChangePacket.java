package mods.juxs.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

import mods.juxs.core.radio.Location;
import mods.juxs.core.radio.RadioInit;
import mods.juxs.lib.Reference;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.ModLoader;

public class StationChangePacket {
	public StationChangePacket(String chan,String currstation,int x,int y,int z){
		Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel=chan;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        try{
        	dos.writeUTF(currstation);
        	dos.writeInt(x);
        	dos.writeInt(y);
        	dos.writeInt(z);
        	packet.data=bos.toByteArray();
        	packet.length=bos.size();
        }catch(Exception e){e.getStackTrace();}
        if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
        	PacketDispatcher.sendPacketToServer(packet);
        }
	}
	public static void execute(Packet250CustomPayload packet) throws IOException{
		DataInputStream in= new DataInputStream(new ByteArrayInputStream(packet.data));
		String station = in.readUTF();
		Location a= new Location(in.readInt(),in.readInt(),in.readInt());
		if(FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER){
			if(packet.channel.equals(Reference.CHANNEL+"REMOVE")){
				RadioInit.removeTheHardWay(a);
			}
			else if(packet.channel.equals(Reference.CHANNEL+"CHANGEN")){
				
			
				//System.out.println("[StationChangePacket] We are changing from "+station+" to "+RadioInit.getNextStation(station));
				//System.out.println("[StationChangePacket] List of boxs for Station:"+station+RadioInit.getStation(station).boxes.toString());
				RadioInit.getStation(station).removeBox(a);
				//System.out.println("[StationChangePacket] List of boxs for Station:"+station+RadioInit.getStation(station).boxes.toString());
				//System.out.println("[StationChangePacket] List of boxs for Station:"+RadioInit.getNextStation(station)+RadioInit.getStation(RadioInit.getNextStation(station)).boxes.toString());
				RadioInit.stations.get(RadioInit.stations.indexOf(RadioInit.getStation(RadioInit.getNextStation(station)))).addBox(a);
				//System.out.println("[StationChangePacket] List of boxs for Station:"+RadioInit.getNextStation(station)+RadioInit.getStation(RadioInit.getNextStation(station)).boxes.toString());
			}
			else if(packet.channel.equals(Reference.CHANNEL+"CHANGEP")){
				RadioInit.getStation(station).removeBox(a);
				RadioInit.stations.get(RadioInit.stations.indexOf(RadioInit.getStation(RadioInit.getPrevStation(station)))).addBox(a);
			}
		}
		else{
			
		}
		
	}
}
