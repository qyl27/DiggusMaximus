package net.kyrptonaught.diggusmaximus.util;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * For something marked as @Deprecated with {@link Holder}
 */
@SuppressWarnings("deprecation")
public class HolderHelper {
    public static Holder<Item> get(Item item) {
        return item.builtInRegistryHolder();
    }

    public static Holder<Block> get(Block block) {
        return block.builtInRegistryHolder();
    }

    /**
     * Check if the original holder and the target holder with the same id.
     * <p>
     *
     * We're using it for block and item for now, no another a registry of them.
     *
     * @param original The original Holder
     * @param target   The target Holder
     * @return true for same
     */
    public static <T> boolean is(Holder<T> original, Holder<T> target) {
        return original.is(target);
    }
}
