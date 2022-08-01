package com.mall.data;

import com.mall.data.domain.entity.Item;
import com.mall.data.infrastructure.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchApiTest {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private ItemRepository itemRepository;
    @Test
    public void createIndex(){
        elasticsearchTemplate.createIndex(Item.class);
    }
    @Test
    public void putMapping(){
        elasticsearchTemplate.putMapping(Item.class);
    }
    @Test
    public void index(){
        Item item=new Item(1L,"小米手机11S","手机","小米",5900.00);
        itemRepository.save(item);
    }
    @Test
    public void indexList(){
        List<Item> items=new ArrayList<Item>();
        items.add(new Item(6L,"荣耀7","手机","华为",2600.00));
        items.add(new Item(7L,"荣耀8","手机","华为",2899.00));
        itemRepository.saveAll(items);
    }
    @Test
    public void searchById(){
        Optional<Item> item=itemRepository.findById(1L);
        System.out.println(item.get());
    }
    @Test
    public void searchList(){
        Iterable<Item> items=itemRepository.findAll();
        items.forEach(item -> System.out.println(item));
    }
    @Test
    public void search(){
        NativeSearchQueryBuilder queryBuilder=new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.matchQuery("title","小米红米"));
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        //queryBuilder.withPageable(PageRequest.of(0,2));
        Page<Item> items=this.itemRepository.search(queryBuilder.build());
        items.forEach(item -> System.out.println(item));
    }
    @Test
    public void searchAgg(){
        NativeSearchQueryBuilder queryBuilder=new NativeSearchQueryBuilder();
        //queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""},null));
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand")
                .subAggregation(AggregationBuilders.avg("avgPrice").field("price"))
        );
        AggregatedPage<Item> aggregatedPage=(AggregatedPage<Item>)this.itemRepository.search(queryBuilder.build());
        StringTerms stringTerms=(StringTerms)aggregatedPage.getAggregation("brands");
        List<StringTerms.Bucket> buckets=stringTerms.getBuckets();
        buckets.forEach(bucket -> {
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());
            InternalAvg internalAvg=(InternalAvg)bucket.getAggregations().asMap().get("avgPrice");
            System.out.println(internalAvg.getValue());
        });
    }
}
