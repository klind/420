package com.mmj.data.web.webservice;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.strategy.RejectDependenciesStrategy;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

import static com.mmj.data.core.constant.Constants.HEADER_USERNAME;
import static com.mmj.data.core.constant.Constants.HEADER_USER_ID;
import static org.mockito.Mockito.mock;

/**
 *
 */
public abstract class BaseTest {

    /**
     * Gets the deployment war.
     *
     * @return The war of the deployment.
     */
    public static WebArchive getDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                // Do not use g4tc-ejb to avoid the persistence.xml, we want to use test-persistence.xml
                .importRuntimeAndTestDependencies().resolve().using(new RejectDependenciesStrategy("com.allegiant.g4tc:g4tc-ejb"))
                .as(File.class);

        WebArchive war = ShrinkWrap.create(WebArchive.class, "g4tcRestWebservice.war")
                .addAsLibraries(files)
                .addPackages(true, "com.allegiant.g4tc.web")
                .addPackages(true, /*Filters.exclude(CacheHandlerScheduleSB.class), */"com.allegiant.g4tc.ejb") /* Load the classes from the ejb module instead of through dependency to avoid getting the g4tc-ejb/src/java/resources/META-INF/persistence.xml, we want the one from the test package */
                //.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml") /* This can be overridden to use beans.xml that specifies @Alternative class. See OrderTest for reference */
                .addAsResource("import.sql") /* g4tc-ejb/src/test/resources/META-INF/import.sql ( Through dependency to the ejb test-jar ) */
                .addAsResource("ehcache.xml")
                .addAsWebInfResource("DefaultTest/beans.xml")
                .addAsWebInfResource("META-INF/test-persistence.xml", "classes/META-INF/persistence.xml");

        System.out.println(war.toString(true));
        return war;
    }

    public static HttpServletRequest getHttpServletRequest(String userId) {
        HttpServletRequest mockRequest1 = mock(HttpServletRequest.class);
        Mockito.when(mockRequest1.getHeader(HEADER_USER_ID)).thenReturn(userId);
        Mockito.when(mockRequest1.getHeader(HEADER_USERNAME)).thenReturn(userId);
        return mockRequest1;
    }
}
