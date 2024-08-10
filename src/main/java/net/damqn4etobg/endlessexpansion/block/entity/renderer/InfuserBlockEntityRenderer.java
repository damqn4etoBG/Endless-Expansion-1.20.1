package net.damqn4etobg.endlessexpansion.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.damqn4etobg.endlessexpansion.block.entity.InfuserBlockEntity;
import net.damqn4etobg.endlessexpansion.util.renderer.BlockEntityFluidRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

public class InfuserBlockEntityRenderer implements BlockEntityRenderer<InfuserBlockEntity> {
    private final BlockEntityRendererProvider.Context context;
    public InfuserBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(InfuserBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStackOutput = blockEntity.getRenderStackOutput();
        ItemStack itemStackInput1 = blockEntity.getRenderStackInput1();
        ItemStack itemStackInput2 = blockEntity.getRenderStackInput2();
        ItemStack itemStackLuminite = blockEntity.getRenderStackLuminite();
        Direction blockEntityFacing = blockEntity.getBlockState().getValue(BlockStateProperties.FACING);
        FluidStack fluidStack = blockEntity.getFluidTank().getFluid();
        Level level = blockEntity.getLevel();
        BlockPos pos = blockEntity.getBlockPos();

        if(level == null) {
            return;
        }

        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(fluidStack);

        if(stillTexture == null) {
            return;
        }

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);

        int tintColor = fluidTypeExtensions.getTintColor(fluidStack.getFluid().defaultFluidState(), level, pos);
        float height = (((float) blockEntity.getFluidTank().getFluidAmount() / blockEntity.getFluidTank().getCapacity()) * 0.37f);

        VertexConsumer builder = buffer.getBuffer(ItemBlockRenderTypes.getRenderLayer(fluidStack.getFluid().defaultFluidState()));
        
        switch (blockEntityFacing) {
            case NORTH -> BlockEntityFluidRenderer.drawCube(builder, poseStack, 0.1875f, 0.625f, 0.6875f, 0.125f, height, 0.125f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), getLightLevel(level, pos), tintColor);
            case SOUTH -> BlockEntityFluidRenderer.drawCube(builder, poseStack, 0.6875f, 0.625f, 0.1875f, 0.125f, height, 0.125f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), getLightLevel(level, pos), tintColor);
            case EAST -> BlockEntityFluidRenderer.drawCube(builder, poseStack, 0.1875f, 0.625f, 0.1875f, 0.125f, height, 0.125f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), getLightLevel(level, pos), tintColor);
            case WEST -> BlockEntityFluidRenderer.drawCube(builder, poseStack, 0.6875f, 0.625f, 0.6875f, 0.125f, height, 0.125f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), getLightLevel(level, pos), tintColor);
        }
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
