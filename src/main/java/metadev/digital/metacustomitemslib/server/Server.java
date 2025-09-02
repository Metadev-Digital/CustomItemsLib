package metadev.digital.metacustomitemslib.server;

import org.bukkit.Bukkit;

public class Server {

	// *******************************************************************
	// Version detection TODO: Finish removing deprecated calls
	// *******************************************************************

	@Deprecated
	public static boolean isMC121OrNewer() {
		return Server.isAfterProvidedVersion("1.21.0");
	}

	@Deprecated
	public static boolean isMC120OrNewer() {
		return Server.isAfterProvidedVersion("1.20.0");
	}

	@Deprecated
	public static boolean isMC119OrNewer() {
		return Server.isAfterProvidedVersion("1.19.0");
	}

	@Deprecated
	public static boolean isMC118OrNewer() {
		return Server.isAfterProvidedVersion("1.18.0");
	}

	@Deprecated
	public static boolean isMC117OrNewer() {
		return Server.isAfterProvidedVersion("1.17.0");
	}

	@Deprecated
	public static boolean isMC1162OrNewer() {
		return Server.isAfterProvidedVersion("1.16.2");
	}

	@Deprecated
	public static boolean isMC116OrNewer() {
		return Server.isAfterProvidedVersion("1.16.0");
	}

	@Deprecated
	public static boolean isMC115OrNewer() {
		return Server.isAfterProvidedVersion("1.15.0");
	}

	@Deprecated
	public static boolean isMC114OrNewer() {
		return Server.isAfterProvidedVersion("1.14.0");
	}

	@Deprecated
	public static boolean isMC113OrNewer() {
		return Server.isAfterProvidedVersion("1.13.0");
	}

	@Deprecated
	public static boolean isMC112OrNewer() {
		return Server.isAfterProvidedVersion("1.12.0");
	}

	@Deprecated
	public static boolean isMC111OrNewer() {
		return Server.isAfterProvidedVersion("1.11.0");
	}

	@Deprecated
	public static boolean isMC110OrNewer() {
		return Server.isAfterProvidedVersion("1.10.0");
	}

	@Deprecated
	public static boolean isMC19OrNewer() {
		return Server.isAfterProvidedVersion("1.9.0");
	}

	// *******************************************************************
	// Version detection
	// *******************************************************************
	public static String getServerVersion() {
		return Bukkit.getBukkitVersion().split("-")[0];
	}

	public static boolean isAfterProvidedVersion(String compareVersion) {
		return getServerVersion().compareTo(compareVersion) >= 0;
	}

	public static ArchitectureEnum getServerArchitecture() {
		String serverName = Bukkit.getServer().getName();
		String versionTitle = Bukkit.getServer().getVersion().toLowerCase();

		if (serverName.equalsIgnoreCase("Paper") && versionTitle.contains("paper")) return ArchitectureEnum.PAPER;
		if (serverName.equalsIgnoreCase("Purpur") && versionTitle.contains("purpur")) return ArchitectureEnum.PURPUR;
		if (serverName.equalsIgnoreCase("CraftBukkit") && versionTitle.contains("spigot")) return ArchitectureEnum.SPIGOT;
		if (serverName.equalsIgnoreCase("CraftBukkit") && versionTitle.contains("bukkit")) return ArchitectureEnum.BUKKIT;
		if (serverName.equalsIgnoreCase("Glowstone")) return  ArchitectureEnum.GLOWSTONE;

		return ArchitectureEnum.UNKNOWN;
	}

	public static boolean isGlowstoneServer() {
		return Bukkit.getServer().getName().equalsIgnoreCase("Glowstone");
	}

    public static boolean isPaperServer() {
        return Bukkit.getServer().getName().equalsIgnoreCase("Paper")
                && Bukkit.getServer().getVersion().toLowerCase().contains("paper");
    }

    public static boolean isPurpurServer() {
        return Bukkit.getServer().getName().equalsIgnoreCase("Purpur")
                && Bukkit.getServer().getVersion().toLowerCase().contains("purpur");
    }

	public static boolean isSpigotServer() {
		return Bukkit.getServer().getName().equalsIgnoreCase("CraftBukkit")
				&& Bukkit.getServer().getVersion().toLowerCase().contains("spigot");
	}

	public static boolean isCraftBukkitServer() {
		return Bukkit.getServer().getName().equalsIgnoreCase("CraftBukkit")
				&& Bukkit.getServer().getVersion().toLowerCase().contains("bukkit");
	}

}
