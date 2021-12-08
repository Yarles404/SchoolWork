package tempName;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the keys in one ER table for enumerating left-deep join plans
 * Also comes with tools relating to left-deep joins
 *
 * @author Charles Yang
 */
public class RelationKeys {
    public static void main(String[] args) {
        ArrayList<RelationKeys> test = new ArrayList<>();
        /*int numRelations = 3; // for testing n! relations with common keys
        for (int i = 0; i < numRelations; i++) {
            test.add(new RelationKeys(new String[]{"id"}));
        }*/
        test.add(new RelationKeys(new String[]{"SSN"}));
        test.add(new RelationKeys(new String[]{"FAAid"}));
        test.add(new RelationKeys(new String[]{"Pid"}));
        test.add(new RelationKeys(new String[]{"SSN", "FAAid", "Pid"}));

        System.out.println(enumerateLeftDeepPlans(test));
    }

    /**
     * Keys in the table
     */
    ArrayList<String> keys;

    /**
     * Constructs a new RelationKeys from an array of keys
     *
     * @param keys
     */
    public RelationKeys(String[] keys) {
        this.keys = new ArrayList<>();
        this.keys.addAll(Arrays.asList(keys));
    }

    /**
     * Joins two RelationKeys to combine their keys
     *
     * @param t1
     * @param t2
     */
    public RelationKeys(RelationKeys t1, RelationKeys t2) {
        this.keys = new ArrayList<>();
        this.keys.addAll(t1.getKeys());
        this.keys.addAll(t2.getKeys());
    }

    /**
     * @param k key of interest
     * @return If this relation has the key of interest.
     */
    public boolean hasKey(String k) {
        return keys.contains(k);
    }

    /**
     * Checks if there's a common key (to avoid cross products)
     *
     * @param t
     * @return
     */
    public boolean hasCommonKey(RelationKeys t) {
        for (String k : t.getKeys()) {
            if (this.keys.contains(k)) return true;
        }

        return false;
    }

    /**
     * @return keys in the relation
     */
    public ArrayList<String> getKeys() {
        return keys;
    }

    /**
     * Enumerates the number of left-deep join plans that avoid cross products.
     *
     * @param relations relations to join
     * @return number of left-deep plans without cross-products
     */
    public static int enumerateLeftDeepPlans(ArrayList<RelationKeys> relations) {
        return recursiveEnumeration(relations, relations.size());
    }

    /**
     * Recursively counts the number of left deep plans.
     *
     * @param relations
     * @return
     */
    private static int recursiveEnumeration(ArrayList<RelationKeys> relations, int numTables) {
        // Base Case: all relations are joined successfully
        if (relations.size() == 1) return 1;

        int count = 0;
        if (relations.size() < numTables) {
            for (int i = 0; i < relations.size(); i++) {
                for (int j = i + 1; j < relations.size(); j++) {
                    if (relations.get(i).hasCommonKey(relations.get(j))) {
                        count = recurseOnNewRelations(relations, numTables, count, i, j);
                    }
                }
            }
        }
        else {
            for (int i = 0; i < relations.size(); i++) {
                for (int j = 0; j < relations.size(); j++) {
                    if (i != j && relations.get(i).hasCommonKey(relations.get(j))) {
                        count = recurseOnNewRelations(relations, numTables, count, i, j);
                    }
                }
            }
        }

        return count;
    }

    /**
     * Facilitates the joining of relations in an iteration and a new recursive call
     *
     * @param relations
     * @param numTables
     * @param count
     * @param i
     * @param j
     * @return
     */
    private static int recurseOnNewRelations(ArrayList<RelationKeys> relations, int numTables, int count, int i, int j) {
        ArrayList<RelationKeys> newRelations = new ArrayList<>(relations);
        newRelations.add(new RelationKeys(relations.get(i), relations.get(j)));
        newRelations.remove(relations.get(i));
        newRelations.remove(relations.get(j));
        count += recursiveEnumeration(newRelations, numTables);
        return count;
    }

}
