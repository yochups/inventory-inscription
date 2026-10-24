package com.nidza.inventoryinscription.network;

import com.nidza.inventoryinscription.InventoryInscription;
import io.redspace.ironsspellbooks.gui.inscription_table.InscriptionTableMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenInscriptionTablePayload() implements CustomPacketPayload {
    public static final OpenInscriptionTablePayload INSTANCE = new OpenInscriptionTablePayload();
    public static final Type<OpenInscriptionTablePayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(InventoryInscription.MOD_ID, "open_inscription_table")
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, OpenInscriptionTablePayload> STREAM_CODEC =
            StreamCodec.unit(INSTANCE);

    public static void handle(OpenInscriptionTablePayload payload, IPayloadContext context) {
        if (context.player() instanceof ServerPlayer player) {
            player.openMenu(new SimpleMenuProvider(
                    (containerId, inventory, ignored) ->
                            new InscriptionTableMenu(containerId, inventory, ContainerLevelAccess.NULL),
                    Component.translatable("block.irons_spellbooks.inscription_table")
            ));
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
