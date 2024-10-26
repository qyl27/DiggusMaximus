package net.kyrptonaught.diggusmaximus;

import java.util.List;

import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

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

    public static boolean isTheSameBlock(Holder.Reference<Block> original, BlockState newBlock, int shapeSelection) {
        if (shapeSelection > -1 && ConfigHelper.getConfig().shapes.includeDifBlocks) {
            return true;
        }

        if (ConfigHelper.getConfig().grouping.customGrouping) {
            for (var g : ConfigHelper.getConfig().grouping.blockGroups) {
                var originalWithIn = false;

                for (var e : g) {
                    if (!originalWithIn) {
                        var v = e.map(original::is, original::is);
                        if (v) {
                            originalWithIn = true;
                        }
                    }

                    var v = e.map(newBlock::is, newBlock::is);
                    if (originalWithIn && v) {
                        return true;
                    }
                }
            }
        }

        return original.is(newBlock.getBlockHolder());
    }

    public static boolean isBlockBlocked(Holder.Reference<Block> block) {
        var config = ConfigHelper.getConfig().blockList;
        if (config.isWhitelist) {
            for (var e : config.blocked) {
                var r = e.map(block::is, block::is);
                if (r) {
                    return false;
                }
            }
            return true;
        } else {
            for (var e : config.blocked) {
                var r = e.map(block::is, block::is);
                if (r) {
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean isValidPos(BlockPos pos) {
        return (Math.abs(pos.getX()) + Math.abs(pos.getY()) + Math.abs(pos.getZ())) != 0;
    }

    public static BlockState getBlockAt(Level world, BlockPos pos) {
        return world.getBlockState(pos);
    }

    public static boolean canMine(Player player, Item tool, Level world, BlockPos startPos, BlockPos pos) {
        return isWithinDistance(startPos, pos) && checkTool(player, tool) && isBreakableBlock(getBlockAt(world, pos).getBlock());
    }

    private static boolean isBreakableBlock(Block block) {
        return block.defaultDestroyTime() >= 0;
    }

    private static boolean isWithinDistance(BlockPos startPos, BlockPos pos) {
        return pos.closerThan(startPos, ConfigHelper.getConfig().config.maxMineDistance + 1);
    }

    private static boolean checkTool(Player player, Item tool) {
        var config = ConfigHelper.getConfig().config;
        if (player.isCreative()) {
            return true;
        }
        ItemStack heldItem = player.getMainHandItem();
        if (config.dontBreakTool && heldItem.getDamageValue() + 1 == heldItem.getMaxDamage()) {
            return false;
        }
        if (heldItem.getItem() != tool) {
            if (config.stopOnToolBreak || config.requiresTool) {
                return false;
            }
        }
        return isTool(heldItem) || !config.requiresTool;
    }

    private static boolean isTool(ItemStack stack) {
        if (stack.isDamageableItem()) {
            return true;
        }

        for (var e : ConfigHelper.getConfig().config.customTools) {
            var v = e.map(l -> stack.getItemHolder().is(l), stack::is);
            if (v) {
                return true;
            }
        }

        return false;
    }
}