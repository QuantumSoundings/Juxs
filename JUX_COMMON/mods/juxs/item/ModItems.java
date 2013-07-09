package mods.juxs.item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;

public class ModItems {
    public static Item feelingGood;
    public static Item sprintDude;
    public static void init(){
        feelingGood= new FeelingGood(4444);
        sprintDude= new SprintDude(4445);
        GameRegistry.registerItem(feelingGood, "FeelingGood");
        LanguageRegistry.addName(feelingGood,"Michael Buble- Feeling Good");
        GameRegistry.registerItem(sprintDude, "Sprint Dude");
        LanguageRegistry.addName(sprintDude, "Sprint Dude");
        //ItemRecord fg= new ItemRecord(4444,"mods/Juxs/sound/feeling_good.ogg");
        
    }
}
