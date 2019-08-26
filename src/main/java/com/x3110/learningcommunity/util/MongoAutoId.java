package com.x3110.learningcommunity.util;

import com.x3110.learningcommunity.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
public class MongoAutoId {

        @Autowired
        MongoTemplate mongo;

        public Long getNextSequence(String collectionName) {
            Post postId = mongo.findAndModify(
                    query(where("_id").is(collectionName)),
                    new Update().inc("postId", 1),
                    options().upsert(true).returnNew(true), Post.class);

            return postId.getPostId();

    }
}
