package org.winterblade.thaumicstages.actions;

import com.blamejared.mtlib.helpers.ReflectionHelper;
import crafttweaker.IAction;
import crafttweaker.api.oredict.IOreDictEntry;
import org.winterblade.thaumicstages.compat.StagedDustTrigger;
import thaumcraft.api.crafting.IDustTrigger;
import thaumcraft.common.lib.crafting.DustTriggerOre;

import java.util.ArrayList;

public class AddOreDustStageAction implements IAction {
    private final String stage;
    private final String ore;
    private final String error;

    public AddOreDustStageAction(String stage, IOreDictEntry stack, String error) {
        this.stage = stage;
        this.ore = stack.getName();
        this.error = error;
    }

    @Override
    public void apply() {
        ArrayList<IDustTrigger> triggers = IDustTrigger.triggers;

        for (int i = 0; i < triggers.size(); i++) {
            IDustTrigger trigger = triggers.get(i);
            if (!(trigger instanceof DustTriggerOre)) continue;
            DustTriggerOre base = (DustTriggerOre) trigger;

            String triggerOre = ReflectionHelper.getObject(base, "target");
            if (!triggerOre.equals(ore)) continue;

            // Do the replacement:
            IDustTrigger.triggers.set(i, new StagedDustTrigger(base, stage, error));
            break;
        }
    }

    @Override
    public String describe() {
        return String.format("Adding dust transformation on '<ore:%s>' to stage '%s'.", ore, stage);
    }
}
