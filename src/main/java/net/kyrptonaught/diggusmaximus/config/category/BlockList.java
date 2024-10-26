package net.kyrptonaught.diggusmaximus.config.category;

import com.mojang.datafixers.util.Either;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Config(name = "blacklist")
public class BlockList implements ConfigData {
    @Comment("Function as whitelist instead")
    public boolean isWhitelist = false;

    @Comment("Block IDs to blacklist from being mined")
    public List<String> blacklistedBlocks = new ArrayList<>();

    @ConfigEntry.Gui.Excluded
    public transient final Set<Either<ResourceKey<Block>, TagKey<Block>>> blocked = new HashSet<>();

    @Override
    public void validatePostLoad() throws ValidationException {
        blocked.clear();

        try {
            for (var s : blacklistedBlocks) {
                blocked.add(ConfigHelper.parseBlockOrTag(s));
            }
        } catch (Exception ex) {
            throw new ValidationException(ex);
        }
    }
}