package net.kyrptonaught.diggusmaximus.config.category;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.kyrptonaught.diggusmaximus.excavate.Shape;

@Config(name = "config_client")
public class ClientCategory implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean invertActivation = false;

    @ConfigEntry.Gui.Tooltip
    public boolean stopBeforeToolBroken = true;

    @ConfigEntry.Gui.Tooltip
    public boolean stopAfterToolBroken = true;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.EnumHandler(
            option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON
    )
    public Shape selectedShape = Shape.NONE;
}
