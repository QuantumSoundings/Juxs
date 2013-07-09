package mods.juxs;

import mods.juxs.item.SprintDude;
import mods.juxs.item.SprintDudeContainer;
import mods.juxs.item.SprintDudeGUI;
import mods.juxs.juxbox.JuxBoxContainer;
import mods.juxs.juxbox.JuxBoxGUI;
import mods.juxs.juxbox.TileEntityJux;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
        //returns an instance of the Container you made earlier
        @Override
        public Object getServerGuiElement(int id, EntityPlayer player, World world,
                        int x, int y, int z) {
        	if(id==0){
                TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
                if(tileEntity instanceof TileEntityJux){
                        return new JuxBoxContainer(player.inventory, (TileEntityJux) tileEntity);
                }
                return null;
        	}
        	else if(id==1){
        		
                return new SprintDudeContainer(player.inventory);
                
        	}
        	return null;
        
        }

        //returns an instance of the Gui you made earlier
        @Override
        public Object getClientGuiElement(int id, EntityPlayer player, World world,
                        int x, int y, int z) {
        	if(id==0){
                TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
                if(tileEntity instanceof TileEntityJux){
                        return new JuxBoxGUI(player.inventory, (TileEntityJux) tileEntity);
                }
                return null;
        	}
        	else if(id==1){
        		
                return new SprintDudeGUI(player.inventory,((SprintDude)player.getCurrentEquippedItem().getItem()),(int)player.posX,(int)player.posY,(int)player.posZ);
                
        	}
        	return null;
			
        }
}