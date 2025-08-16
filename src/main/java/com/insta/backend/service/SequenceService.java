package com.insta.backend.service;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class SequenceService {
    private final MongoOperations mongo;

    public SequenceService(MongoOperations mongo) {
        this.mongo = mongo;
    }

    /**
     * counters 컬렉션에서 name별로 증가. 최초 없으면 0에서 시작 → 1 반환.
     */
    public long getNextSequence(String name) {
        var query = new Query(where("_id").is(name));
        var update = new Update().inc("seq", 1);
        var opts = FindAndModifyOptions.options().upsert(true).returnNew(true);

        var counter = mongo.findAndModify(query, update, opts, Counter.class, "counters");
        return counter.getSeq();
    }

    public static class Counter {
        private String id; // _id
        private long seq;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getSeq() {
            return seq;
        }

        public void setSeq(long seq) {
            this.seq = seq;
        }
    }
}
