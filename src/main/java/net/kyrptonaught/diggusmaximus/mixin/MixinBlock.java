package net.kyrptonaught.diggusmaximus.mixin;

import net.kyrptonaught.diggusmaximus.DiggingPlayerEntity;
import net.kyrptonaught.diggusmaximus.DiggusMaximusMod;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Block.class)
public class MixinBlock {

    @Redirect(method = "playerDestroy", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V"))
    private void diggus$redirect$playerDestroy$causeFoodExhaustion(Player player, float exhaustion) {
        if (!((DiggingPlayerEntity) player).diggus$isExcavating()) {
            player.causeFoodExhaustion(exhaustion);
            return;
        }
        var options = ConfigHelper.getConfig().config;
        if (!options.playerExhaustion) {
            return;
        }
        player.causeFoodExhaustion(exhaustion * options.exhaustionMultiplier);
    }
}
