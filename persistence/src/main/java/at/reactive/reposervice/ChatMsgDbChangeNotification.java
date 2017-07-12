package at.reactive.reposervice;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeRegistration;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

@RequiredArgsConstructor
@Log4j2
public class ChatMsgDbChangeNotification {
    private final OracleConnection oracleConnection;

    public Observable<DatabaseChangeEvent> startListeningForNotifications() {
        return Observable.create(this::startOracleRegistration)
                .doOnError(err -> log.error("Error while registering db notification!", err))
                .doFinally(oracleConnection::close);
    }

    @SneakyThrows
    private void startOracleRegistration(ObservableEmitter<DatabaseChangeEvent> emitter) {
        this.unregisterAllNotifications(oracleConnection);
        // first step: create a registration on the server:
        Properties prop = new Properties();
        prop.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS, "true");

        //Set the DCN_QUERY_CHANGE_NOTIFICATION option for query registration with finer granularity.
        prop.setProperty(OracleConnection.DCN_QUERY_CHANGE_NOTIFICATION, "true");
        //prop.setProperty(OracleConnection.NTF_LOCAL_HOST, "192.168.223.100");

        DatabaseChangeRegistration dcr = oracleConnection.registerDatabaseChangeNotification(prop);
        dcr.addListener(emitter::onNext);

        // second step: add objects in the registration:
        Statement stmt = oracleConnection.createStatement();
        // associate the statement with the registration:
        ((OracleStatement) stmt).setDatabaseChangeRegistration(dcr);
        ResultSet rs = stmt.executeQuery("SELECT ID_CHATMSG FROM CHATMSG");
        while (rs.next()) {
        }
        String[] tableNames = dcr.getTables();
        for (String tableName : tableNames) {
            System.out.println(tableName + " is part of the registration.");
        }
        printRegs(oracleConnection);

        rs.close();
        stmt.close();
    }


    @SneakyThrows
    private void unregisterAllNotifications(OracleConnection conn) {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT regid,callback FROM USER_CHANGE_NOTIFICATION_REGS");
        while (rs.next()) {
            long regid = rs.getLong(1);
            String callback = rs.getString(2);
            conn.unregisterDatabaseChangeNotification(regid, callback);
        }
        rs.close();
        stmt.close();
    }

    @SneakyThrows
    private void printRegs(OracleConnection conn) {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT regid,callback FROM USER_CHANGE_NOTIFICATION_REGS");
        while (rs.next()) {
            long regid = rs.getLong(1);
            String callback = rs.getString(2);
            System.out.printf("Registrations: " + regid + " callback " + callback);
        }
    }
}
