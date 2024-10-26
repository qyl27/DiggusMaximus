package net.kyrptonaught.diggusmaximus.config.category;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.ArrayList;
import java.util.List;

@Config(name = "blacklist")
public class ConfigOptions implements ConfigData {

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
}
