package io.discusser.moretnt.objects.registration;

import io.discusser.moretnt.MoreTNT;
import io.discusser.moretnt.objects.items.MoreTNTBaseBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MoreTNTItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MoreTNT.MODID);

    public static RegistryObject<MoreTNTBaseBlockItem> registerBlockItem(String name,
                                                                          RegistryObject<? extends Block> block) {
        return ITEMS.register(name, () -> new MoreTNTBaseBlockItem(block.get()));
    }
}
