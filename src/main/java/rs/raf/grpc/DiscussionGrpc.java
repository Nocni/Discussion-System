package rs.raf.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.45.1)",
    comments = "Source: discussion.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class DiscussionGrpc {

  private DiscussionGrpc() {}

  public static final String SERVICE_NAME = "Discussion";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<rs.raf.grpc.NewTopicRequest,
      rs.raf.grpc.Response> getSendNewTopicMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendNewTopic",
      requestType = rs.raf.grpc.NewTopicRequest.class,
      responseType = rs.raf.grpc.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.grpc.NewTopicRequest,
      rs.raf.grpc.Response> getSendNewTopicMethod() {
    io.grpc.MethodDescriptor<rs.raf.grpc.NewTopicRequest, rs.raf.grpc.Response> getSendNewTopicMethod;
    if ((getSendNewTopicMethod = DiscussionGrpc.getSendNewTopicMethod) == null) {
      synchronized (DiscussionGrpc.class) {
        if ((getSendNewTopicMethod = DiscussionGrpc.getSendNewTopicMethod) == null) {
          DiscussionGrpc.getSendNewTopicMethod = getSendNewTopicMethod =
              io.grpc.MethodDescriptor.<rs.raf.grpc.NewTopicRequest, rs.raf.grpc.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendNewTopic"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.grpc.NewTopicRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.grpc.Response.getDefaultInstance()))
              .setSchemaDescriptor(new DiscussionMethodDescriptorSupplier("SendNewTopic"))
              .build();
        }
      }
    }
    return getSendNewTopicMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.raf.grpc.NewCommentRequest,
      rs.raf.grpc.Response> getSendNewCommentToTopicMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendNewCommentToTopic",
      requestType = rs.raf.grpc.NewCommentRequest.class,
      responseType = rs.raf.grpc.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.grpc.NewCommentRequest,
      rs.raf.grpc.Response> getSendNewCommentToTopicMethod() {
    io.grpc.MethodDescriptor<rs.raf.grpc.NewCommentRequest, rs.raf.grpc.Response> getSendNewCommentToTopicMethod;
    if ((getSendNewCommentToTopicMethod = DiscussionGrpc.getSendNewCommentToTopicMethod) == null) {
      synchronized (DiscussionGrpc.class) {
        if ((getSendNewCommentToTopicMethod = DiscussionGrpc.getSendNewCommentToTopicMethod) == null) {
          DiscussionGrpc.getSendNewCommentToTopicMethod = getSendNewCommentToTopicMethod =
              io.grpc.MethodDescriptor.<rs.raf.grpc.NewCommentRequest, rs.raf.grpc.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendNewCommentToTopic"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.grpc.NewCommentRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.grpc.Response.getDefaultInstance()))
              .setSchemaDescriptor(new DiscussionMethodDescriptorSupplier("SendNewCommentToTopic"))
              .build();
        }
      }
    }
    return getSendNewCommentToTopicMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.raf.grpc.CommentReplyRequest,
      rs.raf.grpc.Response> getReplyToCommentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReplyToComment",
      requestType = rs.raf.grpc.CommentReplyRequest.class,
      responseType = rs.raf.grpc.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.grpc.CommentReplyRequest,
      rs.raf.grpc.Response> getReplyToCommentMethod() {
    io.grpc.MethodDescriptor<rs.raf.grpc.CommentReplyRequest, rs.raf.grpc.Response> getReplyToCommentMethod;
    if ((getReplyToCommentMethod = DiscussionGrpc.getReplyToCommentMethod) == null) {
      synchronized (DiscussionGrpc.class) {
        if ((getReplyToCommentMethod = DiscussionGrpc.getReplyToCommentMethod) == null) {
          DiscussionGrpc.getReplyToCommentMethod = getReplyToCommentMethod =
              io.grpc.MethodDescriptor.<rs.raf.grpc.CommentReplyRequest, rs.raf.grpc.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReplyToComment"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.grpc.CommentReplyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.grpc.Response.getDefaultInstance()))
              .setSchemaDescriptor(new DiscussionMethodDescriptorSupplier("ReplyToComment"))
              .build();
        }
      }
    }
    return getReplyToCommentMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.raf.grpc.CommentUpdateRequest,
      rs.raf.grpc.Response> getUpdateMyCommentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateMyComment",
      requestType = rs.raf.grpc.CommentUpdateRequest.class,
      responseType = rs.raf.grpc.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.grpc.CommentUpdateRequest,
      rs.raf.grpc.Response> getUpdateMyCommentMethod() {
    io.grpc.MethodDescriptor<rs.raf.grpc.CommentUpdateRequest, rs.raf.grpc.Response> getUpdateMyCommentMethod;
    if ((getUpdateMyCommentMethod = DiscussionGrpc.getUpdateMyCommentMethod) == null) {
      synchronized (DiscussionGrpc.class) {
        if ((getUpdateMyCommentMethod = DiscussionGrpc.getUpdateMyCommentMethod) == null) {
          DiscussionGrpc.getUpdateMyCommentMethod = getUpdateMyCommentMethod =
              io.grpc.MethodDescriptor.<rs.raf.grpc.CommentUpdateRequest, rs.raf.grpc.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateMyComment"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.grpc.CommentUpdateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.grpc.Response.getDefaultInstance()))
              .setSchemaDescriptor(new DiscussionMethodDescriptorSupplier("UpdateMyComment"))
              .build();
        }
      }
    }
    return getUpdateMyCommentMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.raf.grpc.CommentDeleteRequest,
      rs.raf.grpc.Response> getDeleteMyCommentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteMyComment",
      requestType = rs.raf.grpc.CommentDeleteRequest.class,
      responseType = rs.raf.grpc.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.grpc.CommentDeleteRequest,
      rs.raf.grpc.Response> getDeleteMyCommentMethod() {
    io.grpc.MethodDescriptor<rs.raf.grpc.CommentDeleteRequest, rs.raf.grpc.Response> getDeleteMyCommentMethod;
    if ((getDeleteMyCommentMethod = DiscussionGrpc.getDeleteMyCommentMethod) == null) {
      synchronized (DiscussionGrpc.class) {
        if ((getDeleteMyCommentMethod = DiscussionGrpc.getDeleteMyCommentMethod) == null) {
          DiscussionGrpc.getDeleteMyCommentMethod = getDeleteMyCommentMethod =
              io.grpc.MethodDescriptor.<rs.raf.grpc.CommentDeleteRequest, rs.raf.grpc.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteMyComment"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.grpc.CommentDeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.grpc.Response.getDefaultInstance()))
              .setSchemaDescriptor(new DiscussionMethodDescriptorSupplier("DeleteMyComment"))
              .build();
        }
      }
    }
    return getDeleteMyCommentMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.raf.grpc.TopicsRequest,
      rs.raf.grpc.TopicsResponse> getGetTopicsListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTopicsList",
      requestType = rs.raf.grpc.TopicsRequest.class,
      responseType = rs.raf.grpc.TopicsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.grpc.TopicsRequest,
      rs.raf.grpc.TopicsResponse> getGetTopicsListMethod() {
    io.grpc.MethodDescriptor<rs.raf.grpc.TopicsRequest, rs.raf.grpc.TopicsResponse> getGetTopicsListMethod;
    if ((getGetTopicsListMethod = DiscussionGrpc.getGetTopicsListMethod) == null) {
      synchronized (DiscussionGrpc.class) {
        if ((getGetTopicsListMethod = DiscussionGrpc.getGetTopicsListMethod) == null) {
          DiscussionGrpc.getGetTopicsListMethod = getGetTopicsListMethod =
              io.grpc.MethodDescriptor.<rs.raf.grpc.TopicsRequest, rs.raf.grpc.TopicsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTopicsList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.grpc.TopicsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.grpc.TopicsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DiscussionMethodDescriptorSupplier("GetTopicsList"))
              .build();
        }
      }
    }
    return getGetTopicsListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.raf.grpc.TopicCommentsRequest,
      rs.raf.grpc.TopicCommentsResponse> getGetTopicCommentsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTopicComments",
      requestType = rs.raf.grpc.TopicCommentsRequest.class,
      responseType = rs.raf.grpc.TopicCommentsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.raf.grpc.TopicCommentsRequest,
      rs.raf.grpc.TopicCommentsResponse> getGetTopicCommentsMethod() {
    io.grpc.MethodDescriptor<rs.raf.grpc.TopicCommentsRequest, rs.raf.grpc.TopicCommentsResponse> getGetTopicCommentsMethod;
    if ((getGetTopicCommentsMethod = DiscussionGrpc.getGetTopicCommentsMethod) == null) {
      synchronized (DiscussionGrpc.class) {
        if ((getGetTopicCommentsMethod = DiscussionGrpc.getGetTopicCommentsMethod) == null) {
          DiscussionGrpc.getGetTopicCommentsMethod = getGetTopicCommentsMethod =
              io.grpc.MethodDescriptor.<rs.raf.grpc.TopicCommentsRequest, rs.raf.grpc.TopicCommentsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTopicComments"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.grpc.TopicCommentsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.raf.grpc.TopicCommentsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DiscussionMethodDescriptorSupplier("GetTopicComments"))
              .build();
        }
      }
    }
    return getGetTopicCommentsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DiscussionStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DiscussionStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DiscussionStub>() {
        @java.lang.Override
        public DiscussionStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DiscussionStub(channel, callOptions);
        }
      };
    return DiscussionStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DiscussionBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DiscussionBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DiscussionBlockingStub>() {
        @java.lang.Override
        public DiscussionBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DiscussionBlockingStub(channel, callOptions);
        }
      };
    return DiscussionBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DiscussionFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DiscussionFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DiscussionFutureStub>() {
        @java.lang.Override
        public DiscussionFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DiscussionFutureStub(channel, callOptions);
        }
      };
    return DiscussionFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class DiscussionImplBase implements io.grpc.BindableService {

    /**
     */
    public void sendNewTopic(rs.raf.grpc.NewTopicRequest request,
        io.grpc.stub.StreamObserver<rs.raf.grpc.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendNewTopicMethod(), responseObserver);
    }

    /**
     */
    public void sendNewCommentToTopic(rs.raf.grpc.NewCommentRequest request,
        io.grpc.stub.StreamObserver<rs.raf.grpc.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendNewCommentToTopicMethod(), responseObserver);
    }

    /**
     */
    public void replyToComment(rs.raf.grpc.CommentReplyRequest request,
        io.grpc.stub.StreamObserver<rs.raf.grpc.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReplyToCommentMethod(), responseObserver);
    }

    /**
     */
    public void updateMyComment(rs.raf.grpc.CommentUpdateRequest request,
        io.grpc.stub.StreamObserver<rs.raf.grpc.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateMyCommentMethod(), responseObserver);
    }

    /**
     */
    public void deleteMyComment(rs.raf.grpc.CommentDeleteRequest request,
        io.grpc.stub.StreamObserver<rs.raf.grpc.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteMyCommentMethod(), responseObserver);
    }

    /**
     */
    public void getTopicsList(rs.raf.grpc.TopicsRequest request,
        io.grpc.stub.StreamObserver<rs.raf.grpc.TopicsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTopicsListMethod(), responseObserver);
    }

    /**
     */
    public void getTopicComments(rs.raf.grpc.TopicCommentsRequest request,
        io.grpc.stub.StreamObserver<rs.raf.grpc.TopicCommentsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTopicCommentsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSendNewTopicMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                rs.raf.grpc.NewTopicRequest,
                rs.raf.grpc.Response>(
                  this, METHODID_SEND_NEW_TOPIC)))
          .addMethod(
            getSendNewCommentToTopicMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                rs.raf.grpc.NewCommentRequest,
                rs.raf.grpc.Response>(
                  this, METHODID_SEND_NEW_COMMENT_TO_TOPIC)))
          .addMethod(
            getReplyToCommentMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                rs.raf.grpc.CommentReplyRequest,
                rs.raf.grpc.Response>(
                  this, METHODID_REPLY_TO_COMMENT)))
          .addMethod(
            getUpdateMyCommentMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                rs.raf.grpc.CommentUpdateRequest,
                rs.raf.grpc.Response>(
                  this, METHODID_UPDATE_MY_COMMENT)))
          .addMethod(
            getDeleteMyCommentMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                rs.raf.grpc.CommentDeleteRequest,
                rs.raf.grpc.Response>(
                  this, METHODID_DELETE_MY_COMMENT)))
          .addMethod(
            getGetTopicsListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                rs.raf.grpc.TopicsRequest,
                rs.raf.grpc.TopicsResponse>(
                  this, METHODID_GET_TOPICS_LIST)))
          .addMethod(
            getGetTopicCommentsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                rs.raf.grpc.TopicCommentsRequest,
                rs.raf.grpc.TopicCommentsResponse>(
                  this, METHODID_GET_TOPIC_COMMENTS)))
          .build();
    }
  }

  /**
   */
  public static final class DiscussionStub extends io.grpc.stub.AbstractAsyncStub<DiscussionStub> {
    private DiscussionStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DiscussionStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DiscussionStub(channel, callOptions);
    }

    /**
     */
    public void sendNewTopic(rs.raf.grpc.NewTopicRequest request,
        io.grpc.stub.StreamObserver<rs.raf.grpc.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendNewTopicMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendNewCommentToTopic(rs.raf.grpc.NewCommentRequest request,
        io.grpc.stub.StreamObserver<rs.raf.grpc.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendNewCommentToTopicMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void replyToComment(rs.raf.grpc.CommentReplyRequest request,
        io.grpc.stub.StreamObserver<rs.raf.grpc.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReplyToCommentMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateMyComment(rs.raf.grpc.CommentUpdateRequest request,
        io.grpc.stub.StreamObserver<rs.raf.grpc.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateMyCommentMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteMyComment(rs.raf.grpc.CommentDeleteRequest request,
        io.grpc.stub.StreamObserver<rs.raf.grpc.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteMyCommentMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTopicsList(rs.raf.grpc.TopicsRequest request,
        io.grpc.stub.StreamObserver<rs.raf.grpc.TopicsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTopicsListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTopicComments(rs.raf.grpc.TopicCommentsRequest request,
        io.grpc.stub.StreamObserver<rs.raf.grpc.TopicCommentsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTopicCommentsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DiscussionBlockingStub extends io.grpc.stub.AbstractBlockingStub<DiscussionBlockingStub> {
    private DiscussionBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DiscussionBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DiscussionBlockingStub(channel, callOptions);
    }

    /**
     */
    public rs.raf.grpc.Response sendNewTopic(rs.raf.grpc.NewTopicRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendNewTopicMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.raf.grpc.Response sendNewCommentToTopic(rs.raf.grpc.NewCommentRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendNewCommentToTopicMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.raf.grpc.Response replyToComment(rs.raf.grpc.CommentReplyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReplyToCommentMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.raf.grpc.Response updateMyComment(rs.raf.grpc.CommentUpdateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateMyCommentMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.raf.grpc.Response deleteMyComment(rs.raf.grpc.CommentDeleteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteMyCommentMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.raf.grpc.TopicsResponse getTopicsList(rs.raf.grpc.TopicsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTopicsListMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.raf.grpc.TopicCommentsResponse getTopicComments(rs.raf.grpc.TopicCommentsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTopicCommentsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DiscussionFutureStub extends io.grpc.stub.AbstractFutureStub<DiscussionFutureStub> {
    private DiscussionFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DiscussionFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DiscussionFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.grpc.Response> sendNewTopic(
        rs.raf.grpc.NewTopicRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendNewTopicMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.grpc.Response> sendNewCommentToTopic(
        rs.raf.grpc.NewCommentRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendNewCommentToTopicMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.grpc.Response> replyToComment(
        rs.raf.grpc.CommentReplyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReplyToCommentMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.grpc.Response> updateMyComment(
        rs.raf.grpc.CommentUpdateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateMyCommentMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.grpc.Response> deleteMyComment(
        rs.raf.grpc.CommentDeleteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteMyCommentMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.grpc.TopicsResponse> getTopicsList(
        rs.raf.grpc.TopicsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTopicsListMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.raf.grpc.TopicCommentsResponse> getTopicComments(
        rs.raf.grpc.TopicCommentsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTopicCommentsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_NEW_TOPIC = 0;
  private static final int METHODID_SEND_NEW_COMMENT_TO_TOPIC = 1;
  private static final int METHODID_REPLY_TO_COMMENT = 2;
  private static final int METHODID_UPDATE_MY_COMMENT = 3;
  private static final int METHODID_DELETE_MY_COMMENT = 4;
  private static final int METHODID_GET_TOPICS_LIST = 5;
  private static final int METHODID_GET_TOPIC_COMMENTS = 6;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DiscussionImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DiscussionImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_NEW_TOPIC:
          serviceImpl.sendNewTopic((rs.raf.grpc.NewTopicRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.grpc.Response>) responseObserver);
          break;
        case METHODID_SEND_NEW_COMMENT_TO_TOPIC:
          serviceImpl.sendNewCommentToTopic((rs.raf.grpc.NewCommentRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.grpc.Response>) responseObserver);
          break;
        case METHODID_REPLY_TO_COMMENT:
          serviceImpl.replyToComment((rs.raf.grpc.CommentReplyRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.grpc.Response>) responseObserver);
          break;
        case METHODID_UPDATE_MY_COMMENT:
          serviceImpl.updateMyComment((rs.raf.grpc.CommentUpdateRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.grpc.Response>) responseObserver);
          break;
        case METHODID_DELETE_MY_COMMENT:
          serviceImpl.deleteMyComment((rs.raf.grpc.CommentDeleteRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.grpc.Response>) responseObserver);
          break;
        case METHODID_GET_TOPICS_LIST:
          serviceImpl.getTopicsList((rs.raf.grpc.TopicsRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.grpc.TopicsResponse>) responseObserver);
          break;
        case METHODID_GET_TOPIC_COMMENTS:
          serviceImpl.getTopicComments((rs.raf.grpc.TopicCommentsRequest) request,
              (io.grpc.stub.StreamObserver<rs.raf.grpc.TopicCommentsResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class DiscussionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DiscussionBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return rs.raf.grpc.DiscussionServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Discussion");
    }
  }

  private static final class DiscussionFileDescriptorSupplier
      extends DiscussionBaseDescriptorSupplier {
    DiscussionFileDescriptorSupplier() {}
  }

  private static final class DiscussionMethodDescriptorSupplier
      extends DiscussionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DiscussionMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DiscussionGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DiscussionFileDescriptorSupplier())
              .addMethod(getSendNewTopicMethod())
              .addMethod(getSendNewCommentToTopicMethod())
              .addMethod(getReplyToCommentMethod())
              .addMethod(getUpdateMyCommentMethod())
              .addMethod(getDeleteMyCommentMethod())
              .addMethod(getGetTopicsListMethod())
              .addMethod(getGetTopicCommentsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
