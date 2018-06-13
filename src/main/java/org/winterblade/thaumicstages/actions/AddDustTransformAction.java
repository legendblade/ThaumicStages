package org.winterblade.thaumicstages.actions;

import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import thaumcraft.api.crafting.IDustTrigger;

public class AddDustTransformAction implements IAction {


    private final IDustTrigger trigger;
    private final IIngredient block;
    private final IItemStack result;

    public AddDustTransformAction(IDustTrigger trigger, IIngredient block, IItemStack result) {
        this.trigger = trigger;
        this.block = block;
        this.result = result;
    }

    @Override
    public void apply() {
        IDustTrigger.registerDustTrigger(trigger);
    }

    @Override
    public String describe() {
        return String.format(
            "Adding dust trigger to turn '%s' into '%s'.",
            block.toCommandString(),
            result.toCommandString()
        );
    }
}
