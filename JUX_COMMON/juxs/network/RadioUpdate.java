package juxs.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import juxs.core.radio.Location;
import juxs.core.radio.RadioInit;
import juxs.lib.Reference;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

import net.minecraft.network.packet.Packet250CustomPayload;

public class RadioUpdate {

    
    public RadioUpdate(String chan,int x,int y, int z,int ticks,boolean TimeUntil) throws IOException{
        
        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel=chan;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        if(chan.equals(Reference.CHANNEL+"REMOVE")){
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(z);
            packet.data=bos.toByteArray();
            packet.length=bos.size();
        }
        else if(chan.equals(Reference.CHANNEL+"TIMEUNTIL")){
            dos.writeInt(ticks);
            packet.data=bos.toByteArray();
            packet.length=bos.size();
        }
       
            Side side = FMLCommonHandler.instance().getEffectiveSide();
        
        if (side == Side.SERVER) {
            if(TimeUntil){
                PacketDispatcher.sendPacketToAllAround((double)(x),(double)(y),(double)(z),2,0,packet);
                System.out.println("packet sent");
            }
            else
                execute(packet);
        } else if (side == Side.CLIENT) {
                // We are on the client side.
                PacketDispatcher.sendPacketToServer(packet);
        } else {
                // We are on the Bukkit server.
        } 
        
    }
    public static void execute(Packet250CustomPayload packet){
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(packet.data));
        try {
            RadioInit.removeBox(new Location(in.readInt(),in.readInt(),in.readInt()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
