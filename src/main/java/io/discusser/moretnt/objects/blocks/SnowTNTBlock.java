package io.discusser.moretnt.objects.blocks;

import io.discusser.moretnt.objects.entities.BasePrimedTNT;
import io.discusser.moretnt.objects.entities.PrimedSnowTNT;
import io.discusser.moretnt.objects.registration.MoreTNTBlocks;
import io.discusser.moretnt.objects.registration.MoreTNTEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class SnowTNTBlock extends BaseTNTBlock {
    public SnowTNTBlock(float size, boolean fire) {
        super(size, fire);
    }

    @Override
    public BasePrimedTNT createPrimed(Level level, BlockPos blockPos, float size, boolean fire) {
        Block block = level.getBlockState(blockPos).getBlock();
        EntityType<? extends BasePrimedTNT> entityType = MoreTNTEntities.SNOW_TNT.get();
        if (block == MoreTNTBlocks.SNOW_TNT_4X.get()) {
            entityType = MoreTNTEntities.SNOW_TNT_4X.get();
        }
        return new PrimedSnowTNT(entityType, level, blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D, size, fire,
                level.getBlockState(blockPos).getOptionalValue(BaseTNTBlock.FACING).orElse(BasePrimedTNT.DEFAULT_DIRECTION));
    }
}
