//? if <26.4 {
package cc.cassian.effects.entity;

import cc.cassian.effects.registry.ModEffects;
import cc.cassian.effects.registry.ModEntityTypes;
import cc.cassian.effects.registry.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
//? if >26.2
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
//? if >26
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class IceBall extends ThrowableItemProjectile {
	public IceBall(final EntityType<IceBall> type, final Level level) {
		super(type, level);
	}

	//? if >26 {
	public IceBall(final Level level, final LivingEntity mob, final ItemStack itemStack) {
		super(ModEntityTypes.ICE_BALL, mob, level, itemStack);
	}

	public IceBall(final Level level, final double x, final double y, final double z, final ItemStack itemStack) {
		super(ModEntityTypes.ICE_BALL, x, y, z, level, itemStack);
	}
	//?} else {
	/*public IceBall(final Level level, final LivingEntity mob) {
		super(ModEntityTypes.ICE_BALL, mob, level);
	}

	public IceBall(Level level, double x, double y, double z, ItemStack itemStack) {
		super(ModEntityTypes.ICE_BALL, x, y, z, level);
	}
	*///?}

	protected Item getDefaultItem() {
		return ModItems.ICE_BALL;
	}

	protected ParticleOptions getParticle() {
		//? if >26 {
		return new ItemParticleOption(ParticleTypes.ITEM, ItemStackTemplate.fromNonEmptyStack(this.getItem()));
		//?} else {
		/*return new ItemParticleOption(ParticleTypes.ITEM, this.getItem());
		*///?}
	}

	public void handleEntityEvent(final byte id) {
		if (id == 3) {
			ParticleOptions particle = this.getParticle();

			for(int i = 0; i < 8; ++i) {
				this.level().addParticle(particle, this.getX(), this.getY(), this.getZ(), (double)0.0F, (double)0.0F, (double)0.0F);
			}
		}

	}

	protected void onHitEntity(final EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		Entity entity = hitResult.getEntity();
		int damage = getDamage(hitResult);
		if (entity instanceof LivingEntity livingEntity && livingEntity.isAffectedByPotions() && !this
				//? if <26
				//.getType()
				.is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)) {
			livingEntity.addEffect(new MobEffectInstance(ModEffects.FREEZING, 100));
		}
		entity.hurt(this.damageSources().thrown(this, this.getOwner()), (float)damage);
	}

	protected int getDamage(final EntityHitResult hitResult) {
		float pow = (float)this.getDeltaMovement().length();
		return Mth.ceil(Math.clamp(pow * 1.395F, 0.0F, 2.1474836E9F));
	}

	protected void onHit(final HitResult hitResult) {
		super.onHit(hitResult);
		if (!this.level().isClientSide()) {
			this.level().broadcastEntityEvent(this, (byte)3);
			this.discard();
		}

	}
}
//?}