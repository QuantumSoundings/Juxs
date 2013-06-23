package mods.juxs;

import mods.juxs.block.TileEntityJux;
import mods.juxs.juxbox.JuxBoxContainer;
import mods.juxs.juxbox.JuxBoxGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
        //returns an instance of the Container you made earlier
        @Override
        public Object getServerGuiElement(int id, EntityPlayer player, World world,
                        int x, int y, int z) {
                TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
                if(tileEntity instanceof TileEntityJux){
                        return new JuxBoxContainer(player.inventory, (TileEntityJux) tileEntity);
                }
                return null;
        }

        //returns an instance of the Gui you made earlier
        @Override
        public Object getClientGuiElement(int id, EntityPlayer player, World world,
                        int x, int y, int z) {
                TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
                if(tileEntity instanceof TileEntityJux){
                        return new JuxBoxGUI(player.inventory, (TileEntityJux) tileEntity);
                }
                return null;

        }
}