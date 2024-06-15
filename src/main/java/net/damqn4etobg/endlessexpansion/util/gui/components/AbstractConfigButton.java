package net.damqn4etobg.endlessexpansion.util.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractConfigButton extends AbstractButton {
    public static final ResourceLocation BUTTON_LOCATION = new ResourceLocation(EndlessExpansion.MODID, "textures/gui/config_buttons.png");

    public AbstractConfigButton(int pX, int pY, int pWidth, int pHeight, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage);
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        // Adjust the width and height of the button slice
        int sliceWidth = 200;
        int sliceHeight = 20;

        pGuiGraphics.blitNineSliced(BUTTON_LOCATION, this.getX(), this.getY(), this.getWidth(), this.getHeight(),
                200, 20, 200, 20, 0, this.getTextureY());
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = getFGColor();
        this.renderString(pGuiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    private int getTextureY() {
        int i = 0;
        if (!this.active) {
            i = 0; // Disabled button state
        } else if (this.isHoveredOrFocused()) {
            i = 40; // Hovered button state (2 * 20)
        } else {
            i = 20; // Default button state
        }
        return i;
    }
}
