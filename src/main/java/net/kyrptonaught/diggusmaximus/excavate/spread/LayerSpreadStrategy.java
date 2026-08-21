package net.kyrptonaught.diggusmaximus.excavate.spread;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

import java.util.List;

public class LayerSpreadStrategy implements ISpreadStrategy {
    private static final List<Vec3i> X_NORMAL = List.of(
        new Vec3i(0, 1, 0),
        new Vec3i(0, -1, 0),
        new Vec3i(0, 0, 1),
        new Vec3i(0, 0, -1)
    );

    private static final List<Vec3i> Y_NORMAL = List.of(
        new Vec3i(1, 0, 0),
        new Vec3i(0, 0, 1),
        new Vec3i(-1, 0, 0),
        new Vec3i(0, 0, -1)
    );

    private static final List<Vec3i> Z_NORMAL = List.of(
        new Vec3i(0, 1, 0),
        new Vec3i(0, -1, 0),
        new Vec3i(1, 0, 0),
        new Vec3i(-1, 0, 0)
    );

    @Override
    public List<Vec3i> getNextSpreadCores(Direction hitFace, Direction playerFacing) {
        return switch (getNormalAxis(hitFace)) {
            case X -> X_NORMAL;
            case Y -> Y_NORMAL;
            case Z -> Z_NORMAL;
        };
    }

    protected Direction.Axis getNormalAxis(Direction hitFace) {
        return hitFace.getAxis();
    }
}
