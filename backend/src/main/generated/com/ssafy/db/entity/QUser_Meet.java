package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser_Meet is a Querydsl query type for User_Meet
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser_Meet extends EntityPathBase<User_Meet> {

    private static final long serialVersionUID = 710351577L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser_Meet user_Meet = new QUser_Meet("user_Meet");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QMeet meet_id;

    public final QUser user_id;

    public QUser_Meet(String variable) {
        this(User_Meet.class, forVariable(variable), INITS);
    }

    public QUser_Meet(Path<? extends User_Meet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser_Meet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser_Meet(PathMetadata metadata, PathInits inits) {
        this(User_Meet.class, metadata, inits);
    }

    public QUser_Meet(Class<? extends User_Meet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.meet_id = inits.isInitialized("meet_id") ? new QMeet(forProperty("meet_id"), inits.get("meet_id")) : null;
        this.user_id = inits.isInitialized("user_id") ? new QUser(forProperty("user_id")) : null;
    }

}

