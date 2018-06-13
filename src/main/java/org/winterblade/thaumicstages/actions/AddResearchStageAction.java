package org.winterblade.thaumicstages.actions;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import org.winterblade.thaumicstages.compat.StagedResearchManager;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchEntry;

import java.util.ArrayList;
import java.util.Arrays;

public class AddResearchStageAction implements IAction {
    private final String stage;
    private final String research;
    private final boolean autoUnlock;

    public AddResearchStageAction(String stage, String research, boolean autoUnlock) {
        this.stage = stage;
        this.research = research;
        this.autoUnlock = autoUnlock;
    }


    @Override
    public void apply() {
        ResearchEntry entry = ResearchCategories.getResearch(research);

        if (entry == null) {
            CraftTweakerAPI.logInfo(String.format("Unable to find research '%s' to stage.", research));
            return;
        }

        // Update the research's parent with a fake stage entry
        ArrayList<String> list = new ArrayList<>(Arrays.asList(entry.getParents()));
        list.add("!stage"+stage);
        entry.setParents(list.toArray(new String[0]));

        // Add in the metas:
        ArrayList<ResearchEntry.EnumResearchMeta> meta = new ArrayList<>(Arrays.asList(entry.getMeta()));

        if(!entry.hasMeta(ResearchEntry.EnumResearchMeta.HIDDEN)) {
            meta.add(ResearchEntry.EnumResearchMeta.HIDDEN);

            StagedResearchManager.registerStagedHidden(research, stage);
        }

        if (autoUnlock && !entry.hasMeta(ResearchEntry.EnumResearchMeta.AUTOUNLOCK)) {
            meta.add(ResearchEntry.EnumResearchMeta.AUTOUNLOCK);
        }

        entry.setMeta(meta.toArray(new ResearchEntry.EnumResearchMeta[0]));
    }

    @Override
    public String describe() {
        return String.format("Adding '%s' to stage '%s'.", research, stage);
    }
}
