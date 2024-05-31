package net.damqn4etobg.endlessexpansion.screen.renderer;

import net.damqn4etobg.endlessexpansion.effect.FreezeEffect;
import net.damqn4etobg.endlessexpansion.effect.ModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class FreezeEffectRenderer extends ForgeGui {
    private static final ResourceLocation POWDER_SNOW_OUTLINE_LOCATION = new ResourceLocation("textures/misc/powder_snow_outline.png");

    public FreezeEffectRenderer(Minecraft mc) {
        super(mc);
    }

    public void renderIfFreezingEffectActive(GuiGraphics pGuiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            MobEffectInstance freezeEffect = minecraft.player.getEffect(ModMobEffects.FREEZING.get());
            if (freezeEffect != null && freezeEffect.getEffect() instanceof FreezeEffect) {
                float percentFrozen = minecraft.player.getPercentFrozen();
                this.renderTextureOverlay(pGuiGraphics, POWDER_SNOW_OUTLINE_LOCATION, 1);
            }
        }
    }
}

