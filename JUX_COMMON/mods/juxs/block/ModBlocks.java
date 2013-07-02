package mods.juxs.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import mods.juxs.Juxs;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModBlocks {
    //public static Block Jux;
    
    
    public static void init(){
        //Jux = new JuxBox(2451);
        
        GameRegistry.registerBlock(Juxs.juxBox, "JuxBox");
        LanguageRegistry.addName(Juxs.juxBox, "Jux Box");
        GameRegistry.registerTileEntity(TileEntityJux.class, "JuxBox");
        
        initBlockRecipes();
    }
    public static void initBlockRecipes(){
        GameRegistry.addRecipe(new ItemStack(Juxs.juxBox), new Object[]{"ioi","odo","ioi",'i',Item.ingotIron,'o',Item.ingotGold,'d',Item.diamond});
    }

}
