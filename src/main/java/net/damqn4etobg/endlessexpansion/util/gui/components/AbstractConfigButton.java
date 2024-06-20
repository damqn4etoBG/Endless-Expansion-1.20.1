package net.damqn4etobg.endlessexpansion.util.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
@OnlyIn(Dist.CLIENT)
public abstract class AbstractConfigButton extends AbstractButton {
    private float hoverAnimationProgress = 0.0F;
    private static final float ANIMATION_SPEED = 0.2F;

    public AbstractConfigButton(int pX, int pY, int pWidth, int pHeight, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage);
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        int x = this.getX();
        int y = this.getY();
        int width = this.getWidth();
        int height = this.getHeight();
        int borderWidth = 1;

        if (isHoveredOrFocused()) {
            hoverAnimationProgress = Math.min(1.0F, hoverAnimationProgress + ANIMATION_SPEED);
        } else {
            hoverAnimationProgress = Math.max(0.0F, hoverAnimationProgress - ANIMATION_SPEED);
        }

        // Black bg
        pGuiGraphics.fill(x, y, x + width, y + height, 0xFF000000);

        int borderColor = 0xFF59BF9D;
        int borderColor2 = 0xFF3F8C75;

        int hoverBorderColor = 0xFFCDF2F2;
        int hoverBorderColor2 = 0xFF95AFAF;

        int currentBorderColor = interpolateColor(borderColor, hoverBorderColor, hoverAnimationProgress);
        int currentBorderColor2 = interpolateColor(borderColor2, hoverBorderColor2, hoverAnimationProgress);

        renderGradientBorder(pGuiGraphics, x, y, width, height, borderWidth, currentBorderColor, currentBorderColor2);

        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = getFGColor();
        this.renderString(pGuiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    private void renderGradientBorder(GuiGraphics pGuiGraphics, int x, int y, int width, int height, int borderWidth, int borderColor, int borderColor2) {
        for (int i = 0; i < borderWidth; i++) {
            int alpha = (int) (255 * (1.0F - (float) i / borderWidth));
            int color = (alpha << 24) | (borderColor & 0xFFFFFF);
            int color2 = (alpha << 24) | (borderColor2 & 0xFFFFFF);

            // Top border
            pGuiGraphics.fill(x + i, y + i, x + width - i, y + i + 1, color);
            // Bottom border
            pGuiGraphics.fill(x + i, y + height - i - 1, x + width - i, y + height - i, color2);
            // Left border
            pGuiGraphics.fillGradient(x + i, y + i, x + i + 1, y + height - i, color, color2);
            // Right border
            pGuiGraphics.fillGradient(x + width - i - 1, y + i, x + width - i, y + height - i, color, color2);
        }
    }

    private int interpolateColor(int startColor, int endColor, float progress) {
        int startA = (startColor >> 24) & 0xFF;
        int startR = (startColor >> 16) & 0xFF;
        int startG = (startColor >> 8) & 0xFF;
        int startB = startColor & 0xFF;

        int endA = (endColor >> 24) & 0xFF;
        int endR = (endColor >> 16) & 0xFF;
        int endG = (endColor >> 8) & 0xFF;
        int endB = endColor & 0xFF;

        int currentA = (int) (startA + (endA - startA) * progress);
        int currentR = (int) (startR + (endR - startR) * progress);
        int currentG = (int) (startG + (endG - startG) * progress);
        int currentB = (int) (startB + (endB - startB) * progress);

        return (currentA << 24) | (currentR << 16) | (currentG << 8) | currentB;
    }

    public int getFGColor() {
        return this.active ? 0xFFFFFF : 0xA0A0A0; // White if active, gray if inactive
    }
}