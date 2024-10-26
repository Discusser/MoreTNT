package io.github.discusser.moretnt.objects;

import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public record MoreTNTBlockItem<T extends Block>(DeferredBlock<T> block, DeferredItem<?> item) {
}
