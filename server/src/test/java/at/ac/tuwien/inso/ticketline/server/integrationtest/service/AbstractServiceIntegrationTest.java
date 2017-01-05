package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring/test-context.xml", "classpath:spring/test-spring-security.xml"})
@ContextConfiguration(locations = "classpath:spring/test-context.xml")
@Transactional
public abstract class AbstractServiceIntegrationTest {

}
