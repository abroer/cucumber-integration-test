# Cucumber integration test
This sample project shows how you can automate an integration
test using cucumber and docker instances. The setup is simple, the application
is compiled and packaged to a war. A docker container with tomcat is build and de war file
is copied to the container. At startup the tomcat instance deploys and runs the application.
The cucumber test validates the presents of the header by searching for its id. When it finds
the id, the test is successful.

The complete cycle runs on using on the verify phase. Of course you need to have java and
docker installed.

    mvn clean verify

## Application
The application is nothing special. it has a `index.jsp` containing only static content.
It has, next to the standard html content, a `h1` tag with id `textHeader`. It is only there
to be found in the cucumber test. The web application also has a `web.xml`, though it does
contain more then a `display-name` tag.

## Cucumber test
The cucumber test wil search for the `h1` tag with id `textHeader`. It does so by loading
the web application using the headless phantomJs browser. You may want to change the
browserdriver in `nl.arjan.sandbox.BrowserDriver` to firefox just to see it running.
Though when you want to run this on a buildserver then you should stick to the headless
phantomJS browser.

As the Cucumber test needs to run at the integration-test phase the testclass is called
`RunCukesTestIT` to match the pattern required by the maven-failsafe-plugin that searches
classes named `**/*IT.java`.

The Cucumber test uses the `BrowserDriver` to gain access to the browser. The `Navigation`
class is responsible to load pages and the `HomeView` class is an interface on all the
possible operations on the page.

And then there is the `smoketest.feature` and `SmokeTest` to automate the gherkin test
scenario.

## Docker container
There are several ways to automate the build and run process for docker in Maven. I have
chosen the jolokia maven-docker-plugin because it is well documented and provides support
for both the build and run phases. The provided examples are also excellent. The jolokia
plugin makes use of the assembly plugin to add artifacts to the docker container when it
is build. That way the artifacts from your package phase can be deployed to the container.

The docker container is a simple tomcat 7 container. A jetty container might have been more
efficient, though for my purpose the tomcat container corresponded more with the project i
want to apply this sample to.

The container is build and run during the `pre-integration-test` phase and stopped during
the `post-integration-test` phase.

## Environment setup
To run this exaple you need to have the following installed on your system.
- maven 3.1+
- jdk 1.8
- docker

The example should run on windows, linux and OSx, though i have only used windows while
creating this example. Please mind that on a windows system you need to have your
docker-machine runnning and also set the environment settings.

    docker-machine start default
    eval $(docker-machine env default)

