package io.discusser.moretnt;

import com.mojang.logging.LogUtils;
import io.discusser.moretnt.network.MoreTNTPacketHandler;
import io.discusser.moretnt.objects.MoreTNTObject;
import io.discusser.moretnt.objects.PrimedTNTObject;
import io.discusser.moretnt.objects.blocks.BaseTNTBlock;
import io.discusser.moretnt.objects.entities.BasePrimedTNT;
import io.discusser.moretnt.objects.registration.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.function.BiFunction;

@Mod(MoreTNT.MODID)
public class MoreTNT {
    public static final String MODID = "moretnt";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final HashMap<BaseTNTBlock, PrimedTNTObject> blockToPrimedTNTMap = new HashMap<>();

    public MoreTNT() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register all mod objects
        MoreTNTBlocks.BLOCKS.register(modEventBus);
        MoreTNTItems.ITEMS.register(modEventBus);
        MoreTNTEntities.ENTITY_TYPES.register(modEventBus);
        MoreTNTSounds.SOUND_EVENTS.register(modEventBus);
        MoreTNTTabs.TABS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        this.addObjects();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void addObjects() {
        new MoreTNTObjects();
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        for (MoreTNTObject object : MoreTNTObjects.objects) {
            blockToPrimedTNTMap.put(object.blockItem().block().get(), object.primedTNTObject());

            BaseTNTBlock block = object.blockItem().block().get();
            DispenserBlock.registerBehavior(object.blockItem().item().get(),
                    this.dispenserExecute((level, blockPos) -> block.createPrimed(level, blockPos, block.size, block.fire)));
        }

        MoreTNTPacketHandler.register();
    }


    public DefaultDispenseItemBehavior dispenserExecute(BiFunction<Level, BlockPos, BasePrimedTNT> function) {
        return new DefaultDispenseItemBehavior() {
            @Override
            protected @NotNull ItemStack execute(@NotNull BlockSource pSource, @NotNull ItemStack pStack) {
                Level level = pSource.getLevel();
                BlockPos blockpos = pSource.getPos().relative(pSource.getBlockState().getValue(DispenserBlock.FACING));
                BasePrimedTNT instance = function.apply(level, blockpos);
                level.addFreshEntity(instance);
                level.playSound(null, instance.getX(), instance.getY(), instance.getZ(), SoundEvents.TNT_PRIMED,
                        SoundSource.BLOCKS,
                        1.0F, 1.0F);
                level.gameEvent(null, GameEvent.ENTITY_PLACE, blockpos);
                pStack.shrink(1);
                return pStack;
            }
        };
    }
}
