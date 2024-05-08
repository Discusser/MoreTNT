package io.discusser.moretnt.network;

import io.discusser.moretnt.MoreTNT;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class MoreTNTPacketHandler {
    private static final String PROTOCOL_VERSION = "1.0.1";
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
}
