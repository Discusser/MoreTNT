package io.discusser.moretnt.objects.items;

import io.discusser.moretnt.MoreTNT;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class MoreTNTBaseBlockItem extends BlockItem {
    public MoreTNTBaseBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties.tab(MoreTNT.TAB));
    }

    public MoreTNTBaseBlockItem(Block block) {
        this(block, new Item.Properties());
    }
}
