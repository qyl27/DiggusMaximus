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

import java.util.*;

@Config(name = "grouping")
public class BlockGroups implements ConfigData {
    @Comment("Enable block custom grouping")
    public boolean customGrouping = false;

    @Comment("BlockID to be considered the same block when excavating (IDs separated by commas)")
    public List<String> groups = new ArrayList<>();

    @ConfigEntry.Gui.Excluded
    public transient final List<Set<Either<ResourceKey<Block>, TagKey<Block>>>> blockGroups = new ArrayList<>();

    @Override
    public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();
        blockGroups.clear();

        try {
            for (var g : groups) {
                var set = new HashSet<Either<ResourceKey<Block>, TagKey<Block>>>();
                for (var s : g.split(",")) {
                    set.add(ConfigHelper.parseBlockOrTag(s));
                }
                blockGroups.add(set);
            }
        } catch (Exception ex) {
            throw new ValidationException(ex);
        }
    }
}
