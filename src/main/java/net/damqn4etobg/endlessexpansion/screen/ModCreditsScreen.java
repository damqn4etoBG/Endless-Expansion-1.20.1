package net.damqn4etobg.endlessexpansion.screen;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.screen.renderer.ModLogoRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class ModCreditsScreen extends Screen {
    private static final Logger LOGGER = LogUtils.getLogger();
    private List<FormattedCharSequence> lines;
    private IntSet centeredLines;
    private static final ResourceLocation VIGNETTE_LOCATION = new ResourceLocation("textures/misc/vignette.png");
    private static final Component SECTION_HEADING = Component.literal("------------").withStyle(ChatFormatting.GRAY);
    private static final Component RETURN_MESSAGE = Component.translatable("menu.endlessexpansion.credits.pressesc").withStyle(ChatFormatting.GRAY);
    private static final Component CONTROLS_TEXT = Component.translatable("menu.endlessexpansion.credits.press_alt").withStyle(ChatFormatting.GRAY);
    private float scroll;
    private final float unmodifiedScrollSpeed;
    private float scrollSpeed;
    private final ModLogoRenderer logoRenderer = new ModLogoRenderer(true);
    private final Screen lastScreen;
    private boolean showReturnMessage;
    private float returnMessageAlpha;
    private float controlsMessageAlpha;

    protected ModCreditsScreen(Screen screen) {
        super(GameNarrator.NO_TITLE);
        this.unmodifiedScrollSpeed = 1F;
        this.scrollSpeed = unmodifiedScrollSpeed;
        this.lastScreen = screen;
        this.showReturnMessage = false;
        this.returnMessageAlpha = 0.0f;
        this.controlsMessageAlpha = 0.0f;
    }

    @Override
    protected void init() {
        if (this.lines == null) {
            this.lines = Lists.newArrayList();
            this.centeredLines = new IntOpenHashSet();

            this.wrapCreditsIO("texts/credits.json", this::addCreditsFile);
        }
    }

    private void wrapCreditsIO(String pCreditsLocation, ModCreditsScreen.CreditsReader pReader) {
        try (Reader reader = this.minecraft.getResourceManager().openAsReader(new ResourceLocation(EndlessExpansion.MODID, pCreditsLocation))) {
            pReader.read(reader);
        } catch (Exception exception) {
            LOGGER.error("Couldn't load credits", exception);
        }
    }

    private void addCreditsFile(Reader p_232820_) {
        for (JsonElement jsonelement : GsonHelper.parseArray(p_232820_)) {
            JsonObject jsonobject = jsonelement.getAsJsonObject();
            String s = jsonobject.get("section").getAsString();
            this.addCreditsLine(SECTION_HEADING, true);
            this.addCreditsLine(Component.literal(s).withStyle(ChatFormatting.YELLOW), true);
            this.addCreditsLine(SECTION_HEADING, true);
            this.addEmptyLine();
            this.addEmptyLine();

            for (JsonElement jsonelement2 : jsonobject.getAsJsonArray("titles")) {
                JsonObject jsonobject2 = jsonelement2.getAsJsonObject();
                String s2 = jsonobject2.get("title").getAsString();
                JsonArray jsonarray = jsonobject2.getAsJsonArray("names");
                this.addCreditsLine(Component.literal(s2).withStyle(ChatFormatting.GRAY), false);

                for (JsonElement jsonelement3 : jsonarray) {
                    String s3 = jsonelement3.getAsString();
                    this.addCreditsLine(Component.literal("           ").append(s3).withStyle(ChatFormatting.WHITE), false);
                }

                this.addEmptyLine();
                this.addEmptyLine();
            }
        }
    }
    private void addEmptyLine() {
        this.lines.add(FormattedCharSequence.EMPTY);
    }

    private void addCreditsLine(Component pCreditsLine, boolean pCentered) {
        if (pCentered) {
            this.centeredLines.add(this.lines.size());
        }

        this.lines.add(pCreditsLine.getVisualOrderText());
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(this.lastScreen);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.scroll = Math.max(0.0F, this.scroll + pPartialTick * this.scrollSpeed);
        this.renderDirtBackground(pGuiGraphics);
        int i = this.width / 2 - 128;
        int j = this.height + 50; //this
        float f = -this.scroll;
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(0.0F, f, 0.0F);
        this.logoRenderer.renderLogo(pGuiGraphics, this.width, 0.0F, j - 50);
        int k = j + 100; //org 100

        if(Screen.hasAltDown()) {
            this.scrollSpeed += 0.25F;
        } else {
            this.scrollSpeed = unmodifiedScrollSpeed;
        }

        for (int l = 0; l < this.lines.size(); ++l) {
            if (l == this.lines.size() - 1) {
                float f1 = (float) k + f - (float) (this.height / 2 - 6);
                if (f1 < 0.0F) {
                    pGuiGraphics.pose().translate(0.0F, -f1, 0.0F);
                }
            }

            if ((float) k + f + 12.0F + 8.0F > 0.0F && (float) k + f < (float) this.height) {
                FormattedCharSequence formattedcharsequence = this.lines.get(l);
                if (this.centeredLines.contains(l)) {
                    pGuiGraphics.drawCenteredString(this.font, formattedcharsequence, i + 128, k, 16777215);
                } else {
                    pGuiGraphics.drawString(this.font, formattedcharsequence, i, k, 16777215);
                }
            }

            k += 12;
        }
        pGuiGraphics.pose().popPose();

        // Show return message if the credits have scrolled off the screen
        if (k + f < 0) {
            this.showReturnMessage = true;
        }

        pGuiGraphics.pose().popPose();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR);
        pGuiGraphics.blit(VIGNETTE_LOCATION, 0, 0, 0, 0.0F, 0.0F, this.width, this.height, this.width, this.height);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();

        if (this.showReturnMessage) {
            this.returnMessageAlpha = Math.min(this.returnMessageAlpha + pPartialTick * 0.05f, 1.0f);
            int returnMessageColor = (int)(this.returnMessageAlpha * 255.0f) << 24 | 0xFFFFFF;

            float scaleFactor = 1.5F;
            int scaledWidth = (int)(this.width / scaleFactor);
            int scaledHeight = (int)(this.height / scaleFactor);

            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().scale(scaleFactor, scaleFactor, scaleFactor);
            pGuiGraphics.drawCenteredString(this.font, RETURN_MESSAGE.getVisualOrderText(), scaledWidth / 2,
                    scaledHeight / 2 - (this.font.lineHeight / 2), returnMessageColor);
            pGuiGraphics.pose().popPose();
        }

        if(!this.showReturnMessage) {
            this.controlsMessageAlpha = Math.min(this.controlsMessageAlpha + pPartialTick * 0.05f, 1.0f);
            int controlMessageColor = (int)(this.controlsMessageAlpha * 255.0f) << 24 | 0xFFFFFF;

            pGuiGraphics.drawString(font, CONTROLS_TEXT, 2, this.height - this.font.lineHeight - 2, controlMessageColor);
        } else {
            if(controlsMessageAlpha > 0.0f) {
                this.controlsMessageAlpha = Math.max(this.controlsMessageAlpha - pPartialTick * 0.05f, 0.0f);
                int controlMessageColor = (int)(this.controlsMessageAlpha * 255.0f) << 24 | 0xFFFFFF;
                pGuiGraphics.drawString(font, CONTROLS_TEXT, 2, this.height - this.font.lineHeight - 2, controlMessageColor);
            }
        }
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @FunctionalInterface
    @OnlyIn(Dist.CLIENT)
    interface CreditsReader {
        void read(Reader pReader) throws IOException;
    }
}
