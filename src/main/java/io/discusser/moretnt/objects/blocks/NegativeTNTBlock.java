package io.discusser.moretnt.objects.blocks;

import io.discusser.moretnt.objects.entities.BasePrimedTNT;
import io.discusser.moretnt.objects.entities.PrimedNegativeTNT;
import io.discusser.moretnt.objects.registration.MoreTNTBlocks;
import io.discusser.moretnt.objects.registration.MoreTNTEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class NegativeTNTBlock extends BaseTNTBlock {
    public NegativeTNTBlock(float size, boolean fire) {
        super(size, fire);
    }

    @Override
    public BasePrimedTNT createPrimed(Level level, BlockPos blockPos, float size, boolean fire) {
        Block block = level.getBlockState(blockPos).getBlock();
        EntityType<? extends PrimedTnt> entityType = MoreTNTEntities.NEGATIVE_TNT.get();
        if (block == MoreTNTBlocks.NEGATIVE_TNT_4X.get()) {
            entityType = MoreTNTEntities.NEGATIVE_TNT_4X.get();
        }

        return new PrimedNegativeTNT(entityType, level, blockPos.getX() + 0.5D, blockPos.getY(),
                blockPos.getZ() + 0.5D, size, fire, this.getFacing(level, blockPos));
    }
}
