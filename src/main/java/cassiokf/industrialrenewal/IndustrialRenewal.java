package cassiokf.industrialrenewal;

import cassiokf.industrialrenewal.config.IRConfig;
import cassiokf.industrialrenewal.init.FluidInit;
import cassiokf.industrialrenewal.init.ModBlocks;
import cassiokf.industrialrenewal.init.ModItems;
import cassiokf.industrialrenewal.init.TileEntityRegister;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("industrialrenewal")
public class IndustrialRenewal
{
    public static final String MODID = References.MODID;
    public static IndustrialRenewal instance;
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    static
    {
        //FluidRegistry.enableUniversalBucket();
    }

    public IndustrialRenewal()
    {
        instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, IRConfig.COMMON_SPEC, References.MODID + ".toml");
    }


    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info(References.NAME + " is loading preInit!");
        //FluidInit.registerFluids();
        //IRSoundRegister.registerSounds();
        //EntityInit.registerEntities();
        //proxy.preInit();
        //NetworkHandler.init();
        //ForgeChunkManager.setForcedChunkLoadingCallback(instance, new ChunkManagerCallback());
        //proxy.registerRenderers();
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, IRConfig.COMMOM, "industrialrenewal.toml");
        //IRConfig.loadConfig(IRConfig.COMMOM, FMLPaths.CONFIGDIR.get().resolve("industrialrenewal.toml").toString());
        //MinecraftForge.EVENT_BUS.register(IRSoundHandler.class);
        LOGGER.info("Done!");
    }

    private void init()
    {
        LOGGER.info(References.NAME + " is loading init!");
        //ModRecipes.init();
        //proxy.Init();

        //proxy.registerBlockRenderers();
        LOGGER.info("Done!");
    }

    private void clientRegistries(final FMLClientSetupEvent event)
    {
        //RenderHandler.registerEntitiesRender();
        //RenderHandler.registerCustomMeshesAndStates();
        //ModelLoaderRegistry.registerLoader();
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID)
    public static class RegistrationHandler
    {
        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event)
        {
            ModBlocks.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event)
        {
            ModItems.register(event.getRegistry());
            ModBlocks.registerItemBlocks(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event)
        {
            TileEntityRegister.registerTileEntity(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerFluids(final RegistryEvent.Register<Fluid> envent)
        {
            FluidInit.registerFluids(envent.getRegistry());
        }
    }
}