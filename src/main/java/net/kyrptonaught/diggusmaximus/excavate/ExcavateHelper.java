package net.kyrptonaught.diggusmaximus.excavate;

import java.util.List;

import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

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

    @SuppressWarnings("deprecation")
    public static boolean isTheSameBlock(Holder<Block> original, Holder<Block> newBlock, boolean hasShape) {
        if (hasShape && ConfigHelper.getConfig().common.shapeIgnoreIdMismatch) {
            return true;
        }

        if (original.is(newBlock)) {
            return true;
        }

        for (var g : ConfigHelper.getConfig().common.blockGroups) {
            var originalWithIn = g.stream().anyMatch(e -> e.map(original::is, original::is));
            var consideringWithIn = g.stream().anyMatch(e -> e.map(newBlock::is, newBlock::is));
            return originalWithIn && consideringWithIn;
        }

        return false;
    }

    public static boolean isBlockDisallowed(Holder<Block> block) {
        var config = ConfigHelper.getConfig().common;
        for (var e : config.blocked) {
            var r = e.map(block::is, block::is);
            if (r) {
                return !config.asAllowlist;
            }
        }
        return config.asAllowlist;
    }

    public static boolean isValidOffset(Vec3i pos) {
        return (Math.abs(pos.getX()) + Math.abs(pos.getY()) + Math.abs(pos.getZ())) != 0;
    }

    public static @Nullable BlockState getBlockAt(Level level, BlockPos pos) {
        if (level.isLoaded(pos)) {
            return level.getBlockState(pos);
        }
        return null;
    }

    public static boolean canMine(Level world, BlockPos startPos, BlockPos pos) {
        return isWithinDistance(startPos, pos) && isBreakableBlock(getBlockAt(world, pos));
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

    private static boolean isWithinDistance(BlockPos startPos, BlockPos pos) {
        return pos.closerThan(startPos, ConfigHelper.getConfig().common.maxMineDistance + 1);
    }

    public static boolean checkTool(Player player, Item tool, boolean stopBeforeToolBroken, boolean stopAfterToolBroken) {
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
            var v = e.map(l -> stack.getItemHolder().is(l), stack::is);
            if (v) {
                return true;
            }
        }

        return false;
    }

    public static boolean tryToExcavate(ServerPlayer player, BlockPos pos) {
        return player.gameMode.destroyBlock(pos);
    }
}