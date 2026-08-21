package net.kyrptonaught.diggusmaximus.excavate;

import net.kyrptonaught.diggusmaximus.bridge.PlayerEntityBridge;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.kyrptonaught.diggusmaximus.util.HolderHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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

    private final Deque<BlockPos> spreadCores = new ArrayDeque<>();

    private final Shape shape;
    private final Direction hitFace;
    private final Direction facing;
    private final boolean stopBeforeToolBroken;
    private final boolean stopAfterToolBroken;

    public Excavate(BlockPos pos, Identifier startId, ServerPlayer player, Shape shape, Direction hitFace) {
        this(pos, startId, player, shape, hitFace, ConfigHelper.getConfig().common.stopBeforeToolBroken, ConfigHelper.getConfig().common.stopAfterToolBroken);
    }

    public Excavate(BlockPos pos, Identifier startId, ServerPlayer player, Shape shape, Direction hitFace, boolean stopBeforeToolBroken, boolean stopAfterToolBroken) {
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
        onExcavatedAt(startPos);

        if (startBlock == null || startBlock.isAir()) {
            var holder = BuiltInRegistries.BLOCK.get(startId);
            if (holder.isEmpty()) {
                // em, we have no way to determine the id :(
                // Fixme: incorrect capture or bad race?
                return;
            }
            startBlockHolder = holder.get();
        } else {
            startBlockHolder = HolderHelper.get(startBlock.getBlock());
        }

        ((PlayerEntityBridge) player).diggus$setExcavating(true);
        processSpreadCore(startPos, true);
        while (!spreadCores.isEmpty()) {
            var spreadCore = spreadCores.removeFirst();
            processSpreadCore(spreadCore, tryExcavateAt(spreadCore));
        }
        ((PlayerEntityBridge) player).diggus$setExcavating(false);
    }

    private void processSpreadCore(BlockPos current, boolean coreMined) {
        var extraBlocks = shape.getSpreadStrategy().getExtraBlocks(hitFace);
        int extraBlocksMined = 0;

        if (coreMined) {
            for (Vec3i offset : extraBlocks) {
                if (tryExcavateAt(current.offset(offset))) {
                    extraBlocksMined++;
                }
            }
        }

        if (!shape.getSpreadStrategy().canContinueSpread(coreMined, extraBlocksMined, extraBlocks.size())) {
            return;
        }

        for (Vec3i offset : shape.getSpreadStrategy().getNextSpreadCores(hitFace, facing)) {
            spreadCores.addLast(current.offset(offset));
        }
    }

    private boolean tryExcavateAt(BlockPos pos) {
        if (mined >= ConfigHelper.getConfig().common.maxMinedBlocks) {
            return false;
        }

        if (ExcavateHelper.isValid(level, startBlock, startPos, startBlockHolder, pos,
            player, startTool, shape != Shape.NONE, stopBeforeToolBroken, stopAfterToolBroken)
            && ExcavateHelper.tryToExcavate(player, pos)) {
            onExcavatedAt(pos);
            return true;
        }
        return false;
    }

    private void onExcavatedAt(BlockPos pos) {
        mined++;
        if (ConfigHelper.getConfig().common.autoPickup) {
            ExcavateHelper.pickupDrops(level, pos, player);
        }
    }
}
