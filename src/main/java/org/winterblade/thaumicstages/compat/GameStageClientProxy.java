package org.winterblade.thaumicstages.compat;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.EntityPlayer;

public class GameStageClientProxy extends GameStageProxy {
    @Override
    public boolean hasStage(EntityPlayer player, String stage) {
        return GameStageHelper.clientHasStage(player, stage);
    }
}
