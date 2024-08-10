package io.discusser.moretnt.objects.registration;

import io.discusser.moretnt.objects.MoreTNTObject;

import java.util.ArrayList;

public class MoreTNTObjects {
    public static final ArrayList<MoreTNTObject> objects = new ArrayList<>();

    public static final MoreTNTObject NEGATIVE_TNT = addObject(new MoreTNTObject(MoreTNTBlocks.NEGATIVE_TNT, PrimedTNTObjects.NEGATIVE_TNT));
    public static final MoreTNTObject NEGATIVE_TNT_4X = addObject(new MoreTNTObject(MoreTNTBlocks.NEGATIVE_TNT_4X, PrimedTNTObjects.NEGATIVE_TNT_4X));
    public static final MoreTNTObject SHUFFLE_TNT = addObject(new MoreTNTObject(MoreTNTBlocks.SHUFFLE_TNT, PrimedTNTObjects.SHUFFLE_TNT));
    public static final MoreTNTObject SHUFFLE_TNT_4X = addObject(new MoreTNTObject(MoreTNTBlocks.SHUFFLE_TNT_4X, PrimedTNTObjects.SHUFFLE_TNT_4X));
    public static final MoreTNTObject SNOW_TNT = addObject(new MoreTNTObject(MoreTNTBlocks.SNOW_TNT, PrimedTNTObjects.SNOW_TNT));
    public static final MoreTNTObject SNOW_TNT_4X = addObject(new MoreTNTObject(MoreTNTBlocks.SNOW_TNT_4X, PrimedTNTObjects.SNOW_TNT_4X));
    public static final MoreTNTObject CAT_TNT = addObject(new MoreTNTObject(MoreTNTBlocks.CAT_TNT, PrimedTNTObjects.CAT_TNT));
    public static final MoreTNTObject DOME_TNT = addObject(new MoreTNTObject(MoreTNTBlocks.DOME_TNT, PrimedTNTObjects.DOME_TNT));
    public static final MoreTNTObject FIRE_TNT = addObject(new MoreTNTObject(MoreTNTBlocks.FIRE_TNT, PrimedTNTObjects.FIRE_TNT));
    public static final MoreTNTObject KNOCKBACK_TNT = addObject(new MoreTNTObject(MoreTNTBlocks.KNOCKBACK_TNT, PrimedTNTObjects.KNOCKBACK_TNT));
    public static final MoreTNTObject WATER_TNT = addObject(new MoreTNTObject(MoreTNTBlocks.WATER_TNT, PrimedTNTObjects.WATER_TNT));
    public static final MoreTNTObject LAVA_TNT = addObject(new MoreTNTObject(MoreTNTBlocks.LAVA_TNT, PrimedTNTObjects.LAVA_TNT));

    public static MoreTNTObject addObject(MoreTNTObject object) {
        objects.add(object);
        return object;
    }
}
