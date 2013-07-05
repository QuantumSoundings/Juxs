package mods.juxs.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

import mods.juxs.juxbox.TileEntityJux;
import mods.juxs.lib.Reference;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;

public class SongPacket extends JuxPacket{
        
    public SongPacket(String sound,String chan, int x, int y, int z) throws IOException{
        super.setChannel(chan);
        super.writeXYZ(x, y, z);
        super.writeString(sound);
        super.finalizeData();
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER)
                PacketDispatcher.sendPacketToAllAround(x, y, z, 50.0, 0, packet); 
        else if (side == Side.CLIENT)
                PacketDispatcher.sendPacketToServer(packet);
        
    }
    public static void execute(DataInputStream data,Packet250CustomPayload packet) throws IOException{
        int x=data.readInt();
        int y=data.readInt();
        int z=data.readInt();
        String s=data.readUTF();
        if(packet.channel.equals(Reference.CHANNEL+"STOP"))
            ModLoader.getMinecraftInstance().theWorld.playRecord((String)null,x,y,z);
        else{
        	((TileEntityJux) ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(x, y, z)).currPlaying=s;
            ModLoader.getMinecraftInstance().theWorld.playRecord(s,x,y,z);
        }
    }

}