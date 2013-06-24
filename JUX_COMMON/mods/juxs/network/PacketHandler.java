package mods.juxs.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import mods.juxs.core.radio.RadioInit;
import mods.juxs.lib.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
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
                handlePacket(packet,player);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}

    private void handlePacket(Packet250CustomPayload packet, Player player) throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(packet.data));
        EntityPlayer play= (EntityPlayer)player;
        
        if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
        	if(packet.channel.equals(Reference.CHANNEL+"REMOVE"))
        		RadioUpdate.execute(packet);
        	else if(packet.channel.equals(Reference.CHANNEL+"TIMEUNTIL")){
        		int ticks=in.readInt();
        		int minute=ticks/20/60;
        		int sec= (ticks/20)-(minute*60);
        		play.addChatMessage(String.format("Next Song will begin in %d:%02d",minute,sec));
            }
        	else if(packet.channel.equals(Reference.CHANNEL+"REQUEST")){
        		RequestPacket.execute(in, packet);
        	}
        	else
        		SongPacket.execute(in,packet);
            
        }
        else{
            EntityPlayerMP p= (EntityPlayerMP)play;
            if(packet.channel.equals(Reference.CHANNEL+"TIMEUNTIL")){
            	System.out.println("received RadioUpdate packet from player");
                new RadioUpdate(Reference.CHANNEL+"TIMEUNTIL",in.readUTF(),(int)p.posX,(int)p.posY,(int) p.posZ);
            }
            else if(packet.channel.contains(Reference.CHANNEL+"CHANGE")){
            	StationChangePacket.execute(packet);
            }
            else if(packet.channel.equals(Reference.CHANNEL+"REMOVE")){
                StationChangePacket.execute(packet);
            }
            else if(packet.channel.equals(Reference.CHANNEL+"NEXT")){
            	if(MinecraftServer.getServer().getConfigurationManager().getOps().contains(play.username.toLowerCase()))
            		RadioInit.getStation(in.readUTF()).next();
            }
            else if(packet.channel.equals(Reference.CHANNEL+"CHECK")){
            	JuxProxPacket.execue(packet);
            }
            else if(packet.channel.equals(Reference.CHANNEL+"REQUEST")){
            	new RequestPacket(Reference.CHANNEL+"REQUEST",in.readInt(),in.readInt(),in.readInt());
            }
            else
                PacketDispatcher.sendPacketToAllPlayers(packet);
            //System.out.println("Packet Received From Player");
        }

    }
}
