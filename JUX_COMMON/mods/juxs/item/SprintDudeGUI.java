package mods.juxs.item;


import java.io.IOException;
import java.util.ArrayList;

import mods.juxs.client.audio.JuxsSoundManager;
import mods.juxs.core.radio.Location;
import mods.juxs.core.radio.RadioInit;
import mods.juxs.lib.Reference;
import mods.juxs.network.RadioUpdatePacket;
import mods.juxs.network.RequestPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class SprintDudeGUI extends GuiContainer {
		ArrayList<GuiButton> controlList= new ArrayList<GuiButton>();
		SprintDude dude;
		int x,y,z;
        public SprintDudeGUI (InventoryPlayer inventoryPlayer,SprintDude sprintDude,int xc,int yc,int zc) {
                //the container is instanciated and passed to the superclass for handling
        	super(new SprintDudeContainer(inventoryPlayer));
        	x=xc;
        	y=yc;
        	z=zc;       
            dude=sprintDude;
        }
        public void initGui(){
        	super.initGui();
                                         //id, x, y, width, height, text
            buttonList.add(new GuiButton(1, ((width) /2)  , height/2-55, 80, 20, "Next PlayList"));
            buttonList.add(new GuiButton(2, ((width)/2)-80, height/2-55, 80, 20, "Prev PlayList"));
            buttonList.add(new GuiButton(3, ((width)/2)   , height/2-35, 80, 20, "Next Track"));
            buttonList.add(new GuiButton(4, ((width)/2)-80, height/2-35, 80, 20, "Prev Track"));
            
        }
        @Override
        protected void actionPerformed(GuiButton guibutton) {
        	switch(guibutton.id){
        		case 1:{
        			JuxsSoundManager.stopPersonal();
        			dude.currPlayList=RadioInit.getNextStation(dude.currPlayList);
        			dude.currSong=RadioInit.getStation(dude.currPlayList).songs.get(0);
        			dude.curr=0;
        			dude.ticks=0;
        			JuxsSoundManager.playPersonal(x, y, z, dude.currSong,dude.currPlayList);
        			break;
        		}
        		case 2:{
        			JuxsSoundManager.stopPersonal();
        			dude.currPlayList=RadioInit.getPrevStation(dude.currPlayList);
        			dude.currSong=RadioInit.getStation(dude.currPlayList).songs.get(0);
        			dude.curr=0;
        			dude.ticks=0;
        			JuxsSoundManager.playPersonal(x, y, z, dude.currSong,dude.currPlayList);
        			break;
        		}
        		case 3:{
        			JuxsSoundManager.stopPersonal();
        			dude.currSong=RadioInit.getStation(dude.currPlayList).nextSong(dude.curr);
        			dude.curr=RadioInit.getStation(dude.currPlayList).songs.indexOf(dude.currSong);
        			dude.ticks=0;
        			JuxsSoundManager.playPersonal(x, y, z, dude.currSong,dude.currPlayList);
        			break;
        		}
        		case 4:{
        			JuxsSoundManager.stopPersonal();
        			dude.currSong=RadioInit.getStation(dude.currPlayList).prevSong(dude.curr);
        			dude.curr=RadioInit.getStation(dude.currPlayList).songs.indexOf(dude.currSong);
        			dude.ticks=0;
        			JuxsSoundManager.playPersonal(x, y, z, dude.currSong,dude.currPlayList);
        			break;
        		}
        	
        	}
        	this.updateScreen();
            
    }
        @Override
        protected void drawGuiContainerForegroundLayer(int param1, int param2) {
                //draw text and stuff here
                //the parameters for drawString are: string, x, y, color
                fontRenderer.drawString("Current playlist: "+dude.currPlayList, 8, 6, 4210752);
                fontRenderer.drawString(dude.currSong.substring(0,dude.currSong.length()-4),8,18,4210752);
                fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float par1, int par2,
                        int par3) {
                //draw your Gui here, only thing you need to change is the path
        	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        	this.mc.func_110434_K().func_110577_a(new net.minecraft.util.ResourceLocation("juxs","/textures/gui/juxbox_gui.png"));
            int x = (width - xSize) / 2;
            int y = (height - ySize) / 2;
            drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        }

}
