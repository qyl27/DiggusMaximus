package net.kyrptonaught.diggusmaximus.mixin;

import net.kyrptonaught.diggusmaximus.DiggingPlayerEntity;
import net.kyrptonaught.diggusmaximus.DiggusMaximusMod;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class MixinCancelDurability {

    @Inject(method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/server/level/ServerPlayer;Ljava/util/function/Consumer;)V", at = @At(value = "HEAD"), cancellable = true)
    private void beforeHurtAndBreak(int amount, ServerLevel world, ServerPlayer player,
                                    Consumer<Item> breakCallback, CallbackInfo ci) {
        if (player != null
                && ((DiggingPlayerEntity) player).diggus$isExcavating()
                && !ConfigHelper.getConfig().config.toolDurability) {
            ci.cancel();
        }
    }
}
