package elasticSearch.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
   @Bean
   public TransportClient client() throws Exception{
          InetSocketTransportAddress node=new InetSocketTransportAddress(
        		  InetAddress.getByName("127.0.0.1"),9300);
        Settings settings=Settings.builder().put("cluster.name","wali").build();
	    TransportClient client=new PreBuiltTransportClient(settings);
	    client.addTransportAddress(node);
	    return client;
	   
	    
   }
}
