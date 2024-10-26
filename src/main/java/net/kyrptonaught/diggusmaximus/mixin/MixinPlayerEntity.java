package net.kyrptonaught.diggusmaximus.mixin;

import net.kyrptonaught.diggusmaximus.DiggingPlayerEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Player.class)
public class MixinPlayerEntity implements DiggingPlayerEntity {

    @Unique
    private boolean diggus$isExcavating = false;

    @Override
    public Boolean diggus$isExcavating() {
        return diggus$isExcavating;
    }

    @Override
    public void diggus$setExcavating(boolean isExcavating) {
        this.diggus$isExcavating = isExcavating;
    }
}