TITLE: Solution for Homework # 5
COURSE: CSCIE-55, "Java for Distributed Computing", Fall 2013
AUTHOR: Oscar Fabra, oscarfabra@gmail.com
DATE: December 05, 2013

ASSUMPTIONS:

 * Server and Client connection to the RMI Registry where coded using the
   alternate method exhibited in the lectures; the Server itself creates and 
   runs the RMI registry especifying a port (1099) and then bounds the 
   ATMFactory to the created registry.
 * Security manager policy files are assigned under Server and Client's main
   methods respectively, so there's no need to specify them on execution time.
 * Additional assumptions made during the programming assignment were Javadoc-
   documented in their respective Java source files.

HOW TO EXECUTE MAIN PROCESSES:

Be sure to execute the Server and then the Client on a different command
prompt.
# java cscie55.hw5.Server
# java cscie55.hw5.Client
