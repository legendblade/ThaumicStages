package org.winterblade.thaumicstages.compat;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.EntityPlayer;

public class GameStageProxy {
    public boolean hasStage (EntityPlayer player, String stage) {
        return GameStageHelper.hasStage(player, stage);
    }
}

