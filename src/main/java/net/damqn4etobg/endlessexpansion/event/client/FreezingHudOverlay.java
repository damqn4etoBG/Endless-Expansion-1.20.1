package net.damqn4etobg.endlessexpansion.event.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.dimension.ModDimensions;
import net.damqn4etobg.endlessexpansion.effect.FreezeEffectRenderer;
import net.damqn4etobg.endlessexpansion.effect.ModMobEffects;
import net.damqn4etobg.endlessexpansion.tag.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class FreezingHudOverlay {
    private static final ResourceLocation FILLIED_FREEZE = new ResourceLocation(EndlessExpansion.MODID,
            "textures/freeze/filled_freeze.png");

    private static final ResourceLocation EMPTY_FREEZE = new ResourceLocation(EndlessExpansion.MODID,
            "textures/freeze/empty_freeze.png");

    public static final IGuiOverlay HUD_FREEZE = ((gui, guiGraphics, partialTick, width, height) -> {
        gui.getMinecraft().getProfiler().push("freezingOverlay");

        BlockPos playerPos = gui.getMinecraft().player.blockPosition();
        Holder<Biome> holder = gui.getMinecraft().player.level().getBiome(playerPos);

        boolean inSpecificBiome = holder.is(ModTags.Biomes.IS_FROZEN_WASTES);

        if (gui.getMinecraft().player != null && gui.getMinecraft().player.getAbilities().mayfly) {
            return;
        }

        if (gui.getMinecraft().player != null && gui.getMinecraft().player.level().dimension() != ModDimensions.WORLD_BEYOND_LEVEL_KEY) {
            return;
        }

        if (gui.getMinecraft().player != null && !inSpecificBiome) {
            return;
        }

        int x = width / 2;
        int y = height;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, EMPTY_FREEZE);

        int armorValue = gui.getMinecraft().player.getArmorValue();
        int leftHeight = gui.leftHeight;

        for (int i = 0; i < 10; i++) {
            if(armorValue == 0) {
                guiGraphics.blit(EMPTY_FREEZE, x - 96 + (i * 9), y - leftHeight + 10, 0, 0, 10, 10,
                        10, 10);
            } else {
                guiGraphics.blit(EMPTY_FREEZE, x - 96 + (i * 9), y - leftHeight, 0, 0, 10, 10,
                        10, 10);
            }
        }

        RenderSystem.setShaderTexture(0, FILLIED_FREEZE);
            for (int i = 0; i < 10; i++) {
                if (ClientFreezeData.getPlayerFreeze() > i) {
                    if(armorValue == 0) {
                        guiGraphics.blit(FILLIED_FREEZE, x - 96 + (i * 9), y - leftHeight + 10, 0, 0, 10, 10,
                                10, 10); // original 50 px above 0
                    } else {
                        guiGraphics.blit(FILLIED_FREEZE, x - 96 + (i * 9), y - leftHeight, 0, 0, 10, 10,
                                10, 10);
                    }
                } else {
                    break;
                }
            }

        MobEffectInstance freezingEffectInstance = new MobEffectInstance(ModMobEffects.FREEZING.get(), 300, 0);
        if(ClientFreezeData.getPlayerFreeze() >= 10) {
                gui.getMinecraft().player.addEffect(freezingEffectInstance);
            } else {
            gui.getMinecraft().player.removeEffect(ModMobEffects.FREEZING.get());
        }
        gui.getMinecraft().getProfiler().pop();
    });
}