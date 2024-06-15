package net.damqn4etobg.endlessexpansion.util.gui.components;

import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ConfigButton extends AbstractConfigButton {
    public static final int SMALL_WIDTH = 120;
    public static final int DEFAULT_WIDTH = 150;
    public static final int DEFAULT_HEIGHT = 20;
    protected static final ConfigButton.CreateNarration DEFAULT_NARRATION = (p_253298_) -> {
        return p_253298_.get();
    };
    protected final ConfigButton.OnPress onPress;
    protected final ConfigButton.CreateNarration createNarration;

    public static ConfigButton.Builder builder(Component pMessage, ConfigButton.OnPress pOnPress) {
        return new ConfigButton.Builder(pMessage, pOnPress);
    }

    protected ConfigButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, ConfigButton.OnPress pOnPress, ConfigButton.CreateNarration pCreateNarration) {
        super(pX, pY, pWidth, pHeight, pMessage);
        this.onPress = pOnPress;
        this.createNarration = pCreateNarration;
    }

    protected ConfigButton(ConfigButton.Builder builder) {
        this(builder.x, builder.y, builder.width, builder.height, builder.message, builder.onPress, builder.createNarration);
        setTooltip(builder.tooltip); // Forge: Make use of the Builder tooltip
    }

    public void onPress() {
        this.onPress.onPress(this);
    }

    protected MutableComponent createNarrationMessage() {
        return this.createNarration.createNarrationMessage(() -> {
            return super.createNarrationMessage();
        });
    }

    public void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
        this.defaultButtonNarrationText(pNarrationElementOutput);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Builder {
        private final Component message;
        private final ConfigButton.OnPress onPress;
        @Nullable
        private Tooltip tooltip;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;
        private ConfigButton.CreateNarration createNarration = ConfigButton.DEFAULT_NARRATION;

        public Builder(Component pMessage, ConfigButton.OnPress pOnPress) {
            this.message = pMessage;
            this.onPress = pOnPress;
        }

        public ConfigButton.Builder pos(int pX, int pY) {
            this.x = pX;
            this.y = pY;
            return this;
        }

        public ConfigButton.Builder width(int pWidth) {
            this.width = pWidth;
            return this;
        }

        public ConfigButton.Builder size(int pWidth, int pHeight) {
            this.width = pWidth;
            this.height = pHeight;
            return this;
        }

        public ConfigButton.Builder bounds(int pX, int pY, int pWidth, int pHeight) {
            return this.pos(pX, pY).size(pWidth, pHeight);
        }

        public ConfigButton.Builder tooltip(@Nullable Tooltip pTooltip) {
            this.tooltip = pTooltip;
            return this;
        }

        public ConfigButton.Builder createNarration(ConfigButton.CreateNarration pCreateNarration) {
            this.createNarration = pCreateNarration;
            return this;
        }

        public ConfigButton build() {
            return build(ConfigButton::new);
        }

        public ConfigButton build(java.util.function.Function<ConfigButton.Builder, ConfigButton> builder) {
            return builder.apply(this);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public interface CreateNarration {
        MutableComponent createNarrationMessage(Supplier<MutableComponent> pMessageSupplier);
    }

    @OnlyIn(Dist.CLIENT)
    public interface OnPress {
        void onPress(ConfigButton pConfigButton);
    }
}
