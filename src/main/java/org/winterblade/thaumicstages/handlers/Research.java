package org.winterblade.thaumicstages.handlers;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import org.winterblade.thaumicstages.ThaumicStages;
import org.winterblade.thaumicstages.actions.AddResearchStageAction;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("thaumcraft")
@ZenRegister
@ZenClass("mods.thaumcraft.research.ResearchStages")
public class Research {
    @ZenMethod
    public static void addStage(String stage, String research) {
        ThaumicStages.LATE_ADDITIONS.add(new AddResearchStageAction(stage, research));
    }
}
