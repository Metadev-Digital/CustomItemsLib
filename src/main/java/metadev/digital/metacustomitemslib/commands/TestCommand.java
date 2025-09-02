package metadev.digital.metacustomitemslib.commands;

import metadev.digital.metacustomitemslib.Core;
import metadev.digital.metacustomitemslib.compatibility.addons.CMICompat;
import metadev.digital.metacustomitemslib.compatibility.addons.TitleManagerCompat;
import metadev.digital.metacustomitemslib.messages.MessageHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class TestCommand implements ICommand {

	private Core plugin;

	public TestCommand(Core plugin) {
		this.plugin = plugin;
	}

	@Override
	public String getName() {
		return "test";
	}

	@Override
	public String[] getAliases() {
		return new String[] { "test", "-t" };
	}

	@Override
	public String getPermission() {
		return "customitemslib.version";
	}

	@Override
	public String[] getUsageString(String label, CommandSender sender) {
		return new String[] { ChatColor.GOLD + label };
	}

	@Override
	public String getDescription() {
		return Core.getMessages().getString("Local dev test command");
	}

	@Override
	public boolean canBeConsole() {
		return true;
	}

	@Override
	public boolean canBeCommandBlock() {
		return false;
	}

	@Override
	public boolean onCommand(CommandSender sender, String label, String[] args) {
		// MessageHelper.warning("CMI COMPAT : " + CMICompat.isFullyLoaded());
		// TitleManagerCompat.setActionBar(sender.getServer().getPlayer(sender.getName()),"Test Action Bar");
		// TitleManagerCompat.sendTitles(sender.getServer().getPlayer(sender.getName()),"Test Title", "Test Sub Title", 0, 10000, 0);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
		return null;
	}

}
