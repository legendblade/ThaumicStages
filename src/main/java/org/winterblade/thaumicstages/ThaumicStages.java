package org.winterblade.thaumicstages;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import net.darkhax.gamestages.event.GameStageEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

import java.util.LinkedList;
import java.util.List;

@Mod(
    modid = ThaumicStages.MODID,
    name = ThaumicStages.NAME,
    version = ThaumicStages.VERSION
)
public class ThaumicStages {
    public static final String MODID = "thaumicstages";
    public static final String NAME = "Thaumic Stages";
    public static final String VERSION = "0.1";

    public static Logger logger;

    public static final List<IAction> LATE_ADDITIONS = new LinkedList<>();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        logger = event.getModLog();
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        try {
            LATE_ADDITIONS.forEach(CraftTweakerAPI::apply);
        } catch(Exception e) {
            e.printStackTrace();
            CraftTweakerAPI.logError("Error while applying actions", e);
        }
    }

    @SubscribeEvent
    public void stageAdded(GameStageEvent.Added evt) {
        IPlayerKnowledge know = ThaumcraftCapabilities.getKnowledge(evt.getEntityPlayer());
        know.addResearch("!stage"+evt.getStageName());
    }

    @SubscribeEvent
    public void stageRemoved(GameStageEvent.Removed evt) {
        IPlayerKnowledge know = ThaumcraftCapabilities.getKnowledge(evt.getEntityPlayer());
        know.removeResearch("!stage"+evt.getStageName());
    }
}
