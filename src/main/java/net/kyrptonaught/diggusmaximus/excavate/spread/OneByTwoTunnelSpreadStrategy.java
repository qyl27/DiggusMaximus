package net.kyrptonaught.diggusmaximus.excavate.spread;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

import java.util.List;

public final class OneByTwoTunnelSpreadStrategy extends OneByTwoSpreadStrategy {
    @Override
    public List<Vec3i> getNextSpreadCores(Direction hitFace, Direction playerFacing) {
        Direction direction = hitFace.getAxis().isVertical() ? playerFacing : hitFace.getOpposite();
        return List.of(Vec3i.ZERO.relative(direction));
    }
}
