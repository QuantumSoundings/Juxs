package mods.juxs.item;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.juxs.Juxs;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FeelingGood extends Item{

    public FeelingGood(int i) {
        super(i);
        setUnlocalizedName("Feeling_Good");
        this.setCreativeTab(Juxs.juxTab);
    }
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {

        itemIcon = iconRegister.registerIcon("Juxs:juxbox");
    }
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer par2EntityPlayer)   
    {
        //world.pl(par2EntityPlayer, par2, par3, par4, par5, par6)(par2EntityPlayer, "11 Hipster Cutthroat.wav", 1, 1);
        System.out.println(FMLCommonHandler.instance().getEffectiveSide()+"");
        return par1ItemStack;
    }
}
