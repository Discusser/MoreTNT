package io.github.discusser.moretnt.events;

import io.github.discusser.moretnt.MoreTNT;
import io.github.discusser.moretnt.network.ClientboundEntityFacingPacket;
import io.github.discusser.moretnt.network.ClientboundEntityFacingPacketHandler;
import io.github.discusser.moretnt.network.CustomClientboundExplodePacket;
import io.github.discusser.moretnt.network.CustomClientboundExplodePacketHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = MoreTNT.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class NetworkEvents {
    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1.01");
        registrar.playToClient(
                ClientboundEntityFacingPacket.TYPE,
                ClientboundEntityFacingPacket.STREAM_CODEC,
                new ClientboundEntityFacingPacketHandler()
        );
        registrar.playToClient(
                CustomClientboundExplodePacket.TYPE,
                CustomClientboundExplodePacket.STREAM_CODEC,
                new CustomClientboundExplodePacketHandler()
        );
    }
}
