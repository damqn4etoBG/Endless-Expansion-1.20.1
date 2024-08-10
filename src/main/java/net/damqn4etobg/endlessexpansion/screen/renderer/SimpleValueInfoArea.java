package net.damqn4etobg.endlessexpansion.screen.renderer;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

import java.util.List;

public class SimpleValueInfoArea extends InfoArea {
    private int value;
    private int maxValue;
    private final boolean vertical;
    private final int color;
    private final int width;
    private final int height;

    public SimpleValueInfoArea(Minecraft minecraft, MultiBufferSource.BufferSource bufferSource, int minX, int minY, int value, int maxValue, int width, int height, boolean vertical, int color) {
        super(minecraft, bufferSource, new Rect2i(minX, minY, width, height));
        this.value = value;
        this.maxValue = maxValue;
        this.vertical = vertical;
        this.color = color;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GuiGraphics guiGraphics) {
        final int width = area.getWidth();
        final int height = area.getHeight();
        int storedHorizontal = (int)(width * (value / (float) maxValue));
        int storedVertical = (int)(height * (value / (float) maxValue));

        if (!vertical) {
            guiGraphics.fill(area.getX(), area.getY(), area.getX() + storedHorizontal, area.getY() + area.getHeight(), color);
        } else {
            guiGraphics.fill(area.getX(), area.getY() + (height - storedVertical), area.getX() + area.getWidth(), area.getY() + area.getHeight(), color);
        }
    }

    public void updateInfoArea(int value, int maxValue) {
        this.value = value;
        this.maxValue = maxValue;
    }

    public List<Component> getTooltipsPercent() {
        String percentString = (int) ((value / (float) maxValue) * 100) + "%";
        return List.of(Component.translatable("tooltip.endlessexpansion.progress"), Component.literal(percentString).withStyle(ChatFormatting.GRAY));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
