ChatApp is a sample application used to compare performance of commonly used microservice communication frameworks

Following frameworks/protocols are compared using Java their implementation 
* gRPC
* RSocket
* Apache Dubbo
* RESTful webservices

### Prerequisites
* Java 11

### To get the sample code
Download the [ChatApp](https://github.com/thirulabs/chat-app/archive/refs/tags/1.0.0.zip) repo as a zip file

### Steps to run the application
From the chat-app root directory

#### Start chat server
```console
gradlew :chat-server-app:bootRun
```
Starts chat server
#### Start chat client
```console
gradlew :chat-client-app:bootRun --args='--client.type=rsocket'
```
Starts a specific chat client, that uses rsocket for communication  
**Note:** The above command starts chat-client, runs performance tests and logs results to console 

#### Supported client types
- grpc
- rsocket
- dubbo
- rest

#### Supported message encodings
- protobuf
- json

**Note:** gRPC & Dubbo clients use default message encodings 

### Results
Please see [comparison of microservice communication frameworks](https://medium.com/todo) for more details

