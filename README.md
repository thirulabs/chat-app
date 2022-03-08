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
Use the following commands to run server or client from the project root folder 
#### Chat server
```console
user@host$ gradlew :chat-server-app:bootRun
```
#### Chat client
```console
user@host$ gradlew :chat-client-app:bootRun -Pargs=--client.type=rsocket,--message.encoding=protobuf
```

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

