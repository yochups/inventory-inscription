package com.nidza.inventoryinscription;

import com.nidza.inventoryinscription.network.OpenInscriptionTablePayload;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod(InventoryInscription.MOD_ID)
public final class InventoryInscription {
    public static final String MOD_ID = "inventory_inscription";

    public InventoryInscription(IEventBus modEventBus) {
        modEventBus.addListener(InventoryInscription::registerPayloads);
    }

    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar("1").playToServer(
                OpenInscriptionTablePayload.TYPE,
                OpenInscriptionTablePayload.STREAM_CODEC,
                OpenInscriptionTablePayload::handle
        );
    }
}
