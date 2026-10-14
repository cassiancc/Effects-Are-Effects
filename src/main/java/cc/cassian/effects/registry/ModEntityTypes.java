package cc.cassian.effects.registry;

import cc.cassian.effects.entity.IceBall;
import cc.cassian.effects.references.ModEntityTypeIds;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {
	public static final EntityType<IceBall> ICE_BALL = register(
			ModEntityTypeIds.ICE_BALL, EntityType.Builder.<IceBall>of(IceBall::new, MobCategory.MISC).noLootTable().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
	);

	private static <T extends Entity> EntityType<T> register(final ResourceKey<EntityType<?>> id, final EntityType.Builder<T> builder) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, id, builder.build(id));
	}

	public static void init() {

	}
}
