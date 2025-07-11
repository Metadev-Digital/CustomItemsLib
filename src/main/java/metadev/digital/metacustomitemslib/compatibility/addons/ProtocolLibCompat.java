package metadev.digital.metacustomitemslib.compatibility.addons;

import com.comphenix.packetwrapper.WrapperPlayServerCollect;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import metadev.digital.metacustomitemslib.Core;
import metadev.digital.metacustomitemslib.compatibility.Feature;
import metadev.digital.metacustomitemslib.compatibility.FeatureList;
import metadev.digital.metacustomitemslib.compatibility.ICompat;
import metadev.digital.metacustomitemslib.compatibility.IFeatureHolder;
import metadev.digital.metacustomitemslib.compatibility.enums.BoundIdentifierEnum;
import metadev.digital.metacustomitemslib.compatibility.enums.SupportedPluginEntities;
import metadev.digital.metacustomitemslib.compatibility.enums.VersionSetIdentifierEnum;
import metadev.digital.metacustomitemslib.compatibility.exceptions.FeatureNotFoundException;
import metadev.digital.metacustomitemslib.compatibility.exceptions.SpinupShutdownException;
import metadev.digital.metacustomitemslib.messages.MessageHelper;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Iterator;
import java.util.List;

public class ProtocolLibCompat implements ICompat, IFeatureHolder {

	// ****** Standard ******
	private Plugin compatPlugin;
	private static boolean enabled = false, supported = false, loaded = false;
	private static String sMin, sMax, pMin = "5.3.0", pMax;
	private static FeatureList features;

	// ****** Plugin Specific ******
	private static ProtocolManager protocolManager;

	// https://www.spigotmc.org/resources/protocollib.1997/

	public ProtocolLibCompat() {
		compatPlugin = Bukkit.getPluginManager().getPlugin(SupportedPluginEntities.ProtocolLib.getName());

		if(compatPlugin != null) {
			try {
				start();
			} catch (SpinupShutdownException ignored) {
				// Do nothing beyond report error
			}
		}
	}

	// ****** ICompat ******

	@Override
	public void start() throws SpinupShutdownException {
		detectedMessage();
		registerFeatures();

		if (isActive()) {
			addPacketListeners();
			successfullyLoadedMessage();
			loaded = true;
		} else if (enabled && !supported) {
			Feature base = getFeature("base");
			if(base != null) unsupportedMessage(base);
			else pluginError("Plugin is enabled but not supported, and failed to understand the reasoning out of the base " +
					"feature. Likely caused by a corrupt / incorrect construction of the base feature.");
			throw new SpinupShutdownException();
		}
	}

