package cc.cassian.effects.config;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;

public class ModConfig extends WrappedConfig {
	@Comment("Whether mining Ice without Silk Touch drops Ice Balls")
	public boolean iceDropsIceBalls = true;
}
