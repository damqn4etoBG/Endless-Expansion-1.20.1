package net.damqn4etobg.endlessexpansion.screen;

import com.mojang.authlib.minecraft.BanDetails;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.RealmsMainScreen;
import net.damqn4etobg.endlessexpansion.EndlessExpansion;
import net.damqn4etobg.endlessexpansion.EndlessExpansionConfig;
import net.damqn4etobg.endlessexpansion.util.PlatformIconButton;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.versions.forge.ForgeVersion;

import javax.annotation.Nullable;
import java.util.Objects;

public class ModTitleScreen extends Screen {
    public static final CubeMap CUBE_MAP_TITANIC_FOREST = new CubeMap(new ResourceLocation(EndlessExpansion.MODID, "textures/gui/title/background/titanic_forest/panorama"));
    public static final CubeMap CUBE_MAP_FROZEN_WASTES = new CubeMap(new ResourceLocation(EndlessExpansion.MODID, "textures/gui/title/background/frozen_wastes/panorama"));
    public static final CubeMap CUBE_MAP_SINKHOLE = new CubeMap(new ResourceLocation(EndlessExpansion.MODID, "textures/gui/title/background/sinkhole/panorama"));
    private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
    private final PanoramaRenderer panorama_titanic_forest = new PanoramaRenderer(CUBE_MAP_TITANIC_FOREST);
    private final PanoramaRenderer panorama_frozen_wastes = new PanoramaRenderer(CUBE_MAP_FROZEN_WASTES);
    private final PanoramaRenderer panorama_sinkhole = new PanoramaRenderer(CUBE_MAP_SINKHOLE);
    private final EndlessExpansionConfig config;
    private long firstRenderTime;
    private final boolean fading;
    private long fadeInStart;
    public static final Component COPYRIGHT_TEXT = Component.literal("Copyright Mojang AB. Do not distribute!");
    private final LogoRenderer logoRenderer;
    private SplashRenderer splash;
    private static final ResourceLocation CURSEFORGE_LOGO = new ResourceLocation(EndlessExpansion.MODID, "textures/gui/platform/curseforge.png");
    private static final ResourceLocation GITHUB_LOGO = new ResourceLocation(EndlessExpansion.MODID, "textures/gui/platform/github.png");
    private static final ResourceLocation MODRINTH_LOGO = new ResourceLocation(EndlessExpansion.MODID, "textures/gui/platform/modrinth.png");

    public ModTitleScreen() {
        this(false);
    }
    public ModTitleScreen(boolean fading) {
        this(fading, (LogoRenderer)null);
    }
    public ModTitleScreen(boolean fading, @Nullable LogoRenderer pLogoRenderer) {
        super(Component.empty());
        this.fading = fading;
        config = EndlessExpansionConfig.loadConfig();
        this.logoRenderer = Objects.requireNonNullElseGet(pLogoRenderer, () -> {
            return new LogoRenderer(false);
        });
    }

