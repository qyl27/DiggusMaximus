package net.kyrptonaught.diggusmaximus.excavate;

import net.kyrptonaught.diggusmaximus.ModPlatformEvents;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.kyrptonaught.diggusmaximus.util.HolderHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExcavateHelper {
    public static void pickupDrops(Level world, BlockPos pos, Player player) {
        List<ItemEntity> drops = world.getEntitiesOfClass(ItemEntity.class, new AABB(pos), EntitySelector.ENTITY_STILL_ALIVE);
        drops.forEach(item -> {
            ItemStack stack = item.getItem();
            player.getInventory().add(stack);
            if (stack.getCount() <= 0) {
                item.discard();
            }
        });
    }

    // <editor-fold desc="Excavating validation">

    public static boolean isValid(Level level, BlockState original, BlockPos originalPos, Holder<Block> originalHolder, BlockPos targetPos,
                                  Player player, Item tool, boolean hasShape, boolean stopBeforeToolBroken, boolean stopAfterToolBroken) {
        if (!isWithinDistance(originalPos, targetPos)) {
            return false;
        }

        if (!isBlockLoaded(level, targetPos)) {
            return false;
        }

        var target = level.getBlockState(targetPos);
        var targetHolder = HolderHelper.get(target.getBlock());

        if (isBlockDisallowed(targetHolder)) {
            return false;
        }

        if (!isBreakableBlock(target)) {
            return false;
        }

        if (!isSameBlock(original, originalHolder, target, targetHolder, hasShape)) {
            return false;
        }

        if (!checkTool(player, tool, stopBeforeToolBroken, stopAfterToolBroken)) {
            return false;
        }

        return ModPlatformEvents.beforePlayerBreakBlock(level, player, target, targetPos);
    }

    private static boolean isBlockLoaded(Level level, BlockPos pos) {
        return level.isLoaded(pos);
    }

    private static boolean isBlockDisallowed(Holder<Block> block) {
        var config = ConfigHelper.getConfig().common;
        for (var e : config.blocked) {
            if (e.is(block)) {
                return !config.asAllowlist;
            }
        }
        return config.asAllowlist;
    }

    private static boolean isWithinDistance(BlockPos startPos, BlockPos pos) {
        return pos.closerThan(startPos, ConfigHelper.getConfig().common.maxMineDistance + 1);
    }

    private static boolean isBreakableBlock(BlockState state) {
        if (state == null) {
            return false;
        }
        if (state.isAir()) {
            return false;
        }
        return state.getBlock().defaultDestroyTime() >= 0;
    }

    private static boolean checkTool(Player player, Item tool, boolean stopBeforeToolBroken, boolean stopAfterToolBroken) {
        var config = ConfigHelper.getConfig().common;
        if (player.isCreative()) {
            return true;
        }
        ItemStack heldItem = player.getMainHandItem();
        if (stopBeforeToolBroken && heldItem.getDamageValue() + 1 == heldItem.getMaxDamage()) {
            return false;
        }
        if (heldItem.getItem() != tool) {
            if (stopAfterToolBroken || config.requiresTool) {
                return false;
            }
        }
        return isTool(heldItem) || !config.requiresTool;
    }

    private static boolean isTool(ItemStack stack) {
        if (stack.isDamageableItem()) {
            return true;
        }

        for (var e : ConfigHelper.getConfig().common.customTools) {
            if (e.is(HolderHelper.get(stack.getItem()))) {
                return true;
            }
        }

        return false;
    }

    // </editor-fold>

    // <editor-fold desc="Block comparison">

    public static boolean isSameBlock(BlockState original, Holder<Block> originalHolder, BlockState target, Holder<Block> targetHolder, boolean hasShape) {
        if (hasShape && ConfigHelper.getConfig().common.shapeIgnoreIdMismatch) {
            return true;
        }

        var hasSameId = HolderHelper.is(originalHolder, targetHolder);

        if (hasSameId) {
            var shouldMatchState = ConfigHelper.getConfig().common.matchState.stream()
                .anyMatch(e -> e.is(originalHolder));
            if (shouldMatchState) {
                return hasSameState(original, target);
            } else {
                return true;
            }
        }

        return isInSameGroup(originalHolder, targetHolder);
    }

    private static boolean isInSameGroup(Holder<Block> original, Holder<Block> target) {
        for (var g : ConfigHelper.getConfig().common.blockGroups) {
            var originalWithIn = g.stream().anyMatch(e -> e.is(original));
            var consideringWithIn = g.stream().anyMatch(e -> e.is(target));
            return originalWithIn && consideringWithIn;
        }

        return false;
    }

    /**
     * Check if two {@link BlockState} have the same properties and values, assure original and target has same id.
     * But this method won't check original and target has same id. <br/>
     * This method is not symmetric, i.e.,
     * {@code hasSameState(A, B) == true} and {@code hasSameState(B, A) == false} if B has extra properties that A doesn't have.
     *
     * @param original The original BlockState
     * @param target   The target BlockState to compare against
     * @return true if they have same state
     */
    private static boolean hasSameState(BlockState original, BlockState target) {
        return original.getValues().allMatch(value -> hasSamePropertyValue(target, value));
    }

    private static <T extends Comparable<T>> boolean hasSamePropertyValue(BlockState target, Property.Value<T> originalValue) {
        return target.getOptionalValue(originalValue.property())
            .map(targetValue -> originalValue.value().compareTo(targetValue) == 0)
            .orElse(false);
    }

    // </editor-fold>

    public static @Nullable BlockState getBlockAt(Level level, BlockPos pos) {
        if (level.isLoaded(pos)) {
            return level.getBlockState(pos);
        }
        return null;
    }

    public static boolean tryToExcavate(ServerPlayer player, BlockPos pos) {
        return player.gameMode.destroyBlock(pos);
    }
}
