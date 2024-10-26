package net.kyrptonaught.diggusmaximus.mixin;

import net.kyrptonaught.diggusmaximus.DiggusMaximusMod;
import net.kyrptonaught.diggusmaximus.Excavate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayerGameMode.class)
public class MixinServerPlayerInteractionManager {

    @Shadow
    @Final
    protected ServerPlayer player;

    @Shadow
    protected ServerLevel level;

    @Redirect(method = "destroyAndAck", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayerGameMode;destroyBlock(Lnet/minecraft/core/BlockPos;)Z"))
    public boolean diggus$redirect$destroyAndAck$destroyBlock(ServerPlayerGameMode instance, BlockPos pos) {
        if (DiggusMaximusMod.getOptions().sneakToExcavate) {
            ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(level.getBlockState(pos).getBlock());
            boolean result = instance.destroyBlock(pos);
            if (result) {
                if (DiggusMaximusMod.getOptions().sneakToExcavate && player.isShiftKeyDown()) {
                    if (pos.closerToCenterThan(player.position(), 10)) {
                        new Excavate(pos, blockId, player, null).startExcavate(-1);
                    }
                }
            }
            return result;
        }
        return instance.destroyBlock(pos);
    }
}
