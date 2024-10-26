package io.github.discusser.moretnt.objects.blocks;

import io.github.discusser.moretnt.objects.entities.BasePrimedTNT;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface ITNTBlock {
    BasePrimedTNT createPrimed(Level level, BlockPos blockPos, float size, boolean fire);
}
