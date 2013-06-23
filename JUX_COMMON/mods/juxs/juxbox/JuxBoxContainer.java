package mods.juxs.juxbox;



import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import mods.juxs.block.TileEntityJux;

public class JuxBoxContainer extends Container{
	private TileEntityJux tileEntity;
	public JuxBoxContainer (InventoryPlayer inventoryPlayer, TileEntityJux te){
        tileEntity = te;

        //the Slot constructor takes the IInventory and the slot number in that it binds to
        //and the x-y coordinates it resides on-screen
        //for (int i = 0; i < 3; i++) {
        //       for (int j = 0; j < 3; j++) {
        //                addSlotToContainer(new Slot(tileEntity, j + i * 3, 62 + j * 18, 17 + i * 18));
        //        }
        //}

        //commonly used vanilla code that adds the player's inventory
        bindPlayerInventory(inventoryPlayer);
	}
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                        addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                                        8 + j * 18, 84 + i * 18));
                }
        }

        for (int i = 0; i < 9; i++) {
                addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
	}
	@Override
    public boolean canInteractWith(EntityPlayer player) {
            return tileEntity.isUseableByPlayer(player);
    }

}
