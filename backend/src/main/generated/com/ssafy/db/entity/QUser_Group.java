package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser_Group is a Querydsl query type for User_Group
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser_Group extends EntityPathBase<User_Group> {

    private static final long serialVersionUID = 540918317L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser_Group user_Group = new QUser_Group("user_Group");

    public final QGroup groupid;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QUser userid;

    public QUser_Group(String variable) {
        this(User_Group.class, forVariable(variable), INITS);
    }

    public QUser_Group(Path<? extends User_Group> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser_Group(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser_Group(PathMetadata metadata, PathInits inits) {
        this(User_Group.class, metadata, inits);
    }

    public QUser_Group(Class<? extends User_Group> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.groupid = inits.isInitialized("groupid") ? new QGroup(forProperty("groupid"), inits.get("groupid")) : null;
        this.userid = inits.isInitialized("userid") ? new QUser(forProperty("userid")) : null;
    }

}

