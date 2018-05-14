package com.mirzaadil.assignmentpayconiq.database.db;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import com.mirzaadil.assignmentpayconiq.database.entity.Model;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class RepositoriesDao_Impl implements RepositoriesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfRepositoryModel;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public RepositoriesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRepositoryModel = new EntityInsertionAdapter<Model.RepositoryModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `repository`(`id`,`name`,`desceiption`,`postId`,`image_url`,`image`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Model.RepositoryModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getDesceiption() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDesceiption());
        }
        if (value.getPostId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPostId());
        }
        if (value.getImage_url() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getImage_url());
        }
        if (value.getImage() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindBlob(6, value.getImage());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from repository";
        return _query;
      }
    };
  }

  @Override
  public void addRepositories(List<Model.RepositoryModel> repositories) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfRepositoryModel.insert(repositories);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<Model.RepositoryModel> getAllRepositories() {
    final String _sql = "select * from repository";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfDesceiption = _cursor.getColumnIndexOrThrow("desceiption");
      final int _cursorIndexOfPostId = _cursor.getColumnIndexOrThrow("postId");
      final int _cursorIndexOfImageUrl = _cursor.getColumnIndexOrThrow("image_url");
      final int _cursorIndexOfImage = _cursor.getColumnIndexOrThrow("image");
      final List<Model.RepositoryModel> _result = new ArrayList<Model.RepositoryModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Model.RepositoryModel _item;
        _item = new Model.RepositoryModel();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
        final String _tmpDesceiption;
        _tmpDesceiption = _cursor.getString(_cursorIndexOfDesceiption);
        _item.setDesceiption(_tmpDesceiption);
        final String _tmpPostId;
        _tmpPostId = _cursor.getString(_cursorIndexOfPostId);
        _item.setPostId(_tmpPostId);
        final String _tmpImage_url;
        _tmpImage_url = _cursor.getString(_cursorIndexOfImageUrl);
        _item.setImage_url(_tmpImage_url);
        final byte[] _tmpImage;
        _tmpImage = _cursor.getBlob(_cursorIndexOfImage);
        _item.setImage(_tmpImage);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
