package com.nidza.inventoryinscription;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class ClientConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.IntValue BUTTON_X_OFFSET;
    public static final ModConfigSpec.IntValue BUTTON_Y_OFFSET;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        BUTTON_X_OFFSET = builder
                .comment("Horizontal offset from the recipe book button. The default places the button directly to its right.")
                .defineInRange("buttonXOffset", 22, -10000, 10000);
        BUTTON_Y_OFFSET = builder
                .comment("Vertical offset from the recipe book button.")
                .defineInRange("buttonYOffset", 0, -10000, 10000);
        SPEC = builder.build();
    }

    private ClientConfig() {
    }
}
