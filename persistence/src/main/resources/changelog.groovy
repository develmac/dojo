databaseChangeLog {
    changeSet(id: 'cl-init-1', author: 'mac (generated)') {
        createTable(tableName: 'ARTIST') {
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
        addPrimaryKey(columnNames: 'ID', constraintName: 'ARTIST_ID_PK', tableName: 'ARTIST')
    }

    changeSet(id: 'cl-init-3', author: 'mac (generated)') {
        createIndex(indexName: 'ARTIST_NAME_INDEX', tableName: 'ARTIST') {
            column(name: 'NAME')
        }
    }

}
