package net.kyrptonaught.diggusmaximus.mixin;

import net.kyrptonaught.diggusmaximus.ModClientNetworking;
import net.kyrptonaught.diggusmaximus.client.DiggusMaximusClient;
import net.kyrptonaught.diggusmaximus.excavate.Shape;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.kyrptonaught.diggusmaximus.networking.ExcavatePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class MixinClientPlayerInteractionManager {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "destroyBlock", at = @At(value = "HEAD"))
    private void beforeDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        var config = ConfigHelper.getConfig();
        if (!config.common.enabled) {
            return;
        }

        var player = minecraft.player;
        if (player == null) {
            return;
        }

        var pressed = config.client.invertActivation ^ DiggusMaximusClient.EXCAVATE.isDown();
        if (!pressed) {
            return;
        }

        var shape = config.client.selectedShape;
        var hitFace = Direction.NORTH;
        if (shape != Shape.NONE) {
            var result = player.pick(10, 0, false);
            if (result.getType() != HitResult.Type.BLOCK) {
                return;
            }
            hitFace = ((BlockHitResult) result).getDirection();
        }

        var packet = new ExcavatePacket(pos, BuiltInRegistries.BLOCK.getKey(minecraft.level.getBlockState(pos).getBlock()), shape, hitFace);
        ModClientNetworking.sendExcavatePacket(packet);
    }
}
