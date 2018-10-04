package elasticSearch.controller;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElastricSearchQuery {
	@Autowired
	private TransportClient client;
	@RequestMapping("/get")
      public Map<String, Object> queryElasticSerch(){
		System.out.println("进来了");
    	GetResponse result=client.prepareGet("people1","man", "2").get();
    	System.out.println(result.getSource());
		return result.getSource();
    	  
      }
	
	@RequestMapping("/add")
      public ResponseEntity<String> addElasticSerch(@RequestParam(value="country",defaultValue="china") String country
    		  ,@RequestParam(value="name",defaultValue="wali")String name){
		String id="3";
		country="china";
		name="wali";
		System.out.println("进来了");
    	try {
    		//插入
		XContentBuilder context=XContentFactory.jsonBuilder().startObject()
			                                            .field("country",country)
			                                            .field("name",name).endObject();
		IndexResponse result=this.client.prepareIndex("people1","man",id).setSource(context).get();
		System.out.println(result);
		//return new ResponseEntity<String>(result.getId(),HttpStatus.OK);
		return new ResponseEntity<String>(result.getId(),HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	                                           	  
      }
	//根据id删除
	@RequestMapping("/delete")
    public ResponseEntity<String> deleteElasticSerch(@RequestParam(value="country",defaultValue="china") String country
  		  ,@RequestParam(value="name",defaultValue="wali")String name){
		String id="3";
		System.out.println("进来了");
  	try {
  		DeleteResponse result=  this.client.prepareDelete("people1","man",id).get();
		System.out.println(result);
		//return new ResponseEntity<String>(result.getId(),HttpStatus.OK);
		return new ResponseEntity<String>(result.getResult().toString(),HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}                                           	  
    }
	
	  //修改(根据id修改)
		@RequestMapping("/update")
	    public ResponseEntity<String> updateElasticSerch(@RequestParam(value="country",defaultValue="china") String country
	  		  ,@RequestParam(value="name",defaultValue="wali")String name){
			String id="3";
			name="张三";
			System.out.println("进来了");
			//UpdateRequest  update=new UpdateRequest();
	  		//UpdateResponse result=  this.client.prepareUpdate("people1","man",id).get();
			try {
			UpdateRequest  update=new UpdateRequest("people1","man",id);
	    		//插入
			XContentBuilder builder=XContentFactory.jsonBuilder().startObject();
				//可以加判断传进来的值不为null,在builder。
			builder .field("country",country);
			builder.field("name",name);
			builder.endObject();
			update.doc(builder);
			//return new ResponseEntity<String>(result.getId(),HttpStatus.OK);
			UpdateResponse result=  this.client.update(update).get();
			System.out.println(result);
			return new ResponseEntity<String>(result.getResult().toString(),HttpStatus.OK);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}	                                    	  
	    }
} 
