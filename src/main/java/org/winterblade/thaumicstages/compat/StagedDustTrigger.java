package org.winterblade.thaumicstages.compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.SidedProxy;
import thaumcraft.api.crafting.IDustTrigger;

public class StagedDustTrigger implements IDustTrigger {
    private final IDustTrigger base;
    private final String stage;
    private final String error;

    @SidedProxy(
        serverSide = "org.winterblade.thaumicstages.compat.GameStageProxy",
        clientSide = "org.winterblade.thaumicstages.compat.GameStageClientProxy",
        modId = "thaumicstages"
    )
    private static GameStageProxy proxy;

    public StagedDustTrigger(IDustTrigger base, String stage, String error) {
        this.base = base;
        this.stage = stage;
        this.error = error;
    }

    @Override
    public Placement getValidFace(World world, EntityPlayer entityPlayer, BlockPos blockPos, EnumFacing enumFacing) {
        Placement result = base.getValidFace(world, entityPlayer, blockPos, enumFacing);
        if (result == null || proxy.hasStage(entityPlayer, stage)) return result;

        // If we have an error, send it to the player:
        if(error != null && !world.isRemote) {
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
