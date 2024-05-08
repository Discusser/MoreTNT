package io.discusser.moretnt.objects.blocks;

import io.discusser.moretnt.objects.entities.BasePrimedTNT;
import io.discusser.moretnt.objects.entities.PrimedWaterTNT;
import io.discusser.moretnt.objects.registration.MoreTNTEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class WaterTNTBlock extends BaseTNTBlock {
    public WaterTNTBlock(float size, boolean fire) {
        super(size, fire);
    }

    @Override
    public BasePrimedTNT createPrimed(Level level, BlockPos blockPos, float size, boolean fire) {
        return new PrimedWaterTNT(MoreTNTEntities.WATER_TNT.get(), level, blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D, size, fire,
                level.getBlockState(blockPos).getValue(BaseTNTBlock.FACING));
    }
}
