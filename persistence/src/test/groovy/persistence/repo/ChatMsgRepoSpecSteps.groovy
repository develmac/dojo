package persistence.repo

import liquibase.Liquibase
import liquibase.database.jvm.JdbcConnection
import liquibase.exception.LiquibaseException
import liquibase.resource.ClassLoaderResourceAccessor
import org.junit.After
import org.junit.Before
import org.junit.rules.TestName
import org.springframework.beans.factory.annotation.Autowired

import javax.sql.DataSource
import java.sql.SQLException

trait ChatMsgRepoSpecSteps {

    @Autowired
    public ChatMsgRepo chatMsgRepo

    private Liquibase liquibase

    @Autowired
    private DataSource dataSource

    private TestName testName = new TestName()
    private final String XML_EXTENSION = "xml"
    private final String TESTDATA_DIR = "testdata"


    @Before
    public void prepareDb() {
        final String fileNameForTest = getFileNameForTest(testName.getMethodName());
        final URL url = this.getClass().getResource("/" + fileNameForTest);


        if (url == null) {
            printf "No testdata found for " + fileNameForTest
            return
        }

        try {
            liquibase = new Liquibase(
                    fileNameForTest,
                    new ClassLoaderResourceAccessor(),
                    new JdbcConnection(dataSource.getConnection()));
            liquibase.update((String) null);
        } catch (LiquibaseException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void rollback() {
        if (liquibase != null) {
            try {
                liquibase.rollback("emptydatabase", "autotest");
            } catch (LiquibaseException e) {
                throw new RuntimeException(e);
            } finally {
                liquibase = null;
            }
        }
    }

    private String getFileNameForTest(final String testMethod) {
        return String.format("%s/%s_%s.%s", TESTDATA_DIR, this.getClass().getSimpleName(),
                testMethod, XML_EXTENSION);
    }

    boolean "no chatMsgs exist"() {
        chatMsgRepo.deleteAll()
        assert chatMsgRepo.findAll().isEmpty()
    }
}