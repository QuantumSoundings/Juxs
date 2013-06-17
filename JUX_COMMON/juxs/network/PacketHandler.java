package juxs.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import juxs.lib.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler implements IPacketHandler{
    @Override
    public void onPacketData(INetworkManager manager,
        Packet250CustomPayload packet, Player player) {
    
        if (packet.channel.contains(Reference.CHANNEL)) {
            try {
                handleRandom(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}

    private void handleRandom(Packet250CustomPayload packet) throws IOException {
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
    
        if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
            
            SongPacket.execute(inputStream,packet);
        }
        else{
           System.out.println("Packet Received");
           PacketDispatcher.sendPacketToAllPlayers(packet);
           
        }

    }
}
