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
import mods.juxs.juxbox.TileEntityJux;
import mods.juxs.lib.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class RadioUpdatePacket extends JuxPacket{

    
    public RadioUpdatePacket(String chan,String station,int x ,int y, int z){
        
        super.setChannel(chan);
        if(chan.equals(Reference.CHANNEL+"NEXT")){
        	super.writeStation(station);
        	super.finalizeData();
        }
        else if(chan.equals(Reference.CHANNEL+"TIMEUNTIL")){
        	if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
        		super.writeStation(station);
        	}
        	else{
        		super.writeTime(RadioInit.getStation(station).timeRemaining());
        	}
        	super.finalizeData();
        }
        else if(chan.equals(Reference.CHANNEL+"CHANGEP")||chan.equals(Reference.CHANNEL+"CHANGEN")||chan.equals(Reference.CHANNEL+"REMOVE")){
        	super.writeStation(station);
        	super.writeXYZ(x, y, z);
        	super.finalizeData();
        }
        
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER) {
            if(packet.channel.equals(Reference.CHANNEL+"TIMEUNTIL"))
                PacketDispatcher.sendPacketToAllAround(x,y,z,2,0,packet);    
        } 
        else if (side == Side.CLIENT)
                PacketDispatcher.sendPacketToServer(packet);
        
        	
        	
    }
    public static void execute(Packet250CustomPayload packet) throws IOException{
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(packet.data));
        Location a;
        String station;
        if(FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER){
        	if(packet.channel.equals(Reference.CHANNEL+"CHANGEP")){
        			station= in.readUTF();
        			a=new Location(in.readInt(),in.readInt(),in.readInt());
        			RadioInit.getStation(station).removeBox(a);
    				RadioInit.stations.get(RadioInit.stations.indexOf(RadioInit.getStation(RadioInit.getPrevStation(station)))).addBox(a);
    				//((TileEntityJux) Minecraft.getMinecraft().theWorld.getBlockTileEntity(a.x, a.y, a.z)).currStation=RadioInit.getPrevStation(station);

        	}
        	else if(packet.channel.equals(Reference.CHANNEL+"CHANGEN")){
        		station= in.readUTF();
    			a=new Location(in.readInt(),in.readInt(),in.readInt());
				RadioInit.getStation(station).removeBox(a);
				RadioInit.stations.get(RadioInit.stations.indexOf(RadioInit.getStation(RadioInit.getNextStation(station)))).addBox(a);
				//((TileEntityJux) Minecraft.getMinecraft().theWorld.getBlockTileEntity(a.x, a.y, a.z)).currStation=RadioInit.getNextStation(station);


        	}
        	else if(packet.channel.equals(Reference.CHANNEL+"REMOVE")){
        		station= in.readUTF();
    			a=new Location(in.readInt(),in.readInt(),in.readInt());
        		RadioInit.removeStationBox(station, a);
        	}
        }
        
    }

}
