package net.damqn4etobg.endlessexpansion.screen.renderer;

import net.damqn4etobg.endlessexpansion.util.capabilities.ITemperature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

import java.text.DecimalFormat;
import java.util.List;

public class TemperatureInfoArea extends InfoArea {
    private final ITemperature temperature;

    public TemperatureInfoArea(Minecraft minecraft, MultiBufferSource.BufferSource bufferSource, int xMin, int yMin) {
        this(minecraft, bufferSource, xMin, yMin, null, 4, 54);
    }

    public TemperatureInfoArea(Minecraft minecraft, MultiBufferSource.BufferSource bufferSource, int xMin, int yMin, ITemperature temperature) {
        this(minecraft, bufferSource, xMin, yMin, temperature, 4, 54);
    }

    public TemperatureInfoArea(Minecraft minecraft, MultiBufferSource.BufferSource bufferSource, int xMin, int yMin, ITemperature temperature, int width, int height) {
        super(minecraft, bufferSource, new Rect2i(xMin, yMin, width, height));
        this.temperature = temperature;
    }

    public List<Component> getTooltips() {
        DecimalFormat df = new DecimalFormat("#.##");
        String temperatureString = temperature.getTemperature() + " / " + temperature.getMaxTemperature() + " °C";
        return List.of(Component.literal(temperatureString));
    }
    @Override
    public void draw(GuiGraphics guiGraphics) {
        final int height = area.getHeight();
        int stored = (int)(height*(temperature.getTemperature()/(float)temperature.getMaxTemperature()));
        guiGraphics.fillGradient(
                area.getX(), area.getY()+(height-stored),
                area.getX() + area.getWidth(), area.getY() +area.getHeight(),
                0xffff0000, 0xffb10000
        );
    }
}

