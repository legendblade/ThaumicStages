package org.winterblade.thaumicstages.handlers;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.oredict.IOreDictEntry;
import org.winterblade.thaumicstages.actions.AddOreDustStageAction;
import org.winterblade.thaumicstages.actions.AddSimpleDustStageAction;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ModOnly("thaumcraft")
@ZenRegister
@ZenClass("mods.thaumcraft.stages.DustStages")
public class DustStages {
    @ZenMethod
    public static void addStage(String stage, IItemStack block, @Optional String error) {
        CraftTweakerAPI.apply(new AddSimpleDustStageAction(stage, block, error));
    }

    @ZenMethod
    public static void addStage(String stage, IOreDictEntry ore, @Optional String error) {
        CraftTweakerAPI.apply(new AddOreDustStageAction(stage, ore, error));
    }
}
