package mods.juxs.core.radio;

public class Location implements Comparable{
    public int x;
    public int y;
    public int z;
    public Location(int a,int b, int c){
        x=a;y=b;z=c;
    }
    public static boolean within(int radius,Location player,Location box){
    	if(Math.abs(player.x-box.x-0.0)<=20)
    		if(Math.abs(player.y-box.y-0.0)<=20)
    			if(Math.abs(player.z-box.z-0.0)<=20)
    				return true;
    	return false;
    }
    public boolean compareTo(Location a,Location b){
    	if(a.x==b.x&&a.y==b.y&&a.z==b.z)
    		return true;
    	return false;
    }
	@Override
	public int compareTo(Object arg) {
		Location b= (Location)arg;
		if(this.x==b.x&&this.y==b.y&&this.z==b.z)
    		return 0;
    	return 1;
	}

}
