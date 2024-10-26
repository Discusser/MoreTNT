package io.github.discusser.moretnt.data;

import io.github.discusser.moretnt.MoreTNT;
import io.github.discusser.moretnt.objects.MoreTNTBlockItem;
import io.github.discusser.moretnt.objects.blocks.BaseTNTBlock;
import io.github.discusser.moretnt.objects.registration.MoreTNTBlocks;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class MoreTNTDataProviders {
    public static class BlockTags extends BlockTagsProvider {
        public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, modId, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider pProvider) {
            this.tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_SHOVEL).add(MoreTNTBlocks.COMPRESSED_SAND.block().get());
        }
    }

    public static class LootTables extends LootTableProvider {
        public LootTables(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
            super(pOutput, Set.of(), List.of(
                    new LootTableProvider.SubProviderEntry(MoreTNTLootTables::new, LootContextParamSets.BLOCK)
            ), pRegistries);
        }
    }

    public static class Recipes extends RecipeProvider {
        public Recipes(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
            super(pOutput, pRegistries);
        }

        public ShapedRecipeBuilder shapedRecipeBuilder(ItemLike output, ItemLike inputCondition) {
            return shapedRecipeBuilder(output, inputCondition, 1);
        }

        public ShapedRecipeBuilder shapedRecipeBuilder(ItemLike output, ItemLike inputCondition, int count) {
            return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output, count).unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(inputCondition));
        }

        public ShapelessRecipeBuilder shapelessRecipeBuilder(ItemLike output, ItemLike inputCondition) {
            return shapelessRecipeBuilder(output, inputCondition, 1);
        }

        public ShapelessRecipeBuilder shapelessRecipeBuilder(ItemLike output, ItemLike inputCondition, int count) {
            return ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, count).unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(inputCondition));
        }

        public ShapedRecipeBuilder tntShapedRecipeBuilder(MoreTNTBlockItem<?> output) {
            return shapedRecipeBuilder(output.item().get(), Items.TNT);
        }

        public ShapedRecipeBuilder tntSurround(MoreTNTBlockItem<?> output, ItemLike surrounding) {
            return tntSurround(output, surrounding, Items.TNT);
        }

        public ShapedRecipeBuilder tntSurround(MoreTNTBlockItem<?> output, ItemLike surrounding, ItemLike surrounded) {
            return tntShapedRecipeBuilder(output)
                    .pattern("AAA")
                    .pattern("ATA")
                    .pattern("AAA")
                    .define('A', surrounding)
                    .define('T', surrounded);
        }

        public ShapedRecipeBuilder packNine(ItemLike output, ItemLike input) {
            return shapedRecipeBuilder(output, input, 1).pattern("AAA").pattern("AAA").pattern("AAA").define('A', input);
        }

        public ShapedRecipeBuilder packFour(ItemLike output, ItemLike input) {
            return shapedRecipeBuilder(output, input, 1).pattern("AA").pattern("AA").define('A', input);
        }

        public ShapelessRecipeBuilder unpackNine(ItemLike output, ItemLike input) {
            return shapelessRecipeBuilder(output, input, 9).requires(input);
        }

        public ShapelessRecipeBuilder unpackFour(ItemLike output, ItemLike input) {
            return shapelessRecipeBuilder(output, input, 4).requires(input);
        }

        public ResourceLocation packRecipeId(String prefix, ItemLike output) {
            return ResourceLocation.fromNamespaceAndPath(MoreTNT.MODID, prefix + "_into_" + RecipeBuilder.getDefaultRecipeId(output).getPath());
        }

        public void packUnpackFour(ItemLike packed, ItemLike unpacked, RecipeOutput pRecipeOutput) {
            packFour(packed, unpacked).save(pRecipeOutput, packRecipeId("pack_four", packed));
            unpackFour(unpacked, packed).save(pRecipeOutput, packRecipeId("unpack_four", unpacked));
        }

        public void packUnpackNine(ItemLike packed, ItemLike unpacked, RecipeOutput pRecipeOutput) {
            packNine(packed, unpacked).save(pRecipeOutput, packRecipeId("pack_nine", packed));
            unpackNine(unpacked, packed).save(pRecipeOutput, packRecipeId("unpack_nine", unpacked));
        }

        @Override
        protected void buildRecipes(@NotNull RecipeOutput pRecipeOutput) {
            packUnpackNine(MoreTNTBlocks.COMPRESSED_SAND.block().get(), Items.SAND, pRecipeOutput);
            tntSurround(MoreTNTBlocks.DOME_TNT, MoreTNTBlocks.COMPRESSED_SAND.item().get()).save(pRecipeOutput);
            tntSurround(MoreTNTBlocks.FIRE_TNT, Items.FIRE_CHARGE).save(pRecipeOutput);
            tntSurround(MoreTNTBlocks.KNOCKBACK_TNT, Items.FEATHER).save(pRecipeOutput);
            tntSurround(MoreTNTBlocks.LAVA_TNT, Items.LAVA_BUCKET).save(pRecipeOutput);
            tntSurround(MoreTNTBlocks.NEGATIVE_TNT, Items.AMETHYST_SHARD).save(pRecipeOutput);
            tntSurround(MoreTNTBlocks.SHUFFLE_TNT, Items.CHORUS_FRUIT).save(pRecipeOutput);
            tntSurround(MoreTNTBlocks.SNOW_TNT, Items.SNOW_BLOCK).save(pRecipeOutput);
            tntSurround(MoreTNTBlocks.WATER_TNT, Items.WATER_BUCKET).save(pRecipeOutput);
            tntShapedRecipeBuilder(MoreTNTBlocks.COBBLESTONE_TNT).pattern("W L").pattern("WTL").pattern("W L").define('W', Items.WATER_BUCKET).define('L', Items.LAVA_BUCKET).define('T', Items.TNT).save(pRecipeOutput);
            tntShapedRecipeBuilder(MoreTNTBlocks.STONE_TNT).pattern("L").pattern("T").define('L', Items.LAVA_BUCKET).define('T', MoreTNTBlocks.WATER_TNT.item().get()).save(pRecipeOutput);
            tntShapedRecipeBuilder(MoreTNTBlocks.OBSIDIAN_TNT).pattern("W").pattern("T").define('W', Items.WATER_BUCKET).define('T', MoreTNTBlocks.LAVA_TNT.item().get()).save(pRecipeOutput);

            MoreTNTBlocks.BLOCKS.getEntries().forEach(blockRegistryObject -> {
                if (blockRegistryObject.getKey() != null) {
                    String blockPath = blockRegistryObject.getKey().location().getPath();
                    if (blockPath.endsWith("_4x")) {
                        ResourceLocation unpackedBlockLocation = ResourceLocation.fromNamespaceAndPath(MoreTNT.MODID, blockPath.replace("_4x", ""));
                        Block unpackedBlock = BuiltInRegistries.BLOCK.get(unpackedBlockLocation);
                        if (unpackedBlock != Blocks.AIR) {
                            packUnpackFour(blockRegistryObject.get(), unpackedBlock, pRecipeOutput);
                        } else {
                            MoreTNT.LOGGER.warn("Could not create recipes for block '" + blockRegistryObject + "' because there is no block that matches '" + unpackedBlockLocation + "'");
                        }
                    }
                } else {
                    MoreTNT.LOGGER.warn("Could not create recipes for block '" + blockRegistryObject + "' because key is null");
                }
            });
        }
    }

    public static class BlockStates extends BlockStateProvider {
        public BlockStates(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
            super(output, modid, exFileHelper);
        }

        public ModelFile tnt(String name) {
            String texture = "block/" + name;
            return models().cubeBottomTop(name, modLoc(texture + "_side"), modLoc(texture + "_bottom"), modLoc(texture + "_top"));
        }

        public ModelFile tnt4x(String baseName) {
            String texture = "block/" + baseName;
            String name = baseName + "_4x";
            return models().withExistingParent(name, modLoc("block/tnt_4x"))
                    .texture("bottom", texture + "_bottom")
                    .texture("side", texture + "_side")
                    .texture("top", texture + "_top")
                    .texture("particle", texture + "_side")
                    .texture("overlay_top", "block/overlay/4x_overlay");
        }

        public void simpleBlockState(Block block, ModelFile modelFile) {
            getVariantBuilder(block).forAllStates(blockState -> ConfiguredModel.builder().modelFile(modelFile).build());
        }

        public void tntOrientableBlockState(BaseTNTBlock block, ModelFile modelFile) {
            getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder()
                    .modelFile(modelFile)
                    .rotationY((int) state.getValue(BaseTNTBlock.FACING).getOpposite().toYRot())
                    .build());
        }

        public void simpleItemModel(String name) {
            itemModels().withExistingParent(name, modLoc("block/" + name));
        }

        @Override
        protected void registerStatesAndModels() {
            MoreTNTBlocks.BLOCKS.getEntries().forEach(blockRegistryObject -> {
                Block block = blockRegistryObject.get();
                if (blockRegistryObject.getKey() != null) {
                    String name = blockRegistryObject.getKey().location().getPath();
                    if (block instanceof BaseTNTBlock) {
                        boolean is4x = name.endsWith("4x");

                        if (is4x) {
                            tntOrientableBlockState((BaseTNTBlock) block, tnt4x(name.replace("_4x", "")));
                        } else {
                            simpleBlockState(block, tnt(name));
                        }
                    } else {
                        // Default block state, block model for every other block
                        simpleBlockState(block, cubeAll(block));
                    }

                    simpleItemModel(name);
                }
            });
        }
    }
}