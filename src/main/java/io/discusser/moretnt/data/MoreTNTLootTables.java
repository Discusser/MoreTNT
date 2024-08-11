package io.discusser.moretnt.data;

import io.discusser.moretnt.objects.registration.MoreTNTBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class MoreTNTLootTables extends BlockLootSubProvider {
    protected MoreTNTLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        getKnownBlocks().forEach(this::dropSelf);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return MoreTNTBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
