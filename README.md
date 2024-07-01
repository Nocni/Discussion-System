# Discussion System

This project is a distributed system that uses multiple server replicas to ensure fault tolerance. It is built using the SofaJRaft library and gRPC.

## Overview

The Discussion System is a robust and resilient system designed to handle failures and continue operating even when some parts of the system are not functioning. It achieves this through the use of multiple server replicas, which can take over if one server fails.

The system is built using the SofaJRaft library, a production-grade Raft consensus library that is used to manage the replicas and handle the consensus protocol. This ensures that all replicas have the same state and can take over seamlessly if a server fails.

Communication between the servers and the clients is handled using gRPC, a high-performance, open-source universal RPC framework. gRPC allows for efficient communication between the servers and clients and supports a wide range of languages.

## Features

- **Fault Tolerance**: The system can handle failures and continue operating, thanks to the use of multiple server replicas.
- **Consistency**: The SofaJRaft library ensures that all replicas have the same state, providing strong consistency guarantees.
- **Efficient Communication**: gRPC is used for communication between the servers and clients, providing high performance and support for a wide range of languages.

## Getting Started

To get started with the Discussion System, you need to have Java and Maven installed. You can then clone the repository and build the project using Maven.

```bash
git clone https://github.com/Nocni/DiscussionSystem.git
cd DiscussionSystem
mvn clean install

## After building the project, you can start the servers using the provided deployment.yaml file.
kubectl apply -f deployment.yaml
