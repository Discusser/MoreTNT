package io.discusser.moretnt.events;

import io.discusser.moretnt.MoreTNT;
import io.discusser.moretnt.client.renderers.BaseTNTRenderer;
import io.discusser.moretnt.objects.MoreTNTObject;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoreTNT.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for (MoreTNTObject object : MoreTNT.objects) {
            event.registerEntityRenderer(object.entity().get(), pContext -> new BaseTNTRenderer(pContext, object.block().get()));
        }
    }
}
