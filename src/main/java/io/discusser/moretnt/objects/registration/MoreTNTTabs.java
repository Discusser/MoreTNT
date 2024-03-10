package io.discusser.moretnt.objects.registration;

import io.discusser.moretnt.MoreTNT;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class MoreTNTTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreTNT.MODID);

    public static final RegistryObject<CreativeModeTab> ITEMS = TABS.register("items", () -> CreativeModeTab.builder()
            // Set name of tab to display
            .title(Component.translatable("itemGroup.moretnt.items"))
            // Set icon of creative tab
            .icon(() -> new ItemStack(MoreTNTBlocks.NEGATIVE_TNT.get()))
            // Add default items to tab
            .displayItems((params, output) -> {
                MoreTNTItems.ITEMS.getEntries().forEach(object ->
                        output.accept(object.get())
                );
            })
            .build()
    );
}
