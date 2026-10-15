package cc.cassian.effects.config;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;

public class ModConfig extends WrappedConfig {
	@Comment("Whether mining Ice without Silk Touch drops Ice Balls/Cubes")
	public boolean iceDropsIceBalls = true;
	@Comment("Whether to rename Ice Balls to Ice Cubes")
	public boolean iceCubes = true;
}
