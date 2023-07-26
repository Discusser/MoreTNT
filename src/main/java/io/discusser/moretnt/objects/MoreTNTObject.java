package io.discusser.moretnt.objects;

import io.discusser.moretnt.MoreTNT;
import io.discusser.moretnt.objects.blocks.BaseTNTBlock;
import io.discusser.moretnt.objects.entities.BasePrimedTNT;
import io.discusser.moretnt.objects.items.MoreTNTBaseBlockItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

// Helper class for holding a TNT object
public record MoreTNTObject(RegistryObject<? extends BaseTNTBlock> block,
                            RegistryObject<EntityType<? extends BasePrimedTNT>> entity,
                            RegistryObject<? extends MoreTNTBaseBlockItem> item) {
}
