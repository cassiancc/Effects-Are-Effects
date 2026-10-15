package cc.cassian.effects.registry;

import net.minecraft.world.level.block.Block;
//? if <26.4 {
import cc.cassian.effects.block.BrittleIceBlock;
import cc.cassian.effects.block.IceCrystalBlock;
import cc.cassian.effects.block.IcicleBlock;
import cc.cassian.effects.references.ModBlockIds;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
//~ if >=26.2 'EntityType;'->'EntityTypes;'
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;
//?} else {
/*import cc.cassian.effects.util.ModUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
*///?}

public class ModBlocks {
	//? if <26.4 {
	public static final Block BRITTLE_ICE = register(ModBlockIds.BRITTLE_ICE, BrittleIceBlock::new, BlockBehaviour.Properties.of()
			.mapColor(MapColor.ICE)
			.friction(0.98F)
			.strength(0.5F)
			.sound(ModSoundEvents.BRITTLE_ICE)
			.noOcclusion()
			.instrument(NoteBlockInstrument.CHIME)
			//~ if >=26.2 'EntityType.'->'EntityTypes.'
			.isValidSpawn((state, level, pos, type) -> type == EntityTypes.POLAR_BEAR)
			.isRedstoneConductor((state, level, pos) -> false)
			//~ if >=26.3 'DESTROY'->'POPPED'
			.pushReaction(PushReaction.POPPED)
	);
	public static final Block ICE_CRYSTAL = register(ModBlockIds.ICE_CRYSTAL, IceCrystalBlock::new, BlockBehaviour.Properties.of()
			.mapColor(MapColor.ICE)
			.sound(ModSoundEvents.ICE_CRYSTAL)
			.instrument(NoteBlockInstrument.CHIME)
			.strength(1.5F, 1.5F)
			.lightLevel(state -> 4)
			//~ if >=26.3 'DESTROY'->'POPPED'
			.pushReaction(PushReaction.POPPED)
			.isRedstoneConductor((state, level, pos) -> false)
			.noOcclusion()
	);
	public static final Block ICICLE = register(ModBlockIds.ICICLE, IcicleBlock::new, BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_LIGHT_BLUE)
			.forceSolidOn()
			.instrument(NoteBlockInstrument.CHIME)
			.noOcclusion()
			.sound(ModSoundEvents.ICICLE)
			.randomTicks()
			.strength(0.5F, 0.5F)
			.dynamicShape()
			//~ if >=26.3 'DESTROY'->'POPPED'
			.pushReaction(PushReaction.POPPED)
			.isRedstoneConductor((state, level, pos) -> false)
	);

	private static Block register(final ResourceKey<Block> id, final Function<BlockBehaviour.Properties, Block> factory, final BlockBehaviour.Properties properties) {
		Block block = factory.apply(properties
				//? if >1.21.2
				.setId(id)
		);
		return Registry.register(BuiltInRegistries.BLOCK, id, block);
	}
	//?} else {
	/*public static final Block BRITTLE_ICE = Blocks.BRITTLE_ICE;
	public static final Block ICE_CRYSTAL = Blocks.ICE_CRYSTAL;
	public static final Block ICICLE = Blocks.ICICLE;
	*///?}

	public static void init() {
		//? if >26.3 {
		/*ModUtil.addAlias(BuiltInRegistries.BLOCK, "brittle_ice");
		ModUtil.addAlias(BuiltInRegistries.BLOCK, "ice_crystal");
		ModUtil.addAlias(BuiltInRegistries.BLOCK, "icicle");
		*///?}
	}
}
