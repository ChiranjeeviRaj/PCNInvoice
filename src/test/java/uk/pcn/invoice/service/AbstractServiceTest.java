package uk.pcn.invoice.service;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test-applicationContext.xml" })
public class AbstractServiceTest {
	@Resource
	protected IRateCardService rateCardService;
}
