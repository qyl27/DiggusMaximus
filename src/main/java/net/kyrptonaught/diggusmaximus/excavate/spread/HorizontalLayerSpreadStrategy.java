package net.kyrptonaught.diggusmaximus.excavate.spread;

import net.minecraft.core.Direction;

public class HorizontalLayerSpreadStrategy extends LayerSpreadStrategy {
    @Override
    protected Direction.Axis getNormalAxis(Direction hitFace) {
        return Direction.Axis.Y;
    }
}
