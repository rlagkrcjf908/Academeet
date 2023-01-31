package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMeet is a Querydsl query type for Meet
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMeet extends EntityPathBase<Meet> {

    private static final long serialVersionUID = 846290697L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMeet meet = new QMeet("meet");

    public final StringPath chat = createString("chat");

    public final DatePath<java.util.Date> date = createDate("date", java.util.Date.class);

    public final TimePath<java.util.Date> end_time = createTime("end_time", java.util.Date.class);

    public final QGroup group_id;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final TimePath<java.util.Date> start_time = createTime("start_time", java.util.Date.class);

    public final StringPath stt = createString("stt");

    public final StringPath title = createString("title");

    public final QUser user_id;

    public final StringPath video = createString("video");

    public QMeet(String variable) {
        this(Meet.class, forVariable(variable), INITS);
    }

    public QMeet(Path<? extends Meet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMeet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMeet(PathMetadata metadata, PathInits inits) {
        this(Meet.class, metadata, inits);
    }

    public QMeet(Class<? extends Meet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group_id = inits.isInitialized("group_id") ? new QGroup(forProperty("group_id"), inits.get("group_id")) : null;
        this.user_id = inits.isInitialized("user_id") ? new QUser(forProperty("user_id")) : null;
    }

}

