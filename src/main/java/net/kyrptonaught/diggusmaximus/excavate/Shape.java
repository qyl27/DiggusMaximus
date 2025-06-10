package net.kyrptonaught.diggusmaximus.excavate;

import lombok.Getter;
import net.kyrptonaught.diggusmaximus.ModConstants;

@Getter
public enum Shape {
    NONE(-1, ModConstants.SHAPE_NONE),
    HORIZONTAL_LAYER(0, ModConstants.SHAPE_HORIZONTAL_LAYER),
    LAYER(1, ModConstants.SHAPE_LAYER),
    HOLE(2, ModConstants.SHAPE_HOLE),
    ONE_BY_TWO(3, ModConstants.SHAPE_ONE_BY_TWO),
    ONE_BY_TWO_TUNNEL(4, ModConstants.SHAPE_ONE_BY_TWO_TUNNEL),
    THREE_BY_THREE(5, ModConstants.SHAPE_THREE_BY_THREE),
    THREE_BY_THREE_TUNNEL(6, ModConstants.SHAPE_THREE_BY_THREE_TUNNEL),
    ;

    private final int id;
    private final String name;

    Shape(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Shape prev() {
        return prev(this);
    }

    public Shape next() {
        return next(this);
    }

    public static Shape prev(Shape shape) {
        return switch (shape) {
            case HORIZONTAL_LAYER -> THREE_BY_THREE_TUNNEL;
            case NONE, LAYER -> HORIZONTAL_LAYER;
            case HOLE -> LAYER;
            case ONE_BY_TWO -> HOLE;
            case ONE_BY_TWO_TUNNEL -> ONE_BY_TWO;
            case THREE_BY_THREE -> ONE_BY_TWO_TUNNEL;
            case THREE_BY_THREE_TUNNEL -> THREE_BY_THREE;
        };
    }

    public static Shape next(Shape shape) {
        return switch (shape) {
            case HORIZONTAL_LAYER -> LAYER;
            case LAYER -> HOLE;
            case HOLE -> ONE_BY_TWO;
            case ONE_BY_TWO -> ONE_BY_TWO_TUNNEL;
            case ONE_BY_TWO_TUNNEL -> THREE_BY_THREE;
            case THREE_BY_THREE -> THREE_BY_THREE_TUNNEL;
            case NONE, THREE_BY_THREE_TUNNEL -> HORIZONTAL_LAYER;
        };
    }

    public static Shape from(int i) {
        return switch (i) {
            case 0 -> HORIZONTAL_LAYER;
            case 1 -> LAYER;
            case 2 -> HOLE;
            case 3 -> ONE_BY_TWO;
            case 4 -> ONE_BY_TWO_TUNNEL;
            case 5 -> THREE_BY_THREE;
            case 6 -> THREE_BY_THREE_TUNNEL;
            default -> NONE;
        };
    }
}
