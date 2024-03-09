package com.book.iterator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class EsSqlQuery implements EsSqlQueryInterface<EsQueryIterator>{
    private String query;
    private Long fetchSize;
    private String cursor;

    public EsSqlQuery(String cursor) {
        this.cursor = cursor;
    }

    public EsSqlQuery(String query, Long fetchSize) {
        this.query = query;
        this.fetchSize = fetchSize;
    }
    public EsQueryIterator iterator(){
        return new EsQueryIterator(this.query, this.fetchSize);
    }
}
