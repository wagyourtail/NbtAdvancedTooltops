package com.mpcs.nbtadvtooltip;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

@Mod("nbtadvtooltip")
public class NBTPreview {

    public static KeyBinding showNbtKeybinding;

    public NBTPreview() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    @SubscribeEvent
    public void ItemTooltipEvent(ItemTooltipEvent itemTooltipEvent) {
        if (itemTooltipEvent.getFlags().isAdvanced()) {
            ItemStack stack = itemTooltipEvent.getItemStack();
            if (stack.hasTag()) {
                if (showNbtKeybinding.isKeyDown()) {
                    String tags = stack.getTag().toString();
                    itemTooltipEvent.getToolTip().add(new StringTextComponent(tags));
                } else {
                    itemTooltipEvent.getToolTip().add(new TranslationTextComponent("tooltip.nbtadvtooltip.hold_key", I18n.format(showNbtKeybinding.getKey().getTranslationKey())));
                }

            } else {
                itemTooltipEvent.getToolTip().add(new TranslationTextComponent("tooltip.nbtadvtooltip.no_nbt_found"));
            }
        }
    }

    private void clientSetup(FMLClientSetupEvent event) {
        showNbtKeybinding = new KeyBinding("key.nbtadvtooltip.show_nbt.desc", GLFW.GLFW_KEY_LEFT_SHIFT, "key.nbtadvtooltip.category");
        ClientRegistry.registerKeyBinding(showNbtKeybinding);
    }
}
