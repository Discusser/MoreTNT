package io.github.discusser.moretnt.objects.blocks;

import io.github.discusser.moretnt.objects.entities.BasePrimedTNT;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ITNTBlock {
    // Creates a primed entity from the TNT block at the given block position
    BasePrimedTNT createPrimed(Level level, BlockPos blockPos, float size, boolean fire);

    // Creates a primed entity from an item stack
    BasePrimedTNT createPrimedFromStack(Level level, ItemStack stack, BlockPos blockPos, float size, boolean fire);
}
