package io.github.discusser.moretnt.events;

import io.github.discusser.moretnt.MoreTNT;
import io.github.discusser.moretnt.client.renderers.BaseTNTRenderer;
import io.github.discusser.moretnt.objects.MoreTNTObject;
import io.github.discusser.moretnt.objects.registration.MoreTNTObjects;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = MoreTNT.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        MoreTNT.LOGGER.debug("Registering TNT entity renderers");
        for (MoreTNTObject object : MoreTNTObjects.objects) {
            event.registerEntityRenderer(object.primedTNTObject().entityType.get(), pContext -> new BaseTNTRenderer(pContext, object.blockItem().block().get()));
        }
    }
}
