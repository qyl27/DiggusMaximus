package net.kyrptonaught.diggusmaximus.config;

import com.mojang.datafixers.util.Either;
import lombok.SneakyThrows;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigHelper.class);

    public static Screen getConfigScreen(Screen parent) {
        return AutoConfig.getConfigScreen(ModConfig.class, parent).get();
    }

    public static void showConfigScreen(Minecraft mc) {
        var parent = mc.screen;
        var screen = getConfigScreen(parent);
        mc.setScreen(screen);
    }

    public static void registerConfig() {
        registerConfig(true);
    }

    @SneakyThrows
    private static void registerConfig(boolean force) {
        try {
            AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(Toml4jConfigSerializer::new));
        } catch (Exception ex) {
            LOGGER.warn("Couldn't read config file", ex);
            Files.move(getConfigFile("config_common.toml"), getConfigFile("config_common.toml.bak"));
            Files.move(getConfigFile("config_client.toml"), getConfigFile("config_client.toml.bak"));
            if (force) {
                LOGGER.warn("Backup and delete it (to *.bak), lets try again");
                registerConfig(false);
            }
        }
    }

    private static Path getConfigFile(String name) {
        return Path.of("config", "diggusmaximus", name);
    }

    private static ConfigHolder<ModConfig> holder;

    private static ConfigHolder<ModConfig> getHolder() {
        if (holder == null) {
            holder = AutoConfig.getConfigHolder(ModConfig.class);
            holder.registerSaveListener((holder, config) -> {
                config.update();
                return InteractionResult.SUCCESS;
            });
            holder.registerLoadListener((holder, config) -> {
                config.update();
                return InteractionResult.SUCCESS;
            });
        }
        return holder;
    }

    public static ModConfig getConfig() {
        return getHolder().get();
    }

    public static void save() {
        getHolder().save();
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

    public static Either<ResourceKey<Item>, TagKey<Item>> parseItemOrTag(String s) {
        if (s.startsWith("#")) {
            var rl = ResourceLocation.parse(s.substring(1));
            var key = TagKey.create(Registries.ITEM, rl);
            return Either.right(key);
        } else {
            var rl = ResourceLocation.parse(s);
            var rk = ResourceKey.create(Registries.ITEM, rl);
            return Either.left(rk);
        }
    }
}
