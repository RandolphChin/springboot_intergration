package com.shaphar.service.elasticsearch;

import com.mongodb.CommandResult;
import com.shaphar.domain.elasticsearch.Es;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.pipeline.PipelineAggregatorBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


@Service
public class ElasticSearchService {

    @Resource
    TransportClient transportClient;//注入es操作对象

    public String selectfromEsByid(Es es ,BigDecimal userId){
        GetResponse response = transportClient.prepareGet(es.getIndex(),es.getType(),String.valueOf(userId))
                .execute()
                .actionGet();
        String json = response.getSourceAsString();
        return json;
    }
    public SearchHits selectfromEsByField(Es es,String fieldName,String fieldVal ,int page,int size){
//高亮配置

        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("userName")
                .preTags("<em>")
                .postTags("</em>");
        QueryBuilder qb = QueryBuilders.matchQuery(fieldName ,fieldVal);
        SearchResponse response = transportClient.prepareSearch(es.getType()).setQuery(qb).setFrom(page).setSize(size).highlighter(highlightBuilder).get();
       // SearchResponse response = transportClient.prepareSearch(es.getType()).setQuery(qb).get();
        SearchHits searchHits = response.getHits();





        // 一共文档数
        long totalHits = searchHits.getTotalHits();
        return searchHits;
    }

    public  DeleteResponse deleteById(Es es,BigDecimal userId){
        DeleteResponse dResponse = transportClient.prepareDelete(es.getIndex(),es.getType(), String.valueOf(userId)).execute().actionGet();
        return dResponse;
    }

}
