package io.discusser.moretnt.objects.registration;

import io.discusser.moretnt.MoreTNT;
import io.discusser.moretnt.objects.items.MoreTNTBaseBlockItem;
import io.discusser.moretnt.objects.items.MoreTNTBaseItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MoreTNTItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MoreTNT.MODID);

    public static final RegistryObject<MoreTNTBaseBlockItem> NEGATIVE_TNT = registerBlockItem("negative_tnt",
            MoreTNTBlocks.NEGATIVE_TNT);
    public static final RegistryObject<MoreTNTBaseBlockItem> SHUFFLE_TNT = registerBlockItem("shuffle_tnt",
            MoreTNTBlocks.SHUFFLE_TNT);
    public static final RegistryObject<MoreTNTBaseBlockItem> SHUFFLE_TNT_4X = registerBlockItem("shuffle_tnt_4x",
            MoreTNTBlocks.SHUFFLE_TNT_4X);
    public static final RegistryObject<MoreTNTBaseBlockItem> KNOCKBACK_TNT = registerBlockItem("knockback_tnt",
            MoreTNTBlocks.KNOCKBACK_TNT);
    public static final RegistryObject<MoreTNTBaseBlockItem> WATER_TNT = registerBlockItem("water_tnt",
            MoreTNTBlocks.WATER_TNT);
    public static final RegistryObject<MoreTNTBaseBlockItem> LAVA_TNT = registerBlockItem("lava_tnt",
            MoreTNTBlocks.LAVA_TNT);
    public static final RegistryObject<MoreTNTBaseBlockItem> SNOW_TNT = registerBlockItem("snow_tnt",
            MoreTNTBlocks.SNOW_TNT);
    public static final RegistryObject<MoreTNTBaseBlockItem> SNOW_TNT_4X = registerBlockItem("snow_tnt_4x",
            MoreTNTBlocks.SNOW_TNT_4X);
    public static final RegistryObject<MoreTNTBaseBlockItem> CAT_TNT = registerBlockItem("cat_tnt",
            MoreTNTBlocks.CAT_TNT);
    public static final RegistryObject<MoreTNTBaseBlockItem> DOME_TNT = registerBlockItem("dome_tnt",
            MoreTNTBlocks.DOME_TNT);
    public static final RegistryObject<MoreTNTBaseBlockItem> FIRE_TNT = registerBlockItem("fire_tnt",
            MoreTNTBlocks.FIRE_TNT);

    // Shorthands for registering items
    private static RegistryObject<MoreTNTBaseItem> registerItem(String name) {
        return ITEMS.register(name, MoreTNTBaseItem::new);
    }

    private static RegistryObject<MoreTNTBaseBlockItem> registerBlockItem(String name,
                                                                          RegistryObject<? extends Block> block) {
        return ITEMS.register(name, () -> new MoreTNTBaseBlockItem(block.get()));
    }
}
