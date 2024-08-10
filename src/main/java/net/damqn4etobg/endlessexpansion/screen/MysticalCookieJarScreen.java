package net.damqn4etobg.endlessexpansion.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.screen.renderer.SimpleValueInfoArea;
import net.damqn4etobg.endlessexpansion.util.MouseUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class MysticalCookieJarScreen extends AbstractContainerScreen<MysticalCookieJarMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(EndlessExpansion.MODID,"textures/gui/mystical_cookie_jar_gui.png");

    public MysticalCookieJarScreen(MysticalCookieJarMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.minecraft = getMinecraft();
    }
    private SimpleValueInfoArea progressInfoArea;
    private MultiBufferSource.BufferSource bufferSource;
    public MultiBufferSource.BufferSource getBufferSource() {
        return bufferSource;
    }

    @Override
    protected void init() {
        super.init();
        assignValueInfoArea(minecraft, getBufferSource());
    }

    private void assignValueInfoArea(Minecraft minecraft, MultiBufferSource.BufferSource bufferSource) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        progressInfoArea = new SimpleValueInfoArea(minecraft, bufferSource, x + 77, y + 67, menu.blockEntity.getProgress(),
                menu.blockEntity.getMaxProgress(), 22, 5, false, 0xFFE0E0E0);
    }

    public void updateProgress(int progress, int maxProgress) {
        progressInfoArea.updateInfoArea(progress, maxProgress);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.titleLabelY = 6;
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2 + 2;

        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
        guiGraphics.drawString(this.font, playerInventoryTitle, inventoryLabelX, inventoryLabelY + 2, 4210752, false);
        renderProgressInfoAreaTooltips(guiGraphics, pMouseX, pMouseY, x, y, 77, 67);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        progressInfoArea.draw(guiGraphics);
    }

    private void renderProgressInfoAreaTooltips(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, progressInfoArea)) {
            guiGraphics.renderTooltip(this.font, progressInfoArea.getTooltipsPercent(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, SimpleValueInfoArea infoArea) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, infoArea.getWidth(), infoArea.getHeight());
    }
}
