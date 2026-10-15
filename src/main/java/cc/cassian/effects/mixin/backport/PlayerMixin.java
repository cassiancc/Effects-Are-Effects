//? if <26.4 {
package cc.cassian.effects.mixin.backport;

import cc.cassian.effects.EmbersAndIcicles;
import cc.cassian.effects.registry.ModTags;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
//? if >=26
import net.minecraft.server.level.ServerLevel;

// Players ignore attacks that deal no damage, so snowball hits deal basically 0 damage instead
@Mixin(Player.class)
public abstract class PlayerMixin {
	//? if >=26 {
	@WrapMethod(method = "hurtServer")
	private boolean embers$knockBackWithZeroDamage(ServerLevel level, DamageSource source, float damage, Operation<Boolean> original) {
		return original.call(level, source, embers$adjustDamage(source, damage));
	}
	//?} else {
	/*@WrapMethod(method = "hurt")
	private boolean embers$knockBackWithZeroDamage(DamageSource source, float damage, Operation<Boolean> original) {
		return original.call(source, embers$adjustDamage(source, damage));
	}
	*///?}

	@Unique
	private static float embers$adjustDamage(DamageSource source, float damage) {
		if (damage == 0.0F
				&& EmbersAndIcicles.CONFIG.snowballKnockback
				&& source.getDirectEntity() instanceof ThrowableItemProjectile projectile
				&& projectile.getItem().is(ModTags.KNOCKS_BACK_PLAYERS_EVEN_WITH_ZERO_DAMAGE)) {
			return Float.MIN_VALUE;
		}
		return damage;
	}
}
//?}
