package com.project.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QKiosk is a Querydsl query type for Kiosk
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKiosk extends EntityPathBase<Kiosk> {

    private static final long serialVersionUID = 2107006630L;

    public static final QKiosk kiosk = new QKiosk("kiosk");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> storeId = createNumber("storeId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QKiosk(String variable) {
        super(Kiosk.class, forVariable(variable));
    }

    public QKiosk(Path<? extends Kiosk> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKiosk(PathMetadata metadata) {
        super(Kiosk.class, metadata);
    }

}

