package net.kyrptonaught.diggusmaximus.config.category;

import com.mojang.datafixers.util.Either;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Config(name = "config")
public class Options implements ConfigData {

    @Comment("Mod enabled or disabled")
    public boolean enabled = true;

    @Comment("Inverts the keybinding activation")
    public boolean invertActivation = false;

    @Comment("Sneak to excavate(can work serverside only)")
    public boolean sneakToExcavate = false;

    @Comment("Should mine diagonally, excludes shape excavating")
    public boolean mineDiag = true;

    @Comment("Maximum number of blocks to mine")
    @ConfigEntry.BoundedDiscrete(max = 2048)
    public int maxMinedBlocks = 40;

    @Comment("Maximum distance from start to mine")
    @ConfigEntry.BoundedDiscrete(max = 128)
    public int maxMineDistance = 10;

    @Comment("Automatically pick up drops")
    public boolean autoPickup = true;

    @Comment("Tool required to excavate")
    public boolean requiresTool = false;

    @Comment("Stop before tool breaks")
    public boolean dontBreakTool = true;

    @Comment("Stop excavating when tool breaks")
    public boolean stopOnToolBreak = true;

    @Comment("Should tool take durability")
    public boolean toolDurability = true;

    @Comment("Should player get exhaustion")
    public boolean playerExhaustion = true;

    @Comment("Multiply exhaustion when excavating")
    public float exhaustionMultiplier = 1.0f;

    @Comment("Other items to be considered tools ie: \"minecraft:stick\"")
    public List<String> tools = new ArrayList<>();

    @ConfigEntry.Gui.Excluded
    public final Set<Either<ResourceKey<Item>, TagKey<Item>>> customTools = new HashSet<>();

    @Override
    public void validatePostLoad() throws ValidationException {
        try {
            update();
        } catch (Exception ex) {
            throw new ValidationException(ex);
        }
    }

    public void update() {
        customTools.clear();

        for (var s : tools) {
            customTools.add(ConfigHelper.parseItemOrTag(s));
        }
    }
}
