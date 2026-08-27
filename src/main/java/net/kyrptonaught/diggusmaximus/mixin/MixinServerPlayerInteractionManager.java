package net.kyrptonaught.diggusmaximus.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.kyrptonaught.diggusmaximus.excavate.Excavate;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.kyrptonaught.diggusmaximus.excavate.Shape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayerGameMode.class)
public class MixinServerPlayerInteractionManager {
    @Shadow
    @Final
    protected ServerPlayer player;

    @Shadow
    protected ServerLevel level;

    @WrapOperation(method = "destroyAndAck", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayerGameMode;destroyBlock(Lnet/minecraft/core/BlockPos;)Z"))
    public boolean diggus$wrapOperation$destroyAndAck$destroyBlock(ServerPlayerGameMode instance, BlockPos pos, Operation<Boolean> original) {
        if (ConfigHelper.getConfig().common.sneakToExcavate) {
            Identifier blockId = BuiltInRegistries.BLOCK.getKey(level.getBlockState(pos).getBlock());
            boolean result = original.call(instance, pos);
            if (result) {
                if (ConfigHelper.getConfig().common.sneakToExcavate && player.isShiftKeyDown()) {
                    if (pos.closerToCenterThan(player.position(), 10)) {
                        new Excavate(pos, blockId, player, Shape.NONE, Direction.NORTH).startExcavate();
                    }
                }
            }
            return result;
        }
        return original.call(instance, pos);
    }
}
