## Comparison of microservice communication frameworks
Chat App is a sample application used in comparing performance of commonly used microservice communication frameworks

### Communication frameworks compared
* gRPC
* RSocket
* Apache Dubbo
* RESTful webservices

### Requirements
* Java 11

### Downloads
Follow this link to download [ChatApp](https://github.com/thirulabs/chat-app/archive/refs/tags/1.0.0.zip).

### Steps to run the application
Open the command prompt and go the project root folder

#### Chat server
To start the chat server, run 
```console
gradlew :chat-server-app:bootRun
```
#### Chat client
To start a specific chat client (eg. rsocket), run
```console
gradlew :chat-client-app:bootRun --args='--client.type=rsocket'
```
**Note:** The above command starts chat-client and runs performance tests and logs results to console 

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
Please see [comparison of microservice communication frameworks](https://medium.com/todo) for more details.

