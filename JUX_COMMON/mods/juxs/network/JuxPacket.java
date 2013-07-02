package mods.juxs.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;

public class JuxPacket {
	Packet250CustomPayload packet;
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(bos);
	public JuxPacket(){
		packet= new Packet250CustomPayload();
		
	}
	public void writeXYZ(int x, int y, int z){
        try{
        	dos.writeInt(x);
        	dos.writeInt(y);
        	dos.writeInt(z);
        }catch(IOException e){e.printStackTrace();}
	}
	public void writeStation(String station){
		try{dos.writeUTF(station);
		}catch(IOException e){e.printStackTrace();}
	}
	public void writeString(String string){
		try{dos.writeUTF(string);
		}catch(IOException e){e.printStackTrace();}
	}
	public void writeTime(int time){
		try{dos.writeInt(time);
		}catch(IOException e){e.printStackTrace();}
	}
	public void setChannel(String chan){
		packet.channel= chan;
	}
	public void finalizeData(){
		packet.data=bos.toByteArray();
		packet.length=bos.size();
	}

}
