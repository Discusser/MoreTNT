package io.discusser.moretnt.network;

import io.discusser.moretnt.MoreTNT;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class MoreTNTPacketHandler {
    private static final String PROTOCOL_VERSION = "1.01";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MoreTNT.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    private static int id = 0;

    public static void register() {
        INSTANCE.messageBuilder(CustomClientboundExplodePacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(CustomClientboundExplodePacket::write)
                .decoder(CustomClientboundExplodePacket::new)
                .consumerMainThread(CustomClientboundExplodePacket::handle)
                .add();
        INSTANCE.messageBuilder(ClientboundEntityFacingPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ClientboundEntityFacingPacket::write)
                .decoder(ClientboundEntityFacingPacket::new)
                .consumerMainThread(ClientboundEntityFacingPacket::handle)
                .add();
    }

    public static void enqueueToClient(Supplier<NetworkEvent.Context> ctx, Runnable runnable) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> runnable));
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
