package net.kyrptonaught.diggusmaximus.config;

import blue.endless.jankson.Comment;
import net.kyrptonaught.diggusmaximus.ExcavateTypes;
import net.kyrptonaught.diggusmaximus.client.DiggusKeyBinding;
import net.kyrptonaught.kyrptconfig.config.AbstractConfigFile;

public class ExcavatingShapes implements AbstractConfigFile {
    @Comment("Should shape excavating be enabled")
    public boolean enableShapes = false;

    @Comment("Should shape excavating include different blocks")
    public boolean includeDifBlocks = false;

    @Comment("Shape activation key")
    public DiggusKeyBinding shapeKey = new DiggusKeyBinding(false, false, "key.keyboard.unknown");

    @Comment("Shape type cycle key, sneak to reverse cycle")
    public DiggusKeyBinding cycleKey = new DiggusKeyBinding(false, false, "key.keyboard.unknown");

    @Comment("Currently selected shape")
    public ExcavateTypes.Shape selectedShape = ExcavateTypes.Shape.LAYER;
}
