package net.damqn4etobg.endlessexpansion.util.gui.components;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlatformIconButton extends Button {
    protected final ResourceLocation icon;
    protected final float scale;

    public PlatformIconButton(int pX, int pY, int pWidth, int pHeight, ResourceLocation icon, float scale, OnPress pOnPress, Tooltip tooltip) {
        super(pX, pY, pWidth, pHeight, Component.empty(), pOnPress, DEFAULT_NARRATION);
        this.icon = icon;
        this.scale = scale;
        setTooltip(tooltip);
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.renderWidget(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.pose().pushPose();

        int centerX = this.getX() + this.getWidth() / 2;
        int centerY = this.getY() + this.getHeight() / 2;
        pGuiGraphics.pose().translate(centerX, centerY, 0);

        // Calculate the scale to fit the icon within the button
        float iconWidth = 256 * scale;
        float iconHeight = 256 * scale;
        float buttonWidth = this.getWidth();
        float buttonHeight = this.getHeight();
        float xScale = buttonWidth / iconWidth;
        float yScale = buttonHeight / iconHeight;
        float minScale = Math.min(xScale, yScale) * 0.8f; // Adjust the float value to change the scale of the icon

        pGuiGraphics.pose().scale(minScale, minScale, 1);
        pGuiGraphics.blit(this.icon, -(int)(iconWidth / 2), -(int)(iconHeight / 2), 0, 0, 256, 256);
        pGuiGraphics.pose().popPose();
    }
}