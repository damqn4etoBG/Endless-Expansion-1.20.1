package net.damqn4etobg.endlessexpansion.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.EndlessExpansionConfig;
import net.damqn4etobg.endlessexpansion.util.gui.components.ConfigButton;
import net.damqn4etobg.endlessexpansion.util.gui.components.PlatformIconConfigButton;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class EndlessExpansionMainMenuScreen extends Screen {
    public static final CubeMap CUBE_MAP_TITANIC_FOREST = new CubeMap(new ResourceLocation(EndlessExpansion.MODID, "textures/gui/title/background/titanic_forest/panorama"));
    public static final CubeMap CUBE_MAP_FROZEN_WASTES = new CubeMap(new ResourceLocation(EndlessExpansion.MODID, "textures/gui/title/background/frozen_wastes/panorama"));
    public static final CubeMap CUBE_MAP_SINKHOLE = new CubeMap(new ResourceLocation(EndlessExpansion.MODID, "textures/gui/title/background/sinkhole/panorama"));
    private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
    private final PanoramaRenderer panorama_titanic_forest = new PanoramaRenderer(CUBE_MAP_TITANIC_FOREST);
    private final PanoramaRenderer panorama_frozen_wastes = new PanoramaRenderer(CUBE_MAP_FROZEN_WASTES);
    private final PanoramaRenderer panorama_sinkhole = new PanoramaRenderer(CUBE_MAP_SINKHOLE);
    private final EndlessExpansionConfig config;
    private final Screen lastScreen;
    public static final Component MADE_BY_TEXT = Component.translatable("menu.endlessexpansion.config.madeby");
    public static final Component INSPIRED_TEXT = Component.translatable("menu.endlessexpansion.config.inspiredby");
    public static final Component VERSION = Component.literal("Endless Expansion " + EndlessExpansionConfig.MOD_VERSION);
    private static final ResourceLocation CURSEFORGE_LOGO = new ResourceLocation(EndlessExpansion.MODID, "textures/gui/platform/curseforge.png");
    private static final ResourceLocation GITHUB_LOGO = new ResourceLocation(EndlessExpansion.MODID, "textures/gui/platform/github_alt.png");
    private static final ResourceLocation MODRINTH_LOGO = new ResourceLocation(EndlessExpansion.MODID, "textures/gui/platform/modrinth.png");
    private long firstRenderTime;
    private static final ResourceLocation VIGNETTE_LOCATION = new ResourceLocation("textures/misc/vignette.png");

    public EndlessExpansionMainMenuScreen(Screen screen) {
        super(Component.translatable("menu.endlessexpansion.config.name"));
        config = EndlessExpansionConfig.loadConfig();
        this.lastScreen = screen;
    }
    @Override
    protected void init() {
        GridLayout gridlayout = new GridLayout();
        gridlayout.defaultCellSetting().paddingHorizontal(5).paddingBottom(4).alignHorizontallyCenter();
        GridLayout.RowHelper gridlayout$rowhelper = gridlayout.createRowHelper(2);

        gridlayout$rowhelper.addChild(ConfigButton.builder(Component.translatable("menu.endlessexpansion.config.custom_menu"), (button) -> {
            {}
        }).width(100).build())
                .setTooltip(Tooltip.create(Component.translatable("menu.endlessexpansion.config.custom_menu_desc")));

        gridlayout$rowhelper.addChild(ConfigButton.builder(config.isCustomMainMenu() ? Component.literal("ON").withStyle(ChatFormatting.GREEN) : Component.literal("OFF").withStyle(ChatFormatting.RED),
                button -> {
                    // Toggle the customMainMenu state
                    config.setCustomMainMenu(!config.isCustomMainMenu());

                    // Update button label and save the config
                    button.setMessage(
                            config.isCustomMainMenu() ? Component.literal("ON").withStyle(ChatFormatting.GREEN) : Component.literal("OFF").withStyle(ChatFormatting.RED)
                    );
        }).width(100).build()).setTooltip(Tooltip.create(Component.translatable("menu.endlessexpansion.config.custom_menu_switch")));

        gridlayout$rowhelper.addChild(ConfigButton.builder(Component.translatable("menu.endlessexpansion.config.background_selector"), (button) -> {
                    {}
                }).width(100).build())
                .setTooltip(Tooltip.create(Component.translatable("menu.endlessexpansion.config.background_selector_desc")));

        gridlayout$rowhelper.addChild(ConfigButton.builder(
                Component.literal(config.getBackgroundName()).withStyle(getChatFormattingForBackground(config.getBackgroundName())),
                button -> {
                    // Update the background name and button label
                    updateBackgroundName(config);
                    updateButtonLabelBackgroundSelector(button, config);
                }).width(100).build());

        gridlayout$rowhelper.addChild(ConfigButton.builder(Component.translatable("menu.endlessexpansion.config.mod_sounds"), (button) -> {
                    {}
                }).width(100).build())
                .setTooltip(Tooltip.create(Component.translatable("menu.endlessexpansion.config.mod_sounds_desc")));

        gridlayout$rowhelper.addChild(ConfigButton.builder(
                Component.literal(config.getModSounds()).withStyle(getChatFormattingForSounds(config.getModSounds())),
                button -> {
                    updateModSoundsName(config);
                    updateButtonLabelModSoundsSelector(button, config);
                    button.setTooltip(getTooltipForSounds(config));
                }).width(100).build());

        gridlayout$rowhelper.addChild(ConfigButton.builder(CommonComponents.GUI_DONE, (button) -> {
            Minecraft.getInstance().setScreen(this.lastScreen);
        }).width(200).build(), 2, gridlayout$rowhelper.newCellSettings().paddingTop(6));

        gridlayout.arrangeElements();
        FrameLayout.alignInRectangle(gridlayout, 0, this.height / 6 - 12, this.width, this.height, 0.5F, 0.0F);
        gridlayout.visitWidgets(this::addRenderableWidget);

        // Calculate the position for the second square button
        int textWidth1 = this.font.width(MADE_BY_TEXT);
        int textHeight1 = this.font.lineHeight;

        int x = this.width - textWidth1 - 2;
        int y = this.height - textHeight1 - 2;

        this.addRenderableWidget(new PlainTextButton(x, y + 1, textWidth1, textHeight1, MADE_BY_TEXT, (button) -> {
            this.minecraft.setScreen(new ModCreditsScreen(Minecraft.getInstance().screen));
        }, this.font));

        int textWidth2 = this.font.width(INSPIRED_TEXT);
        int textHeight2 = this.font.lineHeight;

        int x2 = this.width - textWidth2 - 2;
        int y2 = y - textHeight2 - 1;

        this.addRenderableWidget(new PlainTextButton(x2, y2, textWidth2, textHeight2, INSPIRED_TEXT, (button) -> {
            Util.getPlatform().openUri("https://twbtiw.miraheze.org/wiki/Main_Page");
        }, this.font)).setTooltip(Tooltip.create(Component.translatable("menu.endlessexpansion.config.ohawhewhe")));

        int textWidth3 = this.font.width(VERSION);
        int textHeight3 = this.font.lineHeight;

        this.addRenderableWidget(new StringWidget(2, y + 1, textWidth3, textHeight3, VERSION, this.font));

        int buttonWidth = 20;
        int buttonHeight = 20;

        this.addRenderableWidget(new PlatformIconConfigButton(2, y2 - 12, buttonWidth, buttonHeight,
                CURSEFORGE_LOGO, 1f, (b) -> {
            Util.getPlatform().openUri("https://www.curseforge.com/minecraft/mc-mods/endless-expansion");
        }, Tooltip.create(Component.literal("§cCurseforge"))));

        this.addRenderableWidget(new PlatformIconConfigButton(2 + 24, y2 - 12, buttonWidth, buttonHeight,
                MODRINTH_LOGO, 1f, (b) -> {
            Util.getPlatform().openUri("https://modrinth.com/mod/endless-expansion");
        }, Tooltip.create(Component.literal("§aModrinth"))));

        this.addRenderableWidget(new PlatformIconConfigButton(2 + 48, y2 - 12, buttonWidth, buttonHeight,
                GITHUB_LOGO, 1f, (b) -> {
            Util.getPlatform().openUri("https://github.com/damqn4etoBG/Endless-Expansion");
        }, Tooltip.create(Component.literal("Github"))));
    }

    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().setScreen(this.lastScreen);
        config.saveConfig();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        if (firstRenderTime == 0L)
            this.firstRenderTime = Util.getMillis();

        float f = (float) (Util.getMillis() - this.firstRenderTime) / 250.0F;
        float alpha = Mth.clamp(f,0.0F, 1.0F);

        switch (config.getBackgroundName()) {
            case "Titanic Forest" -> this.panorama_titanic_forest.render(delta, alpha);
            case "Frozen Wastes" -> this.panorama_frozen_wastes.render(delta, alpha);
            case "Sinkhole" -> this.panorama_sinkhole.render(delta, alpha);
            default -> {
                return;
            }
        }

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR);
        guiGraphics.blit(VIGNETTE_LOCATION, 0, 0, 0, 0.0F, 0.0F, this.width, this.height, this.width, this.height);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(PANORAMA_OVERLAY, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
        RenderSystem.setShaderTexture(0, PANORAMA_OVERLAY);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    private void updateBackgroundName(EndlessExpansionConfig config) {
        String currentBackground = config.getBackgroundName();

        if ("Titanic Forest".equals(currentBackground)) {
            config.setBackgroundName("Frozen Wastes");
        } else if ("Frozen Wastes".equals(currentBackground)) {
            config.setBackgroundName("Sinkhole");
        } else if ("Sinkhole".equals(currentBackground)) {
            config.setBackgroundName("Titanic Forest");
        }

        config.saveConfig();
    }

    private void updateModSoundsName(EndlessExpansionConfig config) {
        String currentString = config.getModSounds();

        if ("ON".equals(currentString)) {
            config.setModSounds("Partial");
        } else if ("Partial".equals(currentString)) {
            config.setModSounds("OFF");
        } else if ("OFF".equals(currentString)) {
            config.setModSounds("ON");
        }

        config.saveConfig();
    }
    private ChatFormatting getChatFormattingForBackground(String backgroundName) {
        return switch (backgroundName) {
            case "Titanic Forest" -> ChatFormatting.AQUA;
            case "Frozen Wastes" -> ChatFormatting.BLUE;
            case "Sinkhole" -> ChatFormatting.WHITE;
            default -> ChatFormatting.RED;
        };
    }

    private ChatFormatting getChatFormattingForSounds(String modSounds) {
        return switch (modSounds) {
            case "ON" -> ChatFormatting.GREEN;
            case "Partial" -> ChatFormatting.YELLOW;
            case "OFF" -> ChatFormatting.RED;
            default -> ChatFormatting.WHITE;
        };
    }

    private Tooltip getTooltipForSounds(EndlessExpansionConfig config) {
        String currentString = config.getModSounds();
        return switch (currentString) {
            case "ON" -> Tooltip.create(Component.translatable("menu.endlessexpansion.config.mod_sounds_on"));
            case "Partial" -> Tooltip.create(Component.translatable("menu.endlessexpansion.config.mod_sounds_partial"));
            case "OFF" -> Tooltip.create(Component.translatable("menu.endlessexpansion.config.mod_sounds_off"));
            default -> Tooltip.create(Component.empty());
        };
    }

    private void updateButtonLabelBackgroundSelector(ConfigButton button, EndlessExpansionConfig config) {
        button.setMessage(Component.literal(config.getBackgroundName()).withStyle(getChatFormattingForBackground(config.getBackgroundName())));
    }
    private void updateButtonLabelModSoundsSelector(ConfigButton button, EndlessExpansionConfig config) {
        button.setMessage(Component.literal(config.getModSounds()).withStyle(getChatFormattingForSounds(config.getModSounds())));
    }
}