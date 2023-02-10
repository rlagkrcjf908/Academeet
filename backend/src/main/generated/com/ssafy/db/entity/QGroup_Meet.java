package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroup_Meet is a Querydsl query type for Group_Meet
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGroup_Meet extends EntityPathBase<Group_Meet> {

    private static final long serialVersionUID = -71879831L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroup_Meet group_Meet = new QGroup_Meet("group_Meet");

    public final QGroup group_id;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QMeet meet_id;

    public QGroup_Meet(String variable) {
        this(Group_Meet.class, forVariable(variable), INITS);
    }

    public QGroup_Meet(Path<? extends Group_Meet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroup_Meet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroup_Meet(PathMetadata metadata, PathInits inits) {
        this(Group_Meet.class, metadata, inits);
    }

    public QGroup_Meet(Class<? extends Group_Meet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group_id = inits.isInitialized("group_id") ? new QGroup(forProperty("group_id"), inits.get("group_id")) : null;
        this.meet_id = inits.isInitialized("meet_id") ? new QMeet(forProperty("meet_id"), inits.get("meet_id")) : null;
    }

}

