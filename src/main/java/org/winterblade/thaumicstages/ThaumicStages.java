package org.winterblade.thaumicstages;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ThaumicStages.MODID, name = ThaumicStages.NAME, version = ThaumicStages.VERSION)
public class ThaumicStages {
    public static final String MODID = "thaumicstages";
    public static final String NAME = "Thaumic Stages";
    public static final String VERSION = "0.1";

    private static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        logger = event.getModLog();
    }
}
