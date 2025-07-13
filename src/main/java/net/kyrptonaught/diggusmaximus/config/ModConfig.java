package net.kyrptonaught.diggusmaximus.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.kyrptonaught.diggusmaximus.ModConstants;
import net.kyrptonaught.diggusmaximus.config.category.*;

@Config(name = ModConstants.MOD_ID)
public class ModConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("common")
    @ConfigEntry.Gui.TransitiveObject
    public CommonCategory common = new CommonCategory();

    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.TransitiveObject
    public ClientCategory client = new ClientCategory();
}
