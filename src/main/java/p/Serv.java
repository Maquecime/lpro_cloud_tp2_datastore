package p;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.StructuredQuery.OrderBy;




@Path("/employes")
public class Serv {
		
 	
	 @POST
	 @Consumes("application/json")
	 public void addEmployee(Employees e) {
		 String kind = "Employees";
		 String id = e.getId();
		 String name = e.getName();
		 Datastore datastore = DatastoreOptions.newBuilder().setProjectId("inf63app10").build().getService();
		 Key taskKey = datastore.newKeyFactory().setKind(kind).newKey(id);
		 Entity employee = Entity.newBuilder(taskKey).set("name", name).build();
		 datastore.put(employee);
	 }
	 
	 @GET
	 @Produces("text/html")
	 public String getEmployees() {
		 try {
		 Datastore datastore = DatastoreOptions.newBuilder().setProjectId("inf63app10").build().getService();
		 Query<Entity> query = Query.newEntityQueryBuilder().setKind("Employees").build();
		 Iterator<Entity> it = datastore.run(query);
		 String ret = "<html><head><meta charset='utf-8'/></head><body><ul>";
		 while(it.hasNext()) {
			 Entity e = it.next();
			 ret += "<li>"+e.getString("name")+"</li>";
		 }
		 ret+="</ul></body></html>";
		 return ret;
		 }
		 catch (DatastoreException e) {System.out.println(e.getReason());
		 throw new WebApplicationException(503);}
	 }	
 }
