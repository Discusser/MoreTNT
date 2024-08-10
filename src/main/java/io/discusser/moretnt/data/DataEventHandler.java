package io.discusser.moretnt.data;

import io.discusser.moretnt.MoreTNT;
import net.minecraft.data.DataProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoreTNT.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataEventHandler {
    public static <T extends DataProvider> void registerClient(GatherDataEvent event, DataProvider.Factory<T> factory) {
        event.getGenerator().addProvider(event.includeClient(), factory);
    }

    public static <T extends DataProvider> void registerServer(GatherDataEvent event, DataProvider.Factory<T> factory) {
        event.getGenerator().addProvider(event.includeServer(), factory);
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        ExistingFileHelper efh = event.getExistingFileHelper();
        registerClient(event, pOutput -> new MoreTNTDataProviders.BlockStates(pOutput, MoreTNT.MODID, efh));
        registerServer(event, MoreTNTDataProviders.Recipes::new);
    }
}
