package io.discusser.moretnt.objects;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public record MoreTNTBlockItem<T extends Block>(RegistryObject<T> block, RegistryObject<? extends Item> item) {
}
