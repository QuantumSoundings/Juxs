package mods.juxs.network;

import cpw.mods.fml.common.network.PacketDispatcher;

public class MessagePacket extends JuxPacket{
	public MessagePacket(String chan,String s,int x,int y,int z){
		super.setChannel(chan);
		super.writeString(s);
		super.writeXYZ(x, y, z);
		super.finalizeData();
		PacketDispatcher.sendPacketToAllAround(x, y, z, 5, 0, packet);
	}

}
