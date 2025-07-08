package net.kyrptonaught.diggusmaximus.excavate;

import java.util.ArrayList;
import java.util.List;

import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

public class ExcavateSpreadHelper {
    public static List<Vec3i> getSpreadShape(Shape shape, Direction hitFace, Direction playerHorizontalFacing,
                                             BlockPos startPos, BlockPos curPos) {
        return switch (shape) {
            case HOLE -> ExcavateSpreadHelper.hole(hitFace);
            case HORIZONTAL_LAYER -> ExcavateSpreadHelper.horizontalLayer();
            case LAYER -> ExcavateSpreadHelper.layers(hitFace);
            case ONE_BY_TWO -> ExcavateSpreadHelper.oneByTwo(startPos, curPos, hitFace);
            case ONE_BY_TWO_TUNNEL -> ExcavateSpreadHelper.oneByTwoTunnel(startPos, curPos, hitFace, playerHorizontalFacing);
            case THREE_BY_THREE -> ExcavateSpreadHelper.threeByThree(startPos, curPos, hitFace);
            case THREE_BY_THREE_TUNNEL -> ExcavateSpreadHelper.threeByThreeTunnel(startPos, curPos, hitFace);
            case NONE ->
                    ConfigHelper.getConfig().config.mineDiag ? ExcavateSpreadHelper.standardDiag : ExcavateSpreadHelper.standard;
        };
    }

    private static List<Vec3i> horizontalLayer() {
        List<Vec3i> cube = new ArrayList<>();
        cube.add(new Vec3i(1, 0, 0));
        cube.add(new Vec3i(0, 0, 1));
        cube.add(new Vec3i(-1, 0, 0));
        cube.add(new Vec3i(0, 0, -1));
        return cube;
    }

    private static List<Vec3i> layers(Direction facing) {
        if (facing.getAxis() == Direction.Axis.Y) {
            return horizontalLayer();
        }

        List<Vec3i> cube = new ArrayList<>();
        cube.add(new Vec3i(0, 1, 0));
        cube.add(new Vec3i(0, -1, 0));
        if (facing.getAxis() == Direction.Axis.Z) {
            cube.add(new Vec3i(1, 0, 0));
            cube.add(new Vec3i(-1, 0, 0));
        } else {
            cube.add(new Vec3i(0, 0, 1));
            cube.add(new Vec3i(0, 0, -1));
        }
        return cube;
    }

    private static List<Vec3i> hole(Direction facing) {
        List<Vec3i> cube = new ArrayList<>();
        cube.add(Vec3i.ZERO.relative(facing.getOpposite()));
        return cube;
    }

    private static List<Vec3i> threeByThree(BlockPos startPos, BlockPos curPos, Direction facing) {
        List<Vec3i> cube = new ArrayList<>();
        if (startPos.equals(curPos)) {
            if (facing.getAxis().isHorizontal()) {
                cube.add(new Vec3i(0, 1, 0));
                cube.add(new Vec3i(0, -1, 0));
                if (facing == Direction.NORTH || facing == Direction.SOUTH) {
                    cube.add(new Vec3i(1, 0, 0));
                    cube.add(new Vec3i(-1, 0, 0));
                    cube.add(new Vec3i(1, 1, 0));
                    cube.add(new Vec3i(-1, 1, 0));
                    cube.add(new Vec3i(1, -1, 0));
                    cube.add(new Vec3i(-1, -1, 0));
                } else {
                    cube.add(new Vec3i(0, 0, 1));
                    cube.add(new Vec3i(0, 0, -1));
                    cube.add(new Vec3i(0, 1, 1));
                    cube.add(new Vec3i(0, 1, -1));
                    cube.add(new Vec3i(0, -1, 1));
                    cube.add(new Vec3i(0, -1, -1));
                }
            } else {
                cube.add(new Vec3i(1, 0, 0));
                cube.add(new Vec3i(-1, 0, 0));
                cube.add(new Vec3i(1, 0, 1));
                cube.add(new Vec3i(-1, 0, 1));
                cube.add(new Vec3i(0, 0, 1));
                cube.add(new Vec3i(0, 0, -1));
                cube.add(new Vec3i(1, 0, -1));
                cube.add(new Vec3i(-1, 0, -1));
            }
        }
        return cube;
    }

    private static List<Vec3i> threeByThreeTunnel(BlockPos startPos, BlockPos curPos, Direction facing) {
        List<Vec3i> cube = threeByThree(startPos, curPos, facing);
        cube.add(Vec3i.ZERO.relative(facing.getOpposite()));
        cube.addAll(cube.stream().map(p -> p.relative(facing.getOpposite())).toList());
        return cube;
    }

    private static List<Vec3i> oneByTwo(BlockPos startPos, BlockPos curPos, Direction hitFace) {
        List<Vec3i> cube = new ArrayList<>();
        if (startPos.getY() == curPos.getY()) {
            if (hitFace == Direction.DOWN) {
                cube.add(new Vec3i(0, 1, 0));
            } else {
                cube.add(new Vec3i(0, -1, 0));
            }
        }
        return cube;
    }

    private static List<Vec3i> oneByTwoTunnel(BlockPos startPos, BlockPos curPos, Direction hitFace, Direction playerFacing) {
        var direction = hitFace;
        if (hitFace.getAxis().isVertical()) {
            direction = playerFacing.getOpposite();
        }
        List<Vec3i> cube = hole(direction);
        cube.addAll(oneByTwo(startPos, curPos, hitFace));
        return cube;
    }

    private final static List<Vec3i> standard = new ArrayList<>();
    private final static List<Vec3i> standardDiag = new ArrayList<>();

    static {
        standard.add(new Vec3i(0, 1, 0));
        standard.add(new Vec3i(0, 0, 1));
        standard.add(new Vec3i(0, -1, 0));
        standard.add(new Vec3i(1, 0, 0));
        standard.add(new Vec3i(0, 0, -1));
        standard.add(new Vec3i(-1, 0, 0));

        standardDiag.add(new Vec3i(-1, -1, -1));
        standardDiag.add(new Vec3i(0, -1, -1));
        standardDiag.add(new Vec3i(1, -1, -1));
        standardDiag.add(new Vec3i(-1, 0, -1));
        standardDiag.add(new Vec3i(0, 0, -1));
        standardDiag.add(new Vec3i(1, 0, -1));
        standardDiag.add(new Vec3i(-1, 1, -1));
        standardDiag.add(new Vec3i(0, 1, -1));
        standardDiag.add(new Vec3i(1, 1, -1));
        standardDiag.add(new Vec3i(-1, -1, 0));
        standardDiag.add(new Vec3i(0, -1, 0));
        standardDiag.add(new Vec3i(1, -1, 0));
        standardDiag.add(new Vec3i(-1, 0, 0));
        standardDiag.add(new Vec3i(0, 0, 0));
        standardDiag.add(new Vec3i(1, 0, 0));
        standardDiag.add(new Vec3i(-1, 1, 0));
        standardDiag.add(new Vec3i(0, 1, 0));
        standardDiag.add(new Vec3i(1, 1, 0));
        standardDiag.add(new Vec3i(-1, -1, 1));
        standardDiag.add(new Vec3i(0, -1, 1));
        standardDiag.add(new Vec3i(1, -1, 1));
        standardDiag.add(new Vec3i(-1, 0, 1));
        standardDiag.add(new Vec3i(0, 0, 1));
        standardDiag.add(new Vec3i(1, 0, 1));
        standardDiag.add(new Vec3i(-1, 1, 1));
        standardDiag.add(new Vec3i(0, 1, 1));
        standardDiag.add(new Vec3i(1, 1, 1));
    }
}