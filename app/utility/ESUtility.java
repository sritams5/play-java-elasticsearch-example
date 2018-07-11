package utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Employee;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import play.libs.Json;

import static org.elasticsearch.index.query.QueryBuilders.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ESUtility {

    TransportClient client;
    //ObjectMapper mapper;

    @Inject
    public ESUtility(ESFactory factory){
        this.client = factory.getESClient();
        //mapper = new ObjectMapper();
    }

    public List<Employee> getAllEmployee(){
        SearchResponse response = client.prepareSearch("hr").setTypes("employee").get();
        return createListFromResponseString(response);
    }

    public List<Employee> getEmployeeByName(String name){
        BoolQueryBuilder query = boolQuery().must(termQuery("name", name));
        SearchResponse response = client.prepareSearch("hr").setTypes("employee").setQuery(query).get();
        return createListFromResponseString(response);
    }

    /*
    public String addEmployee(Employee emp){
        IndexResponse response = client.prepareIndex("hr", "employee").setSource(Json.toJson(emp), XContentType.JSON).get();
        System.out.println(response.toString());
        return null;
    }
    */

    private List<Employee> createListFromResponseString(SearchResponse response){
        return Arrays.stream(response.getHits().getHits()).
                map(hit -> Json.fromJson(Json.parse(hit.getSourceAsString()), Employee.class)).collect(Collectors.toList());
    }


}
