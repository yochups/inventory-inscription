package com.nidza.inventoryinscription.client;

import com.nidza.inventoryinscription.ClientConfig;
import com.nidza.inventoryinscription.InventoryInscription;
import com.nidza.inventoryinscription.network.OpenInscriptionTablePayload;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Map;
import java.util.WeakHashMap;

@EventBusSubscriber(modid = InventoryInscription.MOD_ID, value = Dist.CLIENT)
public final class InventoryScreenEvents {
    private static final int RECIPE_BUTTON_X = 104;
    private static final int RECIPE_BUTTON_Y = -22;
    private static final Map<InventoryScreen, JournalButton> BUTTONS = new WeakHashMap<>();

    private InventoryScreenEvents() {
    }

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof InventoryScreen screen) {
            JournalButton button = new JournalButton(buttonX(screen), buttonY(screen));
            BUTTONS.put(screen, button);
            event.addListener(button);
        }
    }

    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render.Pre event) {
        if (event.getScreen() instanceof InventoryScreen screen) {
            JournalButton button = BUTTONS.get(screen);
            if (button != null) {
                button.setPosition(buttonX(screen), buttonY(screen));
            }
        }
    }

    private static int buttonX(InventoryScreen screen) {
        return screen.getGuiLeft() + RECIPE_BUTTON_X + ClientConfig.BUTTON_X_OFFSET.get();
    }

    private static int buttonY(InventoryScreen screen) {
        return screen.height / 2 + RECIPE_BUTTON_Y + ClientConfig.BUTTON_Y_OFFSET.get();
    }

    private static final class JournalButton extends ImageButton {
        private final ItemStack icon = ItemRegistry.COPPER_SPELL_BOOK.get().getDefaultInstance();

        private JournalButton(int x, int y) {
            super(
                    x,
                    y,
                    20,
                    18,
                    RecipeBookComponent.RECIPE_BUTTON_SPRITES,
                    ignored -> PacketDistributor.sendToServer(OpenInscriptionTablePayload.INSTANCE),
                    Component.translatable("inventory_inscription.open_inscription_table")
            );
            setTooltip(Tooltip.create(Component.translatable("inventory_inscription.open_inscription_table")));
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
            int backgroundColor = isHoveredOrFocused() ? 0xFF8892C9 : 0xFFC6C6C6;
            guiGraphics.fill(getX() + 2, getY() + 2, getX() + 18, getY() + 16, backgroundColor);
            guiGraphics.renderItem(icon, getX() + 2, getY() + 1);
        }
    }
}
