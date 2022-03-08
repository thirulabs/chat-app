## Chat App
Sample chat application used in evaluating performance of commonly used microservice communication frameworks

### Performance of following communication frameworks are compared
* gRPC
* RSocket
* Apache Dubbo
* RESTful webservices

### Requirements
* Java 11
* Gradle 7

### Download
[ChatApp](https://github.com/thirulabs/chat-app/archive/refs/tags/1.0.0.zip).


### Steps to run the application
Open the command prompt and go the project root folder
#### Chat server
Use the following command to start the chat server 
```console
gradlew :chat-server-app:bootRun
```
#### Chat client
Use the following command to start the chat client of a specific type (eg. rsocket)
```console
gradlew :chat-client-app:bootRun --args='--client.type=rsocket'
```
Note: The above command starts client and runs the performance tests and logs the output to console 

#### Client types supported are
- grpc
- rsocket
- dubbo
- rest

#### Message encodings supported are
- protobuf
- json

*Note:* gRPC & Dubbo clients use default message encodings 

### Results
For the performance results see [a quick comparision of commonly used microservice communication frameworks](https://medium.com/todo).

