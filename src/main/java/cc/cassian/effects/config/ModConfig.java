package cc.cassian.effects.config;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;

public class ModConfig extends WrappedConfig {
	@Comment("Whether mining Ice without Silk Touch drops Ice Balls/Cubes")
	public boolean iceDropsIceBalls = true;
	@Comment("Whether to rename Ice Balls to Ice Cubes")
	public boolean iceCubes = true;

	//? if <26.4 {
	@Comment("Whether Ice Caves generate beneath frozen oceans in new chunks")
	public boolean generateIceCaves = true;
	@Comment("Whether Zombies turn into Frostbites, and Husks and Drowned into Zombies, after standing in Powder Snow")
	public boolean freezingConversions = true;
	@Comment("Whether Ice, Packed Ice, Blue Ice and Frosted Ice use their 26.4 sounds")
	public boolean newIceSounds = true;
	@Comment("Whether Snowballs knock back players")
	public boolean snowballKnockback = true;
	@Comment("Whether Water next to Blue Ice freezes into Brittle Ice")
	public boolean blueIceMakesBrittleIce = true;
	@Comment("Whether Mushrooms can be placed on any solid block regardless of light level")
	public boolean mushroomPlacement = true;
	//?}
	//? if <26 {
	/*@Comment("Whether the Frostbite Spawn Egg uses the old two-tone style instead of its 26.4 texture")
	public boolean legacySpawnEgg = true;
	*///?}
}
