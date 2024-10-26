package io.github.discusser.moretnt.data;

import io.github.discusser.moretnt.MoreTNT;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = MoreTNT.MODID, bus = EventBusSubscriber.Bus.MOD)
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
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        registerClient(event, pOutput -> new MoreTNTDataProviders.BlockStates(pOutput, MoreTNT.MODID, efh));
        registerServer(event, pOutput -> new MoreTNTDataProviders.Recipes(pOutput, lookupProvider));
        registerServer(event, pOutput -> new MoreTNTDataProviders.LootTables(pOutput, lookupProvider));
        registerServer(event, pOutput -> new MoreTNTDataProviders.BlockTags(pOutput, lookupProvider, MoreTNT.MODID, efh));
    }
}
