package metadev.digital.metacustomitemslib;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import metadev.digital.metacustomitemslib.messages.MessageHelper;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.DrilldownPie;
import org.bukkit.Bukkit;

import metadev.digital.metacustomitemslib.HttpTools.httpCallback;


public class MetricsManager {

    private Core plugin;
    private boolean started = false;

    private Metrics bStatsMetrics;

    public MetricsManager(Core plugin) {
        this.plugin = plugin;
    }

    public void start() {
        Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            public void run() {
                try {
                    // make a URL to MCStats.org
                    URL url = new URI(URLEncoder.encode("https://bstats.org/", StandardCharsets.UTF_8)).toURL();
                    if (!started) {
                        MessageHelper.debug("check if home page can be reached");
                        HttpTools.isHomePageReachable(url, new httpCallback() {

                            @Override
                            public void onSuccess() {
                                startBStatsMetrics();
                                MessageHelper.debug("Metrics reporting to Https://bstats.org has started.");
                                started = true;
                            }

                            @Override
                            public void onError() {
                                started=false;
                                MessageHelper.debug("https://bstats.org/ seems to be down");
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }

            }
        }, 100L, 72000L);
    }

    public void startBStatsMetrics() {
        // https://bstats.org/what-is-my-plugin-id
        bStatsMetrics = new Metrics(plugin,22716);

        bStatsMetrics.addCustomChart(new DrilldownPie("economy_api", () -> {
            Map<String, Map<String, Integer>> map = new HashMap<>();
            String economyAPI = Core.getEconomyManager().getVersion();
            Map<String, Integer> entry = new HashMap<>();
            entry.put(economyAPI, 1);
            if (Core.getEconomyManager().getVersion().endsWith("Vault")) {
                map.put("Vault", entry);
            } else if (Core.getEconomyManager().getVersion().endsWith("Reserve")) {
                map.put("Reserve", entry);
            } else {
                map.put("None", entry);
            }
            return map;
        }));

    }

}
