package com.project.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKiosk is a Querydsl query type for Kiosk
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKiosk extends EntityPathBase<Kiosk> {

    private static final long serialVersionUID = 2107006630L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKiosk kiosk = new QKiosk("kiosk");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QStore store;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QKiosk(String variable) {
        this(Kiosk.class, forVariable(variable), INITS);
    }

    public QKiosk(Path<? extends Kiosk> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKiosk(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKiosk(PathMetadata metadata, PathInits inits) {
        this(Kiosk.class, metadata, inits);
    }

    public QKiosk(Class<? extends Kiosk> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new QStore(forProperty("store"), inits.get("store")) : null;
    }

}

