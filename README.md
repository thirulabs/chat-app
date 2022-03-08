## Chat App
Sample chat application used in evaluating performance of commonly used microservice communication frameworks

### Communication frameworks used
* gRPC
* RSocket
* Apache Dubbo
* RESTful webservices

### Requirements
* Java 11
* Gradle 7

### Download
Follow this link to download [ChatApp](https://github.com/thirulabs/chat-app/archive/refs/tags/1.0.0.zip).


### Steps to run the application
Open the command prompt and go the project root folder

#### Chat server
Use the following command to start the chat server 
```console
gradlew :chat-server-app:bootRun
```
#### Chat client
Use the following command to start a specific chat client (eg. rsocket)
```console
gradlew :chat-client-app:bootRun --args='--client.type=rsocket'
```
*Note:* The above command starts client and runs the performance tests and logs the output to console 

#### Supported client types
- grpc
- rsocket
- dubbo
- rest

#### Supported message encodings
- protobuf
- json

*Note:* gRPC & Dubbo clients use default message encodings 

### Results
Please see [a quick comparison of commonly used microservice communication frameworks](https://medium.com/todo) for details of results.

