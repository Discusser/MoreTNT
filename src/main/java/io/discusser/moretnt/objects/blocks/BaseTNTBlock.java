package io.discusser.moretnt.objects.blocks;

import io.discusser.moretnt.MoreTNT;
import io.discusser.moretnt.network.ClientboundEntityFacingPacket;
import io.discusser.moretnt.network.MoreTNTPacketHandler;
import io.discusser.moretnt.objects.entities.BasePrimedTNT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BaseTNTBlock extends TntBlock implements ITNTBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public float size = 4.0F;
    public boolean fire = true;

    public BaseTNTBlock() {
        super(Blocks.TNT.properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    public BaseTNTBlock(float size, boolean fire) {
        this();
        this.size = size;
        this.fire = fire;
    }

    @Override
    public void onCaughtFire(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @Nullable Direction face,
                             @Nullable LivingEntity igniter) {
        if (!world.isClientSide) {
            BasePrimedTNT tnt = this.createPrimed(world, pos, this.size, this.fire);
            world.addFreshEntity(tnt);
            sendEntityFacingPacket(tnt);
            world.playSound(null, tnt.getX(), tnt.getY(), tnt.getZ(), SoundEvents.TNT_PRIMED,
                    SoundSource.BLOCKS, 1.0F, 1.0F);
            world.gameEvent(igniter, GameEvent.PRIME_FUSE, pos);
        }
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        if (!level.isClientSide) {
            BasePrimedTNT tnt = this.createPrimed(level, pos, this.size, this.fire);
            int i = tnt.getFuse();
            tnt.setFuse((short) (level.random.nextInt(i / 4) + i / 8));
            level.addFreshEntity(tnt);
            sendEntityFacingPacket(tnt);
        }

        super.onBlockExploded(state, level, pos, explosion);
    }

    @Override
    public void wasExploded(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Explosion pExplosion) {
    }

    public void sendEntityFacingPacket(BasePrimedTNT entity) {
        ClientboundEntityFacingPacket packet = new ClientboundEntityFacingPacket(entity.getId(), entity.facing);
        MoreTNTPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), packet);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING);
    }

    public Direction getFacing(Level level, BlockPos blockPos) {
        return level.getBlockState(blockPos).getOptionalValue(BaseTNTBlock.FACING).orElse(BasePrimedTNT.DEFAULT_DIRECTION);
    }

    public BasePrimedTNT createPrimed(Level level, BlockPos blockPos, float size, boolean fire) {
        Block block = level.getBlockState(blockPos).getBlock();
        if (block instanceof BaseTNTBlock) {
            return new BasePrimedTNT(MoreTNT.blockToPrimedTNTMap.get(block).entityType.get(), (BaseTNTBlock) block, level,
                    blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D,
                    size, fire, this.getFacing(level, blockPos));
        } else {
            throw new RuntimeException("Tried to create a primed TNT from a block of type '" + block.getClass().getCanonicalName() + "' that does not extend '" + BaseTNTBlock.class.getCanonicalName() + "'");
        }
    }
}
