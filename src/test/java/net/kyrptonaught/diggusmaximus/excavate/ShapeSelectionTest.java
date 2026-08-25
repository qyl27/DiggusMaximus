package net.kyrptonaught.diggusmaximus.excavate;

import net.kyrptonaught.diggusmaximus.ModConstants;
import net.kyrptonaught.diggusmaximus.config.category.ClientCategory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShapeSelectionTest {
    @Test
    void cyclesThroughEveryShapeInBothDirections() {
        var shapes = Shape.values();

        assertEquals(Arrays.asList(shapes), cycle(Shape.NONE, shape -> shape.next(), shapes.length));

        var reverse = new ArrayList<Shape>();
        reverse.add(Shape.NONE);
        for (int i = shapes.length - 1; i > 0; i--) {
            reverse.add(shapes[i]);
        }
        assertEquals(reverse, cycle(Shape.NONE, shape -> shape.prev(), shapes.length));
    }

    @Test
    void nextAndPreviousAreInverses() {
        for (Shape shape : Shape.values()) {
            assertEquals(shape, shape.next().prev());
            assertEquals(shape, shape.prev().next());
        }
    }

    @Test
    void exposesLocalizedConfigKeysForEveryShape() {
        assertEquals(
            List.of(
                ModConstants.SHAPE_NONE,
                ModConstants.SHAPE_HORIZONTAL_LAYER,
                ModConstants.SHAPE_LAYER,
                ModConstants.SHAPE_HOLE,
                ModConstants.SHAPE_ONE_BY_TWO,
                ModConstants.SHAPE_ONE_BY_TWO_TUNNEL,
                ModConstants.SHAPE_THREE_BY_THREE,
                ModConstants.SHAPE_THREE_BY_THREE_TUNNEL
            ),
            Arrays.stream(Shape.values()).map(Shape::getKey).toList()
        );
    }

    @Test
    void usesNoShapeAsClientDefault() {
        assertEquals(Shape.NONE, new ClientCategory().selectedShape);
    }

    private static List<Shape> cycle(Shape start, UnaryOperator<Shape> step, int length) {
        var shapes = new ArrayList<Shape>();
        var shape = start;
        for (int i = 0; i < length; i++) {
            shapes.add(shape);
            shape = step.apply(shape);
        }
        assertEquals(start, shape);
        return shapes;
    }
}
