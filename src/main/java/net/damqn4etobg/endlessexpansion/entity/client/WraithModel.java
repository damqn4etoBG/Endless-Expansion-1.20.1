package net.damqn4etobg.endlessexpansion.entity.client;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.damqn4etobg.endlessexpansion.entity.animations.ModAnimationDefinitions;
import net.damqn4etobg.endlessexpansion.entity.custom.WraithEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class WraithModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart wraith;
	private final ModelPart head;

	public WraithModel(ModelPart root) {
		this.wraith = root.getChild("wraith");
		this.head = wraith.getChild("body").getChild("torso").getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition wraith = partdefinition.addOrReplaceChild("wraith", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = wraith.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -12.0F, -2.0F, 6.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 22).addBox(-2.0F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(28, 0).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 1.5F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(28, 7).addBox(0.0F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(20, 2).addBox(1.0F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(24, 18).addBox(-2.5F, -2.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(14, 18).addBox(2.0F, -8.0F, -3.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(20, 0).addBox(2.0F, -9.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 14).addBox(-2.0F, -10.0F, -3.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(24, 22).addBox(-3.0F, -9.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 22).addBox(-4.0F, -8.0F, -3.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(10, 22).addBox(-1.4F, -13.5F, -0.5F, 0.5F, 3.5F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 32).addBox(-1.9F, -12.0F, -0.5F, 0.5F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 13).addBox(1.4F, -12.0F, -0.5F, 0.5F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).addBox(0.9F, -13.5F, -0.5F, 0.5F, 3.5F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 10).addBox(1.0F, -7.5F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0F, -7.5F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(15, 9).addBox(-2.0F, -8.0F, -2.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(16, 28).addBox(-5.0F, -3.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(24, 29).addBox(3.0F, -2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateWalk(ModAnimationDefinitions.WRAITH_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(((WraithEntity) entity).idleAnimationState, ModAnimationDefinitions.WRAITH_IDLE, ageInTicks, 1f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		wraith.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return wraith;
	}
}