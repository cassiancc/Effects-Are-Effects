//? if <26.3 {
/*package cc.cassian.effects.worldgen;

import cc.cassian.effects.registry.ModTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class IceCaveOreFeature extends Feature<IceCaveOreFeature.Configuration> {
	public IceCaveOreFeature() {
		super(Configuration.CODEC);
	}

	@Override
	public boolean place(final FeaturePlaceContext<Configuration> context) {
		Configuration config = context.config();
		BlockPos origin = context.origin();
		Block patchBlock = origin.getY() < 0 ? Blocks.DEEPSLATE : Blocks.STONE;
		OreConfiguration patch = new OreConfiguration(List.of(
				OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), Blocks.STONE.defaultBlockState()),
				OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), Blocks.DEEPSLATE.defaultBlockState()),
				OreConfiguration.target(new BlockMatchTest(Blocks.TUFF), patchBlock.defaultBlockState()),
				OreConfiguration.target(new TagMatchTest(ModTags.ICE_CAVE_ORE_REPLACEABLES), patchBlock.defaultBlockState())
		), config.patchSize);

		Feature.ORE.place(patch, context.level(), context.chunkGenerator(), context.random(), origin);
		return config.ore.value().place(context.level(), context.chunkGenerator(), context.random(), origin);
	}

	public record Configuration(int patchSize, Holder<ConfiguredFeature<?, ?>> ore) implements FeatureConfiguration {
		public static final Codec<Configuration> CODEC = RecordCodecBuilder.create(i -> i.group(
				Codec.intRange(0, 64).fieldOf("patch_size").forGetter(Configuration::patchSize),
				ConfiguredFeature.CODEC.fieldOf("ore").forGetter(Configuration::ore)
		).apply(i, Configuration::new));
	}
}
*///?}
