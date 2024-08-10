package io.discusser.moretnt.events;

import io.discusser.moretnt.MoreTNT;
import io.discusser.moretnt.client.renderers.BaseTNTRenderer;
import io.discusser.moretnt.objects.MoreTNTObject;
import io.discusser.moretnt.objects.registration.MoreTNTObjects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoreTNT.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        MoreTNT.LOGGER.debug("Registering TNT entity renderers");
        for (MoreTNTObject object : MoreTNTObjects.objects) {
            event.registerEntityRenderer(object.primedTNTObject().entityType.get(), pContext -> new BaseTNTRenderer(pContext, object.blockItem().block().get()));
        }
    }
}
