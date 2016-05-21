# lmxnd
This repository hosts the Lab Monitor XBee Network Daemon

# Design Specification
This program takes the form of a server in two directions.
On the one hand, the program communicates with the XBees. On the other hand the
program publishes the current state of the system on an HTTP server.

# XBee Side Server Specification
lmxnd will listen for packets from remote XBees via a local Xbee connected to a
serial (USB) port. The XBees will send packets with a code for the quantity
being reported followed by the value of that quantity. Upon receipt of of this
information lmxnd will reply with an ACK packet.

# Internet Side Server Specification
lmxnd will listen with an http server on some port. Upon receiving a GET
request, lmxnd will respond with a description of the XBees on the XBee network.

This description will consist of a list of XBees along with a time stamp.
For each XBee, there will be a list of key value pairs. The
key will represent the name of the reported quantity, and the value will
represent the quantity itself and a timestamp.
There shall only be one value for a given key
with new values replacing the old.
