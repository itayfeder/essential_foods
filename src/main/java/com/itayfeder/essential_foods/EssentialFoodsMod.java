package com.itayfeder.essential_foods;

import com.itayfeder.essential_foods.client.screens.MillScreen;
import com.itayfeder.essential_foods.client.screens.SeedProducerScreen;
import com.itayfeder.essential_foods.common.creativetabs.ModTab;
import com.itayfeder.essential_foods.init.BlockInit;
import com.itayfeder.essential_foods.init.ContainerInit;
import com.itayfeder.essential_foods.init.RecipeInit;
import com.itayfeder.essential_foods.utils.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("essential_foods")
public class EssentialFoodsMod
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "essential_foods";
    public static final ItemGroup MOD_TAB = new ModTab();

    public EssentialFoodsMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class, RecipeInit::registerRecipeSerializers);

        RegistryHandler.Init();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ContainerInit.MILL_CONTAINER.get(), MillScreen::new);
        ScreenManager.registerFactory(ContainerInit.SEED_PRODUCER_CONTAINER.get(), SeedProducerScreen::new);

        RenderTypeLookup.setRenderLayer(BlockInit.CORN_PLANT.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.RICE_PLANT.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.MILL.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(BlockInit.TOMATO_PLANT.get(), RenderType.getCutout());
    }
}
