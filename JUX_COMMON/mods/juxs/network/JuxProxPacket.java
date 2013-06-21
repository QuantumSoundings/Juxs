package mods.juxs.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

import mods.juxs.core.radio.Location;
import mods.juxs.core.radio.RadioInit;
import mods.juxs.lib.Reference;
import net.minecraft.network.packet.Packet250CustomPayload;

public class JuxProxPacket {
	
	public JuxProxPacket(String chan,int x,int y,int z){
		Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel=chan;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        try {
			dos.writeInt(x);
			dos.writeInt(y);
	        dos.writeInt(z);
		} catch (IOException e) {
			e.printStackTrace();
		}
        packet.data=bos.toByteArray();
        packet.length=bos.size();
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.CLIENT) 
                PacketDispatcher.sendPacketToServer(packet);
         
        
	}
	public static void execue(Packet250CustomPayload packet) throws IOException{
		System.out.println("Executing JuxProxPacket");
		DataInputStream in= new DataInputStream(new ByteArrayInputStream(packet.data));
		Location player= new Location(in.readInt(),in.readInt(),in.readInt());
		RadioInit.removeBox(player);
		ArrayList<Boolean> prox= new ArrayList<Boolean>();
		for(Location a:RadioInit.juxboxes){
			prox.add(Location.within(20,player,a));
		}
		if(!prox.contains(true)){
			//System.out.println(prox.contains(true));
			new SongPacket("",Reference.CHANNEL+"STOP",player.x,player.y,player.z);
		}
	}

}
