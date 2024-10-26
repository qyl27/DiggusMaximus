package net.kyrptonaught.diggusmaximus;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

public class ExcavateHelper {
    static int maxMined = Math.min(DiggusMaximusMod.getOptions().maxMinedBlocks, 2048);
    private static double maxDistance = Math.min(DiggusMaximusMod.getOptions().maxMineDistance + 1, 128);

    public static void resetMaximums() {
        maxMined = Math.min(DiggusMaximusMod.getOptions().maxMinedBlocks, 2048);
        maxDistance = Math.min(DiggusMaximusMod.getOptions().maxMineDistance + 1, 128);
    }

    static void pickupDrops(Level world, BlockPos pos, Player player) {
        List<ItemEntity> drops = world.getEntitiesOfClass(ItemEntity.class, new AABB(pos), EntitySelector.ENTITY_STILL_ALIVE);
        drops.forEach(item -> {
            ItemStack stack = item.getItem();
            player.getInventory().add(stack);
            if (stack.getCount() <= 0)
                item.discard();
        });

    }

    static boolean isTheSameBlock(ResourceLocation startID, ResourceLocation newID, Level world, int shapeSelection) {
        if (shapeSelection > -1 && DiggusMaximusMod.getExcavatingShapes().includeDifBlocks)
            return true;

        // Not sure if this makes sense anymore with the all the tags that exist now. For example this will now consider bamboo and all types of dirt as the same block since they are all in "bamboo_plantable_on"
        /* if (DiggusMaximusMod.getGrouping().tagGrouping) {
            Block newBlock = Registry.BLOCK.get(newID);
            if (Registry.BLOCK.get(startID).getRegistryEntry().streamTags().anyMatch(blockTagKey1 -> newBlock.getRegistryEntry().isIn(blockTagKey1)))
                newID = startID;

        } */
        if (DiggusMaximusMod.getGrouping().customGrouping) {
            newID = DiggusMaximusMod.getIDFromConfigLookup(newID);
            startID = DiggusMaximusMod.getIDFromConfigLookup(startID);
        }
        return startID.equals(newID);
    }


    static boolean configAllowsMining(String blockID) {
        return DiggusMaximusMod.getBlackList().isWhitelist == DiggusMaximusMod.getBlackList().lookup.contains(blockID);
    }

    static boolean isValidPos(BlockPos pos) {
        return (Math.abs(pos.getX()) + Math.abs(pos.getY()) + Math.abs(pos.getZ())) != 0;
    }

    static Block getBlockAt(Level world, BlockPos pos) {
        return world.getBlockState(pos).getBlock();
    }

    static boolean canMine(Player player, Item tool, Level world, BlockPos startPos, BlockPos pos) {
        return isWithinDistance(startPos, pos) && checkTool(player, tool) && isBreakableBlock(getBlockAt(world, pos));
    }

    private static boolean isBreakableBlock(Block block) {
        return block.defaultDestroyTime() >= 0;
    }

    private static boolean isWithinDistance(BlockPos startPos, BlockPos pos) {
        return pos.closerThan(startPos, maxDistance);
    }

    private static boolean checkTool(Player player, Item tool) {
        if (player.isCreative()) {
            return true;
        }
        ItemStack heldItem = player.getMainHandItem();
        if (DiggusMaximusMod.getOptions().dontBreakTool && heldItem.getDamageValue() + 1 == heldItem.getMaxDamage()) {
            return false;
        }
        if (heldItem.getItem() != tool) {
            if (DiggusMaximusMod.getOptions().stopOnToolBreak || DiggusMaximusMod.getOptions().requiresTool) {
                return false;
            }
        }
        return isTool(heldItem) || !DiggusMaximusMod.getOptions().requiresTool;
    }

    private static boolean isTool(ItemStack stack) {
        return stack.isDamageableItem() || DiggusMaximusMod.getOptions().tools.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
    }
}