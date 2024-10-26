package net.kyrptonaught.diggusmaximus;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.ArrayDeque;
import java.util.Deque;

public class Excavate {
    private final BlockPos startPos;
    private final Player player;
    private ResourceLocation startID;
    private final Item startTool;
    private int mined = 0;
    private final Level world;
    private final Deque<BlockPos> points = new ArrayDeque<>();

    private final Direction facing;
    private int shapeSelection = -1;

    private static final int airHash = ResourceLocation.parse("minecraft:air").hashCode();

    public Excavate(BlockPos pos, ResourceLocation blockID, Player player, Direction facing) {
        this.startPos = pos;
        this.player = player;
        this.world = player.getCommandSenderWorld();
        if (ExcavateHelper.configAllowsMining(blockID.toString())) {
            this.startID = blockID;
        }

        this.startTool = player.getMainHandItem().getItem();
        this.facing = facing;
    }

    public void startExcavate(int shapeSelection) {
        this.shapeSelection = shapeSelection;
        forceExcavateAt(startPos);
        if (startID == null) {
            return;
        }
        ((DiggingPlayerEntity) player).diggus$setExcavating(true);
        while (!points.isEmpty()) {
            spread(points.remove());
        }
        ((DiggingPlayerEntity) player).diggus$setExcavating(false);
    }

    private void spread(BlockPos pos) {
        for (BlockPos dirPos : ExcavateTypes.getSpreadType(shapeSelection, facing, startPos, pos)) {
            if (ExcavateHelper.isValidPos(dirPos)) {
                excavateAt(pos.offset(dirPos));
            }
        }
    }

    private void excavateAt(BlockPos pos) {
        if (mined >= ExcavateHelper.maxMined) {
            return;
        }
        ResourceLocation block = BuiltInRegistries.BLOCK.getKey(ExcavateHelper.getBlockAt(world, pos));
        if (block.hashCode() != airHash
                && ExcavateHelper.isTheSameBlock(startID, block, world, shapeSelection)
                && ExcavateHelper.canMine(player, startTool, world, startPos, pos)
                && isExcavatingAllowed(pos)) {
            forceExcavateAt(pos);
        }
    }

    private boolean isExcavatingAllowed(BlockPos pos) {
        return PlayerBlockBreakEvents.BEFORE.invoker().beforeBlockBreak(world, player, pos, world.getBlockState(pos), world.getBlockEntity(pos)) && ((ServerPlayer) player).gameMode.destroyBlock(pos);
    }

    private void forceExcavateAt(BlockPos pos) {
        points.add(pos);
        mined++;
        if (DiggusMaximusMod.getOptions().autoPickup) {
            ExcavateHelper.pickupDrops(world, pos, player);
        }
    }
}
