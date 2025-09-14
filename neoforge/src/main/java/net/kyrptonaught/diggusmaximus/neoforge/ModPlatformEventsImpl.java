package net.kyrptonaught.diggusmaximus.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ModPlatformEventsImpl {
    public static boolean beforePlayerBreakBlock(Level level, Player player,
                                                           BlockState state, BlockPos pos) {
        // No need workaround for NeoForge.
        return true;
    }
}
