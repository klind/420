package com.mmj.data.web.webservice;

import com.mmj.data.ejb.session.EmployeeSB;
import org.junit.Assert;

import javax.inject.Inject;

/**
 *
 */
//@RunWith(Arquillian.class)
public class RefundGuestPassAndVacationUpgradeTest extends BaseTest {

    @Inject
    private EmployeeSB employeeSB;

    //@Test
    public void testRefundUnusedGuestPasses() {
        try {
            Integer remainingGuestPasses = employeeSB.getRemainingGuestPasses("012346");
            Assert.assertEquals(19, remainingGuestPasses.intValue());

            //employeeSB.refundUnusedGuestPasses(DateTimeUtil.getLocalDateNowGMT().minusDays(1), DateTimeUtil.getLocalDateNowGMT().minusDays(1), result);

            Integer remainingGuestPassesAfterUpdate = employeeSB.getRemainingGuestPasses("012346");
            Assert.assertEquals(20, remainingGuestPassesAfterUpdate.intValue());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    //@Test
    public void testRefundUnusedVacationUpgrades() {
        try {
            Integer remainingVacationUpgrades = employeeSB.getRemainingVacationUpgrades("012346");
            Assert.assertEquals(0, remainingVacationUpgrades.intValue());

            //employeeSB.refundUnusedVacationUpgrades(DateTimeUtil.getLocalDateNowGMT().minusDays(1), DateTimeUtil.getLocalDateNowGMT().minusDays(1), result);

            Integer remainingVacationUpgradesAfterUpdate = employeeSB.getRemainingVacationUpgrades("012346");
            Assert.assertEquals(1, remainingVacationUpgradesAfterUpdate.intValue());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}