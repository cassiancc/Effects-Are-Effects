//? if <26.4 {
package cc.cassian.effects.item;

import cc.cassian.effects.entity.IceBall;
import cc.cassian.effects.registry.ModSoundEvents;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
//? if =1.21.1
//import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

public class IceBallItem extends Item implements ProjectileItem {
	public static final float PROJECTILE_SHOOT_POWER = 1F;

	public IceBallItem(final Properties properties) {
		super(properties);
	}

	//? if >26 {
	@Override
	public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		level.playSound(
				null,
				player.getX(),
				player.getY(),
				player.getZ(),
				ModSoundEvents.ICE_BALL_THROW,
				SoundSource.NEUTRAL,
				0.5F,
				0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
		);
		if (level instanceof ServerLevel serverLevel) {
			Projectile.spawnProjectileFromRotation(IceBall::new, serverLevel, itemStack, player, 0.0F, PROJECTILE_SHOOT_POWER, 1.0F);
		}

		player.awardStat(Stats.ITEM_USED.get(this));
		itemStack.consume(1, player);
		return InteractionResult.SUCCESS;
	}
	//?} else {

	/*public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.ICE_BALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
		if (!level.isClientSide()) {
			IceBall iceBall = new IceBall(level, player);
			iceBall.setItem(itemStack);
			iceBall.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
			level.addFreshEntity(iceBall);
		}

		player.awardStat(Stats.ITEM_USED.get(this));
		itemStack.consume(1, player);
		return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
	}
	*///?}

	@Override
	public Projectile asProjectile(final Level level, final Position position, final ItemStack itemStack, final Direction direction) {
		return new IceBall(level, position.x(), position.y(), position.z(), itemStack);
	}
}
//?}