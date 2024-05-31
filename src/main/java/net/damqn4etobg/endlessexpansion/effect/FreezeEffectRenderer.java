package net.damqn4etobg.endlessexpansion.effect;

import com.mojang.blaze3d.systems.RenderSystem;
import net.damqn4etobg.endlessexpansion.util.TickCounter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class FreezeEffectRenderer extends ForgeGui implements Tickable {
    private static int tickCount; // Change to static field
    public FreezeEffectRenderer(Minecraft mc) {
        super(mc);
    }
    @Override
    public void tick() {
        tickCount++;
    }

    public static void renderOutline(GuiGraphics guiGraphics) {
        float alpha = (float) tickCount / 100;
        renderTextureOverlay2(guiGraphics, POWDER_SNOW_OUTLINE_LOCATION, alpha);
        System.out.println(alpha);
        System.out.println(tickCount);
    }

    public static void renderTextureOverlay2(GuiGraphics pGuiGraphics, ResourceLocation pShaderLocation, float pAlpha) {
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, pAlpha);
        pGuiGraphics.blit(pShaderLocation, 0, 0, -90, 0.0F, 0.0F, pGuiGraphics.guiWidth(), pGuiGraphics.guiHeight(),
                pGuiGraphics.guiWidth(), pGuiGraphics.guiHeight());
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}

