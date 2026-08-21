package net.kyrptonaught.diggusmaximus.excavate;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShapeSpreadStrategyTest {
    @Test
    void returnsHorizontalAndAxisAlignedLayerSpreadCores() {
        List<Vec3i> horizontal = List.of(
            vec(1, 0, 0),
            vec(0, 0, 1),
            vec(-1, 0, 0),
            vec(0, 0, -1)
        );
        assertEquals(
            horizontal,
            nextSpreadCores(Shape.HORIZONTAL_LAYER, Direction.NORTH, Direction.NORTH)
        );
        assertEquals(
            horizontal,
            nextSpreadCores(Shape.LAYER, Direction.UP, Direction.NORTH)
        );
        assertEquals(
            List.of(
                vec(0, 1, 0),
                vec(0, -1, 0),
                vec(1, 0, 0),
                vec(-1, 0, 0)
            ),
            nextSpreadCores(Shape.LAYER, Direction.NORTH, Direction.NORTH)
        );
        assertEquals(
            List.of(
                vec(0, 1, 0),
                vec(0, -1, 0),
                vec(0, 0, 1),
                vec(0, 0, -1)
            ),
            nextSpreadCores(Shape.LAYER, Direction.EAST, Direction.NORTH)
        );
    }

    @Test
    void returnsOneByTwoExtraBlockForHitFace() {
        assertEquals(
            List.of(vec(0, 1, 0)),
            Shape.ONE_BY_TWO.getSpreadStrategy().getExtraBlocks(Direction.DOWN)
        );
        assertEquals(
            List.of(vec(0, -1, 0)),
            Shape.ONE_BY_TWO.getSpreadStrategy().getExtraBlocks(Direction.UP)
        );
        assertEquals(
            List.of(vec(0, -1, 0)),
            Shape.ONE_BY_TWO.getSpreadStrategy().getExtraBlocks(Direction.NORTH)
        );
        assertEquals(
            Shape.ONE_BY_TWO.getSpreadStrategy().getExtraBlocks(Direction.DOWN),
            Shape.ONE_BY_TWO_TUNNEL.getSpreadStrategy().getExtraBlocks(Direction.DOWN)
        );
    }

    @Test
    void returnsThreeByThreeExtraBlocksForEachAxis() {
        assertEquals(
            List.of(
                vec(0, 1, 0),
                vec(0, -1, 0),
                vec(1, 0, 0),
                vec(-1, 0, 0),
                vec(1, 1, 0),
                vec(-1, 1, 0),
                vec(1, -1, 0),
                vec(-1, -1, 0)
            ),
            Shape.THREE_BY_THREE.getSpreadStrategy().getExtraBlocks(Direction.NORTH)
        );
        assertEquals(
            List.of(
                vec(0, 1, 0),
                vec(0, -1, 0),
                vec(0, 0, 1),
                vec(0, 0, -1),
                vec(0, 1, 1),
                vec(0, 1, -1),
                vec(0, -1, 1),
                vec(0, -1, -1)
            ),
            Shape.THREE_BY_THREE.getSpreadStrategy().getExtraBlocks(Direction.EAST)
        );
        assertEquals(
            List.of(
                vec(1, 0, 0),
                vec(-1, 0, 0),
                vec(1, 0, 1),
                vec(-1, 0, 1),
                vec(0, 0, 1),
                vec(0, 0, -1),
                vec(1, 0, -1),
                vec(-1, 0, -1)
            ),
            Shape.THREE_BY_THREE.getSpreadStrategy().getExtraBlocks(Direction.UP)
        );
        assertEquals(
            Shape.THREE_BY_THREE.getSpreadStrategy().getExtraBlocks(Direction.NORTH),
            Shape.THREE_BY_THREE_TUNNEL.getSpreadStrategy().getExtraBlocks(Direction.NORTH)
        );
    }

    @Test
    void separatesFiniteShapesAndFloodingShapes() {
        assertTrue(nextSpreadCores(Shape.ONE_BY_TWO, Direction.NORTH, Direction.NORTH).isEmpty());
        assertTrue(nextSpreadCores(Shape.THREE_BY_THREE, Direction.NORTH, Direction.NORTH).isEmpty());

        for (Shape shape : List.of(Shape.NONE, Shape.HORIZONTAL_LAYER, Shape.LAYER, Shape.HOLE)) {
            assertTrue(shape.getSpreadStrategy().getExtraBlocks(Direction.NORTH).isEmpty());
        }
    }

    @Test
    void tunnelsReturnOnlyTheirForwardSpreadCore() {
        List<Vec3i> south = List.of(vec(0, 0, 1));
        assertEquals(
            south,
            nextSpreadCores(Shape.HOLE, Direction.NORTH, Direction.WEST)
        );
        assertEquals(
            south,
            nextSpreadCores(Shape.ONE_BY_TWO_TUNNEL, Direction.NORTH, Direction.WEST)
        );
        assertEquals(
            south,
            nextSpreadCores(Shape.THREE_BY_THREE_TUNNEL, Direction.NORTH, Direction.WEST)
        );

        assertEquals(
            List.of(vec(1, 0, 0)),
            nextSpreadCores(Shape.ONE_BY_TWO_TUNNEL, Direction.UP, Direction.EAST)
        );
    }

    @Test
    void allowsOverlappingOneByTwoTunnelCoreAndExtraBlock() {
        var spreadCores = nextSpreadCores(Shape.ONE_BY_TWO_TUNNEL, Direction.DOWN, Direction.UP);
        var extraBlocks = Shape.ONE_BY_TWO_TUNNEL.getSpreadStrategy().getExtraBlocks(Direction.DOWN);

        assertEquals(List.of(vec(0, 1, 0)), spreadCores);
        assertEquals(extraBlocks, spreadCores);
    }

    @Test
    void continuesOnlyWhenCoreAndAllExtraBlocksWereMined() {
        for (Shape shape : Shape.values()) {
            assertFalse(shape.getSpreadStrategy().canContinueSpread(false, 0, 0));
            assertFalse(shape.getSpreadStrategy().canContinueSpread(false, 1, 1));
            assertTrue(shape.getSpreadStrategy().canContinueSpread(true, 0, 0));
            assertFalse(shape.getSpreadStrategy().canContinueSpread(true, 0, 1));
            assertFalse(shape.getSpreadStrategy().canContinueSpread(true, 1, 2));
            assertTrue(shape.getSpreadStrategy().canContinueSpread(true, 1, 1));
            assertTrue(shape.getSpreadStrategy().canContinueSpread(true, 8, 8));
        }
    }

    private static List<Vec3i> nextSpreadCores(Shape shape, Direction hitFace, Direction playerFacing) {
        return shape.getSpreadStrategy().getNextSpreadCores(hitFace, playerFacing);
    }

    private static Vec3i vec(int x, int y, int z) {
        return new Vec3i(x, y, z);
    }
}
