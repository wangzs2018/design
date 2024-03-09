package com.book.iterator;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EsQueryIterator implements Iterator<Map<String, Object>> {
    //记录当前cursor分页
    private String cursor;
    //记录查询的columns，因为只有第一次查询才会返回columns数据
    private List<String> columns;
    //将ES SQL Rest API的返回值封装到List<Map>中，以便处理返回值
    Iterator<Map<String, Object>> iterator;
    //此处我们从简而行，不再进行@Autowire注入，把更多的精力放到迭代器模式中
    RestTemplate restTemplate = new RestTemplate();

    //构造函数进行第一次查询，并且初始化我们后续需要使用的 columns 和 iterator 和 cursor
    public EsQueryIterator(String query, Long fetchSize) {
        EsResponseData esResponseData = restTemplate.postForObject("http://localhost:9200/_sql?format=json",
                new EsSqlQuery(query, fetchSize), EsResponseData.class);//第一次访问的结果出来了
        this.cursor = esResponseData.getCursor();
        this.columns = esResponseData.getColumns()
                .stream().map(x -> x.get("name"))
                .collect(Collectors.toList());
        this.iterator = convert(columns, esResponseData).iterator();
    }

    // hasNext 根据 是否 cursor 为null进行后续的 第二次，第三次，，，的访问，直到 cursor 为null
    @Override
    public boolean hasNext() {
        return iterator.hasNext() || scrollNext();
    }

    //获取第二次及以后的查询结果
    private boolean scrollNext() {
        if (iterator == null || this.cursor == null) {
            return false;
        }
        EsResponseData esResponseData = restTemplate.postForObject("http://localhost:9200/_sql?format=json",
                new EsSqlQuery(this.cursor), EsResponseData.class);
        this.cursor = esResponseData.getCursor();
        this.iterator = convert(columns, esResponseData).iterator();
        return iterator.hasNext();
    }

    @Override
    public Map<String, Object> next() {
        return iterator.next();
    }
    //将 ES SQL Rest API的返回值转化为 List<Map>
    private List<Map<String, Object>> convert(List<String> columns, EsResponseData esResponseData) {
        List<Map<String, Object>> results = new ArrayList<>();
        for (List<Object> row : esResponseData.getRows()) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < columns.size(); i++) {
                map.put(columns.get(i), row.get(i));
            }
            results.add(map);
        }
        return results;
    }
}