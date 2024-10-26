package net.kyrptonaught.diggusmaximus;

import java.util.ArrayList;
import java.util.List;

import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class ExcavateTypes {
    public enum Shape {
        HORIZONTAL_LAYER, LAYER, HOLE,
        ONE_BY_TWO, ONE_BY_TWO_TUNNEL,
        THREE_BY_THREE, THREE_BY_THREE_TUNNEL
    }

    public static List<BlockPos> getSpreadType(int shapeSelection, Direction facing,
                                               BlockPos startPos, BlockPos curPos) {
        if (shapeSelection == -1) {
            return ConfigHelper.getConfig().config.mineDiag ? ExcavateTypes.standardDiag : ExcavateTypes.standard;
        }

        return switch (Shape.values()[shapeSelection]) {
            case HOLE -> ExcavateTypes.hole(facing);
            case HORIZONTAL_LAYER -> ExcavateTypes.horizontalLayer();
            case LAYER -> ExcavateTypes.layers(facing);
            case ONE_BY_TWO -> ExcavateTypes.oneByTwo(startPos, curPos);
            case ONE_BY_TWO_TUNNEL -> ExcavateTypes.oneByTwoTunnel(startPos, curPos, facing);
            case THREE_BY_THREE -> ExcavateTypes.threeByThree(startPos, curPos, facing);
            case THREE_BY_THREE_TUNNEL -> ExcavateTypes.threeByThreeTunnel(startPos, curPos, facing);
        };
    }

    public static List<BlockPos> horizontalLayer() {
        List<BlockPos> cube = new ArrayList<>();
        cube.add(new BlockPos(1, 0, 0));
        cube.add(new BlockPos(0, 0, 1));
        cube.add(new BlockPos(-1, 0, 0));
        cube.add(new BlockPos(0, 0, -1));
        return cube;
    }

    public static List<BlockPos> layers(Direction facing) {
        if (facing.getAxis() == Direction.Axis.Y) {
            return horizontalLayer();
        }

        List<BlockPos> cube = new ArrayList<>();
        cube.add(new BlockPos(0, 1, 0));
        cube.add(new BlockPos(0, -1, 0));
        if (facing.getAxis() == Direction.Axis.Z) {
            cube.add(new BlockPos(1, 0, 0));
            cube.add(new BlockPos(-1, 0, 0));
        } else {
            cube.add(new BlockPos(0, 0, 1));
            cube.add(new BlockPos(0, 0, -1));
        }
        return cube;
    }

    public static List<BlockPos> hole(Direction facing) {
        List<BlockPos> cube = new ArrayList<>();
        cube.add(BlockPos.ZERO.relative(facing.getOpposite()));
        return cube;
    }

    public static List<BlockPos> threeByThree(BlockPos startPos, BlockPos curPos, Direction facing) {
        List<BlockPos> cube = new ArrayList<>();
        if (startPos.equals(curPos)) {
            if (facing.getAxis().isHorizontal()) {
                cube.add(new BlockPos(0, 1, 0));
                cube.add(new BlockPos(0, -1, 0));
                if (facing == Direction.NORTH || facing == Direction.SOUTH) {
                    cube.add(new BlockPos(1, 0, 0));
                    cube.add(new BlockPos(-1, 0, 0));
                    cube.add(new BlockPos(1, 1, 0));
                    cube.add(new BlockPos(-1, 1, 0));
                    cube.add(new BlockPos(1, -1, 0));
                    cube.add(new BlockPos(-1, -1, 0));
                } else {
                    cube.add(new BlockPos(0, 0, 1));
                    cube.add(new BlockPos(0, 0, -1));
                    cube.add(new BlockPos(0, 1, 1));
                    cube.add(new BlockPos(0, 1, -1));
                    cube.add(new BlockPos(0, -1, 1));
                    cube.add(new BlockPos(0, -1, -1));
                }
            } else {
                cube.add(new BlockPos(1, 0, 0));
                cube.add(new BlockPos(-1, 0, 0));
                cube.add(new BlockPos(1, 0, 1));
                cube.add(new BlockPos(-1, 0, 1));
                cube.add(new BlockPos(0, 0, 1));
                cube.add(new BlockPos(0, 0, -1));
                cube.add(new BlockPos(1, 0, -1));
                cube.add(new BlockPos(-1, 0, -1));
            }
        }
        return cube;
    }

    public static List<BlockPos> threeByThreeTunnel(BlockPos startPos, BlockPos curPos, Direction facing) {
        List<BlockPos> cube = threeByThree(startPos, curPos, facing);
        cube.add(BlockPos.ZERO.relative(facing.getOpposite()));
        cube.addAll(cube.stream().map(blockPos -> blockPos.relative(facing.getOpposite())).toList());
        return cube;
    }

    public static List<BlockPos> oneByTwo(BlockPos startPos, BlockPos curPos) {
        List<BlockPos> cube = new ArrayList<>();
        if (startPos.getY() == curPos.getY())
            cube.add(new BlockPos(0, -1, 0));
        return cube;
    }

    public static List<BlockPos> oneByTwoTunnel(BlockPos startPos, BlockPos curPos, Direction facing) {
        List<BlockPos> cube = hole(facing);
        if (startPos.getY() == curPos.getY()) {
            cube.add(new BlockPos(0, -1, 0));
        }
        return cube;
    }

    public final static List<BlockPos> standard = new ArrayList<>();
    public final static List<BlockPos> standardDiag = new ArrayList<>();

    static {
        standard.add(new BlockPos(0, 1, 0));
        standard.add(new BlockPos(0, 0, 1));
        standard.add(new BlockPos(0, -1, 0));
        standard.add(new BlockPos(1, 0, 0));
        standard.add(new BlockPos(0, 0, -1));
        standard.add(new BlockPos(-1, 0, 0));

        standardDiag.addAll(BlockPos.betweenClosedStream(-1, -1, -1, 1, 1, 1)
                .map(BlockPos::immutable)
                .toList());
    }
}