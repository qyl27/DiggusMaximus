package net.kyrptonaught.diggusmaximus.excavate.spread;

import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

import java.util.List;

public class NoShapeSpreadStrategy implements ISpreadStrategy {
    private static final List<Vec3i> STANDARD = List.of(
        new Vec3i(0, 1, 0),
        new Vec3i(0, 0, 1),
        new Vec3i(0, -1, 0),
        new Vec3i(1, 0, 0),
        new Vec3i(0, 0, -1),
        new Vec3i(-1, 0, 0)
    );

    private static final List<Vec3i> STANDARD_DIAGONALLY = List.of(
        new Vec3i(-1, -1, -1),
        new Vec3i(0, -1, -1),
        new Vec3i(1, -1, -1),
        new Vec3i(-1, 0, -1),
        new Vec3i(0, 0, -1),
        new Vec3i(1, 0, -1),
        new Vec3i(-1, 1, -1),
        new Vec3i(0, 1, -1),
        new Vec3i(1, 1, -1),
        new Vec3i(-1, -1, 0),
        new Vec3i(0, -1, 0),
        new Vec3i(1, -1, 0),
        new Vec3i(-1, 0, 0),
        new Vec3i(1, 0, 0),
        new Vec3i(-1, 1, 0),
        new Vec3i(0, 1, 0),
        new Vec3i(1, 1, 0),
        new Vec3i(-1, -1, 1),
        new Vec3i(0, -1, 1),
        new Vec3i(1, -1, 1),
        new Vec3i(-1, 0, 1),
        new Vec3i(0, 0, 1),
        new Vec3i(1, 0, 1),
        new Vec3i(-1, 1, 1),
        new Vec3i(0, 1, 1),
        new Vec3i(1, 1, 1)
    );

    @Override
    public List<Vec3i> getNextSpreadCores(Direction hitFace, Direction playerFacing) {
        return ConfigHelper.getConfig().common.diagonallyMine ? STANDARD_DIAGONALLY : STANDARD;
    }
}
