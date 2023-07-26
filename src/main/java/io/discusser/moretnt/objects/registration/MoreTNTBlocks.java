package io.discusser.moretnt.objects.registration;

import io.discusser.moretnt.MoreTNT;
import io.discusser.moretnt.objects.blocks.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MoreTNTBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MoreTNT.MODID);

    public static final RegistryObject<NegativeTNTBlock> NEGATIVE_TNT = BLOCKS.register("negative_tnt",
            () -> new NegativeTNTBlock(4.0F, false));
    public static final RegistryObject<ShuffleTNTBlock> SHUFFLE_TNT = BLOCKS.register("shuffle_tnt",
            () -> new ShuffleTNTBlock(4.0F, false));
    public static final RegistryObject<ShuffleTNTBlock> SHUFFLE_TNT_4X = BLOCKS.register("shuffle_tnt_4x",
            () -> new ShuffleTNTBlock(16.0F, false));
    public static final RegistryObject<KnockbackTNTBlock> KNOCKBACK_TNT = BLOCKS.register("knockback_tnt",
            () -> new KnockbackTNTBlock(4.0F, false));
    public static final RegistryObject<WaterTNTBlock> WATER_TNT = BLOCKS.register("water_tnt",
            () -> new WaterTNTBlock(2.0F, false));
    public static final RegistryObject<LavaTNTBlock> LAVA_TNT = BLOCKS.register("lava_tnt",
            () -> new LavaTNTBlock(2.0F, false));
    public static final RegistryObject<SnowTNTBlock> SNOW_TNT = BLOCKS.register("snow_tnt",
            () -> new SnowTNTBlock(4.0F, false));
    public static final RegistryObject<SnowTNTBlock> SNOW_TNT_4X = BLOCKS.register("snow_tnt_4x",
            () -> new SnowTNTBlock(16.0F, false));
    public static final RegistryObject<CatTNTBlock> CAT_TNT = BLOCKS.register("cat_tnt",
            () -> new CatTNTBlock(4.0F, false));
    public static final RegistryObject<DomeTNTBlock> DOME_TNT = BLOCKS.register("dome_tnt",
            () -> new DomeTNTBlock(4.0F, false));
    public static final RegistryObject<FireTNTBlock> FIRE_TNT = BLOCKS.register("fire_tnt",
            () -> new FireTNTBlock(4.0F, true));


    public static boolean isTNT(BlockState blockState) {
        return blockState.getBlock() instanceof BaseTNTBlock;
    }
}
