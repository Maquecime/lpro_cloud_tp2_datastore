package p;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.StructuredQuery.OrderBy;




@Path("/employes")
public class Serv {
		
	Datastore datastore;
	public Serv() {
		 Datastore datastore = DatastoreOptions.newBuilder().setProjectId("inf63app10").build().getService();

	} 
	 
	 @POST
	 @Consumes("application/json")
	 public void addEmployee(Employees e) {
		 String kind = "Employees";
		 String name = "TestDatastore";
		 Key taskKey = datastore.newKeyFactory().setKind(kind).newKey(name);
		 
		 Entity employee = Entity.newBuilder(taskKey).set("id", taskKey).build();
		 datastore.put(employee);
	 }
	 
	 @GET
	 @Produces("text/html")
	 public String getEmployees() {
		 Query<Entity> query = Query.newEntityQueryBuilder().setKind("Employees").setOrderBy(OrderBy.asc("name")).build();
		 Iterator<Entity> it = datastore.run(query);
		 String ret = "<html><head><meta charset='utf-8'/></head><body><ul>";
		 while(it.hasNext()) {
			 Entity e = it.next();
			 ret += "<li>"+e.getString("name")+"</li>";
		 }
		 ret+="</ul></body></html>";
		 return ret;
	 }
	
 }
