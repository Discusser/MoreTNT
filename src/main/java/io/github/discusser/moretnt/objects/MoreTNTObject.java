package io.github.discusser.moretnt.objects;

import io.github.discusser.moretnt.objects.blocks.BaseTNTBlock;

// Helper class for holding a TNT object
public record MoreTNTObject(MoreTNTBlockItem<? extends BaseTNTBlock> blockItem,
                            PrimedTNTObject primedTNTObject) {
}
