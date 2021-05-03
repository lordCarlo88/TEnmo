package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.BalanceData;
import com.techelevator.tenmo.model.TransferData;
import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import com.techelevator.tenmo.dao.AccountDAO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcAccountDAOTest {

    private JdbcTemplate jdbcTemplate;

    private static SingleConnectionDataSource dataSource;
    private AccountDAO dao;
    private UserDAO userDAO;

    @BeforeClass
    public static void setupDataSource(){
        dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        dataSource.setAutoCommit(false);
    }

    @AfterClass
    public static void closeDataSource(){
        dataSource.destroy();
    }

    @Before
    public void setupTest(){
//        String sqlInsertCountry = "INSERT INTO users (user_id, username, password_hash) VALUES (1999, 'dan', 'password')";
//        userDAO = new JdbcUserDAO(dataSource);

//        userDAO.create("dan","password");

        dao = new JdbcAccountDAO(dataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        userDAO = new JdbcUserDAO(jdbcTemplate);


//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        jdbcTemplate.update(sqlInsertCountry, TEST_COUNTRY);
//        dao = new JdbcAccountDAO(dataSource);
    }

    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();

    }

    @Test
    public void check_balance_of_new_user_test(){
        userDAO.create("dan","password");

        int userId = userDAO.findIdByUsername("dan");

        BalanceData testBalanceData = dao.getBalanceGivenAnId(userId);

        Assert.assertEquals(new BigDecimal("1000.00"), testBalanceData.getBalance());
    }

    @Test
    public void get_all_users_test(){
        List<Account> original = dao.getUsers();
        int x = original.size();

        userDAO.create("dan","password");

        List<Account> modified = dao.getUsers();

        int y =modified.size();

        Assert.assertEquals(x+1,y);

    }

    @Test
    public void get_all_transfers_test(){
        userDAO.create("dan","password");
        int userId = userDAO.findIdByUsername("dan");


        userDAO.create("john","password");
        int userId2 = userDAO.findIdByUsername("john");


        dao.transferFunds(userId,userId2,new BigDecimal("123.45"));
        dao.transferFunds(userId2,userId,new BigDecimal(".45"));

        BalanceData testBalanceData = dao.getBalanceGivenAnId(userId);
        List<TransferData> original = dao.getTransfers(userId);

        int expected = 2;
        int actual = original.size();

        Assert.assertEquals(expected,actual);
        BigDecimal actualBalance = testBalanceData.getBalance() ;
        Assert.assertEquals(new BigDecimal("877.00"),actualBalance);
    }


}