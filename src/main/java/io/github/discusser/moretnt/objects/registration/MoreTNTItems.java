package io.github.discusser.moretnt.objects.registration;

import io.github.discusser.moretnt.MoreTNT;
import io.github.discusser.moretnt.objects.items.MoreTNTBaseBlockItem;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MoreTNTItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MoreTNT.MODID);

    public static DeferredItem<MoreTNTBaseBlockItem> registerBlockItem(String name,
                                                                       DeferredBlock<?> block) {
        return ITEMS.register(name, () -> new MoreTNTBaseBlockItem(block.get()));
    }
}
