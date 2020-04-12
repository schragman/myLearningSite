package util;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import java.io.File;

public class TestBaseClass {

  @Deployment()
  public static WebArchive createDeployment() {
    WebArchive deployment = ShrinkWrap
            .create(WebArchive.class, "junit-demo-test.war")
            .as(ZipImporter.class)
            .importFrom(new File("target/junit-demo.war"))
            .as(WebArchive.class)
            .addPackage("learnWEB.test");

    /*deployment.delete("META-INF/persistence.xml");
    deployment.addAsResource("test-persistence.xml", "META-INF/persistence.xml");*/

    return deployment;
  }

}
