package mods.juxs.juxbox;


import java.util.ArrayList;

import mods.juxs.block.TileEntityJux;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class JuxBoxGUI extends GuiContainer {
		ArrayList<GuiButton> controlList= new ArrayList<GuiButton>();
        public JuxBoxGUI (InventoryPlayer inventoryPlayer,
                        TileEntityJux tileEntity) {
                //the container is instanciated and passed to the superclass for handling
                super(new JuxBoxContainer(inventoryPlayer, tileEntity));
        }
        public void initGui(){
        	super.initGui();
            //make buttons
                                    //id, x, y, width, height, text
            buttonList.add(new GuiButton(1, (width) / 2, 52, 80, 20, "Next Station"));
            buttonList.add(new GuiButton(2, (width) / 2, 72, 80, 20, "Prev Station"));
        }
        protected void actionPerformed(GuiButton guibutton) {
        	switch(guibutton.id){
        		case 1://radiostation change code here
        		case 2://radioStation change code here
        	}
        	
            
    }
        @Override
        protected void drawGuiContainerForegroundLayer(int param1, int param2) {
                //draw text and stuff here
                //the parameters for drawString are: string, x, y, color
                fontRenderer.drawString("JuxBox", 8, 6, 4210752);
                fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float par1, int par2,
                        int par3) {
                //draw your Gui here, only thing you need to change is the path
        	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.renderEngine.bindTexture("/mods/juxs/textures/gui/juxbox_gui.png");
            int x = (width - xSize) / 2;
            int y = (height - ySize) / 2;
            drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        }

}
