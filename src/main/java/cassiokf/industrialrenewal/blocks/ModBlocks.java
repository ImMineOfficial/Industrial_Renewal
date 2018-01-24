package cassiokf.industrialrenewal.blocks;

import cassiokf.industrialrenewal.blocks.gates.BlockGateNot;
import cassiokf.industrialrenewal.tileentity.alarm.BlockAlarm;
import cassiokf.industrialrenewal.tileentity.cable.BlockEnergyCable;
import cassiokf.industrialrenewal.tileentity.cable.TileEntityEnergyCable;
import cassiokf.industrialrenewal.tileentity.exhaust.BlockExhaustFan;
import cassiokf.industrialrenewal.tileentity.gates.and.BlockGateAnd;
import cassiokf.industrialrenewal.tileentity.gates.nand.BlockGateNand;
import cassiokf.industrialrenewal.tileentity.gates.or.BlockGateOr;
import cassiokf.industrialrenewal.tileentity.gutter.BlockGutter;
import cassiokf.industrialrenewal.tileentity.valve.BlockValvePipeLarge;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {


    public static BlockOre oreCopper = new BlockOre("ore_copper", "oreCopper");
    public static BlockGateAnd gateAnd = new BlockGateAnd("gate_and");
    public static BlockGateNand gateNand = new BlockGateNand("gate_nand");
    public static BlockGateNot gateNot = new BlockGateNot("gate_not");
    public static BlockGateOr gateOr = new BlockGateOr("gate_or");
    public static BlockBase blockHazard = new BlockBase(Material.IRON, "block_hazard");
    public static BlockIndustrialFloor blockIndFloor = new BlockIndustrialFloor("industrial_floor");
    public static BlockChimney blockChimney = new BlockChimney("block_chimney");
    public static BlockFluidPipe fluidPipe = new BlockFluidPipe("fluid_pipe");
    public static BlockEnergyCable energyCable = new BlockEnergyCable("energy_cable");
    public static BlockFloorPipe floorPipe = new BlockFloorPipe("floor_pipe");
    public static BlockFloorCable floorCable = new BlockFloorCable("floor_cable");
    public static BlockFloorLamp floorLamp = new BlockFloorLamp("floor_lamp");
    public static BlockAlarm alarm = new BlockAlarm("alarm");
    public static BlockCatWalk catWalk = new BlockCatWalk("catwalk");
    public static BlockCatwalkStair catwalkStair = new BlockCatwalkStair("catwalk_stair");
    public static BlockPillar pillar = new BlockPillar("catwalk_pillar");
    public static BlockColumn column = new BlockColumn("catwalk_column");
    public static BlockCatwalkLadder iladder = new BlockCatwalkLadder("catwalk_ladder");
    public static BlockRoof roof = new BlockRoof("roof");
    public static BlockGutter gutter = new BlockGutter("gutter");
    public static BlockLight light = new BlockLight("light");
    public static BlockFluorescent fluorescent = new BlockFluorescent("fluorescent");
    public static BlockDummy dummy = new BlockDummy("dummy");
    public static BlockCatwalkGate catwalkGate = new BlockCatwalkGate("catwalk_gate");
    public static BlockCatwalkHatch hatch = new BlockCatwalkHatch("catwalk_hatch");
    public static BlockWindow window = new BlockWindow("window");
    public static BlockPlatform platform = new BlockPlatform("platform");
    public static BlockBrace brace = new BlockBrace("brace");
    public static BlockScaffold scaffold = new BlockScaffold("scaffold");
    public static BlockFrame frame = new BlockFrame("frame");
    public static BlockElectricFence efence = new BlockElectricFence("electric_fence");

    public static BlockExhaustFan exhaust = new BlockExhaustFan("exhaust_fan");

    public static BlockNormalRail normalRail = new BlockNormalRail("normal_rail");
    public static BlockCrossingRail crossingRail = new BlockCrossingRail("crossing_rail");
    public static BlockDetectorRail detectorRail = new BlockDetectorRail("detector_rail");
    public static BlockBoosterRail boosterRail = new BlockBoosterRail("booster_rail");

    public static BlockValvePipeLarge valveLarge = new BlockValvePipeLarge("valve_pipe_large");

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                oreCopper,
                blockHazard,
                blockIndFloor,
                blockChimney,
                normalRail,
                crossingRail,
                detectorRail,
                boosterRail,
                valveLarge,
                gateAnd,
                gateNand,
                gateNot,
                gateOr,
                alarm,
                fluidPipe,
                energyCable,
                floorPipe,
                floorCable,
                floorLamp,
                catWalk,
                catwalkStair,
                pillar,
                column,
                iladder,
                roof,
                gutter,
                light,
                fluorescent,
                dummy,
                catwalkGate,
                hatch,
                window,
                platform,
                brace,
                scaffold,
                frame,
                exhaust,
                efence
        );
        GameRegistry.registerTileEntity(valveLarge.getTileEntityClass(), valveLarge.getRegistryName().toString());
        GameRegistry.registerTileEntity(TileEntityEnergyCable.class, energyCable.getRegistryName().toString());
        GameRegistry.registerTileEntity(gateAnd.getTileEntityClass(), gateAnd.getRegistryName().toString());
        GameRegistry.registerTileEntity(gateNand.getTileEntityClass(), gateNand.getRegistryName().toString());
        GameRegistry.registerTileEntity(gateOr.getTileEntityClass(), gateOr.getRegistryName().toString());
        GameRegistry.registerTileEntity(alarm.getTileEntityClass(), alarm.getRegistryName().toString());
        GameRegistry.registerTileEntity(gutter.getTileEntityClass(), gutter.getRegistryName().toString());
        GameRegistry.registerTileEntity(exhaust.getTileEntityClass(), exhaust.getRegistryName().toString());
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                oreCopper.createItemBlock(),
                blockHazard.createItemBlock(),
                blockIndFloor.createItemBlock(),
                blockChimney.createItemBlock(),
                normalRail.createItemBlock(),
                crossingRail.createItemBlock(),
                detectorRail.createItemBlock(),
                boosterRail.createItemBlock(),
                valveLarge.createItemBlock(),
                gateAnd.createItemBlock(),
                gateNand.createItemBlock(),
                gateNot.createItemBlock(),
                gateOr.createItemBlock(),
                alarm.createItemBlock(),
                fluidPipe.createItemBlock(),
                energyCable.createItemBlock(),
                catWalk.createItemBlock(),
                catwalkStair.createItemBlock(),
                pillar.createItemBlock(),
                column.createItemBlock(),
                iladder.createItemBlock(),
                roof.createItemBlock(),
                gutter.createItemBlock(),
                light.createItemBlock(),
                fluorescent.createItemBlock(),
                catwalkGate.createItemBlock(),
                hatch.createItemBlock(),
                window.createItemBlock(),
                platform.createItemBlock(),
                brace.createItemBlock(),
                scaffold.createItemBlock(),
                frame.createItemBlock(),
                exhaust.createItemBlock(),
                efence.createItemBlock()
        );
    }

    public static void registerItemModels() {
        oreCopper.registerItemModel(Item.getItemFromBlock(oreCopper));
        blockHazard.registerItemModel(Item.getItemFromBlock(blockHazard));
        blockIndFloor.registerItemModel(Item.getItemFromBlock(blockIndFloor));
        blockChimney.registerItemModel(Item.getItemFromBlock(blockChimney));
        normalRail.registerItemModel(Item.getItemFromBlock(normalRail));
        crossingRail.registerItemModel(Item.getItemFromBlock(crossingRail));
        detectorRail.registerItemModel(Item.getItemFromBlock(detectorRail));
        boosterRail.registerItemModel(Item.getItemFromBlock(boosterRail));
        valveLarge.registerItemModel(Item.getItemFromBlock(valveLarge));
        gateAnd.registerItemModel(Item.getItemFromBlock(gateAnd));
        gateNand.registerItemModel(Item.getItemFromBlock(gateNand));
        gateNot.registerItemModel(Item.getItemFromBlock(gateNot));
        alarm.registerItemModel(Item.getItemFromBlock(alarm));
        gateOr.registerItemModel(Item.getItemFromBlock(gateOr));
        fluidPipe.registerItemModel(Item.getItemFromBlock(fluidPipe));
        energyCable.registerItemModel(Item.getItemFromBlock(energyCable));
        catWalk.registerItemModel(Item.getItemFromBlock(catWalk));
        catwalkStair.registerItemModel(Item.getItemFromBlock(catwalkStair));
        pillar.registerItemModel(Item.getItemFromBlock(pillar));
        column.registerItemModel(Item.getItemFromBlock(column));
        iladder.registerItemModel(Item.getItemFromBlock(iladder));
        roof.registerItemModel(Item.getItemFromBlock(roof));
        gutter.registerItemModel(Item.getItemFromBlock(gutter));
        light.registerItemModel(Item.getItemFromBlock(light));
        fluorescent.registerItemModel(Item.getItemFromBlock(fluorescent));
        catwalkGate.registerItemModel(Item.getItemFromBlock(catwalkGate));
        hatch.registerItemModel(Item.getItemFromBlock(hatch));
        window.registerItemModel(Item.getItemFromBlock(window));
        platform.registerItemModel(Item.getItemFromBlock(platform));
        brace.registerItemModel(Item.getItemFromBlock(brace));
        scaffold.registerItemModel(Item.getItemFromBlock(scaffold));
        frame.registerItemModel(Item.getItemFromBlock(frame));
        exhaust.registerItemModel(Item.getItemFromBlock(exhaust));
        efence.registerItemModel(Item.getItemFromBlock(efence));
    }
}
