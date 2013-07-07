package mods.juxs.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;


import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

import mods.juxs.client.audio.JuxsSoundManager;
import mods.juxs.juxbox.TileEntityJux;
import mods.juxs.lib.Reference;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlayStreamingSourceEvent;
import net.minecraftforge.common.MinecraftForge;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;
public class SongPacket extends JuxPacket{
        
    public SongPacket(String sound,String chan,String station, int x, int y, int z) throws IOException{
        super.setChannel(chan);
        super.writeStation(station);
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
    	String station=data.readUTF();
    	
        int x=data.readInt();
        int y=data.readInt();
        int z=data.readInt();
        if(packet.channel.equals(Reference.CHANNEL+"STOP")){
        	JuxsSoundManager.stop(x, y, z);
        }
        else{
        ((TileEntityJux)ModLoader.getMinecraftInstance().theWorld.getBlockTileEntity(x, y, z)).currStation=station;
        String s=data.readUTF();       
        JuxsSoundManager.playSong(x,y,z,s);
        }
    }

}