    @Override
    protected void init() {
        super.init();
        Button modButton = null;
        int textHeight1 = this.font.lineHeight;
        if (this.splash == null) {
            this.splash = this.minecraft.getSplashManager().getSplash();
        }
        int textHeight2 = this.font.lineHeight;


        int y = this.height - textHeight1 - 2;
        int y2 = y - textHeight2 - 2;

        int textWidth3 = this.font.width(EndlessExpansionMainMenuScreen.VERSION);
        int textHeight3 = this.font.lineHeight;
        int frgVrsWidth = this.font.width("Forge " + ForgeVersion.getVersion());
        int minecraftVersionWidth = this.font.width("Minecraft " + ("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType()));

        int i = this.font.width(COPYRIGHT_TEXT);
        int j = this.width - i - 2;
        int l = this.height / 4 + 48;

        this.addRenderableWidget(new StringWidget(2, y + 1, textWidth3, textHeight3, EndlessExpansionMainMenuScreen.VERSION, this.font));

        this.addRenderableWidget(new StringWidget(this.width - frgVrsWidth - 2, y - 9, frgVrsWidth, textHeight3,
                Component.literal("Forge " + ForgeVersion.getVersion()), this.font));

        this.addRenderableWidget(new StringWidget(this.width - minecraftVersionWidth - 2, y - 18, minecraftVersionWidth, textHeight3,
                Component.literal("Minecraft " + ("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType())), this.font));

        this.addRenderableWidget(new PlatformIconButton(2, y2 - 12, 20, 20,
                CURSEFORGE_LOGO, 1f, (b) -> {
            Util.getPlatform().openUri("https://www.curseforge.com/minecraft/mc-mods/endless-expansion");
        }, Tooltip.create(Component.literal("§cCurseforge"))));

        this.addRenderableWidget(new PlatformIconButton(2 + 24, y2 - 12, 20, 20,
                MODRINTH_LOGO, 1f, (b) -> {
            Util.getPlatform().openUri("https://modrinth.com/mod/endless-expansion");
        }, Tooltip.create(Component.literal("§aModrinth"))));

        this.addRenderableWidget(new PlatformIconButton(2 + 48, y2 - 12, 20, 20,
                GITHUB_LOGO, 1f, (b) -> {
            Util.getPlatform().openUri("https://github.com/damqn4etoBG/Endless-Expansion");
        }, Tooltip.create(Component.literal("Github"))));

        this.createNormalMenuOptions(l, 24);
        modButton = this.addRenderableWidget(Button.builder(Component.translatable("fml.menu.mods"), button -> this.minecraft.setScreen(new net.minecraftforge.client.gui.ModListScreen(this)))
                .pos(this.width / 2 - 100, l + 24 * 2).size(98, 20).build());

        this.addRenderableWidget(new ImageButton(this.width / 2 - 124, l + 72 + 12, 20, 20, 0, 106, 20, Button.WIDGETS_LOCATION, 256, 256, (p_280830_) -> {
            this.minecraft.setScreen(new LanguageSelectScreen(this, this.minecraft.options, this.minecraft.getLanguageManager()));
        }, Component.translatable("narrator.button.language")));
        this.addRenderableWidget(Button.builder(Component.translatable("menu.options"), (p_280838_) -> {
            this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
        }).bounds(this.width / 2 - 100, l + 72 + 12, 98, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("menu.quit"), (p_280831_) -> {
            this.minecraft.stop();
        }).bounds(this.width / 2 + 2, l + 72 + 12, 98, 20).build());
        this.addRenderableWidget(new ImageButton(this.width / 2 + 104, l + 72 + 12, 20, 20, 0, 0, 20, Button.ACCESSIBILITY_TEXTURE, 32, 64, (p_280835_) -> {
            this.minecraft.setScreen(new AccessibilityOptionsScreen(this, this.minecraft.options));
        }, Component.translatable("narrator.button.accessibility")));
        this.addRenderableWidget(new PlainTextButton(j, this.height - 10, i, 10, COPYRIGHT_TEXT, (p_280834_) -> {
            this.minecraft.setScreen(new CreditsAndAttributionScreen(this));
        }, this.font));
    }

    private void createNormalMenuOptions(int pY, int pRowHeight) {
        this.addRenderableWidget(Button.builder(Component.translatable("menu.singleplayer"), (p_280832_) -> {
            this.minecraft.setScreen(new SelectWorldScreen(this));
        }).bounds(this.width / 2 - 100, pY, 200, 20).build());
        Component component = this.getMultiplayerDisabledReason();
        boolean flag = component == null;
        Tooltip tooltip = component != null ? Tooltip.create(component) : null;
        (this.addRenderableWidget(Button.builder(Component.translatable("menu.multiplayer"), (p_280833_) -> {
            Screen screen = (Screen)(this.minecraft.options.skipMultiplayerWarning ? new JoinMultiplayerScreen(this) : new SafetyScreen(this));
            this.minecraft.setScreen(screen);
        }).bounds(this.width / 2 - 100, pY + pRowHeight * 1, 200, 20).tooltip(tooltip).build())).active = flag;
        (this.addRenderableWidget(Button.builder(Component.translatable("menu.online"), (p_210872_) -> {
            this.realmsButtonClicked();
        }).bounds(this.width / 2 + 2, pY + pRowHeight * 2, 98, 20).tooltip(tooltip).build())).active = flag;
    }

    @Nullable
    private Component getMultiplayerDisabledReason() {
        if (this.minecraft.allowsMultiplayer()) {
            return null;
        } else {
            BanDetails bandetails = this.minecraft.multiplayerBan();
            if (bandetails != null) {
                return bandetails.expires() != null ? Component.translatable("title.multiplayer.disabled.banned.temporary") : Component.translatable("title.multiplayer.disabled.banned.permanent");
            } else {
                return Component.translatable("title.multiplayer.disabled");
            }
        }
    }
    private void realmsButtonClicked() {
        this.minecraft.setScreen(new RealmsMainScreen(this));
    }
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        if (this.fadeInStart == 0L && this.fading) {
            this.fadeInStart = Util.getMillis();
        }
        float f = this.fading ? (float)(Util.getMillis() - this.fadeInStart) / 1000.0F : 1.0F;
        float alpha = Mth.clamp(f, 0.0F, 1.0F);
        if(config.getBackgroundName().equals("Titanic Forest")) {
            this.panorama_titanic_forest.render(delta, alpha);
        } else if (config.getBackgroundName().equals("Frozen Wastes")) {
            this.panorama_frozen_wastes.render(delta, alpha);
        } else if (config.getBackgroundName().equals("Sinkhole")) {
            this.panorama_sinkhole.render(delta, alpha);
        } else {
            return;
        }
        RenderSystem.enableBlend();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.fading ? (float)Mth.ceil(Mth.clamp(f, 0.0F, 1.0F)) : 1.0F);
        guiGraphics.blit(PANORAMA_OVERLAY, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, PANORAMA_OVERLAY);
        float f1 = this.fading ? Mth.clamp(f - 1.0F, 0.0F, 1.0F) : 1.0F;
        this.logoRenderer.renderLogo(guiGraphics, this.width, f1);
        int i = Mth.ceil(f1 * 255.0F) << 24;
        if ((i & -67108864) != 0) {
            if (this.splash != null) {
                this.splash.render(guiGraphics, this.width, this.font, i);
            }
            for(GuiEventListener guieventlistener : this.children()) {
                if (guieventlistener instanceof AbstractWidget) {
                    ((AbstractWidget)guieventlistener).setAlpha(f1);
                }
            }
        }
        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}