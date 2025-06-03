package metadev.digital.metacustomitemslib.compatibility.addons;

import metadev.digital.metacustomitemslib.compatibility.enums.SupportedPluginEntities;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.CitizensPlugin;
import net.citizensnpcs.api.event.CitizensEnableEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.TraitInfo;
import metadev.digital.metabagofgold.BagOfGold;
import metadev.digital.metabagofgold.bank.BagOfGoldBankerTrait;
import metadev.digital.metacustomitemslib.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class CitizensCompat implements Listener {

	private BagOfGold plugin;

	private static boolean supported = false;
	private static CitizensPlugin citizensAPI;

	public CitizensCompat() {
		this.plugin = BagOfGold.getInstance();
		if (!isEnabledInConfig()) {
			Bukkit.getConsoleSender().sendMessage(Core.PREFIX
					+ "Compatibility with Citizens2 is disabled in config.yml");
		} else {
			citizensAPI = (CitizensPlugin) Bukkit.getPluginManager().getPlugin(SupportedPluginEntities.Citizens.getName());
			if (citizensAPI == null)
				return;

			TraitInfo trait = TraitInfo.create(BagOfGoldBankerTrait.class).withName("BagOfGoldBanker");
			citizensAPI.getTraitFactory().registerTrait(trait);
			Bukkit.getConsoleSender()
					.sendMessage(Core.PREFIX
							+ "Enabling compatibility with Citizens2 ("
							+ getCitizensPlugin().getDescription().getVersion() + ")");

			Bukkit.getPluginManager().registerEvents(this, plugin);

			supported = true;
		}
	}

	// **************************************************************************
	// OTHER FUNCTIONS
	// **************************************************************************
	public void setVillagerSkin(Entity entity) {
		getNPC(entity).setBukkitEntityType(EntityType.VILLAGER);
	}
	
	public void shutdown() {
		if (supported) {
			TraitInfo trait = TraitInfo.create(BagOfGoldBankerTrait.class).withName("BagOfGoldBanker");
			citizensAPI.getTraitFactory().deregisterTrait(trait);
		}
	}

	public static CitizensPlugin getCitizensPlugin() {
		return citizensAPI;
	}

	public static boolean isSupported() {
		if (supported && citizensAPI != null && CitizensAPI.hasImplementation())
			return supported;
		else
			return false;
	}

	public static boolean isNPC(Entity entity) {
		if (isSupported())
			return citizensAPI.getNPCRegistry().isNPC(entity);
		return false;
	}

	public boolean isNPC(Integer id) {
		if (isSupported())
			return citizensAPI.getNPCRegistry().getById(id) != null;
		return false;
	}

	public int getNPCId(Entity entity) {
		return citizensAPI.getNPCRegistry().getNPC(entity).getId();
	}

	public String getNPCName(Entity entity) {
		return citizensAPI.getNPCRegistry().getNPC(entity).getName();
	}

	public NPC getNPC(Entity entity) {
		return citizensAPI.getNPCRegistry().getNPC(entity);
	}

	public boolean isSentryOrSentinelOrSentries(Entity entity) {
		if (isNPC(entity))
			return citizensAPI.getNPCRegistry().getNPC(entity)
					.hasTrait(citizensAPI.getTraitFactory().getTraitClass("Sentry"))
					|| citizensAPI.getNPCRegistry().getNPC(entity)
							.hasTrait(citizensAPI.getTraitFactory().getTraitClass("Sentinel"))
					|| citizensAPI.getNPCRegistry().getNPC(entity)
							.hasTrait(citizensAPI.getTraitFactory().getTraitClass("Sentries"));
		return false;
	}

	public boolean isSentryOrSentinelOrSentries(String mobtype) {
		if (isNPC(Integer.valueOf(mobtype)))
			return citizensAPI.getNPCRegistry().getById(Integer.valueOf(mobtype))
					.hasTrait(citizensAPI.getTraitFactory().getTraitClass("Sentry"))
					|| citizensAPI.getNPCRegistry().getById(Integer.valueOf(mobtype))
							.hasTrait(citizensAPI.getTraitFactory().getTraitClass("Sentinel"))
					|| citizensAPI.getNPCRegistry().getById(Integer.valueOf(mobtype))
							.hasTrait(citizensAPI.getTraitFactory().getTraitClass("Sentries"));
		else
			return false;
	}

	public boolean isEnabledInConfig() {
		return plugin.getConfigManager().enableIntegrationCitizens;
	}

	// **************************************************************************
	// EVENTS
	// **************************************************************************

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onCitizensEnableEvent(CitizensEnableEvent event) {
		plugin.getMessages().debug("Citizens2 was enabled");
		supported = true;
	}
}