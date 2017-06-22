databaseChangeLog {
  changeSet(id: '1497995131661-1', author: 'mac (generated)') {
    createTable(tableName: 'CHATMSG') {
      column(name: 'ID_CHATMSG', type: 'VARCHAR2(36)') {
        constraints(nullable: false)
      }
      column(name: 'ORIGIN', type: 'VARCHAR2(200)') {
        constraints(nullable: false)
      }
      column(name: 'TEXT', type: 'VARCHAR2(4000)')
      column(name: 'FIP_DATE', type: 'date')
      column(name: 'FK_ID_ROOM', type: 'VARCHAR2(36)') {
        constraints(nullable: false)
      }
    }
  }

  changeSet(id: '1497995131661-2', author: 'mac (generated)') {
    createTable(tableName: 'ROOM') {
      column(name: 'ID_ROOM', type: 'VARCHAR2(36)') {
        constraints(nullable: false)
      }
      column(name: 'NAME', type: 'VARCHAR2(200)') {
        constraints(nullable: false)
      }
    }
  }

  changeSet(id: '1497995131661-3', author: 'mac (generated)') {
    createIndex(indexName: 'ID_CHATMSG_PK', tableName: 'CHATMSG', unique: true) {
      column(name: 'ID_CHATMSG')
    }
    addPrimaryKey(columnNames: 'ID_CHATMSG', constraintName: 'ID_CHATMSG_PK', forIndexName: 'ID_CHATMSG_PK', tableName: 'CHATMSG')
  }

  changeSet(id: '1497995131661-4', author: 'mac (generated)') {
    createIndex(indexName: 'ID_ROOM_PK', tableName: 'ROOM', unique: true) {
      column(name: 'ID_ROOM')
    }
    addPrimaryKey(columnNames: 'ID_ROOM', constraintName: 'ID_ROOM_PK', forIndexName: 'ID_ROOM_PK', tableName: 'ROOM')
  }

  changeSet(id: '1497995131661-5', author: 'mac (generated)') {
    createIndex(indexName: 'CHATMSG_ORIGIN_INDEX', tableName: 'CHATMSG') {
      column(name: 'ORIGIN')
    }
  }

  changeSet(id: '1497995131661-6', author: 'mac (generated)') {
    addForeignKeyConstraint(baseColumnNames: 'FK_ID_ROOM', baseTableName: 'CHATMSG', constraintName: 'ID_CHATMSG_ROOM_FK', deferrable: false, initiallyDeferred: false, onDelete: 'RESTRICT', onUpdate: 'RESTRICT', referencedColumnNames: 'ID_ROOM', referencedTableName: 'ROOM')
  }

}
