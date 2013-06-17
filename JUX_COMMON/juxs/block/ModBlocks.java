package juxs.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ModBlocks {
    public static Block Jux;
    
    
    public static void init(){
        Jux = new JuxBox(2451);
        
        GameRegistry.registerBlock(Jux, "JuxBox");
        LanguageRegistry.addName(Jux, "Jux Box");
        
        initBlockRecipies();
    }
    public static void initBlockRecipies(){
        GameRegistry.addRecipe(new ItemStack(Jux), new Object[]{"iii","iii","iii",Character.valueOf('i'),Block.dirt});
    }

}
