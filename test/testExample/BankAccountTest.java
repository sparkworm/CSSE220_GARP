package testExample;

import org.junit.Before;
import org.junit.Test;

import javadocExample.BankAccount;

import static org.junit.Assert.*;

/**
 * JUnit-test example for CSSE students.
 *
 * <p>Shows the Arrange-Act-Assert pattern without any advanced
 * JUnit features so that new testers can focus on the basics.</p>
 */
public class BankAccountTest {

    private static final double TOLERANCE = 0.00001;   // tolerance for double comparisons

    private BankAccount emptyAccount;   // default constructor
    private BankAccount savings;        // two-argument constructor

    /* ------------------------------------------------------------------
     *  Shared test fixture
     * ------------------------------------------------------------------ */
    @Before
    public void setUp() {
        emptyAccount = new BankAccount();                 // name = "unknown user", balance = 0
        savings      = new BankAccount("savings", 100.0); // name = "savings",      balance = 100
    }

    /* ------------------------------------------------------------------
     *  Constructors
     * ------------------------------------------------------------------ */

    @Test
    public void testDefaultConstructor_setsNameAndBalanceToDefaults() {
        // Arrange - already done in setUp()

        // Act
        String actualName   = emptyAccount.getName();
        double actualBalance = emptyAccount.getBalance();

        // Assert
        assertEquals("unknown user", actualName);
        assertEquals(0.0, actualBalance, TOLERANCE);
    }

    @Test
    public void testTwoArgConstructor_storesGivenNameAndBalance() {
        assertEquals("savings", savings.getName());
        assertEquals(100.0, savings.getBalance(), TOLERANCE);
    }

    /* ------------------------------------------------------------------
     *  deposit
     * ------------------------------------------------------------------ */

    @Test
    public void testDeposit_addsMoneyToBalance() {
        // Arrange
        double amount = 50.0;

        // Act
        savings.deposit(amount);

        // Assert
        assertEquals(150.0, savings.getBalance(), TOLERANCE);
    }

    /* ------------------------------------------------------------------
     *  setName / getName
     * ------------------------------------------------------------------ */

    @Test
    public void testSetName_changesTheAccountName() {
        // Act
        emptyAccount.setName("college");

        // Assert
        assertEquals("college", emptyAccount.getName());
    }
}
