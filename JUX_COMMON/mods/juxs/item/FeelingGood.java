package mods.juxs.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemRecord;

public class FeelingGood extends ItemRecord{

    public FeelingGood(int i) {
        super(i, "mods.Juxs.sound.feeling_good");
        setUnlocalizedName("Feeling_Good");
    }
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {

        itemIcon = iconRegister.registerIcon("Juxs:juxbox");
    }
}
