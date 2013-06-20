package juxs;

import java.io.IOException;
import java.util.EnumSet;

import juxs.core.radio.RadioInit;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class JuxsTickHandler implements ITickHandler{
    private EnumSet<TickType> tickSet;
    public static JuxsTickHandler instance;
    public static JuxsTickHandler getHandler(){return instance;}
    public JuxsTickHandler(EnumSet<TickType> type){
        tickSet=type;
        instance=this;
        
    }
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData){
        RadioInit.onTick();
       // else if(RadioInit.juxboxes.size()>0)
       //     RadioInit.startRadio();
        
    }
    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public EnumSet<TickType> ticks() {
        // TODO Auto-generated method stub
        return tickSet;
    }
    @Override
    public String getLabel() {
        return "JuxsTickServerStuff";
    }
    

  

}
