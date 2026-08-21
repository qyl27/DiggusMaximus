package net.kyrptonaught.diggusmaximus.excavate.spread;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

import java.util.List;

public class OneByTwoSpreadStrategy implements ISpreadStrategy {
    private static final List<Vec3i> ABOVE = List.of(new Vec3i(0, 1, 0));
    private static final List<Vec3i> BELOW = List.of(new Vec3i(0, -1, 0));

    @Override
    public List<Vec3i> getExtraBlocks(Direction hitFace) {
        return hitFace == Direction.DOWN ? ABOVE : BELOW;
    }
}
