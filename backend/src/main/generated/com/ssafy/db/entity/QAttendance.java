package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttendance is a Querydsl query type for Attendance
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAttendance extends EntityPathBase<Attendance> {

    private static final long serialVersionUID = 544183339L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttendance attendance1 = new QAttendance("attendance1");

    public final NumberPath<Double> attendance = createNumber("attendance", Double.class);

    public final DatePath<java.util.Date> date = createDate("date", java.util.Date.class);

    public final QGroup group_id;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QMeet meeting_id;

    public final QUser user_id;

    public QAttendance(String variable) {
        this(Attendance.class, forVariable(variable), INITS);
    }

    public QAttendance(Path<? extends Attendance> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttendance(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttendance(PathMetadata metadata, PathInits inits) {
        this(Attendance.class, metadata, inits);
    }

    public QAttendance(Class<? extends Attendance> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group_id = inits.isInitialized("group_id") ? new QGroup(forProperty("group_id"), inits.get("group_id")) : null;
        this.meeting_id = inits.isInitialized("meeting_id") ? new QMeet(forProperty("meeting_id"), inits.get("meeting_id")) : null;
        this.user_id = inits.isInitialized("user_id") ? new QUser(forProperty("user_id")) : null;
    }

}

