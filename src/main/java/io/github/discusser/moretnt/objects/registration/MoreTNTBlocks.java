package io.github.discusser.moretnt.objects.registration;

import io.github.discusser.moretnt.MoreTNT;
import io.github.discusser.moretnt.objects.MoreTNTBlockItem;
import io.github.discusser.moretnt.objects.blocks.BaseTNTBlock;
import io.github.discusser.moretnt.objects.items.MoreTNTBaseBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MoreTNTBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MoreTNT.MODID);

    public static final MoreTNTBlockItem<BaseTNTBlock> NEGATIVE_TNT = registerBlockWithItem("negative_tnt",
            () -> new BaseTNTBlock(4.0F, false));
    public static final MoreTNTBlockItem<BaseTNTBlock> NEGATIVE_TNT_4X = registerBlockWithItem("negative_tnt_4x",
            () -> new BaseTNTBlock(16.0F, false));
    public static final MoreTNTBlockItem<BaseTNTBlock> SHUFFLE_TNT = registerBlockWithItem("shuffle_tnt",
            () -> new BaseTNTBlock(4.0F, false));
    public static final MoreTNTBlockItem<BaseTNTBlock> SHUFFLE_TNT_4X = registerBlockWithItem("shuffle_tnt_4x",
            () -> new BaseTNTBlock(16.0F, false));
    public static final MoreTNTBlockItem<BaseTNTBlock> KNOCKBACK_TNT = registerBlockWithItem("knockback_tnt",
            () -> new BaseTNTBlock(4.0F, false));
    public static final MoreTNTBlockItem<BaseTNTBlock> WATER_TNT = registerBlockWithItem("water_tnt",
            () -> new BaseTNTBlock(2.0F, false));
    public static final MoreTNTBlockItem<BaseTNTBlock> LAVA_TNT = registerBlockWithItem("lava_tnt",
            () -> new BaseTNTBlock(2.0F, false));
    public static final MoreTNTBlockItem<BaseTNTBlock> COBBLESTONE_TNT = registerBlockWithItem("cobblestone_tnt",
            () -> new BaseTNTBlock(4.0F, false));
    public static final MoreTNTBlockItem<BaseTNTBlock> STONE_TNT = registerBlockWithItem("stone_tnt",
            () -> new BaseTNTBlock(4.0F, false));
    public static final MoreTNTBlockItem<BaseTNTBlock> OBSIDIAN_TNT = registerBlockWithItem("obsidian_tnt",
            () -> new BaseTNTBlock(2.0F, false));
    public static final MoreTNTBlockItem<BaseTNTBlock> SNOW_TNT = registerBlockWithItem("snow_tnt",
            () -> new BaseTNTBlock(4.0F, false));
    public static final MoreTNTBlockItem<BaseTNTBlock> SNOW_TNT_4X = registerBlockWithItem("snow_tnt_4x",
            () -> new BaseTNTBlock(16.0F, false));
    public static final MoreTNTBlockItem<BaseTNTBlock> CAT_TNT = registerBlockWithItem("cat_tnt",
            () -> new BaseTNTBlock(4.0F, false));
    public static final MoreTNTBlockItem<BaseTNTBlock> DOME_TNT = registerBlockWithItem("dome_tnt",
            () -> new BaseTNTBlock(5.0F, false));
    public static final MoreTNTBlockItem<BaseTNTBlock> FIRE_TNT = registerBlockWithItem("fire_tnt",
            () -> new BaseTNTBlock(4.0F, true));

    @SuppressWarnings("unused")
    public static final MoreTNTBlockItem<Block> COMPRESSED_SAND = registerBlockWithItem("compressed_sand",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)
                    .strength(2.0F).requiresCorrectToolForDrops()));


    public static boolean isTNT(BlockState blockState) {
        return blockState.getBlock() instanceof BaseTNTBlock;
    }

    public static <T extends Block> MoreTNTBlockItem<T> registerBlockWithItem(String name, Supplier<T> constructor) {
        DeferredBlock<T> block = BLOCKS.register(name, constructor);
        DeferredItem<MoreTNTBaseBlockItem> item = MoreTNTItems.registerBlockItem(name, block);
        return new MoreTNTBlockItem<>(block, item);
    }
}
