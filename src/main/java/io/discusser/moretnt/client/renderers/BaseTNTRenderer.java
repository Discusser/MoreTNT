package io.discusser.moretnt.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.discusser.moretnt.objects.blocks.BaseTNTBlock;
import io.discusser.moretnt.objects.entities.BasePrimedTNT;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class BaseTNTRenderer extends EntityRenderer<BasePrimedTNT> {
    public final BlockRenderDispatcher blockRenderer;
    public final BaseTNTBlock block;

    public BaseTNTRenderer(EntityRendererProvider.Context pContext, BaseTNTBlock block) {
        super(pContext);
        this.shadowRadius = 0.5F;
        this.blockRenderer = pContext.getBlockRenderDispatcher();
        this.block = block;
    }

    public void render(BasePrimedTNT pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.0F, 0.5F, 0.0F);
        int i = pEntity.getFuse();
        if ((float) i - pPartialTicks + 1.0F < 10.0F) {
            float f = 1.0F - ((float) i - pPartialTicks + 1.0F) / 10.0F;
            f = Mth.clamp(f, 0.0F, 1.0F);
            f *= f;
            f *= f;
            float f1 = 1.0F + f * 0.3F;
            pPoseStack.scale(f1, f1, f1);
        }

        pPoseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        pPoseStack.translate(-0.5F, -0.5F, 0.5F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        TntMinecartRenderer.renderWhiteSolidBlock(this.blockRenderer, block.defaultBlockState().setValue(BaseTNTBlock.FACING, pEntity.facing), pPoseStack, pBuffer, pPackedLight, i / 5 % 2 == 0);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    @Deprecated
    public @NotNull ResourceLocation getTextureLocation(@NotNull BasePrimedTNT pEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}