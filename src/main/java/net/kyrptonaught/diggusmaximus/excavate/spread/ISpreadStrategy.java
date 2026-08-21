package net.kyrptonaught.diggusmaximus.excavate.spread;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

import java.util.List;

/**
 * Stateless rules for generating positions relative to the current spread core.
 */
public interface ISpreadStrategy {
    default List<Vec3i> getNextSpreadCores(Direction hitFace, Direction playerFacing) {
        return List.of();
    }

    default List<Vec3i> getExtraBlocks(Direction hitFace) {
        return List.of();
    }

    default boolean canContinueSpread(boolean coreMined, int extraBlocksMined, int totalExtraBlocks) {
        return coreMined;
    }
}
