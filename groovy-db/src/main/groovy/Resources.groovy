import groovy.sql.Sql
import groovy.transform.CompileStatic

/**
 * Created by vincent on 2015/2/15 0015.
 */
@CompileStatic
class Resources {
    private static final String TABLE_NAME = "resource";

    Sql db;
    public Resources(){
        db = Sql.newInstance("jdbc:sqlite::memory:", "org.sqlite.JDBC");
        db.execute("drop table if exists resource");
        db.execute("create table resource (id integer, resource string, method string, body string, response string)");
    }

    public boolean insert(Resource row){
        StringBuffer buffer  = new StringBuffer()
        buffer.append("insert into resource values(")
        buffer.append(row.resource)
        buffer.append(row.method)
        buffer.append(row.body)
        buffer.append(row.response)
        buffer.append(")")

        db.execute(buffer.toString())
    }

    public void eachRow(Closure closure){
        db.eachRow("select * from resource", closure)
    }

    public void eachRow(String resource, Closure closure){
        db.eachRow("select * from resource where resource="+resource, closure)
    }
}
