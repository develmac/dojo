databaseChangeLog {
    changeSet(id: 'cl-init-1', author: 'mac (generated)') {
        createTable(tableName: 'CHATMSG') {
            column(name: 'ID', type: 'VARCHAR2(36)') {
                constraints(nullable: false)
            }
            column(name: 'NAME', type: 'VARCHAR2(4000)') {
                constraints(nullable: false)
            }
            column(name: 'DESCRIPTION', type: 'VARCHAR2(4000)')
            column(name: 'FIP_DATE', type: 'DATE')
        }
    }

    changeSet(id: 'cl-init-2', author: 'mac (generated)') {
        addPrimaryKey(columnNames: 'ID', constraintName: 'CHATMSG_ID_PK', tableName: 'CHATMSG')
    }

    changeSet(id: 'cl-init-3', author: 'mac (generated)') {
        createIndex(indexName: 'CHATMSG_NAME_INDEX', tableName: 'CHATMSG') {
            column(name: 'NAME')
        }
    }

}
