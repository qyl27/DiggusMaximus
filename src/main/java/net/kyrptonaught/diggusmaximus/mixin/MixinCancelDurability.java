package net.kyrptonaught.diggusmaximus.mixin;

import net.kyrptonaught.diggusmaximus.DiggingPlayerEntity;
import net.kyrptonaught.diggusmaximus.DiggusMaximusMod;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ItemStack.class)
public class MixinCancelDurability {

    @Inject(method = "damage(ILnet/minecraft/util/math/random/Random;Lnet/minecraft/server/network/ServerPlayerEntity;Ljava/lang/Runnable;)V", at = @At(value = "HEAD"), cancellable = true)
    private void DIGGUS$CANCELDURABILITY(int amount, Random random, ServerPlayerEntity player, Runnable breakCallback, CallbackInfo ci) {
        if (player != null
                && ((DiggingPlayerEntity) player).isExcavating()
                && !DiggusMaximusMod.getOptions().toolDurability) {
            ci.cancel();
        }
    }
}
