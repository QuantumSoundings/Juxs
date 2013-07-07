package mods.juxs.core.radio;

import java.io.IOException;
import java.util.ArrayList;

import mods.juxs.lib.Reference;
import mods.juxs.network.SongPacket;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class RadioStation {
	public String Name;
	public ArrayList<String> songs;
	public int curr,ticks=0;
	public boolean next;
	public RadioStation instance;
	public ArrayList<Location> boxes= new ArrayList<Location>();
	public RadioStation(String name){
		next=false;
		Name=name;
		curr=0;
		songs=new ArrayList<String>();
		instance=this;
	}
	public int timeRemaining(){
        return RadioInit.songs.get(songs.get(curr))-ticks;
    }
	public void addSong(String songName){
		songs.add(songName);
		System.out.println("[RadioStation]["+FMLCommonHandler.instance().getEffectiveSide().toString()+"] "+songName+" Added to "+Name);
	}
	public void addBox(Location a){
		boxes.add(a);
		if(boxes.size()==1)
			playNext();
		System.out.println("[RadioStation] Adding Box to "+Name);
	}
	public void removeBox(Location a){
		for(Location b:boxes){
            if(a.x==b.x&&a.y==b.y&&a.z==b.z){
                boxes.remove(b);
                break;
            }
        }
	}
	public String getPlaying(){
		return songs.get(curr);
	}
	public void next(){
    	next=true;
    	
    }
	public void onTick(){
		ticks++;
		//System.out.println(RadioInit.songs.toString()+" "+songs.toString()+(FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER));
		
        if(RadioInit.songs.get(songs.get(curr))==ticks||next){
            playNext();
            if(next)next=false;
        }
	}
	public void playNext(){
		curr= (curr+1)%songs.size();
        if(FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER)
            for(Location a :boxes){
                System.out.printf("Playing at %d %d %d",a.x,a.y,a.z);
                try {
                		new SongPacket(songs.get(curr),Reference.CHANNEL+"PLAY",this.Name,a.x,a.y,a.z);
				} catch (IOException e) {e.printStackTrace();}
            }
        ticks=0;
	}

}
