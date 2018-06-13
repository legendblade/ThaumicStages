package org.winterblade.thaumicstages.compat;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import thaumcraft.api.crafting.IDustTrigger;

public class StagedDustTrigger implements IDustTrigger {
    private final IDustTrigger base;
    private final String stage;
    private final String error;

    public StagedDustTrigger(IDustTrigger base, String stage, String error) {
        this.base = base;
        this.stage = stage;
        this.error = error;
    }

    @Override
    public Placement getValidFace(World world, EntityPlayer entityPlayer, BlockPos blockPos, EnumFacing enumFacing) {
        if (GameStageHelper.hasStage(entityPlayer, stage)) return base.getValidFace(world, entityPlayer, blockPos, enumFacing);

        // If we have an error, send it to the player:
        if(error != null && world.isRemote) {
            entityPlayer.sendStatusMessage(new TextComponentString(error), false);
        }

        return null;
    }

    @Override
    public void execute(World world, EntityPlayer entityPlayer, BlockPos blockPos, Placement placement, EnumFacing enumFacing) {
        // Do the thing:
        base.execute(world, entityPlayer, blockPos, placement, enumFacing);
    }
}
