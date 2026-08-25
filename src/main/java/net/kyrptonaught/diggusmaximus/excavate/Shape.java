package net.kyrptonaught.diggusmaximus.excavate;

import lombok.Getter;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import net.kyrptonaught.diggusmaximus.ModConstants;
import net.kyrptonaught.diggusmaximus.excavate.spread.*;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

import java.util.List;

public enum Shape implements SelectionListEntry.Translatable {
    NONE(ModConstants.SHAPE_NONE, new NoShapeSpreadStrategy()),
    HORIZONTAL_LAYER(ModConstants.SHAPE_HORIZONTAL_LAYER, new HorizontalLayerSpreadStrategy()),
    LAYER(ModConstants.SHAPE_LAYER, new LayerSpreadStrategy()),
    HOLE(ModConstants.SHAPE_HOLE, new HoleSpreadStrategy()),
    ONE_BY_TWO(ModConstants.SHAPE_ONE_BY_TWO, new OneByTwoSpreadStrategy()),
    ONE_BY_TWO_TUNNEL(ModConstants.SHAPE_ONE_BY_TWO_TUNNEL, new OneByTwoTunnelSpreadStrategy()),
    THREE_BY_THREE(ModConstants.SHAPE_THREE_BY_THREE, new ThreeByThreeSpreadStrategy()),
    THREE_BY_THREE_TUNNEL(ModConstants.SHAPE_THREE_BY_THREE_TUNNEL, new ThreeByThreeTunnelSpreadStrategy()),
    ;

    @Getter
    private final String name;

    @Getter
    private final ISpreadStrategy spreadStrategy;

    Shape(String name, ISpreadStrategy spreadStrategy) {
        this.name = name;
        this.spreadStrategy = spreadStrategy;
    }

    @Override
    public String getKey() {
        return name;
    }

    public Shape prev() {
        return prev(this);
    }

    public Shape next() {
        return next(this);
    }

    public static Shape prev(Shape shape) {
        return switch (shape) {
            case NONE -> THREE_BY_THREE_TUNNEL;
            case HORIZONTAL_LAYER -> NONE;
            case LAYER -> HORIZONTAL_LAYER;
            case HOLE -> LAYER;
            case ONE_BY_TWO -> HOLE;
            case ONE_BY_TWO_TUNNEL -> ONE_BY_TWO;
            case THREE_BY_THREE -> ONE_BY_TWO_TUNNEL;
            case THREE_BY_THREE_TUNNEL -> THREE_BY_THREE;
        };
    }

    public static Shape next(Shape shape) {
        return switch (shape) {
            case NONE -> HORIZONTAL_LAYER;
            case HORIZONTAL_LAYER -> LAYER;
            case LAYER -> HOLE;
            case HOLE -> ONE_BY_TWO;
            case ONE_BY_TWO -> ONE_BY_TWO_TUNNEL;
            case ONE_BY_TWO_TUNNEL -> THREE_BY_THREE;
            case THREE_BY_THREE -> THREE_BY_THREE_TUNNEL;
            case THREE_BY_THREE_TUNNEL -> NONE;
        };
    }
}
