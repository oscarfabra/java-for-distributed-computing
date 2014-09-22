TITLE: Solution for Homework # 6
COURSE: CSCIE-55, "Java for Distributed Computing", Fall 2013
AUTHOR: Oscar Fabra, oscarfabra@gmail.com
DATE: December 14, 2013

ASSUMPTIONS:

 * Homework was developed following step by step the instructions given on the
   course's website.
 * Server facilitates one client connection at a time, even though each client
   request is attended on a different thread.
 * Application is customized to attend only one client (as it is required for
   this assignment), although it could be extended to attend multiple
   client connections.

HOW TO EXECUTE MAIN PROCESSES:

Be sure to execute the Server and then the Client on a different command
prompt.
# java -classpath hw6.jar cscie55.hw6.Server 7777
# java -classpath hw6.jar cscie55.hw6.Client localhost 7777
