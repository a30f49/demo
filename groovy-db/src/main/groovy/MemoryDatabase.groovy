import groovy.sql.Sql

/**
 * Created by vincent on 2015/2/15 0015.
 */
class MemoryDatabase {
    Sql db;

    public MemoryDatabase(){
        db = Sql.newInstance("jdbc:sqlite::memory:", "org.sqlite.JDBC");
    }

    public boolean execute(String sql){
        db.execute(sql);
    }
}
