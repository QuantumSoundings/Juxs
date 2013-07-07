package mods.juxs;

import mods.juxs.client.audio.JuxsSoundManager;
import mods.juxs.core.radio.RadioInit;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent.Unload;

public class WorldHandler{
	@ForgeSubscribe
	public void onWorldUnload(Unload event ) {
		RadioInit.clearBoxes();
		System.out.println("I GOT CALLED WORLD UNLOADED");
		JuxsSoundManager.stopAll();
	}

}
