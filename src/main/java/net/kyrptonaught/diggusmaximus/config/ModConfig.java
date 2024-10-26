package net.kyrptonaught.diggusmaximus.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.kyrptonaught.diggusmaximus.ModConstants;
import net.kyrptonaught.diggusmaximus.config.category.BlockGroups;
import net.kyrptonaught.diggusmaximus.config.category.BlockList;
import net.kyrptonaught.diggusmaximus.config.category.ConfigOptions;
import net.kyrptonaught.diggusmaximus.config.category.ExcavatingShapes;

@Config(name = ModConstants.MOD_ID)
public class ModConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("config")
    @ConfigEntry.Gui.TransitiveObject
    public ConfigOptions config = new ConfigOptions();

    @ConfigEntry.Category("blacklist")
    @ConfigEntry.Gui.TransitiveObject
    public BlockList blockList = new BlockList();

    @ConfigEntry.Category("grouping")
    @ConfigEntry.Gui.TransitiveObject
    public BlockGroups grouping = new BlockGroups();

    @ConfigEntry.Category("excavatingshapes")
    @ConfigEntry.Gui.TransitiveObject
    public ExcavatingShapes shapes = new ExcavatingShapes();
}
