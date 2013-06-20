package juxs.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import juxs.core.radio.RadioInit;
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
                handleRandom(packet,player);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}

    private void handleRandom(Packet250CustomPayload packet, Player player) throws IOException {
            DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
            EntityPlayer play= (EntityPlayer)player;
        if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
            if(packet.channel.equals(Reference.CHANNEL+"REMOVE"))
                RadioUpdate.execute(packet);
            else if(packet.channel.equals(Reference.CHANNEL+"TIMEUNTIL")){
                int ticks=inputStream.readInt();
                int minute=ticks/20/60;
                int sec= (ticks/20)-(minute*60);
                
                play.addChatMessage(String.format("Next Song will begin in %d:%02d",minute,sec));
            }
            else
                SongPacket.execute(inputStream,packet);
            System.out.println("Packet Received From Server");
            
        }
        else{
            EntityPlayerMP p= (EntityPlayerMP)play;
            if(packet.channel.equals(Reference.CHANNEL+"TIMEUNTIL")){
                new RadioUpdate(Reference.CHANNEL+"TIMEUNTIL",(int)p.posX,(int)p.posY,(int) p.posZ,RadioInit.timeRemaining(),true);
            }
            else if(packet.channel.equals(Reference.CHANNEL+"REMOVE")){
                RadioUpdate.execute(packet);
                PacketDispatcher.sendPacketToAllPlayers(packet);
            }
            else               
                PacketDispatcher.sendPacketToAllPlayers(packet);
            System.out.println("Packet Received From Player");
        }

    }
}
