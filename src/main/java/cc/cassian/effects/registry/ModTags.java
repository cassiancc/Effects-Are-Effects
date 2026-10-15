package cc.cassian.effects.registry;

import cc.cassian.effects.EmbersAndIcicles;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
	public static final TagKey<Block> SPELEOTHEMS = block("speleothems");
	public static final TagKey<Block> MELTS_ICICLE_ABOVE = block("melts_icicle_above");

	public static final TagKey<Item> FROSTBITE_PREFERRED_WEAPONS = item("frostbite_preferred_weapons");

	public static final TagKey<EntityType<?>> DOES_NOT_BREAK_BRITTLE_ICE = entity("does_not_break_brittle_ice");

	private static TagKey<Block> block(String name) {
		return TagKey.create(Registries.BLOCK, EmbersAndIcicles.of(name));
	}

	private static TagKey<Item> item(String name) {
		return TagKey.create(Registries.ITEM, EmbersAndIcicles.of(name));
	}

	private static TagKey<EntityType<?>> entity(String name) {
		return TagKey.create(Registries.ENTITY_TYPE, EmbersAndIcicles.of(name));
	}
}
