package net.kyrptonaught.diggusmaximus.config.category;

import com.mojang.datafixers.util.Either;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Config(name = "config_common")
public class CommonCategory implements ConfigData {
    public boolean enabled = true;

    @ConfigEntry.Gui.Tooltip
    public boolean sneakToExcavate = false;

    @ConfigEntry.Gui.Tooltip
    public boolean diagonallyMine = true;

    @ConfigEntry.BoundedDiscrete(max = 2048)
    public int maxMinedBlocks = 40;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 128)
    public int maxMineDistance = 10;

    public boolean autoPickup = true;

    public boolean requiresTool = false;

    @ConfigEntry.Gui.Tooltip
    public boolean stopBeforeToolBroken = true;

    @ConfigEntry.Gui.Tooltip
    public boolean stopAfterToolBroken = true;

    @ConfigEntry.Gui.Tooltip
    public boolean causeToolDamage = true;

    @ConfigEntry.Gui.Tooltip
    public boolean causePlayerExhaustion = true;

    public float exhaustionMultiplier = 1;

    @ConfigEntry.Gui.Tooltip
    public List<String> tools = new ArrayList<>();

    @ConfigEntry.Gui.Tooltip
    public List<String> groups = new ArrayList<>();

    @ConfigEntry.Gui.Tooltip
    public boolean enableShapes = true;

    @ConfigEntry.Gui.Tooltip
    public boolean shapeIgnoreIdMismatch = true;

    @ConfigEntry.Gui.Tooltip
    public boolean asAllowlist = false;

    @ConfigEntry.Gui.Tooltip
    public List<String> blocklistedBlocks = new ArrayList<>();

    // region Excluded fields

    @ConfigEntry.Gui.Excluded
    public final Set<Either<ResourceKey<Item>, TagKey<Item>>> customTools = new HashSet<>();

    @ConfigEntry.Gui.Excluded
    public transient final List<Set<Either<ResourceKey<Block>, TagKey<Block>>>> blockGroups = new ArrayList<>();

    @ConfigEntry.Gui.Excluded
    public transient final Set<Either<ResourceKey<Block>, TagKey<Block>>> blocked = new HashSet<>();

    // endregion

    @Override
    public void validatePostLoad() throws ConfigData.ValidationException {
        try {
            update();
        } catch (Exception ex) {
            throw new ConfigData.ValidationException(ex);
        }
    }

    public void update() {
        customTools.clear();
        blockGroups.clear();
        blocked.clear();

        for (var s : tools) {
            customTools.add(ConfigHelper.parseItemOrTag(s));
        }

        for (var g : groups) {
            var set = new HashSet<Either<ResourceKey<Block>, TagKey<Block>>>();
            for (var s : g.split(",")) {
                set.add(ConfigHelper.parseBlockOrTag(s));
            }
            blockGroups.add(set);
        }

        for (var s : blocklistedBlocks) {
            blocked.add(ConfigHelper.parseBlockOrTag(s));
        }
    }
}
