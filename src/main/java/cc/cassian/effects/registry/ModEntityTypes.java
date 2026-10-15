package cc.cassian.effects.registry;

import cc.cassian.effects.references.ModEntityTypeIds;
//? if <26.4 {
import cc.cassian.effects.entity.Frostbite;
import cc.cassian.effects.entity.IceBall;
//?} else {
/*import net.minecraft.world.entity.monster.zombie.Frostbite;
import net.minecraft.world.entity.projectile.throwableitemprojectile.IceBall;
*///?}
import cc.cassian.effects.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
//? if >26.2
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ServerLevelAccessor;
//? if >=26 {
import net.minecraft.world.entity.EntitySpawnReason;
//?} else {
/*import net.minecraft.world.entity.MobSpawnType;
*///?}

public class ModEntityTypes {
	//? if <26.4 {
	public static final EntityType<IceBall> ICE_BALL = register(
			ModEntityTypeIds.ICE_BALL, EntityType.Builder.<IceBall>of(IceBall::new, MobCategory.MISC)
					//? if >1.21.2
					.noLootTable()
					.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
	);
	public static final EntityType<Frostbite> FROSTBITE = register(
			ModEntityTypeIds.FROSTBITE, EntityType.Builder.<Frostbite>of(Frostbite::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F)
					.eyeHeight(1.74F)
					.passengerAttachments(2.0125F)
					.ridingOffset(-0.7F)
					.clientTrackingRange(8)
					//? if >=26
					.notInPeaceful()
	);
	//?} else {
	/*public static final EntityType<IceBall> ICE_BALL = EntityTypes.ICE_BALL;
	public static final EntityType<Frostbite> FROSTBITE = EntityTypes.FROSTBITE;
	*///?}

	private static <T extends Entity> EntityType<T> register(final ResourceKey<EntityType<?>> id, final EntityType.Builder<T> builder) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, id, builder.build(id
		//? if <26
		//.identifier().getPath()
		));
	}

	//? if <26.4 {
	//? if >=26 {
	public static boolean checkFrostbiteSpawnRules(final EntityType<Frostbite> type, final ServerLevelAccessor level,
													final EntitySpawnReason reason, final BlockPos pos, final RandomSource random) {
	//?} else {
	/*public static boolean checkFrostbiteSpawnRules(final EntityType<Frostbite> type, final ServerLevelAccessor level,
													final MobSpawnType reason, final BlockPos pos, final RandomSource random) {
	*///?}
		return Monster.checkMonsterSpawnRules(type, level, reason, pos, random);
	}
	//?}

	public static void init() {
		//? if >26.3 {
		/*ModUtil.addAlias(BuiltInRegistries.ENTITY_TYPE, "ice_ball");
		ModUtil.addAlias(BuiltInRegistries.ENTITY_TYPE, "frostbite");
		*///?}
	}
}
