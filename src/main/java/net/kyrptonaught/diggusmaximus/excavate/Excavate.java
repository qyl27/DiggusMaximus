package net.kyrptonaught.diggusmaximus.excavate;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.kyrptonaught.diggusmaximus.bridge.PlayerEntityBridge;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public class Excavate {
    private final BlockPos startPos;
    private final Player player;
    private ResourceKey<Block> startId;
    private final Item startTool;
    private int mined = 0;
    private final Level level;

    private final Optional<Holder.Reference<Block>> startBlock;
    private final Deque<BlockPos> points = new ArrayDeque<>();

    private final Direction facing;
    private int shapeSelection = -1;

    public Excavate(BlockPos pos, ResourceLocation startId, Player player, Direction facing) {
        this.startPos = pos;
        this.player = player;
        this.level = player.getCommandSenderWorld();
        this.startId = ResourceKey.create(Registries.BLOCK, startId);

        this.startBlock = BuiltInRegistries.BLOCK.get(startId);

        this.startTool = player.getMainHandItem().getItem();
        this.facing = facing;
    }

    public void startExcavate(int shapeSelection) {
        this.shapeSelection = shapeSelection;
        forceExcavateAt(startPos);
        if (startBlock.isEmpty()
                || (startBlock.orElseThrow().is(startId) && ExcavateHelper.isBlockBlocked(startBlock.orElseThrow()))) {
            return;
        }

        ((PlayerEntityBridge) player).diggus$setExcavating(true);
        while (!points.isEmpty()) {
            spread(points.remove());
        }
        ((PlayerEntityBridge) player).diggus$setExcavating(false);
    }

    private void spread(BlockPos pos) {
        for (BlockPos dirPos : ExcavateTypes.getSpreadType(shapeSelection, facing, startPos, pos)) {
            if (ExcavateHelper.isValidPos(dirPos)) {
                excavateAt(pos.offset(dirPos));
            }
        }
    }

    private void excavateAt(BlockPos pos) {
        if (mined >= ConfigHelper.getConfig().config.maxMinedBlocks) {
            return;
        }
        var block = ExcavateHelper.getBlockAt(level, pos);
        if (!block.isAir()
                && ExcavateHelper.isTheSameBlock(startBlock.orElseThrow(), block, shapeSelection)
                && ExcavateHelper.canMine(player, startTool, level, startPos, pos)
                && isExcavatingAllowed(pos)) {
            forceExcavateAt(pos);
        }
    }

    private boolean isExcavatingAllowed(BlockPos pos) {
        return PlayerBlockBreakEvents.BEFORE.invoker().beforeBlockBreak(level, player, pos, level.getBlockState(pos), level.getBlockEntity(pos)) && ((ServerPlayer) player).gameMode.destroyBlock(pos);
    }

    private void forceExcavateAt(BlockPos pos) {
        points.add(pos);
        mined++;
        if (ConfigHelper.getConfig().config.autoPickup) {
            ExcavateHelper.pickupDrops(level, pos, player);
        }
    }
}
