/**
 * Created by vincent on 2015/2/15 0015.
 */
class ResourceManager {
    static Resources resources;

    public static void init(){
        if(resources==null) {
            resources = new Resources();
        }
    }

    public static Resources getTable(){
        return  resources;
    }
}
