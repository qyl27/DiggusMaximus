package net.kyrptonaught.diggusmaximus.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kyrptonaught.diggusmaximus.DiggusMaximusClientMod;
import net.kyrptonaught.diggusmaximus.excavate.Shape;
import net.kyrptonaught.diggusmaximus.networking.ExcavateNetworking;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(MultiPlayerGameMode.class)
public abstract class MixinClientPlayerInteractionManager {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "destroyBlock", at = @At(value = "HEAD"))
    private void beforeDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        var config = ConfigHelper.getConfig();
        if (!config.config.enabled) {
            return;
        }

        {
            var pressed = config.config.invertActivation ^ DiggusMaximusClientMod.EXCAVATE.isDown();
            if (pressed) {
                ExcavateNetworking.sendExcavatePacket(pos, BuiltInRegistries.BLOCK.getKey(minecraft.level.getBlockState(pos).getBlock()), Shape.NONE, Direction.NORTH);
                return;
            }
        }

        if (config.shapes.enableShapes) {
            var pressed = config.config.invertActivation ^ DiggusMaximusClientMod.SHAPED.isDown();
            if (pressed) {
                var shape = config.shapes.selectedShape;
                var result = minecraft.player.pick(10, 0, false);
                if (result.getType() == HitResult.Type.BLOCK) {
                    var facing = ((BlockHitResult) result).getDirection();
                    ExcavateNetworking.sendExcavatePacket(pos, BuiltInRegistries.BLOCK.getKey(minecraft.level.getBlockState(pos).getBlock()), shape, facing);
                }
            }
        }
    }
}