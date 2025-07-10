package net.kyrptonaught.diggusmaximus.config.category;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "config_common")
public class CommonCategory {
    @ConfigEntry.Gui.Tooltip
    public boolean enabled = true;
}
