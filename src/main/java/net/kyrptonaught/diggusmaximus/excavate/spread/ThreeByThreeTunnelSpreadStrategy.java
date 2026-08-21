package net.kyrptonaught.diggusmaximus.excavate.spread;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

import java.util.List;

public final class ThreeByThreeTunnelSpreadStrategy extends ThreeByThreeSpreadStrategy {
    @Override
    public List<Vec3i> getNextSpreadCores(Direction hitFace, Direction playerFacing) {
        return List.of(Vec3i.ZERO.relative(hitFace.getOpposite()));
    }
}
