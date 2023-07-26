package io.discusser.moretnt.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import io.discusser.moretnt.objects.entities.BasePrimedTNT;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class BaseTNTRenderer extends EntityRenderer<BasePrimedTNT> {
    public final BlockRenderDispatcher blockRenderer;
    public final Block block;

    public BaseTNTRenderer(EntityRendererProvider.Context pContext, Block block) {
        super(pContext);
        this.shadowRadius = 0.5F;
        this.blockRenderer = pContext.getBlockRenderDispatcher();
        this.block = block;
    }

    public void render(BasePrimedTNT pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0D, 0.5D, 0.0D);
        int i = pEntity.getFuse();
        if ((float)i - pPartialTicks + 1.0F < 10.0F) {
            float f = 1.0F - ((float)i - pPartialTicks + 1.0F) / 10.0F;
            f = Mth.clamp(f, 0.0F, 1.0F);
            f *= f;
            f *= f;
            float f1 = 1.0F + f * 0.3F;
            pMatrixStack.scale(f1, f1, f1);
        }

        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
        pMatrixStack.translate(-0.5D, -0.5D, 0.5D);
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
        TntMinecartRenderer.renderWhiteSolidBlock(this.blockRenderer, block.defaultBlockState(), pMatrixStack,
                pBuffer, pPackedLight, i / 5 % 2 == 0);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    /**
     * Returns the location of an entity's texture.
     * I don't know what this is supposed to return, I just copied this from the TNT renderer.
     */
    @SuppressWarnings("deprecation")
    public @NotNull ResourceLocation getTextureLocation(@NotNull BasePrimedTNT pEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
