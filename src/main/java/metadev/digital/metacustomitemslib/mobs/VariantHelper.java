package metadev.digital.metacustomitemslib.mobs;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Villager;

import java.util.HashSet;
import java.util.Set;

/***
 *  Helper functions for Variants for readability of MobType
 */
public class VariantHelper {

    static HashSet<EntityType> variantEntities = new HashSet<>(Set.of(EntityType.VILLAGER, EntityType.RABBIT));

    /***
     * Return if the provided entity is a variant
     * @param entity - Killed entity to check
     * @return boolean representing variant status
     */
    public static boolean isEntityAVariant(Entity entity) {
        return doesEntityHaveVariants(entity) && !isEntityBaseVariant(entity);
    }

    /***
     * Return if entity has variants
     * @param entity - Killed entity to check
     * @return boolean representing if there are variants for the provided entity type
     */
    public static boolean doesEntityHaveVariants(Entity entity) {
        return variantEntities.contains(entity.getType());
    }

    /***
     * Comparison function to see if the provided killed entity is the base variant or not
     * @param entity - Killed entity to check
     * @return boolean representing if the passed entity is a variant
     */
    public static boolean isEntityBaseVariant(Entity entity){
        // Villagers
        if(entity.getType() == EntityType.VILLAGER){
            return ((Villager) entity).getProfession() == Villager.Profession.NONE;
        }

        // Rabbits
        if(entity.getType() == EntityType.RABBIT){
            return ((Rabbit) entity).getRabbitType() != Rabbit.Type.THE_KILLER_BUNNY;
        }

        return true;
    }

    public static String getVariantMobType(Entity entity) {
        // Villagers
        if(entity.getType() == EntityType.VILLAGER){
            if (((Villager) entity).getProfession() == Villager.Profession.ARMORER) return "Armorer";
            if (((Villager) entity).getProfession() == Villager.Profession.BUTCHER) return "BUTCHER";
            if (((Villager) entity).getProfession() == Villager.Profession.CARTOGRAPHER) return "Cartographer";
            if (((Villager) entity).getProfession() == Villager.Profession.CLERIC) return "Cleric";
            if (((Villager) entity).getProfession() == Villager.Profession.FARMER) return "FARMER";
            if (((Villager) entity).getProfession() == Villager.Profession.FISHERMAN) return "Fisherman";
            if (((Villager) entity).getProfession() == Villager.Profession.FLETCHER) return "Fletcher";
            if (((Villager) entity).getProfession() == Villager.Profession.LEATHERWORKER) return "Leatherworker";
            if (((Villager) entity).getProfession() == Villager.Profession.LIBRARIAN) return "LIBRARIAN";
            if (((Villager) entity).getProfession() == Villager.Profession.MASON) return "Mason";
            if (((Villager) entity).getProfession() == Villager.Profession.NITWIT) return "NITWIT";
            if (((Villager) entity).getProfession() == Villager.Profession.SHEPHERD) return "Shepherd";
            if (((Villager) entity).getProfession() == Villager.Profession.TOOLSMITH) return "Toolsmith";
            if (((Villager) entity).getProfession() == Villager.Profession.WEAPONSMITH) return "Weaponsmith";

            return "VILLAGER"; // Profession not found
        }
        else if(entity.getType() == EntityType.RABBIT){
            if(((Rabbit) entity).getRabbitType() == Rabbit.Type.THE_KILLER_BUNNY) return "KILLERRABBIT";

            return "RABBIT"; // Base Rabbit
        }

        return "";
    }

    public static String getBaseMobType(Entity entity) {
        if(entity.getType() == EntityType.VILLAGER) return "VILLAGER";
        else if(entity.getType() == EntityType.RABBIT) return "RABBIT";
        return entity.getType().toString();
    }
}
