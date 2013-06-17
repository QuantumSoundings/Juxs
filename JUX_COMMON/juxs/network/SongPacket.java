package juxs.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import juxs.lib.Reference;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.ModLoader;

public class SongPacket {
        
    public SongPacket(String sound,String chan, int x, int y, int z) throws IOException{
        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel=chan;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeInt(x);
        dos.writeInt(y);
        dos.writeInt(z);
        dos.writeUTF(sound+"play");
        packet.data=bos.toByteArray();
        packet.length=bos.size();
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER) {
                // We are on the server side.
        } else if (side == Side.CLIENT) {
                // We are on the client side.
                PacketDispatcher.sendPacketToServer(packet);
        } else {
                // We are on the Bukkit server.
        } 
        
    }
    public static void execute(DataInputStream data,Packet250CustomPayload packet) throws IOException{
        int x=data.readInt();
        int y=data.readInt();
        int z=data.readInt();
        String s=data.readUTF();
        if(packet.channel.equals(Reference.CHANNEL+"Stop"))
            ModLoader.getMinecraftInstance().sndManager.playStreaming((String)null,x,y,z);
        else
            ModLoader.getMinecraftInstance().sndManager.playStreaming(s.substring(0, s.length()-4),x,y,z);
    }

}