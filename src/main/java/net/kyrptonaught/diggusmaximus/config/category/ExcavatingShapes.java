package net.kyrptonaught.diggusmaximus.config.category;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.kyrptonaught.diggusmaximus.ExcavateTypes;

@Config(name = "excavatingshapes")
public class ExcavatingShapes implements ConfigData {
    @Comment("Should shape excavating be enabled")
    public boolean enableShapes = false;

    @Comment("Should shape excavating include different blocks")
    public boolean includeDifBlocks = false;

    @Comment("Currently selected shape")
    @ConfigEntry.Gui.EnumHandler(
            option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON
    )
    public ExcavateTypes.Shape selectedShape = ExcavateTypes.Shape.LAYER;
}
