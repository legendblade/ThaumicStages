package org.winterblade.thaumicstages.handlers;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.block.Block;
import org.winterblade.thaumicstages.actions.AddOreDustStageAction;
import org.winterblade.thaumicstages.actions.AddSimpleDustStageAction;
import org.winterblade.thaumicstages.actions.AddDustTransformAction;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.common.lib.crafting.DustTriggerOre;
import thaumcraft.common.lib.crafting.DustTriggerSimple;

@ModOnly("thaumcraft")
@ZenRegister
@ZenClass("mods.thaumcraft.Dust")
public class DustStages {
    /**
     * Adds a stage to the given dust block transform
     * @param stage The stage to add
     * @param block The block to check for
     * @param error The error message to give, if any
     */
    @ZenMethod
    public static void addStage(String stage, IItemStack block, @Optional String error) {
        CraftTweakerAPI.apply(new AddSimpleDustStageAction(stage, block, error));
    }

    /**
     * Adds a stage to the given dust ore dictionary transform
     * @param stage The stage to add
     * @param ore   The ore dict entry to add it to
     * @param error The error message to give, if any
     */
    @ZenMethod
    public static void addStage(String stage, IOreDictEntry ore, @Optional String error) {
        CraftTweakerAPI.apply(new AddOreDustStageAction(stage, ore, error));
    }

    /**
     * Creates a dust transform for the given block into another item or block
     * @param research The research to require to perform the action
     * @param block    The block to transform
     * @param result   The output of the transform; if result has a block, it will be placed in world
     */
    @ZenMethod
    public static void addTransform(String research, IItemStack block, IItemStack result) {
        CraftTweakerAPI.apply(
            new AddDustTransformAction(
                new DustTriggerSimple(
                    research,
                    Block.getBlockFromItem(InputHelper.toStack(block).getItem()),
                    InputHelper.toStack(result)
                ),
                block,
                result
            )
        );
    }

    /**
     * Creates a dust transform for the given ore dictionary into another item or block
     * @param research The research to require to perform the action
     * @param ore      The ore dictionary to transform
     * @param result   The output of the transform; if result has a block, it will be placed in world
     */
    @ZenMethod
    public static void addTransform(String research, IOreDictEntry ore, IItemStack result) {
        CraftTweakerAPI.apply(
            new AddDustTransformAction(
                new DustTriggerOre(
                        research,
                        ore.getName(),
                        InputHelper.toStack(result)
                ),
                ore,
                result
            )
        );
    }
}
