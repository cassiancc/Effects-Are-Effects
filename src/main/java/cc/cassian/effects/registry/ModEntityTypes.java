package cc.cassian.effects.registry;

import cc.cassian.effects.references.ModEntityTypeIds;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
//? if >26.2
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {
	//? if <26.4 {
	public static final EntityType<cc.cassian.effects.entity.IceBall> ICE_BALL = register(
			ModEntityTypeIds.ICE_BALL, EntityType.Builder.<cc.cassian.effects.entity.IceBall>of(cc.cassian.effects.entity.IceBall::new, MobCategory.MISC)
					//? if >1.21.2
					.noLootTable()
					.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
	);
	//?} else {
	/*public static final EntityType<net.minecraft.world.entity.projectile.throwableitemprojectile.IceBall> ICE_BALL = EntityTypes.ICE_BALL;
	*///?}

	private static <T extends Entity> EntityType<T> register(final ResourceKey<EntityType<?>> id, final EntityType.Builder<T> builder) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, id, builder.build(id
		//? if <26
		//.identifier().getPath()
		));
	}

	public static void init() {

	}
}
