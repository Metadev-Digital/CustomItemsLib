package metadev.digital.metacustomitemslib.rewards;

import metadev.digital.metacustomitemslib.Core;
import metadev.digital.metacustomitemslib.Tools;
import metadev.digital.metacustomitemslib.compatibility.addons.ProtocolLibCompat;
import metadev.digital.metacustomitemslib.compatibility.addons.ProtocolLibHelper;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class PickupRewards {

	private Core plugin;

	public PickupRewards(Core plugin) {
		this.plugin = plugin;
	}

	public void rewardPlayer(Player player, Item item, CallBack callBack) {
		if (Reward.isReward(item)) {
			Reward reward = Reward.getReward(item);
			if (reward.isBagOfGoldReward() || reward.isItemReward()) {
				callBack.setCancelled(true);

				boolean succes = false;
				succes = Core.getEconomyManager().depositPlayer(player, reward.getMoney());
				if (succes) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 29);
					item.remove();
					if (Core.getCoreRewardManager().getDroppedMoney().containsKey(item.getEntityId()))
						Core.getCoreRewardManager().getDroppedMoney().remove(item.getEntityId());
					if (ProtocolLibCompat.isSupported())
						ProtocolLibHelper.pickupMoney(player, item);

					if (reward.getMoney() == 0) {
						Core.getMessages().debug("%s picked up a %s" + ChatColor.RESET + " (# of rewards left=%s)",
								player.getName(), reward.isItemReward() ? "ITEM" : reward.getDisplayName(),
								Core.getCoreRewardManager().getDroppedMoney().size());
					} else {
						Core.getMessages().senderSendMessage(player, ChatColor.GREEN + Core.getMessages().getString("core.moneypickup", "money", ChatColor.valueOf(Core.getConfigManager().rewardTextColor) + Core.getEconomyManager().format(reward.getMoney()), "rewardname", ChatColor.valueOf(Core.getConfigManager().rewardTextColor)	+ (reward.getDisplayName().isEmpty() ? Core.getConfigManager().bagOfGoldName : reward.getDisplayName())));

						Core.getMessages().debug(
								"%s picked up a %s" + ChatColor.RESET + " with a value:%s (# of rewards left=%s)(PickupRewards)",
								player.getName(), reward.isItemReward() ? "ITEM" : reward.getDisplayName(),
								Tools.format(Tools.round(reward.getMoney())),
								Core.getCoreRewardManager().getDroppedMoney().size());
					}
				} else {
					callBack.setCancelled(true);
				}
			}
		}
	}

	public interface CallBack {

		void setCancelled(boolean canceled);

	}

}
