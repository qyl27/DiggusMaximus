package net.kyrptonaught.diggusmaximus.config;

import com.mojang.datafixers.util.Either;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ConfigHelper {
    public static Screen getConfigScreen(Screen parent) {
        return AutoConfig.getConfigScreen(ModConfig.class, parent).get();
    }

    public static void showConfigScreen(Minecraft mc) {
        var parent = mc.screen;
        var screen = getConfigScreen(parent);
        mc.setScreen(screen);
    }

    public static void registerConfig() {
        AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
    }

    public static ModConfig getConfig() {
        return AutoConfig.getConfigHolder(ModConfig.class).get();
    }

    public static Either<ResourceKey<Block>, TagKey<Block>> parseBlockOrTag(String s) {
        if (s.startsWith("#")) {
            var rl = ResourceLocation.parse(s.substring(1));
            var key = TagKey.create(Registries.BLOCK, rl);
            return Either.right(key);
        } else {
            var rl = ResourceLocation.parse(s);
            var rk = ResourceKey.create(Registries.BLOCK, rl);
            return Either.left(rk);
        }
    }
}
