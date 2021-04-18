package com.mpcs.nbtadvtooltip;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
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
    public static boolean isKeybindPressed;

    public NBTPreview() {

        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    @SubscribeEvent
    public void ItemTooltipEvent(ItemTooltipEvent itemTooltipEvent) {
        if (itemTooltipEvent.getFlags().isAdvanced()) {
            ItemStack stack = itemTooltipEvent.getItemStack();
            if (stack.hasTag()) {
                if (isKeybindPressed) {
                    String tags = stack.getTag().toString();
                    itemTooltipEvent.getToolTip().add(new StringTextComponent(tags));
                } else {
                    itemTooltipEvent.getToolTip().add(new TranslationTextComponent("tooltip.nbtadvtooltip.hold_key", showNbtKeybinding.func_238171_j_()));
                }

            } else {
                itemTooltipEvent.getToolTip().add(new TranslationTextComponent("tooltip.nbtadvtooltip.no_nbt_found"));
            }
        }
    }

    @SubscribeEvent
    public void onGuiKeyboardEvent(GuiScreenEvent.KeyboardKeyPressedEvent.Post event) {
        InputMappings.Input input = InputMappings.getInputByCode(event.getKeyCode(), event.getScanCode());
        if (showNbtKeybinding.isActiveAndMatches(input)) {
            isKeybindPressed = true;
        }
    }

    @SubscribeEvent
    public void onGuiKeyboardEvent(GuiScreenEvent.KeyboardKeyReleasedEvent.Post event) {
        InputMappings.Input input = InputMappings.getInputByCode(event.getKeyCode(), event.getScanCode());
        if (showNbtKeybinding.isActiveAndMatches(input)) {
            isKeybindPressed = false;
        }
    }

    private void clientSetup(FMLClientSetupEvent event) {
        showNbtKeybinding = new KeyBinding("key.nbtadvtooltip.show_nbt.desc", GLFW.GLFW_KEY_LEFT_SHIFT, "key.nbtadvtooltip.category");
        ClientRegistry.registerKeyBinding(showNbtKeybinding);
    }
}


/*
    @SubscribeEvent
    public void ItemTooltipEvent(ItemTooltipEvent itemTooltipEvent) {
        System.out.println("Start");
        if (itemTooltipEvent.getFlags().isAdvanced()) {
            System.out.println("Advanced");
            ItemStack stack = itemTooltipEvent.getItemStack();
            if (stack.hasTag()) {
                System.out.print("Has Tag");
                Keyboard
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
 */