package metadev.digital.metacustomitemslib.compatibility;

public interface IFeatureHolder {
    void registerFeatures();
    boolean isFeatureEnabled(String name);
    boolean isFeatureSupported(String name);
    boolean isFeatureActive(String name);
    Feature getFeature(String name);
}
