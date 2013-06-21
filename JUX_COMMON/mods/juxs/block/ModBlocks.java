package mods.juxs.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModBlocks {
    public static Block Jux;
    
    
    public static void init(){
        Jux = new JuxBox(2451);
        
        GameRegistry.registerBlock(Jux, "JuxBox");
        LanguageRegistry.addName(Jux, "Jux Box");
        
        initBlockRecipes();
    }
    public static void initBlockRecipes(){
        GameRegistry.addRecipe(new ItemStack(Jux), new Object[]{"ioi","odo","ioi",'i',Item.ingotIron,'o',Item.ingotGold,'d',Item.diamond});
    }

}
