package net.kyrptonaught.diggusmaximus.excavate;

import net.kyrptonaught.diggusmaximus.bridge.PlayerEntityBridge;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;

public class Excavate {
    private final BlockPos startPos;
    private final ServerPlayer player;
    private final ResourceKey<Block> startId;
    private final Item startTool;
    private int mined = 0;
    private final Level level;

    @Nullable
    private final BlockState startBlock;
    private Holder<Block> startBlockHolder;

    private final Deque<BlockPos> points = new ArrayDeque<>();

    private final Shape shape;
    private final Direction hitFace;
    private final Direction facing;
    private final boolean stopBeforeToolBroken;
    private final boolean stopAfterToolBroken;

    public Excavate(BlockPos pos, ResourceLocation startId, ServerPlayer player, Shape shape, Direction hitFace) {
        this(pos, startId, player, shape, hitFace, ConfigHelper.getConfig().common.stopBeforeToolBroken, ConfigHelper.getConfig().common.stopAfterToolBroken);
    }

    public Excavate(BlockPos pos, ResourceLocation startId, ServerPlayer player, Shape shape, Direction hitFace, boolean stopBeforeToolBroken, boolean stopAfterToolBroken) {
        this.startPos = pos;
        this.player = player;
        this.level = player.level();
        this.startId = ResourceKey.create(Registries.BLOCK, startId);

        this.startBlock = ExcavateHelper.getBlockAt(level, pos);

        this.startTool = player.getMainHandItem().getItem();
        this.shape = shape;
        this.hitFace = hitFace;
        this.facing = player.getNearestViewDirection();

        this.stopBeforeToolBroken = stopBeforeToolBroken;
        this.stopAfterToolBroken = stopAfterToolBroken;
    }

    public void startExcavate() {
        forceExcavateAt(startPos);

        if (startBlock == null || startBlock.isAir()) {
            var holder = BuiltInRegistries.BLOCK.get(startId);
            if (holder.isEmpty()) {
                // em, we have no way to determine the id :(
                // Fixme: incorrect capture or bad race?
                return;
            }
            startBlockHolder = holder.get();
        } else {
            startBlockHolder = startBlock.getBlockHolder();
        }

        ((PlayerEntityBridge) player).diggus$setExcavating(true);
        while (!points.isEmpty()) {
            spread(points.remove());
        }
        ((PlayerEntityBridge) player).diggus$setExcavating(false);
    }

    private void spread(BlockPos pos) {
        for (Vec3i offset : ExcavateSpreadHelper.getSpreadShape(shape, hitFace, facing, startPos, pos)) {
            if (ExcavateHelper.isValidOffset(offset)) {
                excavateAt(pos.offset(offset));
            }
        }
    }

    private void excavateAt(BlockPos pos) {
        if (mined >= ConfigHelper.getConfig().common.maxMinedBlocks) {
            return;
        }
        var block = ExcavateHelper.getBlockAt(level, pos);
        if (block != null
                && ExcavateHelper.isTheSameBlock(startBlockHolder, block.getBlockHolder(), shape != Shape.NONE)
                && ExcavateHelper.canMine(level, startPos, pos)
                && ExcavateHelper.checkTool(player, startTool, stopBeforeToolBroken, stopAfterToolBroken)
                && !ExcavateHelper.isBlockBlocked(block.getBlockHolder())
                && ExcavateHelper.tryToExcavate(player, pos)) {
            forceExcavateAt(pos);
        }
    }

    private void forceExcavateAt(BlockPos pos) {
        points.add(pos);
        mined++;
        if (ConfigHelper.getConfig().common.autoPickup) {
            ExcavateHelper.pickupDrops(level, pos, player);
        }
    }
}
