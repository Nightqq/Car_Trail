package com.zxdz.car.base.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zxdz.car.main.model.domain.CardInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CARD_INFO".
*/
public class CardInfoDao extends AbstractDao<CardInfo, Long> {

    public static final String TABLENAME = "CARD_INFO";

    /**
     * Properties of entity CardInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ZdjId = new Property(1, int.class, "zdjId", false, "ZDJ_ID");
        public final static Property AdminCardNumber = new Property(2, String.class, "adminCardNumber", false, "ADMIN_CARD_NUMBER");
        public final static Property AdminName = new Property(3, String.class, "adminName", false, "ADMIN_NAME");
        public final static Property Remark = new Property(4, String.class, "remark", false, "REMARK");
    }


    public CardInfoDao(DaoConfig config) {
        super(config);
    }
    
    public CardInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CARD_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"ZDJ_ID\" INTEGER NOT NULL ," + // 1: zdjId
                "\"ADMIN_CARD_NUMBER\" TEXT," + // 2: adminCardNumber
                "\"ADMIN_NAME\" TEXT," + // 3: adminName
                "\"REMARK\" TEXT);"); // 4: remark
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CARD_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CardInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getZdjId());
 
        String adminCardNumber = entity.getAdminCardNumber();
        if (adminCardNumber != null) {
            stmt.bindString(3, adminCardNumber);
        }
 
        String adminName = entity.getAdminName();
        if (adminName != null) {
            stmt.bindString(4, adminName);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(5, remark);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CardInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getZdjId());
 
        String adminCardNumber = entity.getAdminCardNumber();
        if (adminCardNumber != null) {
            stmt.bindString(3, adminCardNumber);
        }
 
        String adminName = entity.getAdminName();
        if (adminName != null) {
            stmt.bindString(4, adminName);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(5, remark);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CardInfo readEntity(Cursor cursor, int offset) {
        CardInfo entity = new CardInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // zdjId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // adminCardNumber
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // adminName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // remark
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CardInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setZdjId(cursor.getInt(offset + 1));
        entity.setAdminCardNumber(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAdminName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setRemark(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CardInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CardInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CardInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
