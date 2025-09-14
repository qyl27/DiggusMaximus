package net.kyrptonaught.diggusmaximus;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ModPlatformEvents {
    /**
     * Some mods use a hacky way to treat items as tool. <br />
     * ref: <a href="https://github.com/qyl27/DiggusMaximus/issues/7">this issue</a>
     * @return false for cancelled (also {@link net.minecraft.world.InteractionResult#FAIL})
     */
    @ExpectPlatform
    public static boolean beforePlayerBreakBlock(Level level, Player player,
                                                           BlockState state, BlockPos pos) {
        throw new AssertionError();
    }
}