	@Override
	public void shutdown() throws SpinupShutdownException {
		if (isActive() && loaded) {
			successfullyShutdownMessage();
			loaded = false;
		}
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean isSupported() {
		return supported;
	}

	@Override
	public boolean isActive() {
		return enabled && supported;
	}

	@Override
	public boolean isLoaded() {
		return loaded;
	}

	@Override
	public Plugin getPluginInstance() {
		return compatPlugin;
	}

	@Override
	public String getPluginName() {
		return compatPlugin.getName();
	}

	@Override
	public String getPluginVersion() {
		return compatPlugin.getDescription().getVersion();
	}

	// ****** IFeatureHolder ******

	@Override
	public void registerFeatures() {
		features = new FeatureList(getPluginVersion());

		// Base plugin
		enabled = Core.getConfigManager().enableIntegrationProtocolLib;
		features.addFeature("base", pMin, BoundIdentifierEnum.FLOOR, VersionSetIdentifierEnum.PLUGIN, enabled);
		supported = isFeatureSupported("base");

		// Other features
	}

	@Override
	public boolean isFeatureEnabled(String name) {
		boolean featureEnabled = false;
		try {
			featureEnabled = features.isFeatureEnabled(name);
		} catch (FeatureNotFoundException e) {
			MessageHelper.debug("Triggered a FeatureNotFoundException when trying to return enable flag of the feature " + name + " in the " + compatPlugin.getName() +" compat class." );
		}

		return featureEnabled;
	}

	@Override
	public boolean isFeatureSupported(String name) {
		boolean featureSupported = false;
		try {
			featureSupported = features.isFeatureSupported(name);
		} catch (FeatureNotFoundException e) {
			MessageHelper.debug("Triggered a FeatureNotFoundException when trying to return supported flag of the feature " + name + " in the " + compatPlugin.getName() +" compat class." );
		}

		return featureSupported;
	}

	@Override
	public boolean isFeatureActive(String name) {
		boolean featureActive = false;
		try {
			featureActive = features.isFeatureActive(name);
		} catch (FeatureNotFoundException e) {
			MessageHelper.debug("Triggered a FeatureNotFoundException when trying to return active flag of the feature " + name + " in the " + compatPlugin.getName() +" compat class." );
		}

		return featureActive;
	}

	@Override
	public Feature getFeature(String name) {
		Feature feature;
		try {
			feature = features.getFeature(name);
			return feature;
		} catch (FeatureNotFoundException e) {
			MessageHelper.debug("Triggered a FeatureNotFoundException when trying to return the feature " + name + " in the " + compatPlugin.getName() +" compat class." );
		}
		return null;
	}

	// ****** Plugin Specific ******

	public static ProtocolManager getProtocolmanager() {
		return protocolManager;
	}

	public static void addPacketListeners() {
		protocolManager = ProtocolLibrary.getProtocolManager();

		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Core.getInstance(), ListenerPriority.HIGHEST, PacketType.Play.Server.SET_SLOT, PacketType.Play.Server.WINDOW_ITEMS) {
			@Override
			public void onPacketSending(PacketEvent event) {
				if (event.getPacketType() == PacketType.Play.Server.SET_SLOT) {
					PacketContainer packet = event.getPacket().deepClone();
					StructureModifier<ItemStack> sm = packet.getItemModifier();
					for (int i = 0; i < sm.size(); i++) {
						ItemStack is = sm.getValues().get(i);
						if (is.hasItemMeta()) {
							ItemMeta itemMeta = is.getItemMeta();
							if (itemMeta != null && itemMeta.hasLore()) {
								List<String> lore = itemMeta.getLore();
								if(lore != null) {
									Iterator<String> itr = lore.iterator();
									while (itr.hasNext()) {
										String str = itr.next();
										if (str.startsWith("Hidden("))
											if (event.getPlayer().getGameMode() == GameMode.SURVIVAL)
												itr.remove();
									}
									itemMeta.setLore(lore);
									is.setItemMeta(itemMeta);
								}
							}
						}
					}
					event.setPacket(packet);
				}
				else if (event.getPacketType() == PacketType.Play.Server.WINDOW_ITEMS) {
					PacketContainer packet = event.getPacket().deepClone();
					StructureModifier<List<ItemStack>> modifiers = packet.getItemListModifier();
					for (int j = 0; j < modifiers.size(); j++) {
						List<ItemStack> itemStackList = modifiers.getValues().get(j);
						for (int i = 0; i < itemStackList.size(); i++) {
							ItemStack is = itemStackList.get(i);
							if (is.hasItemMeta()) {
								ItemMeta itemMeta = is.getItemMeta();
								if (itemMeta != null && itemMeta.hasLore()) {
									List<String> lore = itemMeta.getLore();
									if (lore != null ){
										Iterator<String> itr = lore.iterator();
										while (itr.hasNext()) {
											String str = itr.next();
											if (str.startsWith("Hidden("))
												if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
//														BagOfGold.getInstance().getMessages().debug("ProtocolLibHelper:ItemSlots=%s", event.getPacket().getItemSlots().toString());
													itr.remove();
												}
										}
										itemMeta.setLore(lore);
										is.setItemMeta(itemMeta);
									}
								}
							}
						}
					}
					event.setPacket(packet);
				}
			}
		});
	}

	public static void pickupMoney(Player player, Entity ent) {
		WrapperPlayServerCollect wpsc = new WrapperPlayServerCollect();
		wpsc.setCollectedEntityId(ent.getEntityId());
		wpsc.setCollectorEntityId(player.getEntityId());
		wpsc.sendPacket(player);
	}

}
