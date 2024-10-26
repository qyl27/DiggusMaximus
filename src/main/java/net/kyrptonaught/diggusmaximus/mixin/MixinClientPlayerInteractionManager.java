package net.kyrptonaught.diggusmaximus.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kyrptonaught.diggusmaximus.DiggusMaximusClientMod;
import net.kyrptonaught.diggusmaximus.DiggusMaximusMod;
import net.kyrptonaught.diggusmaximus.StartExcavatePacket;
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
        if (DiggusMaximusMod.getOptions().enabled
                && DiggusMaximusClientMod.getActivationKey().isKeybindPressed()) {
            diggus$activate(pos, null, -1);
        } else if (DiggusMaximusMod.getExcavatingShapes().enableShapes
                && DiggusMaximusClientMod.getShapeKey().isKeybindPressed()) {
            Direction facing = null;
            HitResult result = minecraft.player.pick(10, 0, false);
            if (result.getType() == HitResult.Type.BLOCK) {
                facing = ((BlockHitResult) result).getDirection();
            }
            int selection = DiggusMaximusMod.getExcavatingShapes().selectedShape.ordinal();
            diggus$activate(pos, facing, selection);
        }
    }

    @Unique
    private void diggus$activate(BlockPos pos, Direction facing, int shapeSelection) {
        StartExcavatePacket.sendExcavatePacket(pos, BuiltInRegistries.BLOCK.getKey(minecraft.level.getBlockState(pos).getBlock()), facing, shapeSelection);
    }
}