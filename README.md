# XiotTask
A simple app that uses MQTT protocol in sending and recieving messages

for testing this app i used MQTTBox app you can download it from this link: http://workswithweb.com/html/mqttbox/downloads.html
I used as a host this server: broker.hivemq.com and the port: 1883 and the client id: testclient, moreover you can use any host you want.
the first screen in this app it will ask you to make the connection after you add the host,client id and the port it will save it
for the future uses.
the seond screen has two button "Hello" and "Bye". first, Hello button it will sends a message "HELLO" with a default topic "greetings" and a default QOS = 1
second, Bye button will do the same as the Hello button with  a different topic which is "going" and the same QOS value.
in this screen you will find a menu with 4 button which are:
1) New Connection: it will logout from the current session and ask you to make a  new connection
2) Subscribe: it will take you to another screen allowing you to subscribe a certain topic
3)about: it shows you the current connection details
4)logout: it closes the current the session and goes back to the main screen.

Known Issues:
in Subscribe activity there is a problem in getting the message Payload.
