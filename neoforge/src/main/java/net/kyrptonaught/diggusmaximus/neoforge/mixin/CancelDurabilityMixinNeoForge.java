package net.kyrptonaught.diggusmaximus.neoforge.mixin;

import net.kyrptonaught.diggusmaximus.bridge.PlayerEntityBridge;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class CancelDurabilityMixinNeoForge {

    @Inject(method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V", at = @At(value = "HEAD"), cancellable = true)
    private void beforeHurtAndBreak(int damage, ServerLevel level, LivingEntity entity, Consumer<Item> consumer, CallbackInfo ci) {
        if (entity != null
                && ((PlayerEntityBridge) entity).diggus$isExcavating()
                && !ConfigHelper.getConfig().common.causeToolDamage) {
            ci.cancel();
        }
    }
}
