package org.winterblade.thaumicstages.compat;

import net.darkhax.gamestages.event.GameStageEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchEntry;

import java.util.*;

public class StagedResearchManager {
    private static final Map<String, List<String>> StagedHiddenResearches = new HashMap<>();

    /**
     * Registers a research that is hidden solely due to staging.
     * @param research The research
     * @param stage    The stage
     */
    public static void registerStagedHidden(String research, String stage) {
        if(!StagedHiddenResearches.containsKey(stage)) {
            StagedHiddenResearches.put(stage, new LinkedList<>());
        }

        StagedHiddenResearches.get(stage).add(research);
    }

    private static void toggleResearches(String stage, boolean toggle) {
        if(!StagedHiddenResearches.containsKey(stage) || FMLCommonHandler.instance().getSide() != Side.CLIENT) {
            return;
        }

        for (String research : StagedHiddenResearches.get(stage)) {
            ResearchEntry entry = ResearchCategories.getResearch(research);

            if (toggle) {
                entry.setMeta(
                    Arrays.stream(entry.getMeta())
                        .filter(e -> !e.equals(ResearchEntry.EnumResearchMeta.HIDDEN))
                        .toArray(ResearchEntry.EnumResearchMeta[]::new)
                );
            } else {
                if (entry.hasMeta(ResearchEntry.EnumResearchMeta.HIDDEN)) continue;

                ArrayList<ResearchEntry.EnumResearchMeta> meta = new ArrayList<>(Arrays.asList(entry.getMeta()));
                meta.add(ResearchEntry.EnumResearchMeta.HIDDEN);
                entry.setMeta(meta.toArray(new ResearchEntry.EnumResearchMeta[0]));
            }
        }
    }

    @SubscribeEvent
    public void stageAdded(GameStageEvent.Added evt) {
        IPlayerKnowledge know = ThaumcraftCapabilities.getKnowledge(evt.getEntityPlayer());
        know.addResearch("!stage"+evt.getStageName());
        toggleResearches(evt.getStageName(), true);
    }

    @SubscribeEvent
    public void stageRemoved(GameStageEvent.Removed evt) {
        IPlayerKnowledge know = ThaumcraftCapabilities.getKnowledge(evt.getEntityPlayer());
        know.removeResearch("!stage"+evt.getStageName());
        toggleResearches(evt.getStageName(), false);
    }
}
