package org.winterblade.thaumicstages.actions;

import com.google.common.collect.Lists;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchEntry;

import java.util.ArrayList;
import java.util.Arrays;

public class AddResearchStageAction implements IAction {
    private final String stage;
    private final String research;

    public AddResearchStageAction(String stage, String research) {
        this.stage = stage;
        this.research = research;
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
    }

    @Override
    public String describe() {
        return String.format("Adding '%s' to stage '%s'.", research, stage);
    }
}
