package net.kyrptonaught.diggusmaximus.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.kyrptonaught.diggusmaximus.bridge.PlayerEntityBridge;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Block.class)
public class MixinBlock {
    @WrapOperation(method = "playerDestroy", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V"))
    private void diggus$wrapOperation$playerDestroy$causeFoodExhaustion(Player instance, float amount, Operation<Void> original) {
        if (!((PlayerEntityBridge) instance).diggus$isExcavating()) {
            original.call(instance, amount);
            return;
        }
        var options = ConfigHelper.getConfig().common;
        if (!options.causePlayerExhaustion) {
            return;
        }
        original.call(instance, amount * options.exhaustionMultiplier);
    }
}
