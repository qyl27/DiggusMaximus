package net.kyrptonaught.diggusmaximus.networking;

import net.kyrptonaught.diggusmaximus.ModConstants;
import net.kyrptonaught.diggusmaximus.ModNetworking;
import net.kyrptonaught.diggusmaximus.config.ConfigHelper;
import net.kyrptonaught.diggusmaximus.excavate.Excavate;
import net.kyrptonaught.diggusmaximus.excavate.Shape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public record ExcavatePacket(BlockPos pos, Identifier id, Shape shape, Direction hitFace, boolean stopBeforeToolBroken, boolean stopAfterToolBroken) implements CustomPacketPayload {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(ModConstants.MOD_ID, "excavate");
    public static final Type<ExcavatePacket> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, ExcavatePacket> CODEC = StreamCodec.of(ExcavatePacket::write, ExcavatePacket::from);

    public ExcavatePacket(BlockPos pos, Identifier id, Shape shape, Direction hitFace) {
        this(pos, id, shape, hitFace, ConfigHelper.getConfig().client.stopBeforeToolBroken, ConfigHelper.getConfig().client.stopAfterToolBroken);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static ExcavatePacket from(FriendlyByteBuf buf) {
        var pos = buf.readBlockPos();
        var id = buf.readIdentifier();
        var shape = buf.readEnum(Shape.class);
        var hitFace = buf.readEnum(Direction.class);
        var stopBeforeToolBroken = buf.readBoolean();
        var stopAfterToolBroken = buf.readBoolean();
        return new ExcavatePacket(pos, id, shape, hitFace, stopBeforeToolBroken, stopAfterToolBroken);
    }

    public static void write(FriendlyByteBuf buf, ExcavatePacket payload) {
        buf.writeBlockPos(payload.pos);
        buf.writeIdentifier(payload.id);
        buf.writeEnum(payload.shape);
        buf.writeEnum(payload.hitFace);
        buf.writeBoolean(payload.stopBeforeToolBroken);
        buf.writeBoolean(payload.stopAfterToolBroken);
    }

    public static void handleServer(ServerPlayer player, ExcavatePacket packet) {
        var server = player.level().getServer();
        server.execute(() -> {
            if (packet.shape() != Shape.NONE && !ConfigHelper.getConfig().common.enableShapes) {
                ModNetworking.sendFailedPacket(player, new ExcavateFailedPacket(ExcavateFailedPacket.Reason.SHAPE_NOT_ENABLED));
                return;
            }

            if (!ConfigHelper.getConfig().common.enabled) {
                ModNetworking.sendFailedPacket(player, new ExcavateFailedPacket(ExcavateFailedPacket.Reason.NOT_ENABLED));
                return;
            }

            if (packet.pos().closerToCenterThan(player.position(), 10)) {
                new Excavate(packet.pos(), packet.id(), player, packet.shape(), packet.hitFace(), packet.stopBeforeToolBroken(), packet.stopAfterToolBroken()).startExcavate();
            }
        });
    }
}
