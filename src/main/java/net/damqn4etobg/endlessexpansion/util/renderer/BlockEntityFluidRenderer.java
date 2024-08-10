package net.damqn4etobg.endlessexpansion.util.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public class BlockEntityFluidRenderer {
    public static void drawVertex(VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, int color) {
        builder.vertex(poseStack.last().pose(), x, y, z)
                .color(color)
                .uv(u, v)
                .uv2(packedLight)
                .normal(1, 0, 0)
                .endVertex();
    }

    public static void drawQuad(VertexConsumer builder, PoseStack poseStack, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, float u1, float v1, float u2, float v2, int packedLight, int color) {
        drawVertex(builder, poseStack, x1, y1, z1, u1, v1, packedLight, color);
        drawVertex(builder, poseStack, x2, y2, z2, u1, v2, packedLight, color);
        drawVertex(builder, poseStack, x3, y3, z3, u2, v2, packedLight, color);
        drawVertex(builder, poseStack, x4, y4, z4, u2, v1, packedLight, color);
    }

    public static void drawCube(VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float width, float height, float depth, float u1, float v1, float u2, float v2, int packedLight, int color) {
        float x2 = x + width;
        float y2 = y + height;
        float z2 = z + depth;
        //RenderSystem.disableCull();

        drawQuad(builder, poseStack, x, y, z2, x2, y, z2, x2, y2, z2, x, y2, z2, u1, v1, u2, v2, packedLight, color);

        // Back face (NORTH)
        drawQuad(builder, poseStack, x2, y, z, x, y, z, x, y2, z, x2, y2, z, u1, v1, u2, v2, packedLight, color);

        drawQuad(builder, poseStack, x, y, z, x, y, z2, x, y2, z2, x, y2, z, u1, v1, u2, v2, packedLight, color);

        // Right face (EAST)
        drawQuad(builder, poseStack, x2, y, z2, x2, y, z, x2, y2, z, x2, y2, z2, u1, v1, u2, v2, packedLight, color);

        drawQuad(builder, poseStack, x, y2, z, x2, y2, z, x2, y2, z2, x, y2, z2, u1, v1, u2, v2, packedLight, color);

        // Bottom face (DOWN)
        drawQuad(builder, poseStack, x, y, z2, x2, y, z2, x2, y, z, x, y, z, u1, v1, u2, v2, packedLight, color);

        // Make the top face visible from the bottom by drawing it again with reversed normals
        drawQuad(builder, poseStack, x, y2, z2, x2, y2, z2, x2, y2, z, x, y2, z, u1, v1, u2, v2, packedLight, color);
    }
}
