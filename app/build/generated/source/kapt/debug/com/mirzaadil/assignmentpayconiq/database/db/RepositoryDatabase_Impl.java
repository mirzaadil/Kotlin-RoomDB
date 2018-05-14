package com.mirzaadil.assignmentpayconiq.database.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.util.HashMap;
import java.util.HashSet;

public class RepositoryDatabase_Impl extends RepositoryDatabase {
  private volatile RepositoriesDao _repositoriesDao;

  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(6) {
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `repository` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `desceiption` TEXT, `postId` TEXT, `image_url` TEXT, `image` BLOB)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"28376135c898d1237125bf96a2b654b0\")");
      }

      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `repository`");
      }

      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsRepository = new HashMap<String, TableInfo.Column>(6);
        _columnsRepository.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsRepository.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsRepository.put("desceiption", new TableInfo.Column("desceiption", "TEXT", false, 0));
        _columnsRepository.put("postId", new TableInfo.Column("postId", "TEXT", false, 0));
        _columnsRepository.put("image_url", new TableInfo.Column("image_url", "TEXT", false, 0));
        _columnsRepository.put("image", new TableInfo.Column("image", "BLOB", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRepository = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRepository = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRepository = new TableInfo("repository", _columnsRepository, _foreignKeysRepository, _indicesRepository);
        final TableInfo _existingRepository = TableInfo.read(_db, "repository");
        if (! _infoRepository.equals(_existingRepository)) {
          throw new IllegalStateException("Migration didn't properly handle repository(com.mirzaadil.assignmentpayconiq.database.entity.Model.RepositoryModel).\n"
                  + " Expected:\n" + _infoRepository + "\n"
                  + " Found:\n" + _existingRepository);
        }
      }
    }, "28376135c898d1237125bf96a2b654b0");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "repository");
  }

  @Override
  public RepositoriesDao getRepsositoriesDao() {
    if (_repositoriesDao != null) {
      return _repositoriesDao;
    } else {
      synchronized(this) {
        if(_repositoriesDao == null) {
          _repositoriesDao = new RepositoriesDao_Impl(this);
        }
        return _repositoriesDao;
      }
    }
  }
}
