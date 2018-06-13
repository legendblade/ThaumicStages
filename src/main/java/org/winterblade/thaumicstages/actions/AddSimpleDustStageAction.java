package org.winterblade.thaumicstages.actions;

import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.ReflectionHelper;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import net.minecraft.block.Block;
import org.winterblade.thaumicstages.compat.StagedDustTrigger;
import thaumcraft.api.crafting.IDustTrigger;
import thaumcraft.common.lib.crafting.DustTriggerSimple;

import java.util.ArrayList;

public class AddSimpleDustStageAction implements IAction {
    private final String stage;
    private final Block block;
    private final String error;

    public AddSimpleDustStageAction(String stage, IItemStack stack, String error) {
        this.stage = stage;
        this.block = Block.getBlockFromItem(InputHelper.toStack(stack).getItem());
        this.error = error;
    }

    @Override
    public void apply() {
        ArrayList<IDustTrigger> triggers = IDustTrigger.triggers;

        for (int i = 0; i < triggers.size(); i++) {
            IDustTrigger trigger = triggers.get(i);
            if (!(trigger instanceof DustTriggerSimple)) continue;
            DustTriggerSimple simple = (DustTriggerSimple) trigger;

            // Could I probably AT this... yes.  But this runs in init.
            Block triggerBlock = ReflectionHelper.getObject(simple, "target");

            if (!triggerBlock.equals(block)) continue;

            // Do the replacement:
            IDustTrigger.triggers.set(i, new StagedDustTrigger(simple, stage, error));
            break;
        }
    }

    @Override
    public String describe() {
        return String.format("Adding dust transformation on '%s' to stage '%s'.", block.getUnlocalizedName(), stage);
    }
}